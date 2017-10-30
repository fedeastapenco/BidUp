var controller = require('../controllers/brand');
var route = require('express').Router();

route.get('/getByName/:name', controller.getByName);

module.exports = route;
