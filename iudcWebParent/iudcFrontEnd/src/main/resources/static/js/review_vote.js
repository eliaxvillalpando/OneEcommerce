$(document).ready(function() {
	$(".linkVoteReview").on("click", function(e) {
		e.preventDefault();
		voteReview($(this));
	});
});

function voteReview(currentLink) {
	requestURL = currentLink.attr("href");

	$.ajax({
		type: "POST",
		url: requestURL,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		}
	}).done(function(voteResult) {
		console.log(voteResult);
		
		if (voteResult.successful) {
			$("#modalDialog").on("hide.bs.modal", function(e) {
				updateVoteCountAndIcons(currentLink, voteResult);
			});
		}
		
		showModalDialog("Votos de la reseña", voteResult.message);
		
	}).fail(function() {
		showErrorModal("Error al votar la reseña. ");
	});	
}

function updateVoteCountAndIcons(currentLink, voteResult) {
	reviewId = currentLink.attr("reviewId");
	voteUpLink = $("#linkVoteUp-" + reviewId);
	voteDownLink = $("#linkVoteDown-" + reviewId);
	
	$("#voteCount-" + reviewId).text(voteResult.voteCount + " Votos");
	
	message = voteResult.message;
	
	if (message.includes("successfully voted up")) {
		highlightVoteUpIcon(currentLink, voteDownLink);
	} else if (message.includes("successfully voted down")) {
		highlightVoteDownIcon(currentLink, voteUpLink);
	} else if (message.includes("unvoted down")) {
		unhighlightVoteDownIcon(voteDownLink);
	} else if (message.includes("unvoted up")) {
		unhighlightVoteDownIcon(voteUpLink);
	}
}

function highlightVoteUpIcon(voteUpLink, voteDownLink) {
	voteUpLink.removeClass("far").addClass("fas");
	voteUpLink.attr("title", "Quitar voto positivo de esta reseña");
	voteDownLink.removeClass("fas").addClass("far");
}

function highlightVoteDownIcon(voteDownLink, voteUpLink) {
	voteDownLink.removeClass("far").addClass("fas");
	voteDownLink.attr("title", "Quitar voto negativo de esta reseña");
	voteUpLink.removeClass("fas").addClass("far");
}

function unhighlightVoteDownIcon(voteDownLink) {
	voteDownLink.attr("title", "Votar negativo a esta resesña");
	voteDownLink.removeClass("fas").addClass("far");	
}

function unhighlightVoteUpIcon(voteUpLink) {
	voteUpLink.attr("title", "Votar positivo a esta reseña");
	voteUpLink.removeClass("fas").addClass("far");	
}