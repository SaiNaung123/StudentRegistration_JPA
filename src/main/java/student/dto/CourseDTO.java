package student.dto;

import java.io.Serializable;
import java.util.List;
import student.model.StudentBean;


public class CourseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private List<StudentBean> students;
	
	
	
	public CourseDTO() {
		super();
	}
	public CourseDTO(int id, String name, List<StudentBean> students) {
		super();
		this.id = id;
		this.name = name;
		this.students = students;
	}
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
	public List<StudentBean> getStudents() {
		return students;
	}
	public void setStudents(List<StudentBean> students) {
		this.students = students;
	}
	@Override
	public String toString() {
		return "CourseDTO [id=" + id + ", name=" + name + ", students=" + students + "]";
	}
	
	

}
