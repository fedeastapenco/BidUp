var firebaseAdmin = require('firebase-admin');
var serviceAccount = require('../bidupproject-firebase-adminsdk-yckz1-5b550ab4d1.json');

firebaseAdmin.initializeApp({
    credential: firebaseAdmin.credential.cert(serviceAccount),
    databaseURL: "https://bidupproject.firebaseio.com/"
});

function messageNotification(title, body,click_action, data, arrayUsers) {
    //recibo titulo, cuerpo y tokens a cual enviar la notificacion
    var payload = {
        notification: {
            title: title,
            body: body,
            click_action : click_action
        },
        data : data
    };
    var fireBaseTokens = [];
    arrayUsers.forEach(element => {
        fireBaseTokens.push(element.fireBaseRegistrationToken);
    });
    var promise = firebaseAdmin.messaging().sendToDevice(fireBaseTokens, payload);
    promise.then(function (response) {
        console.log("ok enviando notificacion: " + response);
    })
        .catch(function (err) {
            console.log("error enviando notificacion: " + err)
        });
}
function messageData(object, arrayRegistrationToken) {
    //object es un objeto javascript
    var payload = {
        data: object
    }
    console.log(payload);
    //TODO terminar esto
    var promise = firebaseAdmin.messaging().sendToDevice(arrayRegistrationToken, payload);
    promise.then(function (response) {
        console.log("ok enviando messageDataAndNotification " + response);

    }).catch(function (err) {
        console.log("error enviando messageDataAndNotification " + err);
    })
}
function messageDataAndNotification(object, title, body, arrayRegistrationToken) {
    //object es un objeto javascript
    var payload = {
        notification: {
            title: title,
            body: body
        },
        data: object
    };
    var promise = firebaseAdmin.messaging().sendToDevice(arrayRegistrationToken, payload);
    promise.then(function (response) {
        console.log("ok enviando messageDataAndNotification " + response);

    }).catch(function (err) {
        console.log("error enviando messageDataAndNotification " + err);
    })
}
module.exports = { messageNotification, messageData, messageDataAndNotification };