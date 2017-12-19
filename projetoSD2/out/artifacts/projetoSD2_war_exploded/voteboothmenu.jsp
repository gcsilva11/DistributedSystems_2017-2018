<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="java" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>iVotas - Mesas De Voto</title>

</head>
<body>
<java:if test="${session.Admin != true}">
    <java:redirect url="index.jsp"/>
</java:if>
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
        <s:submit value="Adicionar Mesa"/>
    </s:form>
</div>
<div style="display:none;" id='removeBooth'>
    <s:form action="removeBooth" method="post">
        <br>Remover Mesa de Voto:<br>
        <li>ID da Faculdade: <s:textfield name="facID"/></li>
        <li>ID da Eleiçao: <s:textfield name="elecID"/></li><br>
        <s:submit value="Remover Mesa"/>
    </s:form>
</div>
<s:if test="hasActionMessages()">
    <s:actionmessage/>
</s:if>
<div>
    <s:form action="menuButtonAdmin">
        <s:submit value="Back To Menu" class="button" />
    </s:form>
</div>
</body>
</html>