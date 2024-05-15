<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="resources/test.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
	crossorigin="anonymous"></script>

<title>Student Update</title>
</head>

<body>

	<fmt:formatNumber type="number" pattern="000" value="${student.id }"
		var="formattedId" />

	<jsp:include page="navbar.jsp" />
    <jsp:include page="header.jsp" />
	<div class="container">

		<div class="main_contents">
			<div id="sub_content">

				<form:form action="studentupdated" method="post"
					modelAttribute="student" enctype="multipart/form-data">

					<h2 class="col-md-6 offset-md-2 mb-5 mt-4">Student
						Registration</h2>
					<p class="text-danger text-center">${error }</p>

					<!-- img -->
					<div
						class="image d-flex flex-column justify-content-center align-items-center">
						<img id="selectedPhoto"
							src="<c:url value='/resources/images/${student.photo}'/>"
							alt="Selected Photo" class="rounded-circle me-2" height="100"
							width="100" onclick="openFileInput()">
					</div>

					<input type="file" value="/resources/images/${student.photo}"
						class="custom-file-input" id="photoInput" name="photo"
						accept="image/*" style="display: none;">
					<div class="custom-file-label" style="cursor: pointer;"
						align="center">Artist Image</div>


					<!-- <div class="row mb-4">
						<div class="col-md-2"></div>
						<label for="id" class="col-md-2 col-form-label">Student ID</label>
						<div class="col-md-4"> -->
					<form:input type="text" class="form-control" id="id" path="id"
						hidden="true" />

					<!-- </div>
					</div> -->

					<div class="row mb-4">
						<div class="col-md-2"></div>
						<label for="name" class="col-md-2 col-form-label">Name</label>
						<div class="col-md-4">

							<form:input type="text" class="form-control" id="name"
								path="name" required="true" />
						</div>
					</div>
					<div class="row mb-4">
						<div class="col-md-2"></div>
						<label for="dob" class="col-md-2 col-form-label">DOB</label>
						<div class="col-md-4">
							<form:input type="date" class="form-control" id="dob" path="dob"
								required="true" />
						</div>
					</div>
					<fieldset class="row mb-4">
						<div class="col-md-2"></div>
						<legend class="col-form-label col-md-2 pt-0">Gender</legend>
						<div class="col-md-4">


							<form:radiobutton path="gender" value="Male" id="gender"
								class="for-check-input" required="true" />
							Male
							<form:radiobutton path="gender" value="Female" id="gender"
								class="for-check-input" required="true" />
							Female
						</div>
					</fieldset>

					<div class="row mb-4">
						<div class="col-md-2"></div>
						<label for="phone" class="col-md-2 col-form-label">Phone</label>
						<div class="col-md-4">
							<form:input class="form-control" id="contact" path="contact"
								required="true" />
						</div>
					</div>
					<div class="row mb-4">
						<div class="col-md-2"></div>
						<label for="education" class="col-md-2 col-form-label">Education</label>
						<div class="col-md-4">
							<form:select path="education" id="education" class="form-control">
								<form:option value="Bachlor of Information Technology">Bachelor of Information Technology</form:option>
								<form:option value="Diploma in IT">Diploma in IT</form:option>
								<form:option value="Bachlor of Computer  Science">Bachelor of Computer Science</form:option>
							</form:select>
						</div>
					</div>


					<!-- course  -->
					<div>
						<fieldset class="row mb-4">
							<div class="col-md-2"></div>
							<legend class="col-form-label col-md-2 pt-0">Attend</legend>


							<c:forEach var="course" items="${courseList}">
								<div class="form-check-inline col-md-auto">
									<input class="form-check-input" type="checkbox" name="courses"
										id="${course.id}" value="${course.id}"
										<c:if test="${fn:contains(courseIds, course.id)}">checked</c:if>>
									<label class="form-check-label" for="${course.id}">
										${course.name} </label>
								</div>
							</c:forEach>



						</fieldset>
					</div>



					<div class="row mb-4">
						<div class="col-md-4"></div>
						<div class="col-md-4">
							<!-- <button type="reset" class="btn btn-danger ">Reset</button> -->
							<button type="submit" class="btn btn-secondary col-md-2">
								Add</button>
						</div>
					</div>
				</form:form>
			</div>

		</div>
	</div>

	<jsp:include page="footer.jsp" />

</body>

<script>
	function openFileInput() {
		const photoInput = document.getElementById("photoInput");
		photoInput.click();
	}

	const photoInput = document.getElementById("photoInput");
	const selectedPhoto = document.getElementById("selectedPhoto");
	const customFileLabel = document.querySelector(".custom-file-label");

	photoInput.addEventListener("change", function myfunction() {
		const file = this.files[0];
		if (file) {
			const reader = new FileReader();
			reader.onload = function(e) {
				selectedPhoto.src = e.target.result;
			};
			reader.readAsDataURL(file);
			customFileLabel.textContent = file.name;
		} else {
			selectedPhoto.src = "";
			customFileLabel.textContent = "Click to choose file";
		}
	});
</script>


</html>

