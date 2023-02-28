function validateForm() {
    const conDisenoInput = document.querySelector('input[name="tipoDiseno"][value="conDiseno"]');
    const sinDisenoInput = document.querySelector('input[name="tipoDiseno"][value="sinDiseno"]');

    if (conDisenoInput.checked) {
        //alert('Radio input with value "conDiseno" is selected.');

        const clienteDisenoInputs = document.querySelectorAll('input[name="clienteDiseno"]');
        const radioButtonsDiv = document.getElementById('radio-tipo-diseno');


        let isClienteDisenoSelected = false;

        // loop through the radio input options to check if one of them is selected
        for (let i = 0; i < clienteDisenoInputs.length; i++) {
            if (clienteDisenoInputs[i].checked) {
                isClienteDisenoSelected = true;
                break;
            }
        }

        if (!isClienteDisenoSelected) {
            event.preventDefault(); // prevent form submission if no option is selected
            alert('Selecciona una opción de diseño'); // show an error message
            radioButtonsDiv.scrollIntoView();
        }

    }

    const radioDivColores = document.getElementById('seccionElegirColores');

    if (sinDisenoInput.checked) {
        //alert('Radio input with value "conDiseno" is selected.');
        var selectElement = document.getElementById("coloresEtiqueta");
        var selectedValue = selectElement.value;

        if (selectedValue === "" || selectedValue === null) {
            
            alert("Selecciona un color");
            radioDivColores.scrollIntoView();
            return false;
        }

        return true;
    }


}

function check() {

    if (document.getElementById('laminadoStd').checked ||
            document.getElementById('laminadoMate').checked ||
            document.getElementById('laminadoBrillante').checked ||
            document.getElementById('sinLaminado').checked) {

        if (document.getElementById('sinLaminado').checked) {
            document.getElementById('sinBarniz').checked = false;
            document.getElementById("barnizMate").disabled = false;
            document.getElementById("barnizBrillante").disabled = false;
            return true;
        }

        document.getElementById('sinBarniz').checked = true;
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
                     <h4>Lados: <input type='number' name='ladoCuadrado' id='ladoCuadrado' min='.5' max='16' step='.01' required> cm</h4>\n\
                        </div></div></div>";


    var formaContinuaCaptura = "<div class='my-4'> <div class='row' id='capturaFormaContinua'>  \n\
                                   <div class='text-center my-4'>\n\
                     <h4>Ancho: <input type='number' name='anchoContinua' min='.5' max='16' step='.01' required> cm</h4>\n\
                    \n\<h4>Largo: <input type='number' name='largoContinua' min='20' max='1500' step='5' required> m</h4>\n\
                        </div></div></div>";



    var formaCircularCaptura = "<div class='my-4'> <div class='row' id='capturaFormaRegular'>\n\
                                   <div class='text-center my-4'>\n\
                     <h4>Diametro: <input type='number' name='diametroCirculo' id='diametroCirculo' min='.5' max='16' step='.01' required> cm</h4></div></div>";


    var formaOvaladaCaptura = "<div class='my-4'> <div class='row' id='capturaFormaOvalada'>  \n\
                                   <div class='text-center my-4'>\n\
                     <h4>Radio 1: <input type='number' name='radio1' min='.5' max='16' step='.01' required> cm</h4>\n\
                    \n\<h4>Radio 2: <input type='number' name='radio2' min='.5' max='16' step='.01' required> cm</h4>\n\
                        </div></div></div>";


    var radios = document.getElementsByName('tipoForma');
    var radio;
    var capturaCuadrado = document.getElementById("capturaFormaCuadrada");
    var capturaCirulo = document.getElementById("capturaFormaCirculo");
    var capturaEsquinas = document.getElementById("capturaEsquinas");
    var capturaOvalado = document.getElementById("capturaRadios");
    var capturaContinua = document.getElementById("capturaContinua");


    for (radio of radios) {

        if (radio.checked) {
            if (radio.value === "cuadrado") {


                $("#capturaFormaCuadrada").show();
                $("#tituloMedidas").show();
                $("#estiloMedidas").hide();
                $("#capturaFormaCirculo").hide();
                $("#seccionEspacio").show();
                $("#opcionFanFolding").show();
                $("#capturaPleca").show();
                $("#capturaFormaOvalada").hide();
                $("#capturaEsquinas").show();
                $("#capturaFormaContinua").hide();
                capturaCuadrado.innerHTML = formaCuadradaCaptura;



            } else if (radio.value === "circular") {

                $("#tituloMedidas").show();
                $("#estiloMedidas").hide();
                $("#capturaFormaOvalada").hide();
                $("#capturaFormaCuadrada").hide();
                $("#opcionFanFolding").show();
                $("#capturaFormaCirculo").show();
                $("#capturaPleca").show();
                $("#capturaEsquinas").hide();
                $("#capturaRadios").hide();
                $("#seccionEspacio").show();
                $("#capturaFormaContinua").hide();
                capturaCirulo.innerHTML = formaCircularCaptura;



            } else if (radio.value === "rectangulo") {

                $("#tituloMedidas").show();
                $("#capturaFormaCirculo").hide();
                $("#capturaFormaCuadrada").hide();
                $("#capturaFormaOvalada").hide();
                $("#estiloMedidas").show();
                $("#capturaEsquinas").show();
                $("#seccionEspacio").show();
                $("#opcionFanFolding").show();
                $("#capturaFormaContinua").hide();
                $("#capturaPleca").show();
                $("#capturaRadios").hide();


            } else if (radio.value === "continua") {

                $("#tituloMedidas").show();
                $("#capturaFormaCirculo").hide();
                $("#capturaFormaCuadrada").hide();
                $("#estiloMedidas").hide();
                $("#opcionFanFolding").hide();
                $("#opcionPosteta").hide();
                $("#capturaFormaOvalada").hide();
                $("#capturaPleca").hide();
                $("#seccionEspacio").hide();
                $("#capturaFormaContinua").show();
                $("#seccionSalidaRollo").hide();
                $("#capturaEsquinas").hide();
                $("#capturaRadios").hide();

                capturaContinua.innerHTML = formaContinuaCaptura;



            } else if (radio.value === "ovalada") {

                $("#tituloMedidas").show();
                $("#capturaFormaCirculo").hide();
                $("#capturaFormaCuadrada").hide();
                $("#opcionFanFolding").show();
                $("#estiloMedidas").hide();
                $("#capturaEsquinas").hide();
                $("#seccionEspacio").hide();
                $("#capturaPleca").hide();
                $("#formaContinuaCaptura").hide();
                $("#capturaRadios").show();

                capturaOvalado.innerHTML = formaOvaladaCaptura;


            } else {
                $("#capturaRadios").hide();
                $("#tituloMedidas").hide();
                $("#opcionFanFolding").show();
                $("#capturaFormaCirculo").hide();
                $("#capturaFormaOvalada").hide();
                $("#capturaFormaCuadrada").hide();
                $("#estiloMedidas").hide();
                $("#capturaFormaContinua").hide();
                $("#capturaEsquinas").hide();
                $("#seccionEspacio").show();
                $("#capturaPleca").show();


            }


        }

    }



}

window.onscroll = function () {
    myFunction();
};

function myFunction() {
    var winScroll = document.body.scrollTop || document.documentElement.scrollTop;
    var height = document.documentElement.scrollHeight - document.documentElement.clientHeight;
    var scrolled = (winScroll / height) * 100;
    document.getElementById("myBar").style.width = scrolled + "%";
}

