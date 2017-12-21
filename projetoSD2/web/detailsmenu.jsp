<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>iVotas: Menu de Admin - Registar User</title>
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
    <s:form action="detailElection" method="post" >
        <li>ID Eleição: <s:textfield name="elecID" value="1"/><br></li>
        <s:submit value="Verificar Detalhes" cssClass="btn btn-default"/>
    </s:form>
</div>

    <c:choose>
    <c:when test="${DetailElectionBean.votes==null || DetailElectionBean.votes.isEmpty()}">

        Oops - There are no finished elections with that id :(

    </c:when>
    <c:otherwise>
<div class="table-responsive">
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>Lista</th>
            <th>Votos</th>
            <th>Percentagem</th>
        </tr>
        </thead>
        <c:forEach items="${DetailElectionBean.votes}" var="voto">
            <tbody>
                <tr>
                    <td><c:out value="${voto.get(0)}"/></td>
                    <td><c:out value="${voto.get(1)}"/></td>
                    <td><c:out value="${voto.get(2)}"/></td>
                </tr>
            </tbody>
        </c:forEach>
    </table>
    </c:otherwise>
    </c:choose>
</div>

<noscript>JavaScript must be enabled for WebSockets to work.</noscript>
<div style="float:right;">
    <h4>Online User List</h4>
    <div id="container"><div id="history"></div></div>
</div>

</body>
</html>