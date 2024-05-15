

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@page import="student.model.Role"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.List"%>
<%@page import="student.model.User"%>
<%@page import="student.repository.UserDao"%>

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
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@10/dist/sweetalert2.min.css">
<title>Top Menu MNU001</title>
<style>
    h2,
    label {
        color: black;
    }
</style>
</head>
<%
Role userRole = (Role) session.getAttribute("role");
%>
<jsp:include page="navbar.jsp"></jsp:include>
<%
    // Import necessary classes
    // Import your User and UserDao classes here

    // Check if a search query for name or ID is submitted
    String searchName = request.getParameter("uname");
    String searchId = request.getParameter("uid");
    List<User> users;

    if ((searchName != null && !searchName.isEmpty()) || (searchId != null && !searchId.isEmpty())) {
        UserDao dao = new UserDao();

        if ((searchName != null && !searchName.isEmpty()) && (searchId != null && !searchId.isEmpty())) {
            // Perform search by both ID and name if both search queries are provided
            int id = -1;
            try {
                id = Integer.parseInt(searchId);
            } catch (NumberFormatException e) {
                // Handle invalid ID format
                e.printStackTrace();
                // Respond with an error message or redirect to an error page
            }
				
            users = dao.selectAdminByIdAndName(id, searchName);
        } else if (searchName != null && !searchName.isEmpty()) {
            // Perform search by name if only name search query is provided
            users = dao.searchAdminsByName(searchName);
        } else {
            // Perform search by ID if only ID search query is provided
            int id = -1;
            try {
                id = Integer.parseInt(searchId);
            } catch (NumberFormatException e) {
                // Handle invalid ID format
                e.printStackTrace();
                // Respond with an error message or redirect to an error page
            }

            User user = dao.getAdminById(id);
            if (user != null) {
                users = new ArrayList<>();
                users.add(user);
            } else {
                users = Collections.emptyList(); // No user found for the given ID
            }
        }
    } else {
        // Otherwise, fetch all users
        UserDao dao = new UserDao();
        users = dao.selectActiveAdmins();
    }

    // Set the users list as a request attribute
    request.setAttribute("users", users);
%>


<div class="main_contents">
    <div id="sub_content">
        <form  method="post" class="row g-3 mt-3 ms-2">
            <div class="col-auto">
                <label for="staticEmail2" class="visually-hidden">User Id</label>
                <!-- Ensure correct name attribute for user ID input -->
                <input type="text" class="form-control" id="staticEmail2" placeholder="User ID" name="uid" value="${param.uid}" oninput="validateIdInput(event)">
            </div>
            <div class="col-auto">
                <label for="inputPassword2" class="visually-hidden">User Name</label>
                <input type="text" class="form-control" id="inputPassword2" placeholder="User Name" name="uname" value="${param.uname}" oninput="validateNameInput(event)">
            </div>

            <div class="col-auto">
                <button type="submit" class="btn btn-primary mb-3">Search</button>
            </div>
             <% 
    if(userRole != null && userRole == Role.ADMIN || userRole == Role.SUPER_ADMIN ) { %>
            <div class="col-auto">
                <button type="button" class="btn btn-secondary" onclick="location.href = 'userregister';">
                    Add
                </button>
            </div>
            <%} %>
            <div class="col-auto">
                <!-- Move the reset button inside the form tag -->
                <button type="reset" class="btn btn-secondary mb-3" style="background-color: red;" onclick="location.href = 'adminview';">Reset</button>
            </div>
        </form>

<c:if test="${not empty users}">
        <table class="table table-striped" id="stduentTable">
            <thead>
                <tr>

                    <th scope="col">User ID</th>
                    <th scope="col">User Name</th>
                    <th scope="col">Role</th>
                    <th scope="col">Details</th>

                </tr>
            </thead>
            <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>

                        <td>USR00${user.getId() }</td>
                        <td>${user.getName() }</td>
                        <td>${user.getRole() }</td>

                        <td>
                            <button type="button" class="btn btn-success  "
                                onclick="location.href = 'adminprofile?id=${user.getId()}';">
                                See More
                            </button>
                        </td>
                        <%
    if(userRole != null && userRole == Role.SUPER_ADMIN) {%>
                        <td>
                            <c:if test="${user.getRole() eq 'Admin'}">
                <button type="button" class="btn btn-secondary mb-3"
                           onclick="confirmDelete('${user.id}')">
                          Delete</button>
                 
            </c:if>
                        </td>
                        <%} %>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        </c:if>
<c:if test="${empty users}">
    <div style="color: red">
        No users found.
    </div>
</c:if>
</div>
        
        <!-- Hidden input field to store the user ID to be deleted -->
        <input type="hidden" id="userIdToDelete" name="userIdToDelete">

        <!-- Modal -->
        
    </div>

<div id="testfooter">
    <span>Copyright &#169; ACE Inspiration 2022</span>
</div>
<%@ include file="footer.jsp" %>

<!-- JavaScript to set the user ID to be deleted and trigger deletion -->
<script>
    function confirmDelete(userId) {
        Swal.fire({
          title: "DELETE..",
            text: "Are you sure you want to delete?",
            icon: "question",
            showCancelButton: true,
            confirmButtonColor: "#43b974",
            cancelButtonColor: "#9c9898",
            confirmButtonText: "Delete"
        }).then((result) => {
            if (result.isConfirmed) {
                // If user confirms, redirect to delete servlet
                window.location.href = 'StudentRegistration_JPA/userdelete/' + userId;
            }
        });
    }
</script>

<script>
    <%-- Check if success or error message exists and display appropriate alert --%>
    <% if (request.getAttribute("success") != null) { %>
        alert("Successfully updated!");
    <% } else if (request.getAttribute("perror") != null) { %>
        alert("Password does not match. Try again.");
    <% } else if (request.getAttribute("error") != null) { %>
        alert("Update failed.");
    <% } %>
</script>


<script>
    function validateIdInput(event) {
        // Get the value entered by the user
        const inputValue = event.target.value;
        
        // Regular expression to match any non-numeric character
        const regex = /\D/;
        
        // Check if the input contains any non-numeric character
        if (regex.test(inputValue)) {
            // If a non-numeric character is found, prevent it from being input
            event.target.value = inputValue.replace(/\D/g, '');
        }
    }

    function validateNameInput(event) {
        // Get the value entered by the user
        const inputValue = event.target.value;
        
        // Regular expression to match any digit
        const regex = /\d/;
        
        // Check if the input contains any digit
        if (regex.test(inputValue)) {
            // If a digit is found, prevent it from being input
            event.target.value = inputValue.replace(/\d/g, '');
        }
    }
</script>