$(function () {
    updateData();
});

function updateData() {
    var stdURL = "/api/product/view.json";
    var searchAppend = getCurrentUrlParam("id");
    console.log(searchAppend);

    var searchDict = {
        'id': (searchAppend != null || searchDict !== undefined) ? searchAppend : null
    };
    retrieveJSON(stdURL, searchDict, filldata);
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

function filldata(data) {
    var json = data;
    var content = [];
    var productContent = $('#ProductContent');
    productContent.empty();

    $.each(json, function (i, item) {
        content = $("<h1 id='ProductName' class='text-center'>" + item.games_name + "</h1>");
        content.append("<div class='row'>");
        content.append("<center><img id='ProductImage' src='" + item.games_image  + "' class='img-responsive'></center>");
        content.append("<div class='col-lg-2 col-lg-offset-4 col-md-offset-4 col-md-2 col-sm-offset-3 col-sm-3 col-xs-offset-3 col-xs-3'/>");
        content.append("<div class='col-lg-2 col-md-6 col-sm-6'>");
        content.append("</div><center><p id='ProductOmschrijving'>Omschrijving</p></center>");
        content.append("</div>");
        content.append("<p><a class='btn btn-primary btn-lg' href='#' role='button'><span class='glyphicon glyphicon-shopping-cart' aria-hidden='true'/>+Koop nu</a></p>");
        productContent.append(content);
    })
}

function addToWishlist() {
    var dict = {
        "id": getCurrentUrlParam("id")
    };
    post('/wishlist/add', dict, function (data) {
        if (data !== undefined)
            alert(data)
    });
}