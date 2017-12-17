<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="java" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>iVotas - Menu Admin</title>

</head>
<body>
<java:if test="${session.loggedin != true || session.username == null}">
    <java:redirect url="index.jsp"/>
</java:if>
<div>
    <h1>Menu Admin</h1>
</div>

<div>
    <s:form action="registerUserMenu">
        <s:submit  class="button" />
    </s:form>
</div

</body>

</html>