$(document).ready(function() {
	$("#buttonAdd2Cart").on("click", function(evt) {
		addToCart();
	});
});

function addToCart() {
	quantity = $("#quantity" + productId).val();
	url = contextPath + "cart/add/" + productId + "/" + quantity;
	
	$.ajax({
		type: "POST",
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		}
	}).done(function(response) {
		showModalDialog("Carrito de compras", response);
	}).fail(function() {
		showErrorModal("Error al añadir el producto al carrito.");
	});
}