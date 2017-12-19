<%--
  Created by IntelliJ IDEA.
  User: joaoferreiro
  Date: 18/12/2017
  Time: 18:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="java" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>iVotas: Menu de Voto</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
</head>
<body>
<java:if test="${session.loggedin != true || session.username == null}">
    <java:redirect url="index.jsp"/>
</java:if>

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="usermenu.jsp">Menu de Voto</a>
        </div>
        <s:form action="logout">
            <button class="btn btn-danger navbar-btn navbar-right">Logout</button>
        </s:form>
    </div>
</nav>

<div class="content">
    <div class="row">
        <div class="col-xs-3"></div>
        <div class="col-xs-3">
            <s:form action="listElections">
                <s:submit  value="Votar" cssClass="btn btn-default"/>
            </s:form>
        </div>
        <div class="col-xs-3"></div>
        <div class="col-xs-3">
            <s:form action="VotedListMenu">
                <s:submit  value="Listar Locais de Voto" cssClass="btn btn-default"/>
            </s:form>
        </div>
    </div>
</div>
</body>
</html>
