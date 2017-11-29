var controller = require('../controllers/scheduleConfig');
var express = require('express');
var route = express.Router();

route.post('/create/', controller.create);
module.exports = route;
