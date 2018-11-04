angular.module('TodoApp').factory('interceptors', function($q) {

  return {
    request: function(request) {
      if (request.beforeSend) {
        request.beforeSend();
      }
      return request;
    },

    response: function(response) {

      if (response.config.complete) {
        response.config.complete(response);
      }

      return response || $q.when(response);
    },

    responseError: function(response) {
      if (response.status === 500 ) {
       swal('','서버 내부에러입니다.');
      }

      return $q.reject(response);
    }
  };

});
