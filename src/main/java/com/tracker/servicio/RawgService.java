package com.tracker.servicio;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tracker.modelo.Juego;
import com.tracker.modelo.Genero;
import com.tracker.modelo.Plataforma;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


//servicio para consumir la API de RAWG
//La API_KEY se lee desde la variable de entorno RAWG_API_KEY por ahora subo a git sin
//api key, en local la estoy colocando en el tomcat

public class RawgService {

    private static final String BASE_URL = "https://api.rawg.io/api";
    private final String apiKey;
    private final HttpClient httpClient;

    public RawgService() {
        this.apiKey = System.getenv("RAWG_API_KEY");
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }


    //busca juegos en la api por query de texto
    //retorna lista vacia si la api falla
    public List<Juego> buscarJuegos(String query) {
        List<Juego> juegos = new ArrayList<>();
        try {
            String encoded = URLEncoder.encode(query, StandardCharsets.UTF_8);
            String url = BASE_URL + "/games?key=" + apiKey + "&search=" + encoded + "&page_size=20";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(15))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
                JsonArray results = json.getAsJsonArray("results");
                if (results != null) {
                    for (JsonElement elem : results) {
                        juegos.add(parsearJuego(elem.getAsJsonObject()));
                    }
                }
            }
        } catch (Exception e) {
            //si la API falla, retornar lista vacia
            System.err.println("Error al buscar juegos en RAWG: " + e.getMessage());
        }
        return juegos;
    }


     //obtiene el detalle de un juego por su api id
     //retorna null si la api falla.
    public Juego obtenerDetalle(String apiId) {
        try {
            String url = BASE_URL + "/games/" + apiId + "?key=" + apiKey;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(15))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
                return parsearJuegoDetalle(json);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener detalle de juego en RAWG: " + e.getMessage());
        }
        return null;
    }


     //parsea un juego desde la respuesta de búsqueda.
    private Juego parsearJuego(JsonObject obj) {
        Juego juego = new Juego();
        juego.setApiId(String.valueOf(obj.get("id").getAsInt()));
        juego.setNombre(getStringSeguro(obj, "name"));
        juego.setImgUrl(getStringSeguro(obj, "background_image"));

        if (obj.has("metacritic") && !obj.get("metacritic").isJsonNull()) {
            juego.setMetacritic(obj.get("metacritic").getAsDouble());
        }

        String released = getStringSeguro(obj, "released");
        if (released != null && !released.isEmpty()) {
            try {
                juego.setFechaLanzamiento(Date.valueOf(released));
            } catch (IllegalArgumentException ignored) {
            }
        }

        //parsear generos
        if (obj.has("genres") && obj.get("genres").isJsonArray()) {
            for (JsonElement ge : obj.getAsJsonArray("genres")) {
                JsonObject go = ge.getAsJsonObject();
                Genero g = new Genero();
                g.setNombre(getStringSeguro(go, "name"));
                juego.getGeneros().add(g);
            }
        }

        //parsear plataformas
        if (obj.has("platforms") && obj.get("platforms").isJsonArray()) {
            for (JsonElement pe : obj.getAsJsonArray("platforms")) {
                JsonObject po = pe.getAsJsonObject();
                if (po.has("platform") && !po.get("platform").isJsonNull()) {
                    JsonObject platObj = po.getAsJsonObject("platform");
                    Plataforma p = new Plataforma();
                    p.setNombre(getStringSeguro(platObj, "name"));
                    juego.getPlataformas().add(p);
                }
            }
        }

        return juego;
    }

    //parea un juego desde la respuesta de detalle (tiene mas campos)
    private Juego parsearJuegoDetalle(JsonObject obj) {
        return parsearJuego(obj); //el formato es compatible
    }

    private String getStringSeguro(JsonObject obj, String campo) {
        if (obj.has(campo) && !obj.get(campo).isJsonNull()) {
            return obj.get(campo).getAsString();
        }
        return null;
    }
}
