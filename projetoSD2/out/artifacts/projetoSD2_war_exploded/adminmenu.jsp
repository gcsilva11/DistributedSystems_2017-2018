<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="java" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>iVotas: Menu de Admin</title>
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
<div class="center"></div>
<div class="content">
    <div class="row">
        <div class="col-xs-3 center">
            <s:form action="registerUserMenu">
                <s:submit  value= "Register New User" cssClass="btn btn-default" />
            </s:form>
        </div>
        <div class="col-xs-3 center">
            <s:form action="editUserMenu">
                <s:submit  value= "Edit User" cssClass="btn btn-default" />
            </s:form>
        </div>
        <div class="col-xs-3 center">
            <s:form action="createElectionMenu">
                <s:submit  value= "Create election" cssClass="btn btn-default" />
            </s:form>
        </div>
        <div class="col-xs-3 center">
            <s:form action="CreateListMenu">
                <s:submit  value= "Create candidate list" cssClass="btn btn-default" />
            </s:form>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-3 center">
            <s:form action="EditElectionMenu">
                <s:submit  value= "Edit Elections" cssClass="btn btn-default" />
            </s:form>
        </div>
        <div class="col-xs-3 center">
            <s:form action="VoteBoothMenu">
                <s:submit  value= "Add/Remove Booths" cssClass="btn btn-default" />
            </s:form>
        </div>
        <div class="col-xs-3 center">
            <s:form action="ElectionListMenu">
                <s:submit  value= "Election Details" cssClass="btn btn-default" />
            </s:form>
        </div>
        <div class="col-xs-3 center">
            <s:form action="ElectionDetailsMenu">
                <s:submit  value= "Past Elections" cssClass="btn btn-default" />
            </s:form>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-6 center">
            <s:form action="VoteHistoryMenu">
                <s:submit  value= "User Vote History" cssClass="btn btn-default" />
            </s:form>
        </div>
        <div class="col-xs-6 center">
            <h4>Online User List</h4>
            <div id="history"></div>
        </div>
    </div>
</div>

<noscript>JavaScript must be enabled for WebSockets to work.</noscript>

</body>

</html>