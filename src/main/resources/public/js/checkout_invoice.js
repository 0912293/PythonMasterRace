var doc = new jsPDF();
var specialElementHandlers = {
    '#editor': function (element, renderer) {
        return true;
    }
};

$(function () {
    var uid = getCurrentUrlParam("uid");
    retrieveJSON("/invoice.json", {"uid": uid}, function (data) {
        if (data !== undefined)
            updatePage(data)
    });
});

function updatePage(invoice) {
    console.log(invoice);

    if (invoice != null) {
        fillData(invoice);
        var pdfButton = $('#pdfEr');
        pdfButton.show();
        pdfButton.click(function () {
            var printContents = document.getElementById("toPdf").innerHTML;
            var originalContents = document.body.innerHTML;
            document.body.innerHTML = printContents;
            window.print();
            document.body.innerHTML = originalContents;
        });
    } else {
        body.empty();
        body.append("Uw factuur kon niet worden gevonden, " +
            "neem aub contact op met de sitebeheerder.");
    }
}

function fillData(data) {
    var userinfo = data.userinfo;
    var products = data.products;

    $('#orderID').append(userinfo.order_id);
    $('#orderDate').append(userinfo.order_pd);
    $('#customerName').append(userinfo.name + " " +userinfo.surname);
    $('#customerStreetAddress').append(userinfo.address_street + " " + userinfo.address_number + ", " + userinfo.address_city);
    $('#customerPostalAddress').append(userinfo.address_postalcode + " " + userinfo.address_country);
    var totalprice = 0.00;
    $.each(products, function (i, item) {
            $('#invoiceTableBody')
                .append($('<tr>')
                    .append(
                        "<td>" + item.product_name + "</td>" +
                        "<td>" + item.product_price + "</td>" +
                        "<td>" + item.amount + "</td>"
                    )
                );
            totalprice += item.product_price * item.amount;
        }
    );
    $('#totalCost').append("&euro;" + totalprice.toFixed(2));
}