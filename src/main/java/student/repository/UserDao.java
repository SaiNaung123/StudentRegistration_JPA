package student.repository;

import java.util.ArrayList;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import daos.UserDaoInterFace;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.TypedQuery;
import student.service.*;
import student.model.*;

@Service("userdao")
@Component
public class UserDao {

    public int createUser(User user) {
        int status = 0;
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            status = 1;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return status;
    }

    public int updateUser(User user) {
        int status = 0;
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
            status = 1;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return status;
    }

    public int deleteUser(int id) {
        int status = 0;
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            User user = em.find(User.class, id);
            if (user != null) {
                em.remove(user);
                status = 1;
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return status;
    }

    public List<User> getAllUser() {
        List<User> users = new ArrayList<>();
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            users = em.createQuery("SELECT u FROM User u WHERE u.role = 'User'", User.class).getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return users;
    }

    public List<User> getAllAdmin() {
        List<User> users = new ArrayList<>();
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            users = em.createQuery("SELECT u FROM User u WHERE u.role = 'Admin' OR u.role = 'Super_Admin'", User.class).getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return users;
    }

    public User checkUserByEmail(String email) {
        EntityManager em = null;
        User user = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", email);
            List<User> resultList = query.getResultList();
            if (!resultList.isEmpty()) {
                user = resultList.get(0); // Get the first user if the list is not empty
            } else {
                System.out.println("No user found with email: " + email);
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return user;
    }


    public User checkEmail(String email) {
        EntityManager em = null;
        User user = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", email);
            user = query.getSingleResult();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return user;
    }

    public User checkPassword(String password) {
        EntityManager em = null;
        User user = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.password = :password", User.class);
            query.setParameter("password", password);
            user = query.getSingleResult();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return user;
    }

    public User getUserById(int id) {
        EntityManager em = null;
        User user = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.id = :id AND u.status = 'active' AND u.role = 'User'", User.class);
            query.setParameter("id", id);
            List<User> resultList = query.getResultList();
            if (!resultList.isEmpty()) {
                user = resultList.get(0); // Get the first user if the list is not empty
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return user;
    }


    public User getAdminById(int id) {
        EntityManager em = null;
        User user = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.id = :id AND u.status = 'active' AND (u.role = 'Admin' OR u.role = 'Super_Admin')", User.class);
            query.setParameter("id", id);
            List<User> resultList = query.getResultList();
            if (!resultList.isEmpty()) {
                user = resultList.get(0); // Get the first user if the list is not empty
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return user;
    }


    public User getAdminnById(int id) {
        EntityManager em = null;
        User user = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.id = :id AND u.status = 'active' AND u.role = 'Admin'", User.class);
            query.setParameter("id", id);
            user = query.getSingleResult();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return user;
    }

    public List<User> selectUserByName(String name) {
        List<User> users = new ArrayList<>();
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.name = :name AND u.status = 'active'", User.class);
            query.setParameter("name", name);
            users = query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return users;
    }

    public List<User> selectUserByIdAndName(int id, String name) {
        List<User> users = new ArrayList<>();
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.id = :id AND u.name LIKE :name AND u.role = 'User' AND u.status = 'active'", User.class);
            query.setParameter("id", id);
            query.setParameter("name", "%" + name + "%");
            users = query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return users;
    }

    public List<User> selectAdminByIdAndName(int id, String name) {
        List<User> users = new ArrayList<>();
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.id = :id AND u.name LIKE :name AND (u.role = 'Admin' OR u.role = 'Super_Admin') AND u.status = 'active'", User.class);
            query.setParameter("id", id);
            query.setParameter("name", "%" + name + "%");
            users = query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return users;
    }

    public Role findRoleByEmailAndPassword(String email, String password) {
        EntityManager em = null;
        Role role = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            TypedQuery<String> query = em.createQuery("SELECT u.role FROM User u WHERE u.email = :email AND u.password = :password", String.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            String roleString = query.getSingleResult();
            role = Role.valueOf(roleString.toUpperCase());
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return role;
    }

    public List<User> searchUsersByName(String name) {
        EntityManager em = null;
        List<User> users = new ArrayList<>();
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.name LIKE :name AND u.role = 'User' AND u.status = 'active'", User.class);
            query.setParameter("name", "%" + name + "%");
            users = query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return users;
    }

    public List<User> searchAdminsByName(String name) {
        EntityManager em = null;
        List<User> users = new ArrayList<>();
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.name LIKE :name AND (u.role = 'Admin' OR u.role = 'Super_Admin') AND u.status = 'active'", User.class);
            query.setParameter("name", "%" + name + "%");
            users = query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return users;
    }

    public boolean isUserEmailExists(String email) {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class);
            query.setParameter("email", email);
            return query.getSingleResult() > 0;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<User> selectActiveUsers() {
        List<User> users = new ArrayList<>();
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.role = 'User' AND u.status = 'active'", User.class);
            users = query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return users;
    }

    public List<User> selectActiveAdmins() {
        List<User> users = new ArrayList<>();
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE (u.role = 'Admin' OR u.role = 'Super_Admin') AND u.status = 'active'", User.class);
            users = query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return users;
    }

    public int softDeleteUser(int id) {
        int status = 0;
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("delete_user");
            storedProcedure.registerStoredProcedureParameter("user_id", Integer.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("status", String.class, ParameterMode.OUT);
            storedProcedure.setParameter("user_id", id);
            storedProcedure.execute();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return status;
    }
}