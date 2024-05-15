package student.controller;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import student.dto.CourseDTO;
import student.repository.CourseRepository;

@Controller
@Component
@ComponentScan("student")

public class CourseController {

	private CourseRepository courseRepo;

	public CourseController(CourseRepository courseRepo) {
		this.courseRepo = courseRepo;
	}
	
	@GetMapping("/courseList")
	public String courseList(ModelMap model) {
		
		List<CourseDTO> courses= courseRepo.getAllCourses();
		model.addAttribute("CourseList", courses);
		
		return "courseList";
	}

	@GetMapping("/createCourse")
	public ModelAndView courseAdd(ModelMap model) {

		CourseDTO bean = new CourseDTO();
		int nextId = courseRepo.getNextCourseId();
		bean.setId(nextId);
		model.addAttribute("nextId", nextId);
		return new ModelAndView("courseRegistration", "course", new CourseDTO());
	}

	@PostMapping("/createCourse")
	public String createCourse(@ModelAttribute("course") CourseDTO course,
	                           BindingResult bs,
	                           RedirectAttributes redirectAttributes,
	                           ModelMap model) {
	    String courseName = course.getName();

	    if (courseRepo.isCourseNameExists(courseName)) {
	        model.addAttribute("error", "Course with this name already exists!");
	        return "courseRegistration";
	    }

	    CourseDTO bean = new CourseDTO();
	    bean.setName(courseName);
	    boolean result = courseRepo.saveCourse(bean);

	    if (!result) {
	        model.addAttribute("error", "Course Insert Fail!");
	        return "courseRegistration";
	    }

	    redirectAttributes.addFlashAttribute("success", "Course Insert Successful!");
	    return "redirect:/createCourse";
	}
	
	@GetMapping("/updateCourse")
	public String showUpdateForm(@RequestParam("id") int courseId, ModelMap model) {
	    CourseDTO course = courseRepo.getCourseById(courseId);
	    model.addAttribute("course", course);
	    return "updateCourse";
	}


    @PostMapping("/updateCourse")
    public String updateCourse(@ModelAttribute("course") CourseDTO course,
                               BindingResult result,
                               RedirectAttributes redirectAttributes,
                               ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("error", "Validation error occurred!");
            return "updateCourse";
        }

        String courseName = course.getName();
        int courseId = course.getId();

        if (courseRepo.isCourseNameandIdExists(courseName, courseId)) {
            model.addAttribute("error", "Course name already exists!");
            return "updateCourse";
        }

        boolean success = courseRepo.updateCourse(course);
        if (success) {
            redirectAttributes.addFlashAttribute("success", "Course updated successfully!");
            return "redirect:/courseList";
        } else {
            model.addAttribute("error", "Failed to update course!");
            return "updateCourse";
        }
    }

}