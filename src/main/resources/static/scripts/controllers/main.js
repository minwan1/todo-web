'use strict';

/**
 * @ngdoc function
 * @name helperangularApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the helperangularApp
 */
angular.module('TodoApp')
  .controller('MainCtrl', function MainCtrl($scope, TodoService) {
    var MAX_PAGE_BUTTON_SIZE = 5;
    $scope.maxSize = MAX_PAGE_BUTTON_SIZE;
    $scope.currentPage = 1;

    $scope.todo = Todo(0, '', [], false);

    getTodos(1);

    $scope.createTodo = function (todoForm, todo) {
      TodoService.create(todo.content, todo.refIds)
        .then(function (res) {
          getTodos($scope.currentPage);
          swal('', 'Todo가 추가되었습니다.');
          $scope.todo = Todo(0, '', [], false);
        });
    };

    $scope.pageChanged = function (currentPage) {
      getTodos(currentPage);
    };

    $scope.completeTodo = function(todo,currentPage){
      TodoService.complete(todo.id)
        .then(function(){
          getTodos(currentPage);
          swal('', '완료되었습니다.');
        }).catch(function(res){
          if(res.data.code == 'C001'){
            swal('', '이 작업을 참조하고 있는 작업중에 완료되지 않은 작업이 있습니다.')
          }
      });
    };

    $scope.resetRefIds = function(todo){
      todo.refIds = [];
    };

    //현재 기준
    $scope.addReferenceId = function (todo, id) {
      if(!isValidateReferenceId(todo, id)){
        return ;
      }
      addReferenceId(id, todo);
    };

    $scope.updateTodo = function(todoForm, todo, currentPage){
      TodoService.update(todo.id, todo.content, todo.refIds)
        .then(function(){
          swal('', '해당 Todo 내용 수정되었습니다.');
          $scope.todo = Todo(0, '', [], false);
          getTodos(currentPage);
        });
    };

    $scope.setTodoUpdate = function(todo){
      TodoService.getReferenceTodos(todo.id)
        .then(function(res){
          var refIds = getRefIds(res);
          getRefIds(res);
          $scope.todo = Todo(todo.id, todo.originContent, refIds, true);

        });

      function getRefIds(res) {
        var refTodoIds = [];
        for (var i = 0; i < res.length; i++) {
          refTodoIds.push(res[i].id);
        }
        return refTodoIds;
      }
    };

    function Todo(id, content, refIds, updateMode) {
      return {
        id: id,
        refIds: refIds,
        content: content,
        isUpdateMode: updateMode
      };
    }

    function addReferenceId(id, todo) {
      TodoService.checkRefIdIsValid(id)
        .then(function () {
          todo.refIds.push(id);
          swal('', '참조 id가 추가되었습니다.');
        }).catch(function(res){
          if(res.data.code === 'T001'){
            swal('', '존재하지 않는 id입니다.');
          }else if(res.data.code === 'T002'){
            swal('', '이미 완료된 작업입니다.');
          }
        });
    }
    
    function getTodos(page) {
      TodoService.getTodos(page)
        .then(function (res) {
          $scope.todos = res;
          $scope.totalTodoCount = res.totalElements;
        });
    }

    function isValidateReferenceId(todo, id){

      if(!id){
        swal('', 'id를 입력하세요.');
        return false;
      }

      if (todo.refIds.includes(id)) {
        swal('', '이미 추가한 id입니다.');
        return false;
      }
      return true;
    }

  });
