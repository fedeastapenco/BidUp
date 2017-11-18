var express = require('express');
var controller = require('../controllers/auction');
var route = express.Router();

route.post('/create/', controller.create);
route.post('/update/:id', controller.update);
route.get('/get/:id', controller.getById);
route.post('/delete/:id', controller.remove);
route.post('/addPhoto/:id', controller.addPhoto);
route.post('/addBidUp/:id', controller.addBidUp);
route.post('/addFollower/:id', controller.addFollower);
route.post('/removePhoto/:id', controller.removePhoto);
route.post('/removeBidUp/:id', controller.removeBidUp);
route.post('/removeFollower/:id', controller.removeFollower);
route.get('/findByObjectName/:objectName', controller.findByObjectName);
route.get('/getPublishedByUser/', controller.getPublishedByUser);
route.get('/getLastAuctions/', controller.getLastAuctions);
module.exports = route;