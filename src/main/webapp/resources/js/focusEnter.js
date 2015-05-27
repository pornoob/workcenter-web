$(document).keypress(function(event){
 
	var keycode = (event.keyCode ? event.keyCode : event.which);
	if(keycode == '13'){
		var c = $('#conductores');
		c.focus();
	}
 
});

function valida(e){
    tecla = (document.all) ? e.keyCode : e.which;

    //Tecla de retroceso para borrar, siempre la permite
    if (tecla==8 || tecla==9 || tecla==13){
        return true;
    }
        
    // Patron de entrada, en este caso solo acepta numeros
    patron =/[0-9]/;
    tecla_final = String.fromCharCode(tecla);
    return (patron.test(tecla_final) || tecla == 9);
}

function reemplazar(e){
	var cadena = $(e).val();
	cadena = cadena.replace(/[^\d]/,"");
	$(e).val(cadena);	
}


