var controller = require('../controllers/card');
var route = require('express').Router();

route.post('/create/', controller.create);
route.get('/get/:id', controller.getById);
route.post('/remove/:id', controller.remove);
route.get('/getByUser/', controller.getByUser);
module.exports = route;
