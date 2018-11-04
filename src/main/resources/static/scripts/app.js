'use strict';

/**
 * @ngdoc overview
 * @name TodoApp
 * @description
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

