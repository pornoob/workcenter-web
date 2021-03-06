var WebCenter = {
    Init: function() {
        WebCenter.Form.initHelper();
    },
    Key: {
        LEFT_ARROW: 37,
        RIGHT_ARROR: 39,
        BACKSPACE: 8,
        DELETE: 46,
        SHIFT: 16,
        HOME: 36,
        ENTER: 13
    },
    Form: {
        initHelper: function() {
            WebCenter.Form.disableSubmitWithEnterKey();
        },
        disableSubmitWithEnterKey: function () {
            $('form').off('keypress.disableAutoSubmitOnEnter').on('keypress.disableAutoSubmitOnEnter', function (event) {
                var target = $(event.target);
                if (event.which === $.ui.keyCode.ENTER && target.is(':input:not(textarea,:button,:submit,:reset)')) {
                    event.preventDefault();
                }
            });
        },
    },
    Input: {
        formatearRut: function(event) {
            var key = event.keyCode || event.which;
            if (key === WebCenter.Key.LEFT_ARROW || key === WebCenter.Key.RIGHT_ARROR ||
                    key === WebCenter.Key.BACKSPACE || key === WebCenter.Key.DELETE ||
                    key === WebCenter.Key.HOME) {
                return;
            }
            
            var valor = $(this).val();
            if (valor.length > 1) {
                valor = valor.replace(/[^0-9]/g, "");
                valor = valor.substr(0, valor.length - 1) + "-" + valor[valor.length - 1];
                $(this).val(valor);
            }
        }
    },
    Menu: {
        mouseclick: function (url) {
            location.href = url;
        }
    },
    ColumnToggler: {
        fix: function (widget) {
            $(PF(widget).jqId).find("ul li div div.ui-chkbox-box").each(function() {
                var chkbox = $(this);
                if(chkbox.hasClass('ui-state-active')) {
                    PF(widget).check(chkbox);
                }
                else {
                    PF(widget).uncheck(chkbox);
                }
            });
        }
    },
    DataTable: {
        keyUpFilter: function (widget) {
            if ((event.keyCode || event.which) === WebCenter.Key.ENTER) {
                PF(widget).filter();
            }
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
            if (event.which === WebCenter.Key.ENTER) {
                event.preventDefault();
                return false;
            }
        },
        inputKeyUp: function (event) {
            if (event.which === WebCenter.Key.ENTER) {
                event.preventDefault();
                PF(this.componente).filter();
            }
        },
        inputKeyPress: function (event) {
            if (event.which === WebCenter.Key.ENTER) {
                event.preventDefault();
                return false;
            }
        }
    }
};

PrimeFaces.locales['es'] = {
    closeText: 'Cerrar',
    prevText: 'Anterior',
    nextText: 'Siguiente',
    monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
    monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
    dayNames: ['Domingo', 'Lunes', 'Martes', 'Mi??rcoles', 'Jueves', 'Viernes', 'S??bado'],
    dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab'],
    dayNamesMin: ['D', 'L', 'M', 'M', 'J', 'V', 'S'],
    weekHeader: 'Semana',
    firstDay: 1,
    isRTL: false,
    showMonthAfterYear: false,
    yearSuffix: '',
    timeOnlyTitle: 'S??lo hora',
    timeText: 'Tiempo',
    hourText: 'Hora',
    minuteText: 'Minuto',
    secondText: 'Segundo',
    currentText: 'Fecha actual',
    ampm: false,
    month: 'Mes',
    week: 'Semana',
    day: 'D??a',
    allDayText: 'Todo el d??a'
};