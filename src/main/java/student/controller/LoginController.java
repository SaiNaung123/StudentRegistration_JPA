package student.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import student.repository.*;
import student.model.*;


@Controller
public class LoginController {
	
	@Autowired
    UserDao dao;

	 @GetMapping("/")
	    public String loginPage(){
	        return "login";
	    }
	 
	 
	 @PostMapping("/")
	 public String loginPage(@RequestParam String email, String password, HttpSession session, Model model) {
	     User user = dao.checkUserByEmail(email);
	     
	     
	     
	     if (email.equals("") || password.equals("")) {
	         model.addAttribute("error", "Please fill all information.");
	         return "login";
	     }
	     
	     if (user == null) {
	         model.addAttribute("error", "No account found with this email.");
	         return "login";
	     }
	     
	     if (!password.equals(user.getPassword())) {
	         model.addAttribute("error", "Incorrect password.");
	         return "login";
	     }

	     if (user.getStatus().equals("deleted")) {
	         model.addAttribute("error", "Unable to log in. Your account has been restricted.");
	         return "login";
	     }

	     // Setting session attributes and forwarding to menu.jsp
	     session.setAttribute("userinfo", user);
	     Role role = dao.findRoleByEmailAndPassword(email, password);
	     session.setAttribute("role", role);
	     session.setAttribute("date", new Date());
	     return "menu";
	 }
	 
	 @GetMapping("/logout")
	    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
	        HttpSession session = request.getSession(false);
	        if (session != null) {
	            session.invalidate(); // Invalidate the session, effectively logging the user out
	        }
	        return new ModelAndView("redirect:/"); // Redirect the user to the login page
	    }

			 
}
