<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>iVotas: Menu de Admin - Registar User</title>
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
            <a class="navbar-brand" href="adminmenu.jsp">Menu de Admin</a>
        </div>
        <s:form action="AdminLogout">
            <button class="btn btn-danger navbar-btn navbar-right">Logout</button>
        </s:form>
    </div>
</nav>

<div>
    <s:form action="detailElection" method="post" >
        <li>ID Eleição: <s:textfield name="elecID" value="1"/><br></li>
        <s:submit value="Verificar Detalhes" cssClass="btn btn-default"/>
    </s:form>
</div>

    <c:choose>
    <c:when test="${DetailElectionBean.election==null||DetailELectionBean.size()==0}">

        Oops - There are no finished elections with that id :(

    </c:when>
    <c:otherwise>
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
        <tbody>
            <tr>
                <td><c:out value="${DetailElectionBean.election.get(0)}" /></td>
                <td><c:out value="${DetailElectionBean.election.get(1)}" /></td>
                <td><c:out value="${DetailElectionBean.election.get(2)}" /></td>
                <td><c:out value="${DetailElectionBean.election.get(3)}" /></td>
                <td><c:out value="${DetailElectionBean.election.get(4)}" /></td>
            </tr>
        </tbody>
    </table>
    </c:otherwise>
    </c:choose>
</div>
</body>
</html>