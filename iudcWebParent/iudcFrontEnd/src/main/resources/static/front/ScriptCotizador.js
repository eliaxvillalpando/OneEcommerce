function test(){
    
    var validColores = document.getElementById("acabado");
    var value = validColores.options[validColores.selectedIndex].value;
    //var tipoAcabado = document.querySelector('input[name="acabado"]:checked').value;
    //var validAcabado = document.getElementById("acabado").value;
    //var tipoAcabado = validAcabado.options[validAcabado.selectedIndex].value;
    
    alert(value);
    
    
    //alert(validAcabado);
    
}



function validateForm() {



    var validColores = document.getElementById("coloresEtiqueta");
    var value = validColores.options[validColores.selectedIndex].value;
    //var tipoAcabado = document.querySelector('input[name="acabado"]:checked').value;
    var validAcabado = document.getElementById("acabado");
    var tipoAcabado = validAcabado.options[validAcabado.selectedIndex].value;
    
    alert(tipoAcabado);
    alert(value);


    if (value === "8" && tipoAcabado === "barnizMate" || value === "8" && tipoAcabado === "barnizBrillante") {

        alert("La etiqueta no puede tener 8 colores y barniz, deben ser 7 maximo y barniz");
        validColores = null;
        value = null;
        tipoAcabado = null;

        return false;
    } else if (value === "seleccionYCuatro" && tipoAcabado === "barnizMate" || value === "seleccionYCuatro" && tipoAcabado === "barnizBrillante") {

        alert("La etiqueta no puede tener selección de color + 4 colores + barniz, debe ser selección de colores + 3 colores + barniz");
        validColores = null;
        value = null;
        tipoAcabado = null;
        return false;
    } else {

        return true;

    }

    if (document.getElementById('laminadoStd').selected || document.getElementById('laminadoMate').selected || document.getElementById('laminadoBrillante').selected) {

        document.getElementById("conBarniz").selected = true;
        alert("Al seleccionar cualquier tipo de laminado no es posible agregar barniz a tu etiqueta");
        return false;

    } else if (document.getElementById('sinLaminado').selected) {

        document.getElementById("conBarniz").disabled = false;
        return true;
    }




}

function check() {

    if (document.getElementById('laminadoStd').checked ||
            document.getElementById('laminadoMate').checked ||
            document.getElementById('laminadoBrillante').checked ||
            document.getElementById('sinLaminado').checked) {

        if (document.getElementById('sinLaminado').checked) {
            document.getElementById('sinBarniz').checked =  false;
            document.getElementById("barnizMate").disabled = false;
            document.getElementById("barnizBrillante").disabled = false;
            return true;
        }
        
        document.getElementById('sinBarniz').checked =  true;
        document.getElementById("barnizMate").disabled = true;
        document.getElementById("barnizBrillante").disabled = true;
        alert("Al seleccionar cualquier tipo de laminado no es posible agregar barniz a tu etiqueta");

        return false;

    } else if (document.getElementById('sinLaminado').checked) {

        document.getElementById("conBarniz").disabled = false;
        return true;
    }


}


function checkFormaEtiqueta() {
    
    var formaCuadradaCaptura = "<div class='my-4'> <div class='row' id='capturaFormaRegular'>  \n\
                                   <div class='text-center my-4'>\n\
                     <h4>Lados: <input type='number' name='ladoCuadrado' id='ladoCuadrado' min='.5' max='16' step='.01' required></h4>\n\
                        </div></div></div>";
    


    var formaCircularCaptura = "<div class='my-4'> <div class='row' id='capturaFormaRegular'>\n\
                                   <div class='text-center my-4'>\n\
                     <h4>Diametro: <input type='number' name='diametroCirculo' id='diametroCirculo' min='.5' max='16' step='.01' required></h4></div></div>";




    var radios = document.getElementsByName('tipoForma');
    var radio;
    var capturaCuadrado = document.getElementById("capturaFormaCuadrada");
    var capturaCirulo = document.getElementById("capturaFormaCirculo");
    var capturaEsquinas = document.getElementById("capturaEsquinas");
    
    for (radio of radios) {

        if (radio.checked) {
            if (radio.value === "cuadrado") {


                $("#capturaFormaCuadrada").show();
                $("#tituloMedidas").show();
                $("#estiloMedidas").hide();
                $("#capturaFormaCirculo").hide();
                $("#seccionEspacio").show();
                $("#capturaPleca").show();
                $("#capturaEsquinas").show();
                
                capturaCuadrado.innerHTML = formaCuadradaCaptura;
                


            } else if (radio.value === "circular") {
                
                $("#tituloMedidas").show();
                $("#estiloMedidas").hide();
                $("#capturaFormaCuadrada").hide();
                $("#capturaFormaCirculo").show();
                $("#capturaPleca").show();
                $("#capturaEsquinas").hide();
                $("#seccionEspacio").show();
                capturaCirulo.innerHTML = formaCircularCaptura;



            } else if (radio.value === "rectangulo") {

                $("#tituloMedidas").show();
                $("#capturaFormaCirculo").hide();
                $("#capturaFormaCuadrada").hide();
                $("#estiloMedidas").show();
                $("#capturaEsquinas").show();
                $("#seccionEspacio").show();
                $("#capturaPleca").show();



            }else if (radio.value === "continua") {

                $("#tituloMedidas").show();
                $("#capturaFormaCirculo").hide();
                $("#capturaFormaCuadrada").hide();
                $("#estiloMedidas").show();
                
                $("#capturaPleca").hide();
                $("#seccionEspacio").hide();
                $("#seccionSalidaRollo").hide();
                $("#capturaEsquinas").hide();
                



            } 
            
            else if (radio.value === "ovalada") {

                $("#tituloMedidas").show();
                $("#capturaFormaCirculo").hide();
                $("#capturaFormaCuadrada").hide();
                $("#estiloMedidas").show();
                $("#capturaEsquinas").hide();
                $("#seccionEspacio").show();
                $("#capturaPleca").show();
                



            } 
            
            else {
                $("#tituloMedidas").hide();
                $("#capturaFormaCirculo").hide();
                $("#capturaFormaCuadrada").hide();
                $("#estiloMedidas").hide();
                $("#capturaEsquinas").hide();
                $("#seccionEspacio").show();
                $("#capturaPleca").show();
                
            }


        }

    }



}

window.onscroll = function() {myFunction();};

function myFunction() {
  var winScroll = document.body.scrollTop || document.documentElement.scrollTop;
  var height = document.documentElement.scrollHeight - document.documentElement.clientHeight;
  var scrolled = (winScroll / height) * 100;
  document.getElementById("myBar").style.width = scrolled + "%";
}

