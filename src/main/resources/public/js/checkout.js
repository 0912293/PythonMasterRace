$(function() {
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
            $('#tableBody')
                .append($('<tr>')
                    .append(
                        "<td>" + item.games_name + "</td>" +
                        "<td></td>" +
                        "<td>" + item.games_price + "</td>" +
                        "<td>" + item.amount + "</td>"
                    )
                );
            totalprice += item.games_price * item.amount;
        }
    );
    $('#totalCost').append("&euro;" + totalprice.toFixed(2));
}

