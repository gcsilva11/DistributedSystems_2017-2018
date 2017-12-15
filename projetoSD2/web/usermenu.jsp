<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="java" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>iVotas - Menu de Voto</title>

</head>
<body>
<java:if test="${session.loggedIn != true || session.username == null}">
    <java:redirect url="index.jsp"/>
</java:if>


    <div>
        <java:choose>
            <java:when test="${userBean.eleicoes.length == 0}">
                <div>
                    <h1>Nao ha eleicoes.</h1>
                </div>

            </java:when>
            <java:otherwise>
                <div>
                    <java:forEach items="${userBean.eleicoes}" var="eleicoes">
                        <table class="table table-bordered">
                            <tr>
                                <td><java:out value="${}" /></td>
                                <td>
                                    <s:form action="detailsAuction" method="POST">
                                        <input type="hidden" name="id" value="${auction.getId()}"/>
                                        <s:submit value="Details" class="btn btn-default"/>
                                    </s:form>
                                </td>
                            </tr>
                        </table>
                    </java:forEach>
                </div>
            </java:otherwise>
        </java:choose>
    </div>










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