<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>iVotas - Lista De Eleições</title>

</head>
<body>
<java:if test="${session.Admin != true}">
    <java:redirect url="index.jsp"/>
</java:if>
<div>
    <h1>Lista De Eleições</h1>
</div>

<div>
    <s:form action="menuButtonAdmin">
        <s:submit  value= "Back To Menu" class="button" />
    </s:form>
</div>

<div class="container">
    <div class="row">
        <s:form action="listElectionLists">
            <s:select label="ListaEleicoes" headerValue="ListaEleicoes" list="userBean.elNames" name="nomeEleicao"/>
            <s:submit value="Escolher Eleição" class="button"/>
        </s:form>
    </div>
</div>
<div>
    <s:form action="menuButtonAdmin">
    <s:submit  value= "Back To Menu" class="button" />
    </s:form>
</div>
</body>

</html>