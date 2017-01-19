function changepass() {
    console.log("Password changed")
    var dict = {
        'url' : location.pathname,
        'opass' : $('#wachtwoord').val(),
        'npass' : $('#n_wachtwoord').val(),
        'npass2' : $('#n2_wachtwoord').val()
    };
    post('/user/wachtwoord/veranderen', dict, function (data) {
        if(data !== undefined){
            alert(data);
        }
        reload();
    });

    return false;
}