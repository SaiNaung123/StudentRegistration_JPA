package student.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import student.dto.CourseDTO;
import student.model.CourseBean;
import student.service.JPAUtil;
@Service("courseRepo")
@Component

public class CourseRepository {
	@PersistenceContext
	private EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
	
	// Create operation
	public boolean saveCourse(CourseDTO courseDto) {
	    try {
	        CourseBean courseBean = new CourseBean();
	        courseBean.setName(courseDto.getName());
	        EntityTransaction transaction = entityManager.getTransaction();
	        transaction.begin();
	        entityManager.persist(courseBean);
	        transaction.commit();
	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Error : " + e);
	        return false;
	    }
	}

	public boolean updateCourse(CourseDTO courseDto) {
	    try {
	        CourseBean courseBean = entityManager.find(CourseBean.class, courseDto.getId());
	        if (courseBean != null) {
	            courseBean.setName(courseDto.getName());
	            EntityTransaction transaction = entityManager.getTransaction();
	            transaction.begin();
	            entityManager.merge(courseBean);
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

	public boolean deleteCourse(int id) {
	    try {
	        CourseBean courseBean = entityManager.find(CourseBean.class, id);
	        if (courseBean != null) {
	            EntityTransaction transaction = entityManager.getTransaction();
	            transaction.begin();
	            entityManager.remove(courseBean);
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


	public CourseDTO getCourseById(int id) {
		CourseBean courseBean = entityManager.find(CourseBean.class, id);
		return courseBean != null ? new CourseDTO(courseBean.getId(), courseBean.getName(), courseBean.getStudents())
				: null;
	}

	public List<CourseDTO> getAllCourses() {
		TypedQuery<CourseBean> query = entityManager.createQuery("SELECT c FROM CourseBean c", CourseBean.class);
		List<CourseBean> courseBeans = query.getResultList();
		List<CourseDTO> courseDTOs = new ArrayList<>();

		for (CourseBean courseBean : courseBeans) {
			courseDTOs.add(new CourseDTO(courseBean.getId(), courseBean.getName(), courseBean.getStudents()));
		}

		return courseDTOs;
	}

	// Find course by course-name

	public boolean getCourseByName(String coursename) {
		TypedQuery<CourseBean> query = entityManager
				.createQuery("SELECT c FROM CourseBean c WHERE c.name = :coursename", CourseBean.class);
		query.setParameter("coursename", coursename); // Fix parameter name
		try {
			query.getSingleResult(); // Attempt to retrieve the course
			return true; // Course found, return true
		} catch (NoResultException e) {
			return false; // Course not found, return false
		}
	}
	
	public int getNextCourseId() {
        int nextId = 0;
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            Query query = entityManager.createQuery("SELECT MAX(c.id) FROM CourseBean c");
            Object result = query.getSingleResult();
            if (result != null) {
                nextId = ((Number) result).intValue() + 1;
            } else {
                // If there are no courses in the database yet
                nextId = 1;
            }
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Error getting next course ID: " + e.getMessage());
        }
        return nextId;
    }
	
	public boolean isCourseNameExists(String courseName) {
	    TypedQuery<Long> query = entityManager.createQuery(
	            "SELECT COUNT(c) FROM CourseBean c WHERE c.name = :courseName", Long.class);
	    query.setParameter("courseName", courseName);
	    Long count = query.getSingleResult();
	    return count > 0;
	}
	
	public boolean isCourseNameandIdExists(String courseName, int courseId) {
	    TypedQuery<Long> query = entityManager.createQuery(
	            "SELECT COUNT(c) FROM CourseBean c WHERE c.name = :courseName AND c.id != :courseId", Long.class);
	    query.setParameter("courseName", courseName);
	    query.setParameter("courseId", courseId);
	    Long count = query.getSingleResult();
	    return count > 0;
	}

	}
