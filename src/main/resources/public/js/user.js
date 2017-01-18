function changepass() {
    console.log("Password changed")
    var dict = {
        'url' : location.pathname,
        'opass' : $('#wachtwoord').val(),
        'npass' : $('#n_wachtwoord').val()
    };
    post('/user/wachtwoord/veranderen', dict, reload());
    return false;
}