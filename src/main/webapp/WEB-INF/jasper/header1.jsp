<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ page import="java.util.Date" %>
	<%@ page import="java.text.SimpleDateFormat" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>header</title>
</head>
<body>
	<%
		    Date currentDate = new Date();
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    String formattedDate = dateFormat.format(currentDate);
		%>
		
	<fmt:formatNumber type="number" pattern = "000" 
		value ="${adminId}" var="formattedId"/>	
		 
    <div id="testheader">
        <div class="container">
            <div class=row>        
                <div class="col-md-5 ">
            <a href="/StudentRegistration_SpringJPA/adminhome"><h3>Student Registration</h3></a>
        </div>  
        <div class="col-md-6">
        	
        	<%-- <p>Admin Name :${adminId} ${adminName }</p> --%>
            <p>Admin Name :USR${formattedId} ${adminName }</p>
            <p>Current Date : <%= formattedDate %></p>
        </div>  
         <div class="col-md-1">
                <a href="logout" class="btn btn-success">Logout</a>
            </div>  
          
    </div>
</div>
</div>

</body>
</html>