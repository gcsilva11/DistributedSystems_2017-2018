<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="java" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>iVotas - Adicionar User</title>
</head>
<body>

<H1>Add user to list</H1>
<s:form action="addUserToList" method="post">
  <li>ID do user a acrescentar: <s:textfield name="userID"/></li><br>
  <s:submit value="Adicionar User"/>
</s:form>
<h4>${message}</h4>

</body>
</html>