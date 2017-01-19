function addToWishlist(ID) {
    var dict = {
        "id": ID
    };
    post('/wishlist/add', dict, function (data) {
        if (data !== undefined)
            alert(data)
    });
}

function getCartActionFunc(ID, act) {
    function addToCart() {
        var dict = {
            "productId": ID,
            "amount": 1,
            "action": act
        };
        post("/cart/act", dict);
    }
    return addToCart
}

function addToCart() {
    var dict = {
        "productId": ID,
        "amount": 1,
        "action": act
    };
    post("/cart/act", dict);
}