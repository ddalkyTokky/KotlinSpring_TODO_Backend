# 0. Blog
**[[스파르타 코딩클럽] TODO 서버 개발 기획 문서](https://strawberryrabbit.tistory.com/6)**
# 1. USE CASE
# 2. DBMS
## 2-1. Tables
![erd](https://github.com/ddalkyTokky/KotlinSpring_TODO_Backend/assets/47583083/c97c9d9a-9f9a-4788-a585-be762c30e980)
## 2-2. DDL
```
CREATE TABLE `member` (
	`id` bigint UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	`name` VARCHAR(16) NOT NULL,                   
	`pw` VARCHAR(32) NOT NULL                   
);                   
                   
CREATE TABLE `todo` (                   
	`id` bigint UNSIGNED AUTO_INCREMENT PRIMARY KEY,                   
	`member_id` bigint UNSIGNED NOT NULL,                   
	`title`	VARCHAR(32) NOT NULL,                   
	`content` VARCHAR(255) NOT NULL,                   
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

# 3. API
![image](https://github.com/ddalkyTokky/KotlinSpring_TODO_Backend/assets/47583083/83f8bd8b-c2e2-48df-912b-61aa3ecb2b82)
