
$(function () {
    $('.form-login').on("submit", function(e) {
        e.preventDefault();
        login();
    });
});

function logout() {
    var dict = {
        'url' : location.pathname
    };
    post("/logout", dict, reload);
    return false;
}

function login() {
    var dict = {
        'url' : location.pathname,
        'username' : $('#username').val(),
        'pass' : $('#pass').val()
    };
    post("/login", dict, reload);
    return false;
}

function reload() {
    window.location = window.location.href
}

function getKeyChecker(expectedKeyCode, func) {
    function checkKeyAndRun(event) {
        if (key.keyCode == expectedKeyCode) {
            func($('#search').val());
            return false;
        }
        return false;
    }
    return checkKeyAndRun;
}


function retrieveJSON(url, dict, callback) {
    $.ajax({
        type: 'POST',
        url: url,
        data: dict,
        dataType: "json",
        success: function (data) {
            callback(data)
        },
        error: function () {
            console.log("Incorrect or missing JSON")
        }
    })
}

function post(url, dict, callback) {
    $.ajax({
        type: 'POST',
        url: url,
        data: dict,
        success: function (data) {
            callback(data)
        },
        error: function () {
            console.log("Incorrect or missing JSON")
        }
    })
}

function get(url, dict, callback) {
    $.each(dict, function (key, value) {
        url += "?"+key+"="+value;
    });
    $.ajax({
        type: 'GET',
        url: url,
        success: function (data) {
            if (callback !== undefined){
                callback(data);
            }
        },
        error: function () {
            console.log("Incorrect or missing JSON")
        }
    })
}