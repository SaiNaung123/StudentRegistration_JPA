package student.repository;

import java.util.List;

import student.model.*;

public interface ReviewDaoInterface {
	
	public int save(Review review);
	
	public List<Review> findAll();

}
