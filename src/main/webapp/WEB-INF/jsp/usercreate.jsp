<%@page import="student.model.Role"%>

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
<jsp:include page="navbar.jsp"></jsp:include>
<%
Role userRole = (Role) session.getAttribute("role");
%>

<style>
h2,label{
    color: black;
}
</style>


<div class="main_contents">
    <div id="sub_content">
        <form method="post" onsubmit="return validateForm()">
           
            <h2 class="col-md-6 offset-md-2 mb-5 mt-4">User Registration</h2>
             <p style="color: green; text-align: center;">${success }</p>
            <p style="color: red; text-align: center;">${error }</p>
            <h6 style="color: red;text-align: center;">${usererror }</h6>
            <p id="errorMessage" style="color: red; text-align: center;"></p>
             <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="name" class="col-md-2 col-form-label">Name</label>
                <div class="col-md-4">
                    <input type="text" class="form-control" id="name" name="name" value="${param.name}" >
                    <div id="nameError" class="col-md-4" style="color: red;"></div>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="email" class="col-md-2 col-form-label">Email</label>
                <div class="col-md-4">
                    <input type="email" class="form-control" id="email" name="email" value="${param.email}">
                    
                    <div id="emailError" class="col-md-4" style="color: red;"></div>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="password" class="col-md-2 col-form-label">Password</label>
                <div class="col-md-4">
                    <input type="password" class="form-control" id="password" name="password" value="${param.password}">
                    <input type="checkbox" id="showPasswordCheckbox"> Show Password
                    <div id="passwordError" class="col-md-4" style="color: red;"></div> <!-- Add this element for password error -->
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="confirmPassword" class="col-md-2 col-form-label">Confirm Password</label>
                <div class="col-md-4">
                    <input type="password" class="form-control" id="confirmPassword" name="cpassword" value="${param.cpassword}" >
                    <input type="checkbox" id="showPasswordCheckbox1"> Show Password
                    <div id="cpasswordError" class="col-md-4" style="color: red;"></div>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="userRole" class="col-md-2 col-form-label">User Role</label>
                <div class="col-md-4">
                    <select class="form-select" aria-label="Education"  id="role" name="role">
                    <% if(userRole != null && userRole == Role.SUPER_ADMIN) { %>
                           <option  selected>Super_Admin</option>
                        <% } %>
                        <% if(userRole != null && userRole == Role.ADMIN || userRole == Role.SUPER_ADMIN) { %>
                           <option  selected>Admin</option>
                        <% } %>
                        
                        <% if(userRole == null || userRole == Role.USER) { %>
                        <option>User</option>
                        <% } %>
                    </select>
                    <div id="roleError" class="col-md-4" style="color: red;"></div>
                </div>
            </div>
            
            <select class="form-select" aria-label="Education"  id="status" name="status" hidden>
                           <option  selected>active</option>
                    </select>
            <div class="row mb-4">
                <div class="col-md-4"></div>
    
                <div class="col-md-6">
                    <button type="submit" class="btn btn-secondary col-md-2" data-bs-toggle="modal" data-bs-target="#exampleModal">Add</button>
                </div>
            </div>
        </form>
    </div>
</div>

<div id="testfooter">
    <span>Copyright &#169; ACE Inspiration 2022</span>
</div>

<script>
// Function to validate if password and confirm password match
function validatePasswordMatch() {
    var password = document.getElementById("password").value;
    var cpassword = document.getElementById("confirmPassword").value;
    var cpasswordError = document.getElementById("cpasswordError");

    if (password !== cpassword) {
        cpasswordError.innerHTML = "Passwords do not match.";
        return false;
    } else {
        cpasswordError.innerHTML = "";
        return true;
    }
}

function validatePassword() {
    var password = document.getElementById("password").value;
    var passwordError = document.getElementById("passwordError");

    // Define your password pattern here
    var passwordPattern = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$/;

    if (!passwordPattern.test(password)) {
        passwordError.innerHTML = "Password must contain at least one uppercase letter, one lowercase letter, one number, and be at least 8 characters long.";
        return false;
    } else {
        passwordError.innerHTML = "";
        return true;
    }
}

//Function to validate the email field
function validateEmail() {
    var email = document.getElementById("email").value;
    var emailError = document.getElementById("emailError");
    
    // Regular expression for email validation
    var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (email.trim() === "") {
        emailError.innerHTML = "Please enter your email address.";
        return false;
    } else if (!emailPattern.test(email)) {
        emailError.innerHTML = "Please enter a valid email address.";
        return false;
    } else {
        emailError.innerHTML = "";
        return true;
    }
}


// Function to validate the entire form
function validateForm() {
    var name = document.getElementById("name").value;
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var cpassword = document.getElementById("confirmPassword").value;
    var role = document.getElementById("userRole").value;
    var errorMessage = document.getElementById("errorMessage");

    // Check if any field is empty
    if (name.trim() === "" || email.trim() === "" || password.trim() === "" || cpassword.trim() === "" || role.trim() === "") {
    	errorMessage.innerHTML = "Please fill in all information.";
        return false;
    }

    // Run individual validations
    var isPasswordValid = validatePassword();
    var doPasswordsMatch = validatePasswordMatch();
    var isEmailValid = validateEmail();


    // If password is invalid or passwords don't match, prevent form submission
    if (!isPasswordValid || !doPasswordsMatch || !isEmailValid) {
        return false;
    }
    // Clear error message if all validations pass
    errorMessage.innerHTML = "";
    return true;
}

//Function to toggle password visibility for password field
function togglePasswordVisibility() {
    var passwordInput = document.getElementById("password");
    passwordInput.type = this.checked ? "text" : "password";
}

// Function to toggle password visibility for confirm password field
function toggleConfirmPasswordVisibility() {
    var cpasswordInput = document.getElementById("confirmPassword");
    cpasswordInput.type = this.checked ? "text" : "password";
}

// Add event listener to the password checkbox
document.getElementById("showPasswordCheckbox").addEventListener("change", togglePasswordVisibility);

// Add event listener to the confirm password checkbox
document.getElementById("showPasswordCheckbox1").addEventListener("change", toggleConfirmPasswordVisibility);


document.getElementById("password").addEventListener("input", validatePassword);
document.getElementById("confirmPassword").addEventListener("input", validatePasswordMatch);
document.getElementById("email").addEventListener("input", validateEmail);


</script>

<%@ include file="layouts/footer.jsp" %>
