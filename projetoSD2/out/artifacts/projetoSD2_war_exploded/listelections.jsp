<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="java" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>iVotas - Menu de Voto</title>

</head>
<body>
<java:if test="${session.loggedin != true || session.username == null}">
    <java:redirect url="index.jsp"/>
</java:if>

<div>
    <java:forEach var="nomeEleicao" items="${eleicoes}">
        <s:form action="chooseList" method="post">
            ${nomeEleicao}<br>
            <div>
                <input type="button" class="btn btn-default" value="ESCOLHER ELEICAO" onclick="window.location = 'listelectionlists.jsp';${idEleicao}=${i}">
            </div>
        </s:form>
    </java:forEach>

    <jsp:useBean id="eleicoes" type="java.util.ArrayList" scope="session" />
    <% for (int i = 0 ; i < eleicoes.size() ; i++){ %>
        <tr>
            <td>${eleicoes.get(i)}</td>
            <div>
                <input type="button" class="btn btn-default" value="ESCOLHER ELEICAO" onclick="window.location = 'listelectionlists.jsp';<java:set var = "idEleicao" scope = "session" value = "${i}"/>">
            </div>
        </tr>
    <% } %>


    <%--
    <div>
        <input type="button" class="btn btn-default" value="Create auction" onclick="window.location = 'createAuction.jsp'" style="margin-bottom: 2rem">
    </div>

    <form method="post" action="connectFace2">
        <button type="submit" method="execute" class="btn btn-default btn-facebook">
            <i class="fa fa-facebook"></i> Associate with Facebook
        </button>
    </form>
    --%>
</div>

</body>

</html>