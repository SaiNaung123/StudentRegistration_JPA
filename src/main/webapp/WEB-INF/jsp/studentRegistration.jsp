<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">

<head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="resources/test.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
    
        <title>Student Registration</title>
</head>

<style>
	.error-message{
		color: red;
		text-align: center;
	}

</style>

<body>
	<fmt:formatNumber type="number" pattern = "000" 
		value ="${nextId}" var="formattedId"/>
	
    <jsp:include page="navbar.jsp" />

<div class="container">
    <jsp:include page="header.jsp" />
    
      <div class="main_contents">
    <div id="sub_content">
    
		<form:form method="post" action="/StudentRegistration_JPA/addstudent" modelAttribute="student" 
		onsubmit="return validateForm()" enctype="multipart/form-data">
		
            <h2 class="col-md-6 offset-md-2 mb-5 mt-4">Student Registration</h2>
           <div align="center" style="color:red">${error}</div><br>
            
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="studentID" class="col-md-2 col-form-label">Student ID</label>
                <div class="col-md-4">
                    <input type="text" class="form-control" name="id" id="id" value="STU${formattedId }" readonly>
                
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="name" class="col-md-2 col-form-label">Name</label>
                <div class="col-md-4">
                    <form:input type="text" class="form-control" path="name"/>
                	<div id="studentNameError" class="error-message"></div>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="dob" class="col-md-2 col-form-label">DOB</label>
                <div class="col-md-4">
                    <form:input type="date" path="dob" class="form-control"/>
                    <div id="dobError" class="error-message"></div>
                </div>
            </div>
            <fieldset class="row mb-4">
						<div class="col-md-2"></div>
						<legend class="col-form-label col-md-2 pt-0">Gender</legend>
						<div class="col-md-4">
							
								
								<form:radiobutton path="gender" value="Male" id="gender" class="for-check-input"  />Male
								<form:radiobutton path="gender" value="Female" id="gender" class="for-check-input"  />Female
								<div id="genderError" class="error-message"></div>
						</div>
						
			</fieldset>
    
            <div class="row mb-4">
						<div class="col-md-2"></div>
						<label for="phone" class="col-md-2 col-form-label">Phone</label>
						<div class="col-md-4">
							<form:input  class="form-control" id="contact"
								path="contact"  />
							<div id="phoneError" class="error-message"></div>	
						</div>
			</div>
			
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="education" class="col-md-2 col-form-label">Education</label>
                <div class="col-md-4">
                    <form:select class="form-select" aria-label="Education" id="education" path="education">
                        <form:option value="Bachelor of Information Technology">Bachelor of Information Technology</form:option>
                        <form:option value="Diploma in IT">Diploma in IT</form:option>
                        <form:option value="Bachelor of Computer Science">Bachelor of Computer Science</form:option>
    
                    </form:select>
                    <div id="educationError" class="error-message"></div>
                </div>
                
            </div>
            
			<fieldset class="row mb-4">
				<div class="col-md-2"></div>
					<legend class="col-form-label col-md-2 pt-0">Attend</legend>

						<div class="col-md-4">
							<!-- Iterate through the attend options list and generate checkboxes -->
							<c:forEach items="${courses }" var="course">

								<input type="checkbox" name="course"
									value="${course.id}" class="form-check-input" /> ${course.name }
	        
					</c:forEach>
					 <div id="coursesError" class="error-message"></div> 
				</div>
			</fieldset>


					<div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="name" class="col-md-2 col-form-label">Photo</label>
                <div class="col-md-4">
                    <input type="file" class="form-control" id="photo" name="image" >
                    <div id="photoError" class="error-message"></div>
                </div>
            </div> 
    
           <div class="row mb-4">
						<div class="col-md-4"></div>

						<div class="col-md-4">
							<button type="reset" class="btn btn-danger ">Reset</button>
							<button type="submit" class="btn btn-secondary col-md-2 form-check-inline col-md-2">
								Add</button>

							
						</div>
					</div>
    
            </form:form>
    </div>
</div>
</div>
        <jsp:include page="footer.jsp"></jsp:include>
        
        
</body>

<script>
    function validateForm() {
        var studentName = document.getElementById('name').value;
        var dob = document.getElementById('dob').value;
        var gender = document.querySelector('input[name="gender"]:checked');
        var phone = document.getElementById('contact').value;
        var education = document.getElementById('education').value;
        var photo = document.getElementById('photo').value;
        var coursesChecked = document.querySelectorAll('input[name="course"]:checked').length;

        var isValid = true;

        if (studentName.trim() === "") {
            document.getElementById('studentNameError').innerText = "Name is required.";
            isValid = false;
        } else {
            document.getElementById('studentNameError').innerText = "";
        }

        if (dob.trim() === "") {
            document.getElementById('dobError').innerText = "DOB is required.";
            isValid = false;
        } else {
            document.getElementById('dobError').innerText = "";
        }

        if (gender === null) {
            document.getElementById('genderError').innerText = "Gender is required.";
            isValid = false;
        } else {
            document.getElementById('genderError').innerText = "";
        }

        if (phone.trim() === "") {
            document.getElementById('phoneError').innerText = "Phone is required.";
            isValid = false;
        } else {
            document.getElementById('phoneError').innerText = "";
        }

        if (education === "") {
            document.getElementById('educationError').innerText = "Education is required.";
            isValid = false;
        } else {
            document.getElementById('educationError').innerText = "";
        }

        if (coursesChecked === 0) {
            document.getElementById('coursesError').innerText = "At least one course must be selected.";
            isValid = false;
        } else {
            document.getElementById('coursesError').innerText = "";
        }

        if (photo.trim() === "") {
            document.getElementById('photoError').innerText = "Photo is required.";
            isValid = false;
        } else {
            document.getElementById('photoError').innerText = "";
        }

        return isValid;
    }
</script>

</html>
