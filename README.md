[![Build Status](https://travis-ci.com/minwan1/todo-web.svg?branch=master)](https://travis-ci.com/minwan1/todo-web)
[![Coverage Status](https://coveralls.io/repos/github/minwan1/todo-web/badge.svg?branch=master)](https://coveralls.io/github/minwan1/todo-web?branch=master)
# Todo 웹 만들기
할일 목록(todo-list) 웹 어플리케이션 구현


# 1. 기능
* 사용자는 텍스트로 된 할일을 추가할 수 있다.
    * 할일 추가 시 다른 할일들을 참조 걸 수 있다.
    * 참조는 다른 할일의 id를 명시하는 형태로 구현한다. (예시 참고)
* 사용자는 할일을 수정할 수 있다.
* 사용자는 할일 목록을 조회할 수 있다.
    * 조회시 작성일, 최종수정일, 내용이 조회 가능하다.
    * 할일 목록은 페이징 기능이 있다.
* 사용자는 할일을 완료처리 할 수 있다.
    * 완료처리 시 참조가 걸린 완료되지 않은 할일이 있다면 완료처리할 수 없다. (예시 참고)


## 목록조회 예시 및 설명
| id | 할일 | 작성일시 | 최종수정일시 | 완료처리 |
|----|-------------|---------------------|----------|---------------------|
| 1 | 집안일 | 2018-04-01 10:00:00 | 2018-04-01 13:00:00 |  |
| 2 | 빨래 @1 | 2018-04-01 11:00:00 | 2018-04-01 11:00:00 |  |
| 3 | 청소 @1 | 2018-04-01 12:00:00 | 2018-04-01 13:00:00 |  |
| 4 | 방청소 @1 @3 | 2018-04-01 12:00:00 | 2018-04-01 13:00:00 |  | 

                        | 1 | 2 | 3 | 4 | 5 |

* 할일 2, 3번은 1번에 참조가 걸린 상태이다.
* 할일 4번은 할일 1, 3번에 참조가 걸린 상태이다.
* 할일 1번은 할일 2번, 3번, 4번이 모두 완료되어야 완료처리가 가능하다.
* 할일 3번은 할일 4번이 완료되어야 완료처리가 가능하다.



##  기능 구현 체크


* [ ] 사용자는 텍스트로 된 할일을 추가할 수 있다.
* [ ] 할일 추가 시 다른 할일들을 참조 걸 수 있다.
* [ ] 참조는 다른 할일의 id를 명시하는 형태로 구현한다. (예시 참고)
* [ ] 사용자는 할일을 수정할 수 있다.
* [ ] 사용자는 할일 목록을 조회할 수 있다.
* [ ] 조회시 작성일, 최종수정일, 내용이 조회 가능하다.
* [ ] 할일 목록은 페이징 기능이 있다.
* [ ] 사용자는 할일을 완료처리 할 수 있다.
* [ ] 완료처리 시 참조가 걸린 완료되지 않은 할일이 있다면 완료처리할 수 없다. (예시 참고)
* [ ] 단위테스트 필수, 통합테스트는 선택

# 2. 프로젝트 설계 및 생성

## 테이블 설계
부모의 아들들 보다 아들의 부모들을 조회하는게 중요하다. 왜냐 아들들은 수정하거나 완료될때만 검사한다 반면.
아들들은 필수로 부모들을 필요로한다.

![](https://i.imgur.com/xQ3iSom.png)

# 3. 사용 기술 및 툴

* angularjs
* Spring Boot
* Spring Data JPA
* JAVA 8
* Mockito
* H2(Database)
* Swagger



# 4. 빌드 및 실행
```
mvn spring-boot:run
```

