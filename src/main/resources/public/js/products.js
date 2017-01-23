function addToWishlist(ID) {
    var dict = {
        "id": ID
    };
    post('/wishlist/add', dict, function (data) {
        if (data !== undefined)
            alert(data);
    });
}

function getCartActionFunc(ID, name, imgPath,  act) {
    function addToCart() {
        var dict = {
            "productId": ID,
            "amount": 1,
            "action": act
        };
        post("/cart/act", dict, function (msg) {
            if (msg !== undefined)
                SpawnNotification('Winkelwagen - Product: ' + name, msg, imgPath, 5000);
                // alert(msg);
            updateCart();
        });
    }
    return addToCart
}