'use strict';

/**
 * @ngdoc overview
 * @name TodoApp
 * @description
 * # helperangularApp
 *
 * Main module of the application.
 */
const config = {
  devDomain : 'http://localhost:8080/api/v1',
  localDomain: 'http://localhost:8080/'
};


angular
  .module('TodoApp', [
    'ngRoute',
    'ui.bootstrap'
  ]).constant('config', config)
  .config(function ($routeProvider, $locationProvider, $httpProvider) {
    $locationProvider.hashPrefix('');
    $httpProvider.interceptors.push('interceptors');

    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        controllerAs: 'main'
      })
      .when('/login', {
        templateUrl: 'views/login.html',
        controller: 'LoginCtrl',
        controllerAs: 'login'
      })
      .when('/main', {
        templateUrl: 'views/main.html',
        controller: 'LoginCtrl',
        controllerAs: 'main'
      })
      .when('/write', {
        templateUrl: 'views/write.html',
        controller: 'WriteCtrl',
        controllerAs: 'write'
      })
      .otherwise({
        redirectTo: '/'
      });
  }).run([
  '$rootScope',
  '$http',
  '$location',
  function($rootScope,$http,$location){


  }

]);

