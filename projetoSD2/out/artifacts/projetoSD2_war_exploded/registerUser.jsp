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
    <s:form action="registerUser" method="post">
        <li>Nome: <s:textfield name="name"/></li>
        <li>ID: <s:textfield name="id"/></li>
        <li>Data de Validade: <s:textfield name="expDate"/></li>
        <li>Telefone: <s:textfield name="phone"/></li>
        <li>Morada: <s:textfield name="address"/></li>
        <li>Faculdade: <s:textfield name="faculdade"/><br>
        <li>Profissao: <s:textfield name="profession"/> 1-Estudante 2-Professor 3-Empregado</li>
        <li>Password: <s:password name="password"/><br></li>
        <s:submit value="Register" cssClass="btn btn-default"/>
    </s:form>
</div>
</body>

</html>