package student.controller;

import javax.servlet.http.HttpSession;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import student.repository.*;
import student.dto.*;
import student.model.*;

@Controller
public class ReviewController {
	
	@Autowired
    private ReviewDao dao;

    @Autowired
    private ModelMapper modelMapper;
	
	@GetMapping("/submitReview")
	public String submitReview() {
	    return "addRating";
	}
	

    @PostMapping("/submitReview")
    public String submitReview(@ModelAttribute ReviewDto reviewDto, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        // Convert ReviewDto to Review entity
    	if(reviewDto==null) {
    		model.addAttribute("error","Please fill in all infomation");
    		return "redirect:/submitReview";
    	}else {
        Review review = modelMapper.map(reviewDto, Review.class);

        // Save the review to the database
        model.addAttribute("success","Thanks for your feedback!");
        dao.save(review);

        // Redirect to the home page and show feedbacks
//        redirectAttributes.addFlashAttribute("showFeedBacks", true);
        return "redirect:/submitReview";
    	}
    }
    
    @GetMapping("/reviews")
    public String showReviews(Model model, HttpSession session) {
        List<Review> reviews = dao.findAll();
        model.addAttribute("reviews", reviews); // Add reviews to the model
        return "reviewsView"; // Assuming this is the correct view path
    }




}
