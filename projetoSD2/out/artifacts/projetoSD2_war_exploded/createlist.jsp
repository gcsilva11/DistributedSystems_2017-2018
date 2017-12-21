<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="java" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>iVotas: Menu de Admin - Criar Lista de Candidatos</title>
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

<div class="container">
    <div class="row">
        <div class="col-xs-12 center">
            <h1>Criar Nova Eleição</h1>
        </div>
        <div class="col-xs-12 center">
            <select id = "purpose">
                <option value="0">None</option>
                <option value="1">Lista Para Eleição De Nucleos</option>
                <option value="2">Lista Para Eleição Geral</option>
            </select>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12 center">
            <div class="hide-display" id='studentList'>
                <s:form action="createList" method="post">
                    <br>Lista de Estudantes:<br>
                    <li>Nome da Lista: <s:textfield name="listName"/></li>
                    <li>ID da Eleição a associar: <s:textfield name="elecID"/></li>
                    <s:textfield name="listType" value = "2" type ="hidden"/></li><br>
                    <s:submit value="Criar Lista" cssClass="btn btn-default"/>
                </s:form>
            </div>
            <div class="hide-display" id='genList'>
                <s:form action="createList" method="post">
                    <br>Lista de Concelho Geral:<br>
                    <li>Nome da Lista: <s:textfield name="listName"/></li>
                    <li>ID da Eleição a associar: <s:textfield name="elecID"/></li>
                    <s:textfield name="listType" value = "1" type ="hidden"/></li><br>
                    <s:submit value="Criar Lista" cssClass="btn btn-default"/>
                </s:form>
            </div>
        </div>
    </div>
</div>

<noscript>JavaScript must be enabled for WebSockets to work.</noscript>

</body>
</html>