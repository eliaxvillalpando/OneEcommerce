$(document).ready(function() {
	$("#productList").on("click", ".linkRemove", function(e) {
		e.preventDefault();
		
		if (doesOrderHaveOnlyOneProduct()) {
			showWarningModal("No fue posible remover el producto. La orden debe tener al menos un producto.");
		} else {
			removeProduct($(this));		
			updateOrderAmounts();	
		}
	});
});

function removeProduct(link) {
	rowNumber = link.attr("rowNumber");
	$("#row" + rowNumber).remove();
	$("#blankLine" + rowNumber).remove();
	
	$(".divCount").each(function(index, element) {
		element.innerHTML = "" + (index + 1);
	});
}

function doesOrderHaveOnlyOneProduct() {
	productCount = $(".hiddenProductId").length; //hiddenProductId es un dato que trae de order_form_products
	return productCount == 1;
}