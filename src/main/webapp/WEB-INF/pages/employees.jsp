<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
</head>
<body>
<h1>Employees</h1>
<c:if test="${not empty list}">
    <table>
        <tr><td>ID</td><td>Name</td><td>Email</td><td>Dept</td><td>Location</td></tr>
        <c:forEach var="val" items="${list}" varStatus="status">
            <tr>
                <td>${val.id}</td><td>${val.name}</td><td>${val.email}</td><td>${val.dept}</td><td>${val.location}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

</body>
</html>


