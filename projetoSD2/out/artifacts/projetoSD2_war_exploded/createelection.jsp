<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="java" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>iVotas: Menu de Admin - Criar Eleicao</title>
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
            <a class="navbar-brand" href="adminmenu.jsp">Menu de Voto</a>
        </div>

        <s:form action="AdminLogout">
            <button class="btn btn-danger navbar-btn navbar-right">Logout</button>
        </s:form>
    </div>
</nav>

<div>
    <h1>Criar Nova Eleição</h1>
</div>


<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js">
</script>
<script>
    $(document).ready(function(){
        $('#purpose').on('change', function() {
            if ( this.value == '1'){
                $('#genEle').hide();
                $('#studentEle').show();

            }
            else if(this.value == '2'){
                $('#studentEle').hide();
                $('#genEle').show();
            }
            else{
                $('#genEle').hide();
                $('#studentEle').hide();
            }
        });
    });
</script>

<select id = "purpose">
    <option value="0">None</option>
    <option value="1">Student Election</option>
    <option value="2">General Election</option>
</select>

<div style="display:none;" id='studentEle'>
    <s:form action="createElection" method="post">
        <br>Eleição De Estudantes:<br>
        <li>ID da Eleição: <s:textfield name="elecID"/></li>
        <li>Nome: <s:textfield name="elecName"/></li>
        <li>Descriçao: <s:textfield name="elecDescription"/></li>
        <li>Data de Inicio: <s:textfield name="elecStartDate"/></li>
        <li>Data de Fim: <s:textfield name="elecEndDate"/></li>
        <li>Faculdade(ID): <s:textfield name="elecFacID"/></li>
        <s:textfield name="elecType" value = "2" type="hidden"/></li><br>
        <s:submit value="Create Election" cssClass="btn btn-default"/>
    </s:form>
</div>
<div style="display:none;" id='genEle'>
    <s:form action="createElection" method="post">
        <br>Eleição Geral:<br>
        <li>ID da Eleição: <s:textfield name="elecID"/></li>
        <li>Nome: <s:textfield name="elecName"/></li>
        <li>Descriçao: <s:textfield name="elecDescription"/></li>
        <li>Data de Inicio: <s:textfield name="elecStartDate"/></li>
        <li>Data de Fim: <s:textfield name="elecEndDate"/></li>
        <s:textfield name="elecFacID" value = "0" type ="hidden"/></li>
        <s:textfield name="elecType" value = "1" type="hidden"/></li><br>
        <s:submit value="Create Election" cssClass="btn btn-default"/>
    </s:form>
</div>
<s:if test="hasActionMessages()">
    <s:actionmessage/>
</s:if>
</body>
</html>