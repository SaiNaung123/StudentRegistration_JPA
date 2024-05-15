package student.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="review")
public class Review implements Serializable {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private long id;
//	    private String comment,rating,date;
	private int rating;
	private String reviewV;
	
	   
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public int getRating() {
			return rating;
		}
		public void setRating(int rating) {
			this.rating = rating;
		}
		public String getReviewV() {
			return reviewV;
		}
		public void setReviewV(String reviewV) {
			this.reviewV = reviewV;
		}
	    
	    

}
