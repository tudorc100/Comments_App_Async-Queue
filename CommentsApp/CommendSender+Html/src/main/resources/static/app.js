const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-guide-websocket'
});

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
    $("#commentsList").html("");
}

// Function to display comments
    stompClient.activate();
    displayComments();

function displayComments() {
    fetch('http://localhost:8088/comments')
        .then(response => response.json())
        .then(data => {
            $("#commentsList").empty(); // Clear the existing comments list
            data.forEach(comment => {
                $("#commentsList").append("<li>" + comment.text + "</li>");
            });
        })
        .catch(error => console.error('Error fetching comments:', error));
    console.log("merge")
}

// function disconnect() {
//     stompClient.deactivate();
//     setConnected(false);
//     console.log("Disconnected");
// }

function sendName() {
    stompClient.publish({
        destination: "/app/hello",
        body: JSON.stringify({'name': $("#name").val()})
    });
    $("#commentsList").append("<li>" + $("#name").val() + "</li>");
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    // $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendName());
});

