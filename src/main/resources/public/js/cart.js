$(function () {
    retrieveJSON("/session/user.ses", {}, function (data) {
        var checkoutbutton = $('#checkoutButton');
        console.log(data);
        if (data !== undefined) {
            if (data.username != "" && data.username != null) {
                checkoutbutton.click(function() {
                    window.location='/checkout/verify';
                });
            } else {
                checkoutbutton.attr("data-toggle", "modal");
                checkoutbutton.attr("data-target", "#login_modal");
            }
        } else {
            checkoutbutton.attr("data-toggle", "modal");
            checkoutbutton.attr("data-target", "#login_modal");
        }
    });

    retrieveJSON("/api/cart.json", {}, updatePage)
});

function updatePage(data) {
    console.log(data.length);
    if (data.length <= 0) {
        $('#checkoutButton').attr("disabled", "disabled");
        $('#deleteButton').attr("disabled", "disabled");
    } else {
        $('#checkoutButton').removeAttr("disabled");
        $('#deleteButton').removeAttr("disabled");
    }

    var table = $('#cartTable');
    $.each(data, function (i, item) {
        table.find('tbody')
            .append($('<tr>')
                .append(
                    "<td><input type = 'checkbox'  value='"+ item.games_id + "' id = 'cartchk'" + item.games_id + "'/></td>" +
                    "<td>" + item.games_name + "</td>" +
                    "<td>" + item.games_platform + "</td>" +
                    "<td>" + item.games_price + "</td>" +
                    "<td>" + item.amount + "</td>"
                )
            );
    }
)}

function deleteSelectedRows() {
    var dict = {
        "action" : 1
    };
    var i = 0;
    $('[id = cartchk]:checked').each(function () {
        $(this).closest('tr').remove();
        //$('[id = deleteList]').append($(this).val());
        var key = i.toString();
        console.log("The key = " + key);
        console.log("The value = " + $(this).val());
        dict["p_id" + key] = ($(this).val());
        i++
    });
    post("/cart/act", dict, function (msg) {
        if (msg !== undefined)
            alert(msg)
    });
    updateCart();
}