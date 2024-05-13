# 1. USE CASE
# 2. DBMS
## 2-1. Tables
![erd](https://github.com/ddalkyTokky/KotlinSpring_TODO_Backend/assets/47583083/c97c9d9a-9f9a-4788-a585-be762c30e980)
## 2-2. DDL
# 3. API
|API 설명|Method|URL|Request Query|Response|
|:---:|:---:|:---:|:---:|:---:|
|할일 생성|POST|/todo/create||생성 성공 여부|
|할일 목록 조회|GET|/todo/list||할일 목록|
|할일 상세 조회|GET|/todo/detail/{todoID}||할일 상세 정보|
|할일 수정|PATCH|/todo/update/{todoID}||업데이트 성공 여부|
|할일 삭제|DELETE|/todo/delete/{todoID}||삭제 성공 여부|
