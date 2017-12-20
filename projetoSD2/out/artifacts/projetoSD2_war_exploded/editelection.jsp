<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="java" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>iVotas: Menu de Admin - Editar Eleição</title>
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
    <h1>Editar Eleição</h1>
</div>


<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js">
</script>
<script>
    $(document).ready(function(){
        $('#purpose').on('change', function() {
            if ( this.value == '1'){
                $('#eleDate').hide();
                $('#eleText').show();

            }
            else if(this.value == '2'){
                $('#eleText').hide();
                $('#eleDate').show();
            }
            else{
                $('#eleDate').hide();
                $('#eleText').hide();
            }
        });
    });
</script>

<select id = "purpose">
    <option value="0">None</option>
    <option value="1">Edit Title/Description</option>
    <option value="2">Edit Start Date/End Date</option>
</select>

<div style="display:none;" id='eleText'>
    <s:form action="editElText" method="post">
        <br>Edição de Texto:<br>
        <li>ID da Eleição: <s:textfield name="elecID"/></li>
        <li>Novo nome: <s:textfield name="newElecName"/></li>
        <li>Nova descrição: <s:textfield name="newElecDescription"/></li><br>
        <s:submit value="Edit Election" cssClass="btn btn-default"/>
    </s:form>
</div>
<div style="display:none;" id='eleDate'>
    <s:form action="editElDate" method="post">
        <br>Edição de Data:<br>
        <li>ID da Eleição: <s:textfield name="elecID"/></li>
        <li>Nova data de inicio: <s:textfield name="newStartDate"/></li>
        <li>Nova data de fim: <s:textfield name="newEndDate"/></li><br>
        <s:submit value="Edit Election" cssClass="btn btn-default"/>
    </s:form>
</div>
<s:if test="hasActionMessages()">
        <s:actionmessage/>
</s:if>
</body>
</html>