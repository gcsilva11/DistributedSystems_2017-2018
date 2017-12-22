<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="java" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>iVotas: Menu de Voto - Escolher Eleição</title>
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
<java:if test="${session.loggedin != true || session.username == null || session.isUser != true}">
    <java:redirect url="index.jsp"/>
</java:if>

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="usermenu.jsp">Menu de Voto</a>
        </div>
        <ul class="nav navbar-nav">
            <li><a>${session.username}</a></li>
        </ul>
        <s:form action="logout">
            <button class="btn btn-danger navbar-btn navbar-right">Logout</button>
        </s:form>
    </div>
</nav>

<div class="container">
    <s:form action="listElectionLists">
        <div class="row">
            <div class="col-xs-6 center">
                <s:select label="ListaEleicoes" headerValue="ListaEleicoes" list="userBean.elNames" name="nomeEleicao"/>
            </div>
            <div class="col-xs-6 center">
                <s:submit value="Escolher Eleição" cssClass="btn btn-default"/>
            </div>
        </div>
    </s:form>
</div>

<noscript>JavaScript must be enabled for WebSockets to work.</noscript>

</body>

</html>