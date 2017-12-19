<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="java" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>iVotas - Menu de Voto</title>

</head>
<body>
<java:if test="${session.loggedin != true || session.username == null}">
    <java:redirect url="index.jsp"/>
</java:if>

<div class="container">
    <div class="row">
        <s:form action="listElectionLists">
            <s:select label="ListaEleicoes" headerValue="ListaEleicoes" list="userBean.elNames" name="nomeEleicao"/>
            <s:submit value="Escolher Eleição" class="button"/>
        </s:form>
    </div>
</div>


</body>

</html>