<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="java" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>iVotas: Menu de Admin - Criar Eleicao</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <script type="text/javascript">

        var websocket = null;

        window.onload = function() {
            connect('ws://' + window.location.host + '/ws');
            document.getElementById("chat").focus();
        }
        function connect(host) {
            if('WebSocket' in window)
                websocket = new WebSocket(host);
            else if('MozWebSocket' in window)
                websocket = new MozWebSocket(host);
            else{
                writeToHistory('Get a real browser which supports WebSocket.');
                return;
            }

            websocket.onopen    = onOpen; // set the event listeners below
            websocket.onclose   = onClose;
            websocket.onmessage = onMessage;
            websocket.onerror   = onError;
        }

        function onOpen(event) {
            var username = "${session.username}";
            websocket.send(username);
            writeToHistory('Connected to ' + window.location.host + '.');
        }

        function onClose(event) {
            writeToHistory('WebSocket closed.');
            document.getElementById('chat').onkeydown = null;
        }

        function onMessage(message) { // print the received message
            writeToHistory(message.data);
        }

        function onError(event) {
            writeToHistory('WebSocket error (' + event.data + ').');
            document.getElementById('chat').onkeydown = null;
        }


        /* function doSend() {
             var message = document.getElementById('chat').value;
             if (message != '')
                 websocket.send(message); // send the message
             document.getElementById('chat').value = '';
         }*/

        function writeToHistory(text) {
            var historyUsers = document.getElementById('history');
            while (historyUsers.firstChild) {
                historyUsers.removeChild(historyUsers.firstChild);
            }
            var line = document.createElement('p');
            line.style.wordWrap = 'break-word';
            line.innerHTML = text;
            historyUsers.appendChild(line);
            historyUsers.scrollTop = historyUsers.scrollHeight;
        }

    </script>

</head>
<body>
<java:if test="${session.loggedin != true || session.username == null}">
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

<noscript>JavaScript must be enabled for WebSockets to work.</noscript>
<div style="float:right;">
    <h4>Online User List</h4>
    <div id="container"><div id="history"></div></div>
</div>

</body>
</html>