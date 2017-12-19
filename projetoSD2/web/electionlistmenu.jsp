<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>iVotas - Lista De Eleições</title>

    <style>
        table {
            font-family: arial, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        td, th {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }

        tr:nth-child(even) {
            background-color: #dddddd;
        }
    </style>
</head>

</head>
<body>
<java:if test="${session.Admin != true}">
    <java:redirect url="index.jsp"/>
</java:if>
<div>
    <h1>Lista De Eleições</h1>
</div>

<div>
    <s:form action="menuButtonAdmin">
        <s:submit  value= "Back To Menu" class="button" />
    </s:form>
</div>

<div>
    <s:form action="listAllElections">
        <s:submit  value= "List Elections" class="button" />
    </s:form>
</div>
<div>
    <c:choose>
        <c:when test="${ListAllElectionsBean.elections.size()==0}">
            <div>
                <h3> No elections created yet.</h3>
            </div>
        </c:when>
        <c:otherwise>
            <div>
                <table class="table table-bordered">
                    <tr>
                        <th>ID</th>
                        <th>Titulo</th>
                        <th>Descriçao</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                    </tr>
                <c:forEach items="${ListAllElectionsBean.elections}" var="election">
                        <tr>
                            <td><c:out value="${election.get(0)}" /></td>
                            <td><c:out value="${election.get(1)}" /></td>
                            <td><c:out value="${election.get(2)}" /></td>
                            <td><c:out value="${election.get(3)}" /></td>
                            <td><c:out value="${election.get(4)}" /></td>
                        </tr>
                </c:forEach>
                </table>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>

</html>