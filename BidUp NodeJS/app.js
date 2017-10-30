//definicion de la aplicación
//requerimientos externos
var express = require('express');
var path = require('path');
//var favicon = require('serve-favicon');
var logger = require('morgan');
//var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var notify = require('./notify/notifierManager');

var app = express();

// view engine setup - para la web api no vamos a utilizar views
//app.set('views', path.join(__dirname, 'views'));
//app.set('view engine', 'jade');

// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
//app.use(cookieParser());
//archivos estáticos
//app.use(express.static(path.join(__dirname, 'public')));
//registro las rutas al app

var auction = require('./routes/auction');
var bidUp = require('./routes/bidUp');
var brand = require('./routes/brand');
var card = require('./routes/card');
var user = require('./routes/user');
app.use('/auction', auction);
app.use('/bidup', bidUp);
app.use('/brand', brand);
app.use('/card', card);
app.use('/user', user);
// catch 404 and forward to error handler
app.use(function (req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});
// error handler
app.use(function (err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
