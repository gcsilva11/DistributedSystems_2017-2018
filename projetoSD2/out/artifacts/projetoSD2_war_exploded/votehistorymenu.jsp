<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>iVotas: Menu de Admin - Historico Votos</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
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
    <s:form action="listAllLocal" method="post" >
        <li>ID User: <s:textfield name="userID" value="0"/><br></li>
        <s:submit value="Verificar Detalhes" cssClass="btn btn-default"/>
    </s:form>
</div>

<div class="table-responsive">
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>ID Eleicao</th>
            <th>Local de Voto</th>
        </tr>
        </thead>
        <c:forEach items="${ListAllLocalBean.placesVoted}" var="place">
            <tbody>
            <tr>
                <td><c:out value="${place.get(0)}" /></td>
                <td><c:out value="${place.get(1)}" /></td>
            </tr>
            </tbody>
        </c:forEach>
    </table>
</div>

</body>
</html>