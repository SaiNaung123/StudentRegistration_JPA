package student.model;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table (name ="student")

public class StudentBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="dob")
	private String dob;

	@Column(name="gender")
	private String gender;

	@Column(name="contact")
	private String contact;

	@Column(name="education")
	private String education;
	
	@Column(name="photo")
	private String photo;
	
	@Column(name="isDeleted")
	private boolean isDeleted;
	
	@Transient
    private String coursesAsString;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "student_course",
	joinColumns = @JoinColumn(name="student_id") ,
	inverseJoinColumns = @JoinColumn(name = "course_id") )
	
	private List<CourseBean> courses;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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
	
	

	public String getCoursesAsString() {
		return coursesAsString;
	}

	public void setCoursesAsString(String coursesAsString) {
		this.coursesAsString = coursesAsString;
	}

	public StudentBean() {
		super();
	}

	public StudentBean(int id, String name, String dob, String gender, String contact, String education, String photo,
			boolean isDeleted, List<CourseBean> courses) {
		super();
		this.id = id;
		this.name = name;
		this.dob = dob;
		this.gender = gender;
		this.contact = contact;
		this.education = education;
		this.photo = photo;
		this.isDeleted = isDeleted;
		this.courses = courses;
	}
	
	
}
