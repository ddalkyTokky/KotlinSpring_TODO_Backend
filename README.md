# 0. Blog
**[[스파르타 코딩클럽] TODO 서버 개발 기획 문서](https://strawberryrabbit.tistory.com/6)**
# 1. USE CASE
# 2. DBMS
## 2-1. Tables
![erd](https://github.com/ddalkyTokky/KotlinSpring_TODO_Backend/assets/47583083/c97c9d9a-9f9a-4788-a585-be762c30e980)
## 2-2. DDL
# 3. API
![image](https://github.com/ddalkyTokky/KotlinSpring_TODO_Backend/assets/47583083/83f8bd8b-c2e2-48df-912b-61aa3ecb2b82)

|API 설명|Method|URL|Response|
|:---:|:---:|:---:|:---:|
|할일 생성|POST|/todo/new|생성 성공 여부|
|할일 목록 조회|GET|/todo/list?order={ascend or descend}|할일 목록|
|할일 상세 조회|GET|/todo/{todoId}|할일 상세 정보|
|할일 수정|PATCH|/todo/{todoId}/edit|업데이트 성공 여부|
|할일 완료|PATCH|/todo/{todoId}/done|업데이트 성공 여부|
|할일 삭제|DELETE|/todo/{todoId}|삭제 성공 여부|

|API 설명|Method|URL|Response|
|:---:|:---:|:---:|:---:|
|댓글 생성|POST|/reply/new/{todoId}/{userId}|생성 성공 여부|
|댓글 수정|PATCH|/reply/{replyId}|수정 성공 여부|
|댓글 삭제|DELETE|/reply/{replyId}|삭제 성공 여부|

|API 설명|Method|URL|Response|
|:---:|:---:|:---:|:---:|
|회원 가입|POST|/user/new|가입 성공 여부|
|로그인|POST|/user/signin|토큰등 식별자|
