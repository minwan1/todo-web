angular.module('TodoApp').factory('TodoService', ($http, config, $q) => {

  function create(content,refIds){
    var deferred = $q.defer();
    var json = {
      content : content,
      refIds : refIds
    };

    $http({
      method: 'POST',
      url: config.devDomain + '/todos',
      data: json
    }).then(function (res) {
      deferred.resolve(res.data);
    }).catch(function (err) {
      deferred.reject(err);
    });

    return deferred.promise;
  }

  function getTodos(page) {
    var deferred = $q.defer();

    $http({
      method: 'GET',
      url: config.devDomain + '/todos',
      params: {
        page: page,
        size: 10
      }
    }).then(function (res) {
      deferred.resolve(res.data);
    }).catch(function (err) {
      deferred.reject(err);
    });

    return deferred.promise;
  }


  function getReferenceTodos(id) {
    var deferred = $q.defer();

    $http({
      method: 'GET',
      url: config.devDomain + '/todos/'+id+ "/ref"
    }).then(function (res) {
      deferred.resolve(res.data);
    }).catch(function (err) {
      deferred.reject(err);
    });

    return deferred.promise;
  }


  function checkRefIdIsValid(id) {
    var deferred = $q.defer();

    $http({
      method: 'GET',
      url: config.devDomain + '/todos/'+id +'/ref-check',
    }).then(function (res) {
      deferred.resolve(res.data);
    }).catch(function (err) {
      deferred.reject(err);
    });

    return deferred.promise;
  }

  function update(id, content) {
    var deferred = $q.defer();
    var json = {
      content : content
    };

    $http({
      method: 'PUT',
      url: config.devDomain + '/todos/'+ id,
      data: json
    }).then(function (res) {
      deferred.resolve(res.data);
    }).catch(function (err) {
      deferred.reject(err);
    });

    return deferred.promise;
  }


  function complete(id) {
    var deferred = $q.defer();

    $http({
      method: 'PUT',
      url: config.devDomain + '/todos/'+ id +'/complete'
    }).then(function (res) {
      deferred.resolve(res.data);
    }).catch(function (err) {
      deferred.reject(err);
    });

    return deferred.promise;
  }


  return {
    create: create,
    update: update,
    getTodos: getTodos,
    checkRefIdIsValid : checkRefIdIsValid,
    complete: complete,
    getReferenceTodos:getReferenceTodos
  }
});
