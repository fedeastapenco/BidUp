var controller = require('../controllers/user');
var route = require('express').Router();

route.post('/create/', controller.create);
route.get('/get/:id', controller.getById);
route.post('/remove/:id', controller.remove);
route.post('/update/:id', controller.update);
route.post('/createCard/', controller.createCard);
route.post('/updateRegistrationToken/:id',controller.updateRegistrationToken);
module.exports = route;
