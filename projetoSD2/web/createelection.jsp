<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="java" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>iVotas - Criar Eleicao</title>

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
        <li>Faculdade(ID): <s:textfield name="elecFacID"/></li><br>
        <s:submit value="Create Election"/>
    </s:form>
</div>
<div style="display:none;" id='genEle'>
    <s:form action="createElection" method="post">
        <br>Eleição De Estudantes:<br>
        <li>ID da Eleição: <s:textfield name="elecID"/></li>
        <li>Nome: <s:textfield name="elecName"/></li>
        <li>Descriçao: <s:textfield name="elecDescription"/></li>
        <li>Data de Inicio: <s:textfield name="elecStartDate"/></li>
        <li>Data de Fim: <s:textfield name="elecEndDate"/></li><br>
        <s:submit value="Create Election"/>
    </s:form>
</div>
<div>
    <s:form action="menuButtonAdmin">
        <s:submit  value= "Back To Menu" class="button" />
    </s:form>
</div>
</body>
</html>