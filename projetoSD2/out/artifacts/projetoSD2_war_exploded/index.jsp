<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="java" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>iVotas - Online Auction</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
  <link rel="stylesheet" href="css/styles.css">
</head>
<body>

<H1>iVotas - Voting online</H1>
<h3>SD 17/18</h3>
<s:form action="login" method="post">
  <li>Nome: <s:textfield name="username"/></li>
  <li>Password: <s:password name="password"/><br></li>
  <s:submit value="LOGIN" cssClass="btn btn-default"/>
</s:form>
<h4>${session.message}</h4>

</body>
</html>