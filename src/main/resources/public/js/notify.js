$(function () {
    if(!('Notification' in window)) {
    }
    else if(Notification.permission !== 'granted' && Notification.permission !== 'denied') {
        SpawnPermissionDialog();
    }
});

function SpawnPermissionDialog(){
    Notification.requestPermission().then(function(result) {
    });
}

function SpawnNotification(Title, Message, MessagePicture, TimeoutInterval) {
    /// <summary>Spawns a notifcation with the current params, when the browser has permissions or is able to do so.</summary>
    /// <param name="Title" type="String">Title of the notification.</param>
    /// <param name="Message" type="String">Message body of the notification.</param>
    /// <param name="MessagePicture" type="String">The path to the picture that show inside the notification.(preferably quite small pictures, like icons(254x254))</param>
    /// <param name="TimeoutInterval" type="Number">TimeoutInterval in milisecond(s). A suggested value would be 5000, which corresponds 5 seconds.</param>
    if(NotificationAbilityCheck()){
        var optionsDict = {
            body : Message,
            icon: MessagePicture
        };
        var notification = new Notification(Title, optionsDict);
        setTimeout(notification.close.bind(notification), TimeoutInterval);
    }
    else{ /// ALERT FALLBACK
        alert(Title + Message);
    }
}

function NotificationAbilityCheck(){
    return 'Notification' in window && Notification.permission === 'granted';
}