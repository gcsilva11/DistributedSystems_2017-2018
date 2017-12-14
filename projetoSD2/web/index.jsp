<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>iVotas - Online auction</title>
</head>
<body>

<H1>iVotas - Voting online</H1>
<h3>SD 17/18</h3>
<s:form action="login" method="post">
  <li>Username: <s:textfield name="username" /></li>
  <li>Password: <s:password name="password"/><br></li>
  <s:submit/>
</s:form>
</body>
</html>