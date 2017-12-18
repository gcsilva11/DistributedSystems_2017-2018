<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="java" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>iVotas - Menu Admin</title>

</head>
<body>
<java:if test="${session.Admin != true}">
    <java:redirect url="index.jsp"/>
</java:if>
<div>
    <h1>Menu Admin</h1>
</div>

<div>
    <s:form action="registerUserMenu">
        <s:submit  value= "Register New User" class="button" />
    </s:form>

    <s:form action="createElectionMenu">
        <s:submit  value= "Create election" class="button" />
    </s:form>

    <s:form action="CreateListMenu">
        <s:submit  value= "Create candidate list" class="button" />
    </s:form>

    <s:form action="VoteBoothMenu">
        <s:submit  value= "Add/Remove Booths" class="button" />
    </s:form>
</div

</body>

</html>