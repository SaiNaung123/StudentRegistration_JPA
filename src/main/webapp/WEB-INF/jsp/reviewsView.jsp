<%@ page import="java.util.List" %>
<%@ page import="student.model.Review" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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

<jsp:include page="navbar.jsp"></jsp:include>

<div class="container">
	<div id="sub_content">
    <h1>Reviews</h1>
    <% 
        List<Review> reviews = (List<Review>) request.getAttribute("reviews");
        if (reviews != null && !reviews.isEmpty()) {
            for (Review review : reviews) {
    %>
        <div class="card mb-3">
            <div class="card-header">
                <!-- Display stars based on the rating -->
                Rating: 
                <% for (int i = 0; i < review.getRating(); i++) { %>
                    &#9733; <!-- Unicode character for a filled star -->
                <% } %>
            </div>
            <div class="card-body">
                <p class="card-text">Review: <%= review.getReviewV() %></p>
                <!-- Add more fields as needed -->
            </div>
        </div>
    <% 
            }
        } else {
    %>
        <div class="alert alert-info" role="alert">
            No reviews found.
        </div>
    <%
        }
    %>
    </div>
</div>

<%@ include file="footer.jsp" %>
