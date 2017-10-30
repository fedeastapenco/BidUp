var controller = require('../controllers/bidUp');
var express = require('express');
var route = express.Router();

route.post('/create/', controller.create);
route.post('/update/:id', controller.update);
route.get('/get/:id', controller.getById);

module.exports = route;