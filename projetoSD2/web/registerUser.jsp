<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>iVotas - Admin</title>

</head>
<body>
<div>
    <h1>Menu Admin</h1>
</div>

<div>
    <s:form action="registerUser" method="post">
        <s:submit value="Register New User...." class="btn btn-default" />
    </s:form>
</div>

</body>

</html>