# Week04 Solution - Spring Boot Memory CRUD

완성 예제입니다. 학생 배포용보다는 교수/TA 확인용으로 사용하세요.

## API
- POST `/api/books` -> 201
- GET `/api/books` -> 200
- GET `/api/books/{id}` -> 200 / 404
- PUT `/api/books/{id}` -> 200 / 404
- DELETE `/api/books/{id}` -> 204 / 404

## 저장소
DB 없이 `LinkedHashMap<Long, Book>` 사용. 애플리케이션 종료 시 데이터는 사라집니다.
