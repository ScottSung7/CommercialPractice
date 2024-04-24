const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8085/gs-guide-websocket'
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/greetings', (greeting) => {
        var data = JSON.parse(greeting.body);
        showGreeting(data);
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    var roomId = $("#roomId").val();
    var token = $("#uuid").val();

    if (!roomId && !token) {
        $("#roomId, #uuid").css("border-color", "red");
        return;
    }
    if (!roomId) {
        $("#roomId").css("border-color", "red");
        $("#uuid").css("border-color", "");
        return;
    }
    if(!token){
        $("#uuid").css("border-color", "red");
        $("#roomId").css("border-color", "red");
        return;
    }
    $("#roomId, #uuid").css("border-color", "");

    var destination = "/app/hello/" + roomId;
    var headers = {
        authorization : "Bearer " + token
    }
    var body = JSON.stringify({'content': $("#message").val()});

    stompClient.publish({
        destination: destination,
        headers: headers,
        body: body
    });
}

function showGreeting(data) {
    var message = data.nickname + " : " + data.content;
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    connect(); // 페이지가 로드될 때 자동으로 연결되도록 변경
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendName());
});

document.addEventListener('DOMContentLoaded', function() {
    const messageInput = document.getElementById('message');

    messageInput.addEventListener('keypress', function(event) {
        if (event.key === 'Enter') {
            sendName();
        }
    });
});
