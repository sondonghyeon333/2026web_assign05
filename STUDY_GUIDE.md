# Week04 Solution 학습 가이드

이 프로젝트는 데이터베이스 없이 메모리에 책을 저장하는 Spring Boot REST CRUD 예제입니다. 코드를 실행한 뒤, 각 요청이 어떤 클래스와 메서드를 거치는지 직접 따라가 보세요.

> 이 Solution은 기본 예제로 `title`, `author`, `price`를 사용합니다. 과제에서 요구하는 추가 필드는 직접 확장하세요.

## 1. 먼저 실행해 보기

애플리케이션을 실행하고 Postman 또는 curl로 다음 요청을 순서대로 테스트합니다.

| 순서 | HTTP Method | URL | 확인할 내용 |
|---|---|---|---|
| 1 | POST | `/api/books` | 책 등록과 생성된 ID |
| 2 | GET | `/api/books` | 전체 목록 |
| 3 | GET | `/api/books/{id}` | 한 권 조회 |
| 4 | PUT | `/api/books/{id}` | 기존 책 수정 |
| 5 | DELETE | `/api/books/{id}` | 책 삭제 |
| 6 | GET | `/api/books/{id}` | 삭제한 ID의 응답 상태 |

등록 요청의 JSON 예:

```json
{
  "title": "Spring Boot",
  "author": "Kim",
  "price": 25000
}
```

## 2. 클래스와 역할 찾기

다음 클래스를 열어보고 각 클래스가 맡은 일을 한 문장으로 설명해 보세요.

| 클래스 | 확인할 항목 |
|---|---|
| `Week04BookCrudApplication` | 프로그램 시작 지점 |
| `BookController` | URL과 HTTP Method 처리 |
| `BookService` | CRUD 처리와 데이터 변환 |
| `BookRepository` | 저장소가 제공해야 할 기능 |
| `MemoryBookRepository` | 메모리에 데이터를 저장하는 방법 |
| `Book` | 저장소에서 관리하는 데이터 |
| `BookRequest` | 클라이언트가 보내는 데이터 |
| `BookResponse` | 클라이언트에게 반환하는 데이터 |

코드를 읽는 순서는 `BookController → BookService → BookRepository → MemoryBookRepository`를 권장합니다.

## 3. 주요 메서드 찾기

### `BookController`

- `create()`
- `findAll()`
- `findById()`
- `update()`
- `delete()`

각 메서드에 연결된 **HTTP Method와 URL**을 적어보세요. `@RequestBody`와 `@PathVariable`이 각각 어떤 값을 받는지도 확인합니다.

### `BookService`

- `create()`
- `findAll()`
- `findById()`
- `update()`
- `delete()`
- `findBook()`
- `toResponse()`

Controller가 Repository를 직접 호출하지 않고 Service를 거치는 이유를 생각해 보세요. 특히 `findBook()`과 `toResponse()`가 반복되는 코드를 어떻게 줄이는지 확인합니다.

### `BookRepository`와 `MemoryBookRepository`

인터페이스에 선언된 메서드가 구현체에서 어떻게 작성되었는지 비교합니다.

- `save()`
- `findAll()`
- `findById()`
- `update()`
- `deleteById()`

## 4. 핵심 키워드 정리

소스에서 아래 키워드를 찾아 위치와 역할을 정리해 보세요.

| 영역 | 키워드 |
|---|---|
| Spring Boot | `@SpringBootApplication`, `SpringApplication.run()` |
| HTTP 요청 | `@RestController`, `@RequestMapping`, `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping` |
| 요청 데이터 | `@RequestBody`, `@PathVariable` |
| 계층 구조 | `@Service`, `@Repository`, 인터페이스, 생성자 주입 |
| Java 데이터 구조 | `Map`, `LinkedHashMap`, `ArrayList`, `List`, `Optional` |
| Java 문법 | `record`, getter/setter, `stream()`, `map()`, `toList()` |
| 응답과 오류 | `ResponseEntity`, `HttpStatus`, `ResponseStatusException`, `orElseThrow()` |

## 5. 소스 분석 질문

코드에서 근거를 찾아 답해 보세요.

1. `POST /api/books` 요청이 들어오면 어떤 메서드를 순서대로 거치나요?
2. 새 책의 ID는 어느 메서드에서 생성하나요?
3. `BookRequest`, `Book`, `BookResponse`는 각각 왜 필요한가요?
4. `BookService`는 왜 `BookRepository` 인터페이스 타입으로 주입받나요?
5. `findById()`에서 찾는 ID가 없으면 어떤 과정으로 `404 Not Found`가 반환되나요?
6. `Optional.ofNullable()`과 `orElseThrow()`는 각각 어떤 역할을 하나요?
7. `findAll()`은 `List<Book>`을 어떻게 `List<BookResponse>`로 바꾸나요?
8. `create()`와 `delete()`에서 `ResponseEntity`를 사용하는 이유는 무엇인가요?
9. 서버를 재시작하면 등록한 데이터는 어떻게 되나요? 그 이유는 무엇인가요?

## 6. 직접 변경해 보기

아래 작업 중 하나 이상을 수행하고 결과를 확인해 보세요.

- `Book`에 `category`, `isbn` 필드를 추가하고 요청·응답 DTO와 Service 코드도 함께 수정하기
- 존재하지 않는 ID로 조회·수정·삭제를 요청하고 응답 상태 확인하기
- `BookController`의 `create()`에서 반환하는 상태 코드를 확인하고, 객체를 직접 반환하는 메서드와 비교하기
- `MemoryBookRepository`의 `store`에 데이터가 추가·수정·삭제되는 위치에 breakpoint를 설정해 실행 순서 확인하기

## 7. 학습 완료 확인

- [ ] 다섯 가지 CRUD 요청을 직접 실행했다.
- [ ] Controller, Service, Repository의 역할을 설명할 수 있다.
- [ ] 요청 JSON이 `BookRequest`로 전달되는 과정을 설명할 수 있다.
- [ ] `Book`이 `BookResponse`로 변환되는 위치를 찾았다.
- [ ] 데이터가 없을 때 404가 발생하는 위치를 찾았다.
- [ ] 메모리 저장소의 특징을 설명할 수 있다.