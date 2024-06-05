# v1.0.0

**[[스파르타 코딩클럽] TODO 서버 개발 기획 문서](https://strawberryrabbit.tistory.com/6)**
# 0. 변경점
- API 버전업.

# 1. 질문 사항


💡 **Why: 과제 제출시에는 아래 질문을 고민해보고 답변을 함께 제출해주세요.**

1. 수정, 삭제 API의 request를 어떤 방식으로 사용 하셨나요? (param, query, body)                
**_- 수정 삭제의 목표가 되는 '할일', '댓글'의 ID (PK) 에 대해서는 Param으로, 수정할 내용은 body에 담아 사용함._**               
               
2. RESTful한 API를 설계하셨나요? 어떤 부분이 그런가요? 어떤 부분이 그렇지 않나요?               
_**- 모든 API에서 행위에 대한 부분은 메쏘드만으로 표현하도록 함.**_               
_**- 수정 / 상태수정 과 같은 메소드와 URL 모두 겹치는 일부의 경우에만 한하여 URL에 행위를 추가함.**_               
               
3. 적절한 관심사 분리를 적용하셨나요? (Controller, Service, Repository)               
_**- 숙련 강의 예제의 구조를 모사함.**_               
_**- 현 수준에서는 충분하다고 생각됨.**_               
               
4. API 명세서 작성 가이드라인을 검색하여 직접 작성한 API 명세서와 비교해보세요!               
_**- 굿.**_               
               
❓ **Why: 과제 제출시에는 아래 질문을 고민해보고 답변을 함께 제출해주세요.**               
               
1. 처음 설계한 API 명세서에 변경사항이 있었나요?                
변경 되었다면 어떤 점 때문 일까요? 첫 설계의 중요성에 대해 작성해 주세요!               
_**- URI 와 메쏘드에 변경사항이 있어 0.0.0 -> 1.0.0 으로 버전업 진행. (튜터님 피드백 반영)**_              
_**- 기능 구분에 대한 것은 변경 없음.**_              
2. ERD를 먼저 설계한 후 Entity를 개발했을 때 어떤 점이 도움이 되셨나요?               
_**- ERD 없이 개발을 진행해 본 적은 없지만, 그렇게 진행될 경우 많은 부분에서, 그리고 더욱 잦은 DBMS 모델 수정이 일어날 것으로 생각된.**_
3. 만약 댓글이 여러개 달려있는 할일을 삭제하려고 한다면 무슨 문제가 발생할까요? Database 테이블 관점에서 해결방법이 무엇일까요?               
_**- DBMS DDL 단에서 Cascade 옵션을 걸었기에 문제없음.**_             
4. IoC / DI 에 대해 간략하게 설명해 주세요!               
                  
# 2. USE CASE
![image](https://github.com/ddalkyTokky/KotlinSpring_TODO_Backend/assets/47583083/e619d45f-ade4-415d-bfa3-594e6da3ddff)
# 3. DBMS (MYSQL)
## 3-1. Tables
![todo_list](https://github.com/ddalkyTokky/KotlinSpring_TODO_Backend/assets/47583083/1bcaaa0d-caf6-47cc-b259-aed5a8aa3e60)
## 3-2. DDL
```
CREATE TABLE `member` (
	`id` bigint UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	`name` VARCHAR(16) NOT NULL UNIQUE,
	`pw` VARCHAR(64) NOT NULL
);

CREATE TABLE `todo` (
	`id` bigint UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	`member_id` bigint UNSIGNED NOT NULL,
	`title`	VARCHAR(200) NOT NULL,
	`content` VARCHAR(1000) NOT NULL,
	`created_at` timestamp NOT NULL,
	`status` ENUM ('WORKING', 'DONE') NOT NULL,
	FOREIGN KEY (member_id) REFERENCES member(id) ON DELETE CASCADE
);

CREATE TABLE `reply` (
	`id` bigint UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	`member_id` bigint UNSIGNED NOT NULL,
	`todo_id` bigint UNSIGNED NOT NULL,
	`content` VARCHAR(255) NOT NULL,
	FOREIGN KEY (member_id) REFERENCES member(id) ON DELETE CASCADE,
	FOREIGN KEY (todo_id) REFERENCES todo(id) ON DELETE CASCADE
);

ALTER TABLE `todo` ADD FOREIGN KEY (`member_id`) REFERENCES `member` (`id`);
ALTER TABLE `reply` ADD FOREIGN KEY (`member_id`) REFERENCES `member` (`id`);
ALTER TABLE `reply` ADD FOREIGN KEY (`todo_id`) REFERENCES `todo` (`id`);
```

# 4. API (1.0.0)
## 4-1. Todo API
![image](https://github.com/ddalkyTokky/KotlinSpring_TODO_Backend/assets/47583083/5269e8bf-8afd-40bf-b7a1-5a02747047a4)
## 4-2. Reply API
![image](https://github.com/ddalkyTokky/KotlinSpring_TODO_Backend/assets/47583083/2aca15ad-1f80-4e0d-9588-6467e196c71c)
## 4-3. Member API
![image](https://github.com/ddalkyTokky/KotlinSpring_TODO_Backend/assets/47583083/cbf9a009-1055-4c3c-97a4-f7edeaef55a4)

# 5. 코드 특징
예제 코드와 몇 가지 다른 점이 존재한다.

## 5-1. Entity 생성 수정 전략
Entity Class Property 기능을 사용하지 않고, companion 등을 활용한 [독자적인 생성 및 수정 패턴](https://github.com/ddalkyTokky/KotlinSpring_TODO_Backend/blob/main(1.0.0)/src/main/kotlin/com/soonyong/todo/domain/todo/model/Todo.kt#L42)을 사용햇다.

```
companion object{
        fun createTodo(
            todoRequest: TodoRequest,
            member: Member
            ): Todo {
            val todo: Todo = Todo()
            todo.member = member
            todo.title = todoRequest.title
            todo.content = todoRequest.content
            todo.status = TodoStatus.WORKING
            return todo
        }
    }
```

```
fun updateTodo(todoRequest: TodoRequest): Todo{
        if(todoRequest.name != null){
            this.member!!.updateName(todoRequest.name)
        }
        if(todoRequest.title != null){
            this.title = todoRequest.title
        }
        if(todoRequest.content != null) {
            this.content = todoRequest.content
        }
        return this
    }
```
## 5-2. Assert 도입
쿼리문 검증을 [Assert](https://github.com/ddalkyTokky/KotlinSpring_TODO_Backend/blob/main(1.0.0)/src/main/kotlin/com/soonyong/todo/domain/todo/controller/TodoController.kt#L29)로 진행함.
```
Assert.isTrue(
    todoSearch.order.equals("descend") ||
	    todoSearch.order.equals("ascend") ||
	    (todoSearch.order == null),
    "BAD_REQUEST from query: order has to be one of (descend, ascend)"
)

Assert.isTrue(
    (todoSearch.member?.isBlank() == false) ||
	    (todoSearch.member == null),
    "BAD_REQUEST from query: member should not be Blank"
)
```

## 5-3. Null-able 을 기본으로 채용.
Java의 변수가 항상 Null을 허용하는 것처럼, 모든 변수를 가급적이면 Null 허용 변수로 사용키로 함.                     
이에 따라 HTTP Request의 일부 내용이 누락된 것을 허용하고, [Entity](https://github.com/ddalkyTokky/KotlinSpring_TODO_Backend/blob/main(1.0.0)/src/main/kotlin/com/soonyong/todo/domain/todo/model/Todo.kt#L25) 와 [GlobalExceptionHandler](https://github.com/ddalkyTokky/KotlinSpring_TODO_Backend/blob/main(1.0.0)/src/main/kotlin/com/soonyong/todo/infra/exception/GlobalExceptionHandler.kt#L27) 단에서 @Valid 검사를 사용하기로 함.                   
오류가 발생하는 부분을 한 곳으로 통합 수 있어서 편함.                   
                   
```
@ExceptionHandler(IllegalArgumentException::class)
fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ErrorResponse> {
return ResponseEntity
    .status(HttpStatus.BAD_REQUEST)
    .body(ErrorResponse(e.message))
}

@ExceptionHandler(ConstraintViolationException::class)
fun handleConstraintViolationException(e: ConstraintViolationException): ResponseEntity<List<String>> {
return ResponseEntity
    .status(HttpStatus.BAD_REQUEST)
    .body(e.constraintViolations.map {it.message})
}
```
