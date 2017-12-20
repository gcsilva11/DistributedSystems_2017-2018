<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="java" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>iVotas: Menu de Admin - Mesas De Voto</title>
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
    <h1>Adicionar/Remover Mesas de Voto</h1>
</div>


<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js">
</script>
<script>
    $(document).ready(function(){
        $('#purpose').on('change', function() {
            if ( this.value == '1'){
                $('#removeBooth').hide();
                $('#addBooth').show();

            }
            else if(this.value == '2'){
                $('#addBooth').hide();
                $('#removeBooth').show();
            }
            else{
                $('#addBooth').hide();
                $('#removeBooth').hide();
            }
        });
    });
</script>

<select id = "purpose">
    <option value="0">None</option>
    <option value="1">Adicionar Mesa</option>
    <option value="2">Remover Mesa</option>
</select>

<div style="display:none;" id='addBooth'>
    <s:form action="addBooth" method="post">
        <br>Adicionar Mesa de Voto:<br>
        <li>ID da Faculdade: <s:textfield name="facID"/></li>
        <li>ID da Eleiçao: <s:textfield name="elecID"/></li><br>
        <s:submit value="Adicionar Mesa" cssClass="btn btn-default"/>
    </s:form>
</div>
<div style="display:none;" id='removeBooth'>
    <s:form action="removeBooth" method="post">
        <br>Remover Mesa de Voto:<br>
        <li>ID da Faculdade: <s:textfield name="facID"/></li>
        <li>ID da Eleiçao: <s:textfield name="elecID"/></li><br>
        <s:submit value="Remover Mesa" cssClass="btn btn-default"/>
    </s:form>
</div>
<s:if test="hasActionMessages()">
    <s:actionmessage/>
</s:if>
</body>
</html>