var WebCenter = {
    Input: {
        formatearRut: function(obj) {
            var valor = $(obj).val();
            if (valor.length > 1) {
                valor = valor.replace(/[^0-9]/g, "");
                valor = valor.substr(0, valor.length - 1) + "-" + valor[valor.length - 1];
                $(obj).val(valor);
            }
        }
    },
    Menu: {
        mouseover: function (menu) {
            var img = menu.childNodes[0];
            var src = img.getAttribute('src');
            src = src.substring(0, src.lastIndexOf('.')) + '_hover' + src.substring(src.lastIndexOf('.'));
            img.setAttribute('src', src);
        },
        mouseout: function (menu) {
            var img = menu.childNodes[0];
            var src = img.getAttribute('src');
            src = src.substring(0, src.lastIndexOf('_')) + src.substring(src.lastIndexOf('.'));
            img.setAttribute('src', src);
        },
        mouseclick: function (url) {
            location.href = url;
        }
    },
    Filter: {
        componente: null,
        run: function (componente) {
            this.componente = componente;
            console.log(this.componente);
            $('.inputFilter').keydown(this.inputKeyDown.bind(this));
            $('.inputFilter').keyup(this.inputKeyUp.bind(this));
            $('.inputFilter').keypress(this.inputKeyPress.bind(this));
        },
        inputKeyDown: function (event) {
            if (event.which == 13) {
                event.preventDefault();
                return false;
            }
        },
        inputKeyUp: function (event) {
            if (event.which == 13) {
                event.preventDefault();
                PF(this.componente).filter();
            }
        },
        inputKeyPress: function (event) {
            if (event.which == 13) {
                event.preventDefault();
                return false;
            }
        }
    }
}

PrimeFaces.locales['es'] = {
    closeText: 'Cerrar',
    prevText: 'Anterior',
    nextText: 'Siguiente',
    monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
    monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
    dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
    dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab'],
    dayNamesMin: ['D', 'L', 'M', 'M', 'J', 'V', 'S'],
    weekHeader: 'Semana',
    firstDay: 1,
    isRTL: false,
    showMonthAfterYear: false,
    yearSuffix: '',
    timeOnlyTitle: 'Sólo hora',
    timeText: 'Tiempo',
    hourText: 'Hora',
    minuteText: 'Minuto',
    secondText: 'Segundo',
    currentText: 'Fecha actual',
    ampm: false,
    month: 'Mes',
    week: 'Semana',
    day: 'Día',
    allDayText: 'Todo el día'
};