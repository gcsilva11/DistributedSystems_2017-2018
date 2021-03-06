<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="java" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>iVotas: Menu de Admin - Mesas De Voto</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="css/styles.css">
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
            var message = "bye";
            websocket.send(username);
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

<java:if test="${session.loggedin != true || session.username != 'Admin'}">
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

<div class="container">
    <div class="row">
        <div class="col-xs-12 center">
            <h1>Adicionar/Remover Mesas de Voto</h1>
        </div>
        <div class="col-xs-12 center">
            <select id = "purpose">
                <option value="0">None</option>
                <option value="1">Adicionar Mesa</option>
                <option value="2">Remover Mesa</option>
            </select>
        </div>
        <div class="col-xs-12 center">
            <div class="hide-display" id='addBooth'>
                <s:form action="addBooth" method="post">
                    <br>Adicionar Mesa de Voto:<br>
                    <li>ID da Faculdade: <s:textfield name="facID"/></li>
                    <li>ID da Eleiçao: <s:textfield name="elecID"/></li><br>
                    <s:submit value="Adicionar Mesa" cssClass="btn btn-default"/>
                </s:form>
            </div>
        </div>
        <div class="col-xs-12 center">
            <div class="hide-display" id='removeBooth'>
                <s:form action="removeBooth" method="post">
                    <br>Remover Mesa de Voto:<br>
                    <li>ID da Faculdade: <s:textfield name="facID"/></li>
                    <li>ID da Eleiçao: <s:textfield name="elecID"/></li><br>
                    <s:submit value="Remover Mesa" cssClass="btn btn-default"/>
                </s:form>
            </div>
        </div>
    </div>
</div>
<s:if test="hasActionMessages()">
    <s:actionmessage/>
</s:if>

<noscript>JavaScript must be enabled for WebSockets to work.</noscript>

</body>
</html>