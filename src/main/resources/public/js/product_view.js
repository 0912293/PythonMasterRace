$(function () {
    updateData();
    get('/api/admincheck.ses', {}, showbutton);
    retrieveJSON('/api/user.ses', {}, showWishlistButton)
});

function showbutton(j){
    if(j[11] == "1") {
        document.getElementById('editable').style.display = 'block';
    }else {
        document.getElementById('editable').style.display = 'none';
    }
}

function showWishlistButton(data) {
    var username = data.username;
    console.log(username);
    if (username !== undefined) {
        if (data.username != "" && data.username != null) {
            document.getElementById('wishlistButtonDiv').style.display = 'block';
        }
    }
}

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

function addToWishlist() {
    var dict = {
        "id": getCurrentUrlParam("id")
    };
    post('/wishlist/add', dict, function (data) {
        if (data !== undefined)
            alert(data);
        window.location.replace("/wishlist");
    });
}

function addToFavList(){
    var dict = {
        "id" : getCurrentUrlParam("id")
    };
    post('/favorites/add', dict, function (data){
        if (data !== undefined)
            alert(data)
    });
}

function editGame() {
    var dict = {
        "id": getCurrentUrlParam("id")
    };
    console.log(dict.id);
    post('/api/product/id', dict, function (data) {
        window.location.replace("/admin/product/edit");
    });
}

function filldata(data) {
    var json = data;
    var content = [];
    var productContent = $('#ProductContent');
    productContent.empty();

    $.each(json, function (i, item) {
        console.log(item.games_id);
        content = $("<h1 id='ProductName' class='text-center'>" + item.games_name + "</h1>");
        content.append("<div class='row'>");
        content.append("<center><img id='ProductImage' src='" + item.games_image  + "' class='img-responsive'></center>");
        content.append("<div class='col-lg-2 col-lg-offset-4 col-md-offset-4 col-md-2 col-sm-offset-3 col-sm-3 col-xs-offset-3 col-xs-3'/>");
        content.append("<div class='col-lg-2 col-md-6 col-sm-6'>");
        content.append("</div><center><p id='ProductOmschrijving'></p></center>");
        content.append("</div>");
        content.append("<p><a class='btn btn-primary btn-lg' role='button' id='productViewCartButton"+ item.games_id +"'><span class='glyphicon glyphicon-shopping-cart' aria-hidden='true'/> toevoegen aan winkelwagen</a></p>");
        productContent.append(content);
        $('#productViewCartButton' + item.games_id).click(getCartActionFunc(item.games_id, item.games_name, item.games_image, 0));
    })
}

