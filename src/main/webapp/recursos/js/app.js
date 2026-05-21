
//codigo realizado con apoyo de IA
//PlayLog — Client-side JavaScript


//Toggle Edit Panel
function toggleEdit(idUsuarioJuego) {
    const panel = document.getElementById('editPanel-' + idUsuarioJuego);
    if (panel) {
        panel.style.display = panel.style.display === 'none' ? 'block' : 'none';
    }
}

//roggle Add to Library Form
function toggleAddForm() {
    const panel = document.getElementById('addFormPanel');
    const toggleBtn = document.getElementById('btnToggleAdd');
    if (panel && toggleBtn) {
        const isHidden = panel.style.display === 'none';
        panel.style.display = isHidden ? 'block' : 'none';
        toggleBtn.style.display = isHidden ? 'none' : 'inline-flex';
        if (isHidden) {
            panel.style.animation = 'slideDown 0.3s ease';
            panel.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
        }
    }
}

//Validación dinamica: reseña y calificacion obligatorias
function actualizarRequisitos() {
    const estado = document.getElementById('estado');
    const cal = document.getElementById('calificacion');
    const resena = document.getElementById('resena');
    if (!estado || !cal || !resena) return;
    const requiere = estado.value !== 'QUIERO_JUGAR';
    cal.required = requiere;
    resena.required = requiere;
    //actualizar placeholders
    cal.placeholder = requiere ? 'Obligatorio' : 'Opcional';
    resena.placeholder = requiere
        ? 'Escribe tu opinión (obligatorio para este estado)...'
        : 'Escribe tu opinión sobre este juego (opcional)...';
    //actualizar labels visualmente
    const calLabel = cal.closest('.form-group')?.querySelector('label');
    const resLabel = resena.closest('.form-group')?.querySelector('label');
    if (calLabel) calLabel.innerHTML = requiere
        ? 'Calificación (1-10) <span class="required-mark">*</span>'
        : 'Calificación (1-10)';
    if (resLabel) resLabel.innerHTML = requiere
        ? '<i class="fas fa-pen"></i> Reseña <span class="required-mark">*</span>'
        : '<i class="fas fa-pen"></i> Reseña';
}

//Community Tabs
function showTab(tabName) {
    //desactivar todos los tabs
    document.querySelectorAll('.tab-content').forEach(tc => {
        tc.classList.remove('active');
    });
    document.querySelectorAll('.tab-btn').forEach(btn => {
        btn.classList.remove('active');
    });

    //activar el tab seleccionado
    const tabContent = document.getElementById('tab-' + tabName);
    if (tabContent) {
        tabContent.classList.add('active');
    }

    //activar el botón correspondiente
    const tabBtn = document.getElementById('tab' + capitalize(tabName));
    if (tabBtn) {
        tabBtn.classList.add('active');
    }
}

function capitalize(str) {
    return str.charAt(0).toUpperCase() + str.slice(1);
}

//smooth animations on scroll
document.addEventListener('DOMContentLoaded', function() {
    // Intersection Observer para animaciones al hacer scroll
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.style.opacity = '1';
                entry.target.style.transform = 'translateY(0)';
            }
        });
    }, {
        threshold: 0.1,
        rootMargin: '0px 0px -50px 0px'
    });

    //observar game cards
    document.querySelectorAll('.game-card, .library-item, .ranking-item').forEach(el => {
        el.style.opacity = '0';
        el.style.transform = 'translateY(20px)';
        el.style.transition = 'opacity 0.4s ease, transform 0.4s ease';
        observer.observe(el);
    });

    //observar stat cards con delay escalonado
    document.querySelectorAll('.stat-card').forEach((el, i) => {
        el.style.opacity = '0';
        el.style.transform = 'translateY(20px)';
        el.style.transition = `opacity 0.4s ease ${i * 0.1}s, transform 0.4s ease ${i * 0.1}s`;
        observer.observe(el);
    });

    //search form submit on Enter
    const searchInput = document.getElementById('searchInput');
    if (searchInput) {
        searchInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                this.closest('form').submit();
            }
        });
    }
    //validacion dinamica del formulario de agregar juego
    const estadoSelect = document.getElementById('estado');
    if (estadoSelect) {
        estadoSelect.addEventListener('change', actualizarRequisitos);
        actualizarRequisitos(); // Estado inicial
    }
});
