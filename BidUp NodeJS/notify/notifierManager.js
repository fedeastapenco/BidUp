var firebaseAdmin = require('firebase-admin');
var serviceAccount = require('../prueba-d667c-firebase-adminsdk-119gq-c29c061056.json');

firebaseAdmin.initializeApp({
    credential: firebaseAdmin.credential.cert(serviceAccount),
    databaseURL: "https://prueba-d667c.firebaseio.com"
});

function messageNotification(title, body, arrayRegistrationToken) {
    //recibo titulo, cuerpo y tokens a cual enviar la notificacion
    var payload = {
        notification: {
            title: title,
            body: body
        }
    };
    var promise = firebaseAdmin.messaging().sendToDevice(arrayRegistrationToken, payload);
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