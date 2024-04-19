const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8085/gs-guide-websocket'
});
//publish 로 파싱해서 보내고 /prefix/customURL -> @MessageMapping("/customURL")로 보낸다.
//subscribe 로 받고 /topic/customURL -> @SendTo("/topic/customURL")로 받는다.
// 이전 메시지가 필요하다면 여기서 받고 방은 어떻게 처리하지..?
stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/greetings', (greeting) => {
        showGreeting(JSON.parse(greeting.body).content);
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
    console.log('hihi2');
    stompClient.activate();
    console.log('hihi');
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    console.log('hello');
    stompClient.publish({
        destination: "/app/hello/3",
        body: JSON.stringify({'name': $("#name").val()})
    });
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendName());
});