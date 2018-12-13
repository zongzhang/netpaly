$(function () {
    var ws = null;
    if ("WebSocket" in window) {
        console.log("您的浏览器支持 WebSocket!");
        // 打开一个 web socket
        ws = new WebSocket('ws://127.0.0.1:9999/ws');

        console.log("准备...");


        ws.onclose = function () {
            console.log("连接已关闭...");
        };

        ws.onerror = function (evt) {
            console.log("ws错误=>" + evt);
        };

        ws.onopen = function () {
            console.log("连接已建立...");
            // ws.send('test msg');
            connected = true;
        };


    } else {
        // 浏览器不支持 WebSocket
        alert("您的浏览器不支持 WebSocket!");
    }

    ws.onmessage = function (evt) {
        console.log(evt.data);
        console.log(evt.data.username);
        console.log(evt.data.message);
        var obj = JSON.parse(evt.data);
        addChatMessage({
            username: obj.username,
            message: obj.message
        });
    };

    let FADE_TIME = 150; // ms
    let TYPING_TIMER_LENGTH = 400; // ms
    let COLORS = [
        '#e21400', '#91580f', '#f8a700', '#f78b00',
        '#58dc00', '#287b00', '#a8f07a', '#4ae8c4',
        '#3b88eb', '#3824aa', '#a700ff', '#d300e7'
    ];

    // Initialize variables
    let $window = $(window);
    let $usernameInput = $('.usernameInput'); // Input for username
    let $messages = $('.messages'); // Messages area
    let $inputMessage = $('.inputMessage'); // Input message input box

    // var $loginPage = $('.login.page'); // The login page
    let $loginPage = $('#logpage');
    let $chatPage = $('.chat.page'); // The chatroom page

    // Prompt for setting a username
    var $theusername = $('.theusername');
    var connected = false;
    var typing = false;
    var lastTypingTime;
    var $currentInput = $usernameInput.focus();

    // Keyboard events
    $window.keydown(event => {
        // Auto-focus the current input when a key is typed
        if (!(event.ctrlKey || event.metaKey || event.altKey)) {
            $currentInput.focus();
        }
        // When the client hits ENTER on their keyboard
        if (event.which === 13) {
            if ($theusername.val() === "" || $theusername.val() == null || $theusername.val() === undefined) {
                // socket.emit('stop typing');
                console.log("setUsername");
                setUsername();
            } else {
                console.log("send message");
                sendMessage();
            }
        }
    });


    const addParticipantsMessage = (data) => {
        var message = '';
        if (data.numUsers === 1) {
            message += "there's 1 participant";
        } else {
            message += "there are " + data.numUsers + " participants";
        }
        log(message);
    };

    const setUsername = () => {
        $theusername.val(cleanInput($usernameInput.val().trim()));
        // If the username is valid
        if ($theusername.val() != null) {
            debugger;
            $loginPage.fadeOut();
            $chatPage.show();
            $loginPage.off('click');
            $currentInput = $inputMessage.focus();
            // Tell the server your username
            // ws.send('add user username');
        }
    };


    const sendMessage = () => {
        var message = $inputMessage.val();
        // Prevent markup from being injected into the message
        message = cleanInput(message);
        // if there is a non-empty message and a socket connection
        if (message && connected) {
            $inputMessage.val('');
            addChatMessage({
                username: $theusername.val(),
                message: message
            });
            // tell server to execute 'new message' and send along one parameter
            // socket.emit('new message', message);
            var stringJson = '{ "username": "' + $theusername.val() + '", "message": "' + message + '" }';
            ws.send(stringJson);
        }
    };

    const log = (message, options) => {
        var $el = $('<li>').addClass('log').text(message);
        addMessageElement($el, options);
    };


    // Adds the visual chat message to the message list
    const addChatMessage = (data, options) => {
        // Don't fade the message in if there is an 'X was typing'
        // var $typingMessages = getTypingMessages(data);
        // options = options || {};
        // if ($typingMessages.length !== 0) {
        //     options.fade = false;
        //     $typingMessages.remove();
        // }

        var $usernameDiv = $('<span class="username"/>')
            .text(data.username)
            .css('color', getUsernameColor(data.username));
        var $messageBodyDiv = $('<span class="messageBody">')
            .text(data.message);

        // var typingClass = data.typing ? 'typing' : '';
        var $messageDiv = $('<li class="message"/>')
            .data('username', data.username)
            // .addClass(typingClass)
            .append($usernameDiv, $messageBodyDiv);

        addMessageElement($messageDiv, options);
    };

    // Adds the visual chat typing message
    const addChatTyping = (data) => {
        data.typing = true;
        data.message = 'is typing';
        addChatMessage(data);
    };

    // Removes the visual chat typing message
    const removeChatTyping = (data) => {
        getTypingMessages(data).fadeOut(function () {
            $(this).remove();
        });
    };

    const addMessageElement = (el, options) => {
        var $el = $(el);

        // Setup default options
        if (!options) {
            options = {};
        }
        if (typeof options.fade === 'undefined') {
            options.fade = true;
        }
        if (typeof options.prepend === 'undefined') {
            options.prepend = false;
        }

        // Apply options
        if (options.fade) {
            $el.hide().fadeIn(FADE_TIME);
        }
        if (options.prepend) {
            $messages.prepend($el);
        } else {
            $messages.append($el);
        }
        $messages[0].scrollTop = $messages[0].scrollHeight;
    };

    // Prevents input from having injected markup
    const cleanInput = (input) => {
        return $('<div/>').text(input).html();
    };

    // Gets the 'X is typing' messages of a user
    const getTypingMessages = (data) => {
        return $('.typing.message').filter(function (i) {
            return $(this).data('username') === data.username;
        });
    };

    // Gets the color of a username through our hash function
    const getUsernameColor = (username) => {
        // Compute hash code
        var hash = 7;
        for (var i = 0; i < username.length; i++) {
            hash = username.charCodeAt(i) + (hash << 5) - hash;
        }
        // Calculate color
        var index = Math.abs(hash % COLORS.length);
        return COLORS[index];
    };


    $inputMessage.on('input', () => {
        console.log("inputing");
    });

    // Click events

    // Focus input when clicking anywhere on login page
    $loginPage.click(() => {
        $currentInput.focus();
    });

    // Focus input when clicking on the message input's border
    $inputMessage.click(() => {
        $inputMessage.focus();
    });

});
