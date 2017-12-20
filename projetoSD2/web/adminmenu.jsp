<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="java" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>iVotas: Menu de Admin</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
</head>
<body>
<java:if test="${session.Admin != true}">
    <java:redirect url="index.jsp"/>
</java:if>

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="adminmenu.jsp">Menu de Admin</a>
        </div>
        <s:form action="AdminLogout">
            <button class="btn btn-danger navbar-btn navbar-right">Logout</button>
        </s:form>
    </div>
</nav>

<div>
    <s:form action="registerUserMenu">
        <s:submit  value= "Register New User" cssClass="btn btn-default" />
    </s:form>

    <s:form action="editUserMenu">
        <s:submit  value= "Edit User" cssClass="btn btn-default" />
    </s:form>

    <s:form action="createElectionMenu">
        <s:submit  value= "Create election" cssClass="btn btn-default" />
    </s:form>

    <s:form action="CreateListMenu">
        <s:submit  value= "Create candidate list" cssClass="btn btn-default" />
    </s:form>

    <s:form action="EditElectionMenu">
        <s:submit  value= "Edit Elections" cssClass="btn btn-default" />
    </s:form>

    <s:form action="VoteBoothMenu">
        <s:submit  value= "Add/Remove Booths" cssClass="btn btn-default" />
    </s:form>

    <s:form action="ElectionListMenu">
        <s:submit  value= "Election Details" cssClass="btn btn-default" />
    </s:form>

    <s:form action="ElectionDetailsMenu">
        <s:submit  value= "Past Elections" cssClass="btn btn-default" />
    </s:form>

    <s:form action="VoteHistoryMenu">
        <s:submit  value= "User Vote History" cssClass="btn btn-default" />
    </s:form>
</div

</body>

</html>