# v1.1.1

**[[스파르타 코딩클럽] TODO 서버 개발 기획 문서](https://strawberryrabbit.tistory.com/6)**                     
**[[스파르타 코딩클럽] TODO 서버 로그인 기능](https://strawberryrabbit.tistory.com/12)**                    

# 0. 변경점
- io.jsonwebtoken 사용.
- DBMS member 테이블 secret 열 삭제
- 비밀번호 암호화 알고리즘 변경 (sha256 MD5 => BCryptPasswordEncoder)

# 1. 개발 & 테스트 환경
## 개발
- IntelliJ 2024.1 Ultimate
- Spring boot 3.2.5
- java 17
- Mysql 8.3

## 테스트
- Swagger
- Talend 크롬 확장 프로그램

# 2. USE CASE
![image](https://github.com/ddalkyTokky/KotlinSpring_TODO_Backend/assets/47583083/bcc2cfd9-16fc-41d2-913d-23de396c82f0)
# 3. DBMS (MYSQL) (1.1.1)
## 3-1. Tables
![todo_list](https://github.com/ddalkyTokky/KotlinSpring_TODO_Backend/assets/47583083/4330220b-867d-48a1-8acb-2b983596e0af)
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
카카오 로그인 (FE가 직접 URL을 제공한다는 시나리오)
[https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=c2cd759e95a110bd1bd3208ce6069b5e&redirect_uri=http://soonyong.strawberryrabbit.kro.kr/member/redirect](https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=c2cd759e95a110bd1bd3208ce6069b5e&redirect_uri=http://soonyong.strawberryrabbit.kro.kr/member/redirect)

# 5. 코드 특징
작성 요.
