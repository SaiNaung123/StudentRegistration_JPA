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
    
<title> Student Registration LGN001 </title>
</head>
<body class="login-page-body"> 
  
    <div class="login-page">
      <div class="form">
        <div class="login">
          <div class="login-header">
            <h1>Welcome!</h1>
            <h3>Login For Users</h3>
    		<p style="color: red">${error}</p>
    		<p style="color: red">${usererror}</p>
          </div>
        </div>
        <form class="login-form" method="post" name="confirm">
          <input type="text" placeholder="Email" name="email"/>
          <input type="password" placeholder="Password" name="password"/>
          <div class="col-md-4">
          </div>
          <button>login</button>
          <p class="message">Not registered as a user? <a href="userregister">Create an account</a></p>
        </form>
        
      </div>
    </div>
    <script>
  document.addEventListener('DOMContentLoaded', function() {
    var showPasswordCheckbox = document.getElementById('showPasswordCheckbox');
    var passwordField = document.querySelector('input[type="password"]');

    showPasswordCheckbox.addEventListener('change', function() {
      if (showPasswordCheckbox.checked) {
        passwordField.setAttribute('type', 'text');
      } else {
        passwordField.setAttribute('type', 'password');
      }
    });
  });
</script>
<%@ include file = "layouts/footer.jsp"%>