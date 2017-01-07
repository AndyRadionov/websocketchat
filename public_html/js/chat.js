var websocket;
$(document).ready(function () {
    $("#registerBtn").click(function () {
        loginFormProcess("/api/signup");
    });

    $("#loginBtn").click(function () {
        loginFormProcess("/api/login");
    });

    $("#logoutBtn").click(function () {
        logout();
        showAutoHideModal("Successfully logged out!");
    }.bind(this));

    $("#sendMessage").click(function () {
        sendMessage();
    });
});

function loginFormProcess(url) {
    var jqxhr = $.post(url, $("#userForm").serialize());
    jqxhr.done(function () {
        $("#userLogin").text($("#loginInput").val());
        $("#loginForm").hide();
        $("#logoutForm").show();
        $("#helloMessageContainer").hide();
        $("#chatContainer").show();
        showAutoHideModal("Successfully logged in!");
        websocket = init();
        userListWorker();
    }.bind(this));
    jqxhr.fail(function (e) {
        $("#modal").modal('show');
        $(".modal-title").text("Error");
        $("#modalMessage").text(e["responseText"]);
    });
}

$('#autoHideModal').on('show.bs.modal', function () {
    var modal = $(this);
    clearTimeout(modal.data('hideInterval'));
    modal.data('hideInterval', setTimeout(function () {
        modal.modal('hide');
    }, 2000));
});

function logout() {
    var jqxhr = $.post("/api/logout", "login=" + $("#userLogin").text());
    jqxhr.done(function () {
        $("#userLogin").hide();
        $("#logoutForm").hide();
        $("#chatContainer").hide();
        $("#helloMessageContainer").show();
        $("#loginForm").show();
    });
    $("#loginInput").val("");
    $("#passInput").val("");
}

function showAutoHideModal(text) {
    $("#autoHideModal h2").text(text);
    $("#autoHideModal").modal('show');
}

function init() {
    var ws = new WebSocket("ws://localhost:8080/api/chat");
    ws.onopen = function (event) {
    }
    ws.onmessage = function (event) {
        $("#messages").val($("#messages").val() + event.data + "\n")
    };
    ws.onclose = function (event) {
        logout();
        $("#modal").modal('show');
        $(".modal-title").text("Connection Closed");
        $("#modalMessage").text("Please log in");
    };
    return ws;
}

function sendMessage() {
    websocket.send($("#message").val());
    $("#message").val("");
}

function userListWorker() {
    $.get("api/users", function (data) {
        $("#users").val("");
        $.each(data, function (index, value) {
            $("#users").val($("#users").val() + value + "\n");
        });
        setTimeout(userListWorker, 10000);
    });
}