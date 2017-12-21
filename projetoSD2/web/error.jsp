
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>iVotas - Error Page</title>

</head>
<body>
<div>
    <h1>Something didn't go as planned....</h1>
</div>
<div>
    <p>An error occurred:</p>
    <p><s:property value="exceptionStack" /></p>
</div>

<noscript>JavaScript must be enabled for WebSockets to work.</noscript>
<div style="float:right;">
    <h4>Online User List</h4>
    <div id="container"><div id="history"></div></div>
</div>


</body>

</html>