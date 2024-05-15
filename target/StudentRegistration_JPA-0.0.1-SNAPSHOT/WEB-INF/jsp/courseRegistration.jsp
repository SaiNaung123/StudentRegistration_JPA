 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
    <title>Course Registration</title>
    
</head>
<style>

	.error{
		color:red;
	}
	
	.success{
		color:green;
	}

</style>

<body>
	<fmt:formatNumber type="number" pattern = "000" 
		value ="${nextId}" var="formattedId"/>	
	
    <jsp:include page="layouts/header.jsp" />
    <div class="container">
        <jsp:include page="layouts/sidebar.jsp" />
        <div class="main_contents">
            <div id="sub_content">
                <form:form method="post" action="createCourse" modelAttribute="course" onsubmit="return validateForm()">
                    
                    <h2 class="col-md-6 offset-md-2 mb-5 mt-4">Course Registration</h2>
                    <div class="error" style="text-align:center;">${error }</div>
                    <div class="success" style="text-align:center;">${success }</div>
                    
                    <div class="row mb-4">
                        <div class="col-md-2"></div>
                        <label for="id" class="col-md-2 col-form-label"> ID</label>
                        <div class="col-md-4">
                            <input  class="form-control" id="id" name="id" value="COU${formattedId}" readonly>
                            
                        </div>
                    </div>
                    <div class="row mb-4">
                        <div class="col-md-2"></div>
                        <label for="name" class="col-md-2 col-form-label">Name</label>
                        <div class="col-md-4">
                            <form:input type="text" class="form-control" id="name" path="name" />
                            <div class="error" id="name-error"></div>
                        </div>
                    </div>
                    <div class="row mb-4">
                        <div class="col-md-4"></div>
                        <div class="col-md-6">
                            <button type="submit" class="btn btn-secondary col-md-2">Add</button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
    <jsp:include page="layouts/footer.jsp" />
</body>

<script>
function validateForm() {
    var courseName = document.getElementById("name").value;
   
    var isValid = true;

    if (courseName === "") {
        document.getElementById("name-error").innerHTML = "Name is required";
        isValid = false;
    } else {
        document.getElementById("name-error").innerHTML = "";
    }

    
    return isValid;
}
</script>

</html>
