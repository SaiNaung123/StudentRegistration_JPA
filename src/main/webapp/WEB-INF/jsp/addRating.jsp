
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org/">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Feedback</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="resources/test.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
    integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    
    
   <style>
        
.container1 {
    width: 50%;
    background-color: rgb(93, 189, 115);
    margin: auto;
    padding: 0.5rem 3rem;
    border-radius: 20px;
    /* Remove margin-top */
    /* Add absolute positioning */
    position: absolute;
    top: 40px; /* Adjust according to your navbar height */
    left: 17%;
    right: 1;
    /* Add z-index to ensure it's above the navbar */
    z-index: 1;
    /* Add box-shadow */
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5);
}

        .header {
		    color: #e8eef1; /* Text color */
		    background-color: none; /* Background color */
		    border-bottom: 2px solid #1d6d70; /* Darker border on bottom */
		    border-top: 2px solid #1d6d70; /* Darker border on top */
		    padding: 0.6rem 0; /* Increase padding for better spacing */
		    text-align: center; /* Center-align text */
		    font-size: 1.5rem; /* Increase font size for emphasis */
		}
        .star{
            color: rgb(182,172 , 172);
            font-size: 1.3rem;
        }
        #review{
            width: 100%;
            background: initial;
            color: seashell;
            height: 187px;
        }

        .btns {
    margin: 1rem 0rem;
    display: flex;
    justify-content: flex-end; /* Align items to the end of the container */
}

.btns button {
    margin-right: 0.5rem; /* Adjust this value to control the space between buttons */
    border-radius: 8px;
    font-weight: bold;
    padding: 10px 20px; /* Add padding to give buttons more space */
    background-color: #4CAF50; /* Set background color */
    color: white; /* Set text color */
    border: none; /* Remove border */
    cursor: pointer; /* Change cursor on hover */
    transition: background-color 0.3s ease; /* Add transition for smooth hover effect */
}

/* Hover effect */
.btns button:hover {
    background-color: #45a049; /* Darker background color on hover */
}

/* Additional styles for different button states (optional) */
.btns button:focus {
    outline: none; /* Remove focus outline */
    box-shadow: 0 0 0 3px rgba(0, 0, 0, 0.2); /* Add a subtle focus effect */
}

.btns button:active {
    transform: translateY(1px); /* Add a slight downward shift on button press */
}

       h3 {
    color: black; ; /* Text color */
    font-size: 1.5rem; /* Font size */
    font-weight: bold; /* Bold font weight */
    margin-bottom: 0.5rem; /* Bottom margin for spacing */
}


        #btnsubmit{
            background-color: rgb(82, 126, 219);
            color: white;
        }

        #btncancel{
            background-color: rgb(96, 99, 98);
            color: white;
        }
  
        </style>


    <script>

        let rating;
        let reviewV;
        let ratingV;

        let chg=(star)=>{
            for(i=1;i<=5;i++){
                document .getElementById(i).style.color="rgb(182,172 , 172)";
            }
            let n=parseInt(star.id);
            rating=n;
            for(i=1;i<=n;i++){
                document .getElementById(i).style.color="#d7e60c";
            }
            document.getElementById("rating").value = rating;

            reviewV = document.getElementById("review").value;
        }
    </script>
    
</head>
	
<jsp:include page="navbar.jsp"></jsp:include>

<div class="container">	
	<div id="sub_content">		
		<div class="container1">
    <form method="post" >
        <h2 class="header">PLEASE! Rate and Review:</h2>
        <p style="text-align: center;">${error }</p>
         <p style="text-align: center">${success }</p>
        <h3>Rating</h3>
        <div class="star">
            <i class="fas fa-star" id="1" onclick="chg(this);"></i>
            <i class="fas fa-star" id="2" onclick="chg(this);"></i>
            <i class="fas fa-star" id="3" onclick="chg(this);"></i>
            <i class="fas fa-star" id="4" onclick="chg(this);"></i>
            <i class="fas fa-star" id="5" onclick="chg(this);"></i>
        </div>
        <h3>Review</h3>
        <textarea name="reviewV" id="review" rows="10"></textarea>
        <input type="hidden" name="rating" id="rating" value="" />
        <div class="btns">
            <button type="submit" id="btnsubmit">SUBMIT</button>
        </div>
    </form>
    
</div>
	</div>
</div>


<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>
</html>