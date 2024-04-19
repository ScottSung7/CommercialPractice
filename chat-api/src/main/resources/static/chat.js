// var stompClient = null;
//
// function connect() {
//     var socket = new SockJS('/chat-websocket');
//     stompClient = Stomp.over(socket);
//     stompClient.connect({}, function (frame) {
//         console.log('Connected: ' + frame);
//         stompClient.subscribe('/topic/public', function (chatMessage) {
//             showMessage(JSON.parse(chatMessage.body));
//         });
//     });
// }
//
// function showMessage(message) {
//     var chatMessages = document.getElementById('chat-messages');
//     var messageElement = document.createElement('div');
//     messageElement.classList.add('message');
//     messageElement.innerHTML = '<strong>' + message.sender + '</strong>: ' + message.content;
//     chatMessages.appendChild(messageElement);
// }
//
// document.getElementById('chat-form').addEventListener('submit', function (event) {
//     event.preventDefault();
//     var messageInput = document.getElementById('message-input');
//     var messageContent = messageInput.value.trim();
//     if (messageContent) {
//         var chatMessage = {
//             sender: 'Anonymous',
//             content: messageContent
//         };
//         stompClient.send('/app/chat.sendMessage', {}, JSON.stringify(chatMessage));
//         messageInput.value = '';
//     }
// });
//
// document.addEventListener('DOMContentLoaded', function () {
//     connect();
// });