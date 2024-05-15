package student.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import student.dto.CourseDTO;
import student.dto.StudentDTO;
import student.model.CourseBean;
import student.model.StudentBean;
import student.repository.CourseRepository;
import student.repository.StudentRepository;

@Controller
@Component
@ComponentScan("student")
public class StudentController {
	@Autowired
	private StudentRepository studentRepo;
	
	@Autowired
	private CourseRepository courseRepo;
	
	@Autowired
	private ServletContext servletContext;
	
	@RequestMapping(value = "/studentList", method = RequestMethod.GET)
	public String stuList(ModelMap model,HttpServletRequest request,
			@RequestParam(name="id",required=false) String stuId,
			@RequestParam(name="name", required=false) String stuName) {
		
		List<StudentBean> dto = studentRepo.selectAllStudent();
		
		//List<StudentDTO> dto = studentRepo.getAllStudents();
		System.out.println("Student List ->  " + dto);
		model.addAttribute("StudentList", dto);
		
		request.getServletContext().setAttribute("list", dto);
		return ("studentList");
		
	}
	
	@RequestMapping(value = "/studentreg", method = RequestMethod.GET)
	public String studentReg (HttpSession session, ModelMap model) {
		
		/*
		 * if (session.getAttribute("userId") == null) { return ("redirect:/");
		 * 
		 * }else {
		 */
        	StudentDTO student = new StudentDTO();
        	int nextId = studentRepo.getNextStudentId();
        	student.setId(nextId);
        	System.out.println("Next Id : " +nextId);
        	model.addAttribute("nextId", nextId);
        	
        	List<CourseDTO> courses = courseRepo.getAllCourses();
    		session.setAttribute("courses", courses);
        	model.addAttribute("student", student);
        	
		return "studentRegistration";
        }
		/* } */
	
