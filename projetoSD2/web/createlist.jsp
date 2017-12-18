<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="java" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>iVotas - Criar Lista de Candidatos</title>

</head>
<body>
<java:if test="${session.Admin != true}">
    <java:redirect url="index.jsp"/>
</java:if>
<div>
    <h1>Criar Nova Eleição</h1>
</div>


<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js">
</script>
<script>
    $(document).ready(function(){
        $('#purpose').on('change', function() {
            if ( this.value == '1'){
                $('#genList').hide();
                $('#studentList').show();

            }
            else if(this.value == '2'){
                $('#studentList').hide();
                $('#genList').show();
            }
            else{
                $('#genList').hide();
                $('#studentList').hide();
            }
        });
    });
</script>

<select id = "purpose">
    <option value="0">None</option>
    <option value="1">Lista Para Eleição De Nucleos</option>
    <option value="2">Lista Para Eleição Geral</option>
</select>

<div style="display:none;" id='studentList'>
    <s:form action="createList" method="post">
        <br>Lista de Estudantes:<br>
        <li>Nome da Lista: <s:textfield name="listName"/></li>
        <li>ID da Eleição a associar: <s:textfield name="elecID"/></li>
        <s:textfield name="listType" value = "2" type ="hidden"/></li><br>
        <s:submit value="Criar Lista"/>
    </s:form>
</div>
<div style="display:none;" id='genList'>
    <s:form action="createList" method="post">
        <br>Lista de Concelho Geral:<br>
        <li>Nome da Lista: <s:textfield name="listName"/></li>
        <li>ID da Eleição a associar: <s:textfield name="elecID"/></li>
        <s:textfield name="listType" value = "1" type ="hidden"/></li><br>
        <s:submit value="Criar Lista"/>
    </s:form>
</div>
<div>
    <s:form action="menuButtonAdmin">
        <s:submit value="Voltar ao Menu" class="button" />
    </s:form>
</div>
</body>
</html>