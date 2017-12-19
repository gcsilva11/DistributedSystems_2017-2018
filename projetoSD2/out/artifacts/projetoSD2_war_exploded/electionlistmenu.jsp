<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>iVotas: Menu de Admin - Lista De Eleições</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
<java:if test="${session.Admin != true}">
    <java:redirect url="index.jsp"/>
</java:if>

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="adminmenu.jsp">Menu de Voto</a>
        </div>
        <s:form action="AdminLogout">
            <button class="btn btn-danger navbar-btn navbar-right">Logout</button>
        </s:form>
    </div>
</nav>
<div>
    <h1>Lista De Eleições</h1>
</div>

<div>
    <s:form action="listAllElections">
        <s:submit  value= "List Elections" cssClass="btn btn-default listbutton"/>
    </s:form>
</div>
<div class="table-responsive">
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>ID</th>
                <th>Titulo</th>
                <th>Descriçao</th>
                <th>Start Date</th>
                <th>End Date</th>
            </tr>
        </thead>
    <c:forEach items="${ListAllElectionsBean.elections}" var="election">
        <tbody>
            <tr>
                <td><c:out value="${election.get(0)}" /></td>
                <td><c:out value="${election.get(1)}" /></td>
                <td><c:out value="${election.get(2)}" /></td>
                <td><c:out value="${election.get(3)}" /></td>
                <td><c:out value="${election.get(4)}" /></td>
            </tr>
        </tbody>
    </c:forEach>
    </table>
</div>
</body>

</html>