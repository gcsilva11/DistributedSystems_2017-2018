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
    <title>iVotas - Menu de Voto</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
</head>
<body>
<java:if test="${session.loggedin != true || session.username == null}">
    <java:redirect url="index.jsp"/>
</java:if>

<div class="content">
    <div class="row">
        <div class="col-xs-6">
            <s:form action="listElections">
                <s:submit  value= "Votar" class="button" />
            </s:form>
        </div>

        <div class="col-xs-6">
        <s:form action="logout">
            <s:submit  value= "Logout" class="button" />
        </s:form>
        </div>
    </div>
    <div class="row">
        <p>${session.eleicao}</p>
    </div>
    <div class="row">
        <p>${session.lista}</p>
    </div>
</div>
</body>
</html>
