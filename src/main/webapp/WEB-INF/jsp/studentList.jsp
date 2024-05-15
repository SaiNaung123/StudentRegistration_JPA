<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
<title>Student List</title>
<style>

.dataTables_filter {
 margin-bottom: 10px;
    position: absolute;
    top: 7px; /* Adjust top positioning */
    left: 60%; /* Move filter to 3/4 of the screen */
    width: 50%; /* Set width to half of the screen */
}

</style>
</head>
<body>
	<jsp:include page="navbar.jsp" />

<div class="container">
    <jsp:include page="header.jsp" />
		<div class="main_contents">
			<div id="sub_content">
			 <a href="generateReport?export=excel"> Excel</a> 
    		<a href="generateReport?export=pdf"> PDF</a>
			
				<div class="success" style="text-align:center; color:green">${success }</div>
				
				<div class="table-responsive table mt-2" id="dataTable" role="grid"
					aria-describedby="dataTable_info">
					<table id="mytable" class="display" style="width: 95%">
						<thead>
							<tr>
								<th>Student Id</th>
								<th>Image</th>
								<th>Student Name</th>
								<th>Courses</th>
								<th>Details</th>
							</tr>
						</thead>
						<tbody>
							<c:set var="itemsPerPage" value="5" />

							<!-- Iterate over each student -->
							<c:forEach var="list" items="${StudentList}" varStatus="i">
								<c:if test="${!list.deleted}">
									<tr>
										<td>${i.index + 1}</td>
										<td><img class="rounded-circle me-2" width="60"
											height="60" src="resources/images/${list.photo}"></td>
										<td>${list.name}</td>
										<td>
											<!-- Iterate over each course of the student and display -->
											<c:forEach var="course" items="${list.courses}"
												varStatus="loop">
       												 ${course.name}
        												<c:if test="${!loop.last}">, </c:if>
												<!-- Add comma if not last course -->
											</c:forEach>
										</td>
										<td><a class="btn btn-primary mb-3"
											href="studentupdate?id=${list.id}">Update</a> <a
											class="btn btn-danger mb-3"
											href="softDeleteStudent?id=${list.id}">Delete</a></td>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>

					</table>
				</div>
			</div>
		</div>
	</div>

	<!-- Include jQuery, Bootstrap JS, and DataTables JS -->
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

	<!-- Data Table JS -->
	<script type="text/javascript"
		src="https://code.jquery.com/jquery-3.7.0.js"></script>
	<script type="text/javascript"
		src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>

	<script>
		$(document).ready(function() {
			const usersTable = $('#mytable').DataTable({
				pagingType : 'full_numbers',
				"lengthMenu" : [ 3, 5, 10, 15, -1 ],
				"pageLength" : 5,
			});
		});
	</script>
</body>
</html>
