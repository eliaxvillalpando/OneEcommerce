package com.iudc.review.vote;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iudc.ControllerHelper;
import com.iudc.common.entity.Customer;

@RestController
public class VoteReviewRestController {

	@Autowired private ReviewVoteService service;
	@Autowired private ControllerHelper helper;
	
	@PostMapping("/vote_review/{id}/{type}")
	public VoteResult voteReview(@PathVariable(name = "id") Integer reviewId,
			@PathVariable(name = "type") String type,
			HttpServletRequest request) {
		
		Customer customer = helper.getAuthenticatedCustomer(request);
		
		if (customer == null) {
			return VoteResult.fail("Debes iniciar sesión para votar la reseña.");
		}
		
		VoteType voteType = VoteType.valueOf(type.toUpperCase());
		return service.doVote(reviewId, customer, voteType);
	}
}