	@RequestMapping(value = "/addstudent", method = RequestMethod.POST)
	public String studentRegister(@ModelAttribute("student") @Validated StudentDTO register,BindingResult br,
			ModelMap model, HttpServletRequest request, HttpSession session,
			@RequestParam( "course") List<Integer> courseIds,
			@RequestParam( "image") CommonsMultipartFile  image) throws IOException {
		
		int id = register.getId();
		System.out.println("STUDENT ID : " + id);
		
		boolean error=false;
		
		String storageFileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
		
		try {
			
			File uploadDir = new File("D:/OJTProject/StudentRegistration_JPA/src/main/webapp/resources/images");
			
			
			
			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			
			// Save the file to the upload directory
			
			  File file = new File(uploadDir, storageFileName); image.transferTo(file);
			 
			
			// Save the file to the upload directory
			/*
			 * File file = new
			 * File(request.getServletContext().getRealPath("/resources/images/"),
			 * storageFileName); image.transferTo(file);
			 */
			
			 } catch (Exception ex) {
			System.out.println("Exception : " + ex.getMessage());
		}
		
		if (error) {
			return ("studentRegistration");
		} else {
		
		System.out.println("Course :"+courseIds);
		StudentDTO bean = new StudentDTO();	
		
		bean.setId(id);
		bean.setName(register.getName());
		bean.setDob(register.getDob());
		bean.setGender(register.getGender());
		bean.setContact(register.getContact());
		bean.setEducation(register.getEducation());
		bean.setPhoto(storageFileName);
		bean.setDeleted(false);
		
		System.out.println("storageFileName : "+ storageFileName);
		
		
		bean.setCourseIds(courseIds);
		
		
		
		boolean result = studentRepo.saveStudent(bean);
		
		if(!result) {
			model.addAttribute("error", "Insert Failed...");
			return "studentRegistration";
		}
		System.out.println("rs : "+result);
		}
		
		return "redirect:/studentList";
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/studentupdate", method = RequestMethod.GET)
	public String updateStudent(@RequestParam("id") int id, ModelMap model, HttpSession session) {
		
		/*
		 * if (session.getAttribute("adminId") == null) { return "redirect:/"; } else {
		 */
	        StudentDTO student = studentRepo.getStudentById(id);
	        System.out.println("RIT" + student.getName() + "ID : " + id);
	        if (student == null) {
	            // Handle the case when the student with the provided ID is not found
	            // Redirect to an appropriate error page or action
	            return "studentUpdate";
	        }

	     // Populate courseIds list with the IDs of the courses associated with the student
	        List<Integer> courseIds = new ArrayList<>();
	        for (CourseBean course : student.getCourses()) {
	            courseIds.add(course.getId());
	        }

	        List<CourseDTO> courseList = courseRepo.getAllCourses();
	        model.addAttribute("student", student);
	        model.addAttribute("courseList", courseList);
	        model.addAttribute("courseIds", courseIds); // Add courseIds to the model

	        return "studentUpdate";
	    }
		/* } */

	@RequestMapping(value = "/studentupdated", method = RequestMethod.POST)
	public String studentUpdated(@ModelAttribute("student") @Validated StudentDTO student,
	                             BindingResult bs, ModelMap model,
	                             @RequestParam(name = "courses", required = false) List<Integer> courseIds,
	                             @RequestParam(name = "photo", required = false) CommonsMultipartFile photo,
	                             @RequestParam("id") int id,
	                             HttpServletRequest request, HttpSession session,RedirectAttributes attribute) {

	    // Retrieve the existing student details from the database
	    StudentDTO bean = studentRepo.getStudentById(id);

	    if (bean == null) {
	        // Handle the case when the student with the provided ID is not found
	        // Redirect to an appropriate error page or action
	        return "studentUpdate";
	    }

	    // Update student details with the new values
	    bean.setName(student.getName());
	    bean.setDob(student.getDob());
	    bean.setGender(student.getGender());
	    bean.setContact(student.getContact());
	    bean.setEducation(student.getEducation());

	 // Update student courses
	    if (courseIds != null) {
	        // Convert courseIds to CourseDTO objects
	        List<CourseBean> selectedCourses = new ArrayList<>();
	        for (Integer courseId : courseIds) {
	            CourseDTO courseDTO = courseRepo.getCourseById(courseId);
	            if (courseDTO != null) {
	                // Convert CourseDTO to CourseBean
	                CourseBean courseBean = new CourseBean();
	                courseBean.setId(courseDTO.getId());
	                courseBean.setName(courseDTO.getName());
	                // Add the converted course to selectedCourses
	                selectedCourses.add(courseBean);
	            }
	        }
	        // Set the updated courses to the student
	        bean.setCourses(selectedCourses);
	    } else {
	        // No courses selected, clear the student's courses
	        bean.setCourses(new ArrayList<>());
	    }


	    String directoryPath = "D:/OJTProject/StudentRegistration_JPA/src/main/webapp/resources/images";

	    try {
	        if (photo != null && !photo.isEmpty()) {
	            // A new photo is uploaded
	            String fileUrl = UUID.randomUUID() + "_" + photo.getOriginalFilename();

	            // Save the new photo to the file system
	            File uploadDir = new File(directoryPath);
	            if (!uploadDir.exists()) {
	                uploadDir.mkdirs();
	            }
	            photo.transferTo(new File(uploadDir, fileUrl));

	            // Update student's photo attribute with the new photo URL
	            bean.setPhoto(fileUrl);

	            // Delete the old photo file if it exists
	            String oldPhotoPath = student.getPhoto();
	            if (oldPhotoPath != null && !oldPhotoPath.isEmpty()) {
	                File oldPhotoFile = new File(directoryPath, oldPhotoPath);
	                if (oldPhotoFile.exists() && oldPhotoFile.isFile()) {
	                    if (oldPhotoFile.delete()) {
	                        System.out.println("Old photo file deleted successfully.");
	                    } else {
	                        System.out.println("Failed to delete the old photo file.");
	                    }
	                } else {
	                    System.out.println("Old photo file does not exist or is not a file.");
	                }
	            }
	        }

	        // Update the student in the database
	        studentRepo.updateStudent(bean);

	    } catch (IOException e) {
	        // Handle file upload error
	        System.out.println("Photo Error: " + e.getMessage());
	    }

	    // Update the photo attribute of the student bean before passing it to the view
	    student.setPhoto(bean.getPhoto());

	    // Add the student object to the model to display in the JSP
	    model.addAttribute("student", student);

	    // Redirect to the student list page after successful update
	    attribute.addFlashAttribute("success", "Update Successful ...");
	    return "redirect:/studentList";
	}


	
	@RequestMapping(value = "/softDeleteStudent", method = RequestMethod.GET)
	public String softDeleteStudent(@RequestParam("id") int id, RedirectAttributes attribute) {
	    boolean softDelete = studentRepo.softDeleteStudent(id);
	    
	    if(!softDelete) {
			attribute.addFlashAttribute("error","Delete Fail....");
			return "redirect:/studentList";
		}else {	
		return "redirect:/studentList"; 
		}

	}
	
	@GetMapping("/generateReport")
    @ResponseBody
    public void generateReport(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam("export") String export) throws IOException {
        String path = servletContext.getRealPath("/WEB-INF/jasper/rep_stu.jrxml");
        JRBeanCollectionDataSource source = null;
        JasperReport jasperReport;
        JasperPrint print;
        ArrayList<StudentBean> list = new ArrayList<>();

        Object listAttribute = servletContext.getAttribute("list");
        if (listAttribute instanceof Vector) {
            Vector<StudentBean> vectorList = (Vector<StudentBean>) listAttribute;
            list = new ArrayList<>(vectorList);
        } else if (listAttribute instanceof ArrayList) {
            list = (ArrayList<StudentBean>) listAttribute;
        } else {
            System.out.println("Attribute 'list' is null or not an instance of Vector or ArrayList");
        }

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ReportTitle", "Student List");

        try {
            source = new JRBeanCollectionDataSource(list);
            jasperReport = JasperCompileManager.compileReport(path);
            print = JasperFillManager.fillReport(jasperReport, parameters, source);

            if ("excel".equals(export)) {
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                response.setHeader("Content-Disposition", "attachment; filename=displayStudent.xlsx");

                JRXlsxExporter exporterXLS = new JRXlsxExporter();
                exporterXLS.setParameter(JRDocxExporterParameter.JASPER_PRINT, print);
                exporterXLS.setParameter(JRDocxExporterParameter.OUTPUT_STREAM, response.getOutputStream());
                exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);

                
                exporterXLS.exportReport();
            } else {
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=displayStudent.pdf");

                JRPdfExporter exporterPdf = new JRPdfExporter();
                exporterPdf.setExporterInput(new SimpleExporterInput(print));
                exporterPdf.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
                exporterPdf.exportReport();
            }
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
	  
	 
}


