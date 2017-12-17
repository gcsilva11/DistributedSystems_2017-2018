<%--
  Created by IntelliJ IDEA.
  User: joaoferreiro
  Date: 17/12/2017
  Time: 02:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>iVotas - Menu de Voto</title>
</head>
<body>
<java:if test="${session.loggedin != true || session.username == null}">
    <java:redirect url="index.jsp"/>
</java:if>

<div>
    ${idEleicao}
    <%--<java:forEach var="nomeLista" items="${listas}">
        <s:form action="vote" method="post">
            ${nomeLista}<br>
        </s:form>
    </java:forEach>--%>
    oi
</div>

</body>

</html>
