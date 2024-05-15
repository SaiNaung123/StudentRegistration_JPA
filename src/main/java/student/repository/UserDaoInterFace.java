package student.repository;

import java.util.List;

import student.model.*;


public interface UserDaoInterFace {
	
	 public int createUser(User user);

	    public int updateUser(User user);

	    public int deleteUser(int id);

	    public List<User> getAllUser();

	    public List<User> getAllAdmin();

	    public User checkUserByEmail(String email);

	    public User checkEmail(String email);

	    public User checkPassword(String password);

	    public User getUserById(int id);

	    public User getAdminById(int id);

	    public User getAdminnById(int id);

	    public List<User> selectUserByName(String name);

	    public List<User> selectUserByIdAndName(int id, String name);

	    public List<User> selectAdminByIdAndName(int id, String name);

	    public Role findRoleByEmailAndPassword(String email, String password);

	    public List<User> searchUsersByName(String name);

	    public List<User> searchAdminsByName(String name);

	    public boolean isUserEmailExists(String email);

	    public List<User> selectActiveUsers();

	    public List<User> selectActiveAdmins();

	    public int softDeleteUser(int id);

}
