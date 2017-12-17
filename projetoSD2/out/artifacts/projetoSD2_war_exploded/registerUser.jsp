<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>iVotas - Admin</title>

</head>
<body>
<java:if test="${session.Admin != true}">
    <java:redirect url="index.jsp"/>
</java:if>
<div>
    <h1>Registar Novo User</h1>
</div>

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
        <s:submit value="Register"/>
    </s:form>
</div>
<div>
    <s:form action="menuButtonAdmin">
    <s:submit  value= "Back To Menu" class="button" />
    </s:form>
</div>
</body>

</html>