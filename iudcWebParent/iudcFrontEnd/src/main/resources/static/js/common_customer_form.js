var dropDownCountry;
var dataListState;
var fieldState;

$(document).ready(function() {
	dropDownCountry = $("#country");
	dataListState = $("#listStates");
	fieldState = $("#state");
	
	dropDownCountry.on("change", function() {
		loadStatesForCountry();
		fieldState.val("").focus();
	});
});

function loadStatesForCountry() {
	selectedCountry = $("#country option:selected");
	countryId = selectedCountry.val();
	url = contextPath + "settings/list_states_by_country/" + countryId;
	
	$.get(url, function(responseJSON) {
		dataListState.empty();
		
		$.each(responseJSON, function(index, state) {
			$("<option>").val(state.name).text(state.name).appendTo(dataListState);
		});
		
	}).fail(function() {
		alert('fallo la conexión al servidor!');
	});
}

function checkPasswordMatch(confirmPassword) {
	if (confirmPassword.value != $("#password").val()) {
		confirmPassword.setCustomValidity("Las contraseñas no coinciden.");
	} else {
		confirmPassword.setCustomValidity("");
	}
}

function showModalDialog(title, message) {
	$("#modalTitle").text(title);
	$("#modalBody").text(message);
	$("#modalDialog").modal();
}

function showErrorModal(message) {
	showModalDialog("Error", message);
}

function showWarningModal(message) {
	showModalDialog("Advertencia", message);
}	