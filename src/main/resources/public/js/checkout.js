$(function() {
    $('#verifyData_form').submit(function () {
        post("/invoice/new", {}, function (data) {
            window.location = "/invoice?uid=" + data;
            var dict = {
                "title" : "invoice",
                "url" : "http://" + location.href.split( '/' )[2] + "/invoice?uid="+data,
                "to" : $('#mailadressTextCheck').val()
            };
            post("/mail", dict, function (data) {
                SpawnNotification('Mail - Actie: ', 'Verzonden', '/img/okIcon.png', 5000);
            })
        });
        return false;
    });
    retrieveJSON("/api/user/checkout_info.json", {}, fillValues);
});

function fillValues(data) {
    var toFill = {
        'email' : $('#mailadressTextCheck'),
        'name' : $('#nameTextCheck'),
        'surname' : $('#surnameTextCheck'),
        'country' : $('#countryTextCheck'),
        'city' : $('#cityTextCheck'),
        'postalcode' : $('#postalcodeTextCheck'),
        'street' : $('#streetTextCheck'),
        'number' : $('#housenumberTextCheck')
    };

    $.each(toFill, function(key, value) {
        value.empty();
        value.val(data.userinfo[key])
    });
    fillTable(data.products)
}

function fillTable(data) {
    var totalprice = 0.00;
    $.each(data, function (i, item) {
            var table = $('#tableBody')
                if(item.games_name != undefined){
                    table.append($('<tr>')
                    .append(
                        "<td>" + item.games_name + "</td>" +
                        "<td></td>" +
                        "<td>" + item.games_price + "</td>" +
                        "<td>" + item.amount + "</td>"
                    )
                );totalprice += item.games_price * item.amount;}
                else{
                    table.append($('<tr>')
                        .append(
                            "<td>" + item.platform_name + "</td>" +
                            "<td></td>" +
                            "<td>" + item.platform_price + "</td>" +
                            "<td>" + item.amount+ "</td>"
                        )
                    );
                    totalprice += item.platform_price * item.amount;
                }
        }
    );
    $('#totalCost').append("&euro;" + totalprice.toFixed(2));
}

