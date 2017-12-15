<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="java" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>iVotas - Menu de Voto</title>

</head>
<body>
<div>

    <s:form action="listElection" method="get"/>

    <java:forEach items="${eleicao}" var="eleicoes">
        <tr>
            <td>${eleicao}</td>
        </tr>
    </java:forEach>

    <s:form>
        <li>ID Eleição: <s:textfield name="electionid"/></li>
    </s:form>


</div>

</body>

</html>