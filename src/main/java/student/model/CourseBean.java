package student.model;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "course")
public class CourseBean implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotEmpty
	private String name;
	
	@ManyToMany(mappedBy = "courses")
	private List<StudentBean> students;
	
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

}
