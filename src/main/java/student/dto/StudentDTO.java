package student.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import student.model.CourseBean;


public class StudentDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	
	private String name;
	
	private String dob;
	
	private String gender;

	private String contact;
	
	private String education;
	
	private String photo;
	
	private boolean isDeleted;
	private String coursesAsString;
	
	private List<CourseBean> courses;
	private  List<Integer> courseIds=new ArrayList<>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public List<CourseBean> getCourses() {
		return courses;
	}
	public void setCourses(List<CourseBean> courses) {
		this.courses = courses;
	}
	public List<Integer> getCourseIds() {
		return courseIds;
	}
	public void setCourseIds(List<Integer> courseIds) {
		this.courseIds = courseIds;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCoursesAsString() {
		return coursesAsString;
	}
	public void setCoursesAsString(String coursesAsString) {
		this.coursesAsString = coursesAsString;
	}
	
	
	
}
