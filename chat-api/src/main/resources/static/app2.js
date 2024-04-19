$(document).ready(function () {
    var socket = new SockJS('http://localhost:8085/ws/my-websocket');

    socket.onopen = function() {
        console.log('Connected');
    };

    $("#connect").click(function (event) {
        console.log('checking')
        event.preventDefault();
    });

    $("#disconnect").click(function (event) {
        event.preventDefault();
        if (socket !== null) {
            socket.close();
        }
        console.log("Disconnected");
    });

    $("#send").click(function (event) {
        event.preventDefault();
        var message = JSON.stringify({'name': $("#name").val()});
        socket.send(message);
    });
    socket.onmessage = function(event) {
        // 서버에서 메시지를 받으면 showGreeting 함수를 호출합니다.
        var message = JSON.parse(event.data);
        showGreeting(message.content);
    };

    function showGreeting(message) {
        $("#greetings").append("<tr><td>" + message + "</td></tr>");
    }

});