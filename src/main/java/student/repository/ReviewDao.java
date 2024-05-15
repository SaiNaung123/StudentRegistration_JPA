package student.repository;

import jakarta.persistence.EntityManager;
import student.service.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import student.model.*;

;

@Service("reviewdao")
@Component
public class ReviewDao implements ReviewDaoInterface{

    

    public int save(Review review) {
    	int status=0;
    	EntityManager em=null;
    	try{
    		 em = JPAUtil.getEntityManagerFactory().createEntityManager();
             em.getTransaction().begin();
             em.persist(review);
             em.getTransaction().commit();
             status = 1;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    	return status;
    }

    public List<Review> findAll() {
        List<Review> reviews = new ArrayList<>();
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            reviews = em.createQuery("SELECT r FROM Review r", Review.class).getResultList();
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return reviews;
    }
}
