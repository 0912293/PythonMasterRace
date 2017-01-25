$(document).ready(function () {
    updateData();
});

function updateData() {
    getJsonUser();
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

// We pass a function in the parameter because we need the JSon data from this function for the share button
// as well as the getJSONID
function getJsonUser() {
    $.ajax({
        type: 'POST',
        url: "/api/getUserCrypt.json",
        dataType: "json",
        success: getJsonID,
        error: function () {
            console.log("Incorrect or missing JSON")
        }
    });
}

function getJsonUserCrypt() {
    $.ajax({
        type: 'POST',
        url: "/api/getUserCrypt.json",
        dataType: "json",
        success: shareLink,
        error: function () {
            console.log("Incorrect or missing JSON")
        }
    });
}

// This function is given the username from getJsonUser and then checks if the url contains an wishlistID
// Depending on if it does or not we present them a different table
function getJsonID(data) {
    var json = data;
    var cryptUser = json[0].crypted_user;
    var dict = {};
    // Je moet zorgen dat je hier die encrypted user krijgt
    var success;
    var searchAppend = getCurrentUrlParam("wishlistID");
    if(searchAppend == null || ""){
        searchAppend = cryptUser;
        success = filltable
    }
    else{
        success = fillviewTable
    }
    // We insert a key into our earlier declared Json with the current searchappend ID so it can return a Json with the specific games of that wishlistID
    dict["wishlist_id"] = searchAppend;
    $.ajax({
        type: 'POST',
        url: "/api/wishlist.json",
        data: dict,
        dataType: "json",
        success: success
    })
}

function shareLink(data) {
    var cryptUser = data[0].crypted_user;

    var link = window.location.href
        +"\?wishlistID="+cryptUser;

    window.prompt("Copy to clipboard: Ctrl+C, Enter", link);
}

function deleterows() {
    var table = $('table');
    var dict = {};
    var i = 0;
    $('[id = chk]:checked').each(function () {
        console.log($(this).val());
        $(this).closest('tr').remove();
        var key = i.toString();
        console.log("The key = " + key);
        console.log("The value = " + $(this).val());
        dict[key] = ($(this).val());
        i++
    });
    deleteSQL(dict)
}

function deleteSQL(dict) {
    var dict = dict;
    $.ajax({
        type: "POST",
        url: "/wishlist/delete",
        data: dict,
        success: function (data) {
            alert(data)
        }
    });
}

function fillviewTable(data) {
    var json = data;
    var tr = [];

    console.log(data);
    $.each(json, function (i, item) {
        tr = $('<tr/>')
        tr.append("<input type='hidden' id='" + item.games_id + "' value='" + item.games_id + "'/>");
        tr.append("<td>" + item.games_name + "</td>");
        tr.append("<td>" + item.games_price + "</td>");
        tr.append("<td>" + item.games_platform+ "</td>");
        $('#viewWishListTable').append(tr);
    });

    var divOne = document.getElementById('wishListDiv');
    divOne.style.display='none';
}

function filltable(data) {
    var json = data;
    var tr = [];
    $.each(json, function (i, item) {
        tr = $('<tr/>')
        tr.append("<input type='hidden' id='" + item.games_id + "' value='" + item.games_id + "'/>");
        tr.append("<td>" + item.games_name + "</td>");
        tr.append("<td>" + item.games_price + "</td>");
        tr.append("<td>" + item.games_platform+ "</td>");
        tr.append("<td><input type = 'checkbox'  value='"+ item.games_id + "' id = 'chk'" + item.games_id + "'/></td>");
        $('#wishListTable').append(tr);
    });

    var divTwo = document.getElementById('viewTableDiv');
    divTwo.style.display='none';
}

