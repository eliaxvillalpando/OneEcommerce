function clearFilter() {
	window.location = moduleURL;	
}

function showDeleteConfirmModal(link, entityName) {
	entityId = link.attr("entityId");
	
	$("#yesButton").attr("href", link.attr("href"));	
	$("#confirmText").text("Deseas eliminar este "
							 + entityName + " ID " + entityId + "?");
	$("#confirmModal").modal();	
}