var stompClient = null;

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
    var socket = new SockJS('/spring-boot-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        // // /topic：每次连接都会自动创建queue，可以做全局消息
        // stompClient.subscribe('/topic/greetings', function (greeting) {
        //     showGreeting(JSON.parse(greeting.body).content);
        // });
        // // /exchange：不会创建exchange，每次订阅都会生成一个queue，可以做全局消息
        // stompClient.subscribe('/exchange/greetings/greetings', function (greeting) {
        //     showGreeting(JSON.parse(greeting.body).content);
        // });
        // // /queue：只会创建一个queue并绑定到默认exchange，queue不会自动删除，轮询消费
        // stompClient.subscribe('/queue/greetings', function (greeting) {
        //     showGreeting(JSON.parse(greeting.body).content);
        // });
        // // /amq/queue：不会创建queue，若不存在会报错，轮询消费
        // stompClient.subscribe('/amq/queue/greetings', function (greeting) {
        //     showGreeting(JSON.parse(greeting.body).content);
        // });
        // /user/{id}/：内存消息代理，点对点推送
        stompClient.subscribe('/user/' + 'rainlin' + '/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});

