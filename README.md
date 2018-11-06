
[![Build Status](https://travis-ci.com/minwan1/todo-web.svg?branch=master)](https://travis-ci.com/minwan1/todo-web)
[![Coverage Status](https://coveralls.io/repos/github/minwan1/todo-web/badge.svg?branch=master)](https://coveralls.io/github/minwan1/todo-web?branch=master)
# Todo 웹 만들기

할일 목록(Todo-list) 웹 어플리케이션 구현

# 목차

<!-- TOC -->

- [Todo 웹 만들기](#todo-웹-만들기)
- [목차](#목차)
- [1. 프로젝트 환경](#1-프로젝트-환경)
- [2. 기능 정의](#2-기능-정의)
    - [기능 구현](#기능-구현)
    - [목록조회 예시 및 설명](#목록조회-예시-및-설명)
    - [기능 화면](#기능-화면)
- [3. 데이터베이스 설계](#3-데이터베이스-설계)
    - [테이블 설계](#테이블-설계)
- [4. API 정보](#4-api-정보)
    - [API 목록](#api-목록)
- [5. 전체적인 문제 해결 방법 및 시나리오](#5-전체적인-문제-해결-방법-및-시나리오)
    - [문제 해결](#문제-해결)
    - [시나리오](#시나리오)
- [6. 빌드 및 실행](#6-빌드-및-실행)
        - [Maven](#maven)

<!-- /TOC -->
# 1. 프로젝트 환경
* 프론트엔드
    * Angularjs
* 백엔드
    * Spring Boot
    * Spring Data JPA
    * JAVA 8
    * Mockito
    * H2(Database)
    * Swagger
    * Lombock

# 2. 기능 정의



##  기능 구현

* [X] 사용자는 텍스트로 된 할일을 추가할 수 있다.
    * [X] 할일 추가 시 다른 할일들을 참조 걸 수 있다.
    * [x] 참조는 다른 할일의 id를 명시하는 형태로 구현한다. (아래 예시 참고)
* [x] 사용자는 할일을 수정할 수 있다. (수정시 참조 번호도 수정가능하다 ?)
* [x] 사용자는 할일 목록을 조회할 수 있다.
    * [x] 조회시 작성일, 최종수정일, 내용이 조회 가능하다.
    * [x] 할일 목록은 페이징 기능이 있다.
* [x] 사용자는 할일을 완료처리 할 수 있다.
    * [x] 완료처리 시 참조가 걸린 완료되지 않은 할일이 있다면 완료처리할 수 없다. (아래 예시 참고)
* [x] 단위테스트


## 목록조회 예시 및 설명
| id  | 할일        | 작성일시                | 최종수정일시              | 완료처리 |
| --- | --------- | ------------------- | ------------------- | ---- |
| 1   | 집안일       | 2018-04-01 10:00:00 | 2018-04-01 13:00:00 |      |
| 2   | 빨래 @1     | 2018-04-01 11:00:00 | 2018-04-01 11:00:00 |      |
| 3   | 청소 @1     | 2018-04-01 12:00:00 | 2018-04-01 13:00:00 |      |
| 4   | 방청소 @1 @3 | 2018-04-01 12:00:00 | 2018-04-01 13:00:00 |      |

                       <center> | 1 | 2 | 3 | 4 | 5 | </center>

* 할일 2, 3번은 1번에 참조가 걸린 상태이다.
* 할일 4번은 할일 1, 3번에 참조가 걸린 상태이다.
* 할일 1번은 할일 2번, 3번, 4번이 모두 완료되어야 완료처리가 가능하다.
* 할일 3번은 할일 4번이 완료되어야 완료처리가 가능하다.


## 기능 화면
![](https://i.imgur.com/mdOtvv7.png)


# 3. 데이터베이스 설계

## 테이블 설계

대략적인 테이블 구조는 아래와 같다. 

* Todo를 추가할 때 마다 todo테이블에 Todo 정보를 넣고 참조하는 Todo에 정보를 todo_reference 테이블에 넣는 구조로 설계.
* todo는 여러개의 todo를 참조하고 또 반대로 여러개의 todo에 의해 참조가 되어질 수 있는데 이것을 관계로 표현하면 다대다 또는 일대다 다대일로 풀 수 있음.
* 중요한것은 해당 Todo가 참조하고 있는 Todo 즉 크게 보면 부모 Todo임, 해당 Todo를 참조하고 있는 Todo의 정보 즉 자식 Todo정보만 알면 되기 때문에 원투매니 매니투원보다는 다대다로 접근.

![](https://i.imgur.com/xQ3iSom.png)


# 4. API 정보

## API 목록
* 해당 URL은 프로젝트 실행 후 클릭해주세요.

| Method | URL                          | Description     |
| ------ | ---------------------------- | --------------- |
| GET    | [/api/v1/todos](http://localhost:8080/swagger-ui.html#!/todo45controller/getTodosUsingGET)                | Todo 리스트 조회     |
| POST   | [/api/v1/todos](http://localhost:8080/swagger-ui.html#!/todo45controller/createUsingPOST)                | Todo 생성       |
| PUT    | [/api/v1/todos/{id}](http://localhost:8080/swagger-ui.html#!/todo45controller/updateUsingPUT)           | Todo 수정 |
| PUT    | [/api/v1/todos/{id}/complete](http://localhost:8080/swagger-ui.html#!/todo45controller/completeUsingPUT)  | Todo 완료 처리 |
| GET    | [/api/v1/todos/{id}/ref](http://localhost:8080/swagger-ui.html#!/todo45controller/getReferenceParentTodosUsingGET)            | 해당 id Todo의 참조값들 조회 |
| GET    | [/api/v1/todos/{id}/ref-check](http://localhost:8080/swagger-ui.html#!/todo45controller/verifyTodoIsReferableUsingGET) | 해당 id Todo가 참조가능한지 조회          |

* Swagger 문서 정보 : [http://localhost:8080/swagger-ui.html#/](http://localhost:8080/swagger-ui.html#/)
      
  
# 5. 전체적인 문제 해결 방법 및 시나리오

## 문제 해결

* Todo 도메인에 비지니스적 규칙 정의함으로써 객체의 응집력을 높임
    * 해당 Todo가 완료 가능 여부
    * 해당 Todo가 참조 가능한지 여부
    * Todo 도메인의 기능 정의

* Exception 정의 및 ExceptionController 모든 Exception을 핸들링 하도록 처리
    * 방어 코딩을 위해 몇몇의 사용자 Exception 정의
    * Exception 같은 경우에 단위테스트보다는 제대로 해당 Controller를 거치는지 확인하기위해 통합테스트 진행
    * 통일된 에러코드 및 통일된 에러 포멧팅으로 프론트엔드에 전달




## 시나리오
1. 사용자는 텍스트로 된 할일을 추가할 수 있다. (POST - /api/v1/todos)
    *  할일 추가 시 다른 할일들을 참조 걸 수 있다.
        *  해당 id가 유효한 id인지 검사 필요 (GET - /api/v1/todos/{id}/ref-check)
    *  참조는 다른 할일의 id를 명시하는 형태로 구현한다. 
2. 사용자는 할일을 수정할 수 있다. (PUT - /api/v1/todos/{id})
    *  정보를 수정하기전에 미리 해당 todo가 조회한 todo(부모) 정보들을 불러온다 (GET - /api/v1/todos/{id}/ref)
3. 사용자는 할일 목록을 조회할 수 있다. (GET - /api/v1/todos)
    * 조회시 작성일, 최종수정일, 내용이 조회 가능하다.
    * 할일 목록은 페이징 기능이 있다.
4. 사용자는 할일을 완료처리 할 수 있다. (PUT - /api/v1/todos/{id}/complete)
    * 완료처리 시 참조가 걸린 완료되지 않은 할일이 있다면 완료처리할 수 없다. (아래 예시 참고)



# 6. 빌드 및 실행
### Maven
```
$ git clone https://github.com/minwan1/todo-web.git
$ cd todo-web
$ mvn spring-boot:run

http://localhost:8080/index.html#/
```

### Docker
```
$ git clone https://github.com/minwan1/todo-web.git
$ cd todo-web 
$ mvn clean package
$ docker run -d -p 8080:8080 wan/tododemo

http://localhost:8080/index.html#/
```
