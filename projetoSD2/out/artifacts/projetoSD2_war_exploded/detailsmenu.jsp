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
    <c:when test="${DetailElectionBean.votes==null || DetailElectionBean.votes.isEmpty()}">

        Oops - There are no finished elections with that id :(

    </c:when>
    <c:otherwise>
<div class="table-responsive">
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>Lista</th>
            <th>Votos</th>
            <th>Percentagem</th>
        </tr>
        </thead>
        <c:forEach items="${DetailElectionBean.votes}" var="voto">
            <tbody>
                <tr>
                    <td><c:out value="${voto.get(0)}"/></td>
                    <td><c:out value="${voto.get(1)}"/></td>
                    <td><c:out value="${voto.get(2)}"/></td>
                </tr>
            </tbody>
        </c:forEach>
    </table>
    </c:otherwise>
    </c:choose>
</div>
</body>
</html>