$(function () {
    $('.form-login').on("submit", function(e) {
        e.preventDefault();
        login();
    });
    updateCart()
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

function updateCart() {
    function clear_refill(data) {
        var cartButton = $('#cartReftext');
        var nr_of_items = data.count;
        cartButton.empty();
        cartButton.append("<span class='glyphicon glyphicon-shopping-cart'></span> ");
        if (nr_of_items == 1) {
            cartButton.append(nr_of_items + " Product");
        } else if (nr_of_items == 0) {
            cartButton.append("No Products");
        } else {
            cartButton.append(nr_of_items + " Products");
        }
    }
    retrieveJSON("/api/cart/count.json", {}, clear_refill);
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

function getCurrentUrlParam(param) {
    var results = new RegExp('[\?&]' + param + '=([^&#]*)').exec(window.location.href);
    if (results==null){
        return null;
    }
    else{
        return results[1] || 0;
    }
}

function retrieveJSON(url, dict, callback) {
    $.ajax({
        type: 'POST',
        url: url,
        data: dict,
        dataType: "json",
        success: function (data) {
            if (callback !== undefined)
                callback(data)
        },
        error: function () {
        }
    })
}

function post(url, dict, callback) {
    $.ajax({
        type: 'POST',
        url: url,
        data: dict,
        success: function (data) {
            if (callback !== undefined)
                callback(data)
        },
        error: function () {
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
        }
    })
}