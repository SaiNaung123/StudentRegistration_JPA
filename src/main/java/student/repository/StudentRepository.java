package student.repository;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import student.dto.StudentDTO;
import student.model.CourseBean;
import student.model.StudentBean;
import student.service.JPAUtil;

@Service("studentRepo")
@Component
public class StudentRepository {
    
    @PersistenceContext
    private EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

    @Autowired
    private ModelMapper modelMapper;

 // Create operation
    public boolean saveStudent(StudentDTO studentDto) {
        try {
            System.out.println("Name in Dto: " + studentDto.getName());
            StudentBean studentBean = modelMapper.map(studentDto, StudentBean.class);
            System.out.println("Name from bean: " + studentBean.getName());
            
            for (Integer courseId :  studentDto.getCourseIds()) {
    			System.out.println("Course Name : " + courseId );
               
              
            }
            
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(studentBean);

            // If courses are associated with the student, save them and update the relationship
            if (studentDto.getCourseIds() != null && !studentDto.getCourseIds().isEmpty()) {
                for (Integer courseId : studentDto.getCourseIds()) {
                    CourseBean courseBean = entityManager.find(CourseBean.class, courseId);
                    if (courseBean != null) {
                        studentBean.getCourses().add(courseBean);
                        courseBean.getStudents().add(studentBean);
                        entityManager.merge(courseBean); // Update courseBean
                    }
                }
            }

            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update operation
    public boolean updateStudent(StudentDTO studentDto) {
        try {
            StudentBean studentBean = entityManager.find(StudentBean.class, studentDto.getId());
            if (studentBean != null) {
                modelMapper.map(studentDto, studentBean);
                EntityTransaction transaction = entityManager.getTransaction();
                transaction.begin();
                entityManager.merge(studentBean);
                transaction.commit();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

 // Soft Delete operation
    public boolean softDeleteStudent(int id) {
        try {
            StudentBean studentBean = entityManager.find(StudentBean.class, id);
            if (studentBean != null) {
                studentBean.setDeleted(true); // Set the flag to mark as deleted
                EntityTransaction transaction = entityManager.getTransaction();
                transaction.begin();
                entityManager.merge(studentBean); // Update the entity with the flag
                transaction.commit();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // Retrieve operation by ID
    public StudentDTO getStudentById(int id) {
        StudentBean studentBean = entityManager.find(StudentBean.class, id);
        return studentBean != null ? modelMapper.map(studentBean, StudentDTO.class) : null;
    }

    // Retrieve all students
    public List<StudentDTO> getAllStudents() {
        TypedQuery<StudentBean> query = entityManager.createQuery("SELECT s FROM StudentBean s", StudentBean.class);
        List<StudentBean> studentBeans = query.getResultList();
        List<StudentDTO> studentDTOs = new ArrayList<>();

        for (StudentBean studentBean : studentBeans) {
            studentDTOs.add(modelMapper.map(studentBean, StudentDTO.class));
        }

        return studentDTOs;
    }

    // Check if student exists by name
    public boolean getStudentByName(String studentName) {
        TypedQuery<StudentBean> query = entityManager.createQuery("SELECT s FROM StudentBean s WHERE s.name = :studentName", StudentBean.class);
        query.setParameter("studentName", studentName);
        try {
            query.getSingleResult();
            return true; // Student found
        } catch (NoResultException e) {
            return false; // Student not found
        }
    }

    public int getNextStudentId() {
		int nextId = 0;
		EntityManager em = null;
		try {
			em = JPAUtil.getEntityManagerFactory().createEntityManager();
			em.getTransaction().begin();
			Query query = em.createQuery("SELECT MAX(s.id) FROM StudentBean s");
			Object result = query.getSingleResult();
			if (result != null) {
				nextId = ((Number) result).intValue() + 1;
			} else {
				// If there are no courses in the database yet
				nextId = 1;
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Error getting next student ID: " + e.getMessage());
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
			}
		}
		return nextId;
	}
    public List<StudentBean> selectAllStudent() {
        List<StudentBean> students =new ArrayList<>();
      EntityManager em=JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
          transaction.begin();

            Query query = em.createQuery("SELECT s FROM StudentBean s");
            List<StudentBean> resultList = query.getResultList();
            for (StudentBean student : resultList) {
                String courses = findNamesByStudentId(student.getId());
                student.setCoursesAsString(courses);
                students.add(student);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return students;
    }
    public String findNamesByStudentId(int id) {
          StringBuilder names = new StringBuilder();
      EntityManager em=null;

          try {
        em=JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
              Query query = em.createQuery("SELECT c.name FROM StudentBean s JOIN s.courses c WHERE s.id = :id");
              query.setParameter("id", id);
              List<String> resultList = query.getResultList();
              for (String name : resultList) {
                  if (names.length() > 0) {
                      names.append(",");
                  }
                  names.append(name);  
              }
          }catch(Exception e) {
        em.getTransaction().rollback();
        System.out.println(e.getMessage());
      }finally{
        em.close();
      }
          return names.toString();
      }


}
