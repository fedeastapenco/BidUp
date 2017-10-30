var mongoose = require('mongoose');
var app = require("./app");
var port = process.env.port || 8080;
var urlMongo = 'mongodb://localhost:27017/BidUp';
mongoose.Promise = global.Promise;
mongoose.connect(urlMongo, function (err, res) {
    if (err)
        throw err;
    else {
        app.listen(port, function () {
            console.log("API REST runing in http://localhost:" + port);
        });
    }
}); 