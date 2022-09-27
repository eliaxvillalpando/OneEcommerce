package com.iudc.admin.review;

import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iudc.admin.paging.PagingAndSortingHelper;
import com.iudc.admin.product.ProductRepository;
import com.iudc.common.entity.Review;
import com.iudc.common.exception.ReviewNotFoundException;

@Service
@Transactional
public class ReviewService {
	public static final int REVIEWS_PER_PAGE = 5;
	
	@Autowired private ReviewRepository reviewRepo;
	@Autowired private ProductRepository productRepo;
	
	public void listByPage(int pageNum, PagingAndSortingHelper helper) {
		helper.listEntities(pageNum, REVIEWS_PER_PAGE, reviewRepo);
	}
	
	public Review get(Integer id) throws ReviewNotFoundException {
		try {
			return reviewRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new ReviewNotFoundException("No se encontraron reseñas con ID " + id);
		}
	}
	
	public void save(Review reviewInForm) {
		Review reviewInDB = reviewRepo.findById(reviewInForm.getId()).get();
		reviewInDB.setHeadline(reviewInForm.getHeadline());
		reviewInDB.setComment(reviewInForm.getComment());
		
		reviewRepo.save(reviewInDB);
		productRepo.updateReviewCountAndAverageRating(reviewInDB.getProduct().getId());
	}
	
	public void delete(Integer id) throws ReviewNotFoundException {
		if (!reviewRepo.existsById(id)) {
			throw new ReviewNotFoundException("No se encontraron reseñas con ID " + id);
		}
		
		reviewRepo.deleteById(id);
	}
}
