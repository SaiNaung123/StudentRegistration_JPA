package student.controller;

import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;

import student.repository.*;
import student.model.*;
import student.dto.*;

@Controller
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ModelMapper modelMapper; // Autowire ModelMapper bean

    @GetMapping("/userregister")
    public String userRegister() {
        return "usercreate";
    }

    @PostMapping("/userregister")
    public String registerUser(UserDto userDto, Model model) {
        // Map UserDto to User entity
        User user = modelMapper.map(userDto, User.class);

        // Validate User entity
        if (user.getName() == null || user.getEmail() == null || user.getPassword() == null || user.getRole() == null || user.getStatus() == null) {
            model.addAttribute("error", "Please fill all information.");
            return "usercreate";
        }

        boolean emailExists = userDao.isUserEmailExists(user.getEmail());
        if (emailExists) {
            model.addAttribute("error", "This email already exists.");
            return "usercreate";
        }

        if (!user.getPassword().equals(userDto.getCpassword())) {
            model.addAttribute("error", "Passwords do not match.");
            return "usercreate";
        }
        
        String passwordPattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        if (!user.getPassword().matches(passwordPattern)) {
            model.addAttribute("error", "Please enter a valid password.");
            return "usercreate";
        }

        if (!isValidEmail(user.getEmail())) {
            model.addAttribute("error", "Please enter a valid email address.");
            return "usercreate";
        }

        // Save User entity
        int status = userDao.createUser(user);
        if (status == 1) {
            model.addAttribute("success", "Successfully added.");
        } else {
            model.addAttribute("error", "Something went wrong. Please try again.");
        }

        return "usercreate";
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email.matches(emailPattern);
    }
    
    @GetMapping("/userview")
    public String userSearch(Model model,
                             @RequestParam(value = "uname", required = false) String name,
                             @RequestParam(value = "uid", required = false) String sid,
                             HttpSession session) throws SQLException {

      Role userRole = (Role) session.getAttribute("role");
    if(userRole!=null ) {
        // Create a new UserDto object to hold the search criteria
        UserDto userDto = new UserDto();
        userDto.setName(name); // Set the name parameter to the UserDto
        if (sid != null && !sid.isEmpty()) {
            userDto.setId(Integer.parseInt(sid)); // Set the id parameter to the UserDto if provided
        }

        // Map the UserDto to a User object using ModelMapper
        User user = modelMapper.map(userDto, User.class);

        List<User> users;

        if (user.getId() != 0 && user.getName() != null && !user.getName().isEmpty()) {
            // If both ID and name are provided, search by both criteria
            users = userDao.selectUserByIdAndName(user.getId(), user.getName());
        } else if (user.getId() != 0) {
            // If only ID is provided, search by ID
            user = userDao.getUserById(user.getId());
            if (user != null) {
                users = new ArrayList<>();
                users.add(user);
            } else {
                users = Collections.emptyList(); // No user found for the given ID
            }
        } else if (user.getName() != null && !user.getName().isEmpty()) {
            // If only name is provided, search by name
            users = userDao.selectUserByName(user.getName());
        } else {
            // If neither ID nor name is provided, return all users
            users = userDao.selectActiveUsers();
        }

        model.addAttribute("users", users);
        return "userManagement";
    }else {
    return "redirect:/";
  }
    }

    
    @PostMapping("/userview")
    public String searchUsers(UserDto userDto, Model model) throws SQLException {
        
        User user1 = modelMapper.map(userDto, User.class);

        try {
            List<User> users;

            if (user1.getId() != 0 && user1.getName() != null && !user1.getName().isEmpty()) {
                // If both ID and name are provided, search by both criteria
                users = userDao.selectUserByIdAndName(user1.getId(), user1.getName());
            } else if (user1.getId() != 0) {
                // If only ID is provided, search by ID
                User user = userDao.getUserById(user1.getId());
                if (user != null) {
                    users = new ArrayList<>();
                    users.add(user);
                } else {
                    users = Collections.emptyList(); // No user found for the given ID
                }
            } else if (user1.getName() != null && !user1.getName().isEmpty()) {
                // If only name is provided, search by name
                users = userDao.selectUserByName(user1.getName());
            } else {
                // If neither ID nor name is provided, return all users
                users = userDao.getAllUser();
            }

            model.addAttribute("users", users); // Set the search results as a model attribute
            return "userManagement"; // Return the name of the view to render
        } catch (NumberFormatException e) {
            // Handle invalid ID format or SQL exceptions
            e.printStackTrace();
            // You might want to add an error message to the model
            return "error"; // Return the name of the error view
        }
    }
    
    @GetMapping("/adminview")
    public String adminSearch(Model model,
                              @RequestParam(value = "uname", required = false) String name,
                              @RequestParam(value = "uid", required = false) String sid) throws SQLException {

        // Create a new UserDto object to hold the search criteria
        UserDto userDto = new UserDto();
        userDto.setName(name); // Set the name parameter to the UserDto
        if (sid != null && !sid.isEmpty()) {
            userDto.setId(Integer.parseInt(sid)); // Set the id parameter to the UserDto if provided
        }

        // Map the UserDto to a User object using ModelMapper
        User user = modelMapper.map(userDto, User.class);

        List<User> users;

        if (user.getId() != 0 && user.getName() != null && !user.getName().isEmpty()) {
            // If both ID and name are provided, search by both criteria
            users = userDao.selectAdminByIdAndName(user.getId(), user.getName());
        } else if (user.getId() != 0) {
            // If only ID is provided, search by ID
            user = userDao.getAdminById(user.getId());
            if (user != null) {
                users = new ArrayList<>();
                users.add(user);
            } else {
                users = Collections.emptyList(); // No user found for the given ID
            }
        } else if (user.getName() != null && !user.getName().isEmpty()) {
            // If only name is provided, search by name
            users = userDao.searchAdminsByName(user.getName());
        } else {
            // If neither ID nor name is provided, return all admins
            users = userDao.selectActiveAdmins();
        }

        model.addAttribute("users", users);
        return "adminManagement";
    }


    @PostMapping("/adminview")
    public String searchAdmins( UserDto userDto, Model model) throws SQLException {

        User user1 = modelMapper.map(userDto, User.class);

        try {
            List<User> users;

            if (user1.getId() != 0 && user1.getName() != null && !user1.getName().isEmpty()) {
                // If both ID and name are provided, search by both criteria
                users = userDao.selectAdminByIdAndName(user1.getId(), user1.getName());
            } else if (user1.getId() != 0) {
                // If only ID is provided, search by ID
                User user = userDao.getAdminById(user1.getId());
                if (user != null) {
                    users = new ArrayList<>();
                    users.add(user);
                } else {
                    users = Collections.emptyList(); // No user found for the given ID
                }
            } else if (user1.getName() != null && !user1.getName().isEmpty()) {
                // If only name is provided, search by name
                users = userDao.searchAdminsByName(user1.getName());
            } else {
                // If neither ID nor name is provided, return all admins
                users = userDao.selectActiveAdmins();
            }

            model.addAttribute("users", users); // Set the search results as a model attribute
            return "adminManagement"; // Return the name of the view to render
        } catch (NumberFormatException e) {
            // Handle invalid ID format or SQL exceptions
            e.printStackTrace();
            // You might want to add an error message to the model
            return "error"; // Return the name of the error view
        }
    }
    
    @GetMapping("/userprofile")
	public String userProfile() {
		return "useredit";
	}
 
 @GetMapping("/adminprofile")
	public String adminProfile() {
		return "adminedit";
	}
 
 @GetMapping("/userupdate")
 public String userUpdate(@RequestParam("id") int userId, Model model) {
     int id = Integer.valueOf(userId);
     User user = userDao.getUserById(id);
     model.addAttribute("user", user);
     return "useredit";
 }

 @PostMapping("/userupdate")
 public String updateUser(
         @ModelAttribute("user") UserDto userDto,
         Model model) {

     // Map UserDto to User entity
     User user = modelMapper.map(userDto, User.class);

     // Validate input fields
     if (user.getName() == null || user.getEmail() == null || user.getPassword() == null ||
             user.getName().isEmpty() || user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
         model.addAttribute("error", "All fields are required. Please fill in all information.");
         return "useredit";
     }

     // Check if passwords match
     if (!user.getPassword().equals(userDto.getCpassword())) {
         model.addAttribute("perror", "Passwords do not match.");
         return "useredit";
     }

     // Update user
     int status = userDao.updateUser(user);
     if (status == 1) {
         model.addAttribute("success", "Successfully updated.");
     } else {
         model.addAttribute("error", "Update failed.");
     }

     return "useredit";
 }
 @RequestMapping(value = "/userdelete/{id}", method = RequestMethod.GET)
 public String userDelete(Model model, @PathVariable int id) {

   userDao.softDeleteUser(id);
   return "redirect:/userview";
 }
 
 @GetMapping("/adminupdate")
 public String adminUpdate(@RequestParam("id") int userId, Model model) {
     int id = Integer.valueOf(userId);
     User user = userDao.getAdminById(id);
     model.addAttribute("user1", user);
     return "adminedit";
 }

 @PostMapping("/adminupdate")
 public String updateAdmin(
         @ModelAttribute("user1") UserDto userDto,
         Model model) {

     // Map UserDto to User entity
     User user = modelMapper.map(userDto, User.class);

     // Validate input fields
     if (user.getName() == null || user.getEmail() == null || user.getPassword() == null ||
             user.getName().isEmpty() || user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
         model.addAttribute("error", "All fields are required. Please fill in all information.");
         return "adminedit";
     }

     // Check if passwords match
     if (!user.getPassword().equals(userDto.getCpassword())) {
         model.addAttribute("perror", "Passwords do not match.");
         return "adminedit";
     }

     // Update user
     int status = userDao.updateUser(user);
     if (status == 1) {
         model.addAttribute("success", "Successfully updated.");
     } else {
         model.addAttribute("error", "Update failed.");
     }

     return "adminedit";
 }
 
 
}