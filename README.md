#  영화 관리 시스템 (Movie Management System API)

## ① 프로젝트 소개

### 1. 주제 및 관리 데이터
* **주제:** 영화 정보를 관리하는 RESTful 웹 서비스 (CRUD 및 입력 검증, 통계 기능 포함)
* **관리 데이터 (Fields):**
    * `id` (Long): 영화 고유 식별 번호
    * `title` (String): 영화 제목 (필수)
    * `director` (String): 감독명 (필수)
    * `genre` (String): 장르 (필수)
    * `year` (int): 개봉 연도 (1900 ~ 2100 범위 허용)
    * `price` (int): 가격 / 관람료 (0 이상만 허용)

### 2. 프로젝트 구조 (계층형 아키텍처)
```text
com.webservice.week04
 ┣ domain/       # 도메인 모델 (Movie)
 ┣ dto/          # 데이터 전송 객체 (MovieRequest, MovieResponse)
 ┣ repository/   # 데이터 저장소 (Java Collection 기반 Memory Repository)
 ┣ service/      # 비즈니스 로직 및 유효성 검증 (MovieService)
 ┗ controller/   # HTTP 요청 처리 및 엔드포인트 매핑 (MovieController)
```
### 3. 로컬 실행 방법
1. 프로젝트 루트 디렉토리에서 터미널을 엽니다.
2. 아래 명령어를 실행하여 빌드 및 실행합니다.
   ```bash
   ./gradlew bootRun
3. 로컬 서버 주소: http://localhost:8080

### 4. API Endpoint 표

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/movies` | 전체 영화 목록 조회 |
| GET | `/api/movies/{id}` | 특정 영화 단건 조회 (존재하지 않으면 404) |
| POST | `/api/movies` | 새 영화 등록 (잘못된 값이면 400 Bad Request) |
| PUT | `/api/movies/{id}` | 기존 영화 정보 수정 (유효성 검증 포함) |
| DELETE | `/api/movies/{id}` | 특정 영화 삭제 |
| GET | `/api/movies/count` | 등록된 영화 총 개수 조회 |

### 5. 요청 및 응답 JSON 예시

#### POST /api/movies (영화 등록 요청)

**Request Body:**

```json
{
  "title": "인셉션",
  "director": "크리스토퍼 놀란",
  "genre": "SF",
  "year": 2010,
  "price": 12000
}
```

**Response Body (200 OK):**

```json
{
  "id": 1,
  "title": "인셉션",
  "director": "크리스토퍼 놀란",
  "genre": "SF",
  "year": 2010,
  "price": 12000
}
```

#### GET /api/movies/count (영화 개수 조회 응답)

**Response (200 OK):**


### 6. 링크 정보

- **GitHub Repository URL:** https://github.com/sondonghyeon333/2026web_assign05
- **Deployment URL:** https://two026web-assign05-j0j6.onrender.com

---

## ② 개발환경 및 Dependency

### 1. 개발 환경 요약표

| 항목 | 작성 내용 |
|------|-----------|
| IDE | IntelliJ IDEA |
| JDK | Java 21 (Eclipse Temurin JDK 21) |
| Spring Boot | 3.5.5 |
| Build Tool | Gradle (Groovy DSL) |
| 데이터 저장 | Java Collection (List 및 Map 기반의 메모리 저장소 구현) |
| 배포 환경 | Render (Docker 기반 컨테이너 배포, 배포 URL 이용) |

### 2. 주요 Dependency 및 선정 이유

- **`spring-boot-starter-web`**
  - 필요한 이유: 클라이언트의 HTTP 요청(GET, POST, PUT, DELETE)을 받아 처리하는 컨트롤러 구현과 JSON 데이터 직렬화/역직렬화를 위한 필수 웹 스타터 라이브러리입니다.
- **`lombok`** (compileOnly / annotationProcessor)
  - 필요한 이유: Getter, Setter, 생성자 등 반복적으로 작성해야 하는 보일러플레이트 코드를 줄이고 가독성을 높이기 위해 사용합니다.
- **`spring-boot-starter-test`**
  - 필요한 이유: 애플리케이션의 정상적인 구동 및 유닛/통합 테스트 환경을 구성하기 위해 사용합니다.

## ③ Solution 분석

### 1. POST /api/books 요청이 들어오면 어떤 메서드를 순서대로 거치나요?
* **답변:** 클라이언트가 POST 요청을 보내면 `BookController`의 `create()` ➔ `BookService`의 `create()` (유효성 검증 `validateRequest()` 포함) ➔ `BookRepository`의 `save()` 순서대로 메서드를 거쳐서 데이터가 저장돼!
* **관련 클래스 및 메서드:** `BookController.create()`, `BookService.create()`, `BookRepository.save()`

---

### 2. 새 책의 ID는 어느 메서드에서 생성하나요?
* **답변:** 데이터 저장소인 `MemoryBookRepository`의 `save()` 메서드 안에서 생성해! 새로운 객체가 저장될 때마다 식별자 번호(`sequence`)를 1씩 증가시켜서 고유한 ID를 부여해 주는 방식이야.
* **관련 클래스 및 메서드:** `MemoryBookRepository.save()`

---

### 3. BookRequest, Book, BookResponse는 각각 왜 필요한가요?
* **답변:** 계층 간 역할을 명확하게 나누기 위해서야! `BookRequest`는 클라이언트가 보낸 입력 데이터를 받고, `Book`은 저장소에 보관되는 핵심 도메인 객체로 사용되며, `BookResponse`는 클라이언트에게 필요한 데이터만 예쁘게 정돈해서 돌려주는 역할을 해. 이렇게 분리해야 데이터가 오염되지 않고 유지보수도 쉬워지거든!
* **관련 클래스:** `BookRequest`, `Book`, `BookResponse`

---

### 4. Optional.ofNullable()과 orElseThrow()는 각각 어떤 역할을 하나요?
* **답변:** Null 에러(NPE)를 안전하게 방지하려고 써! `Optional.ofNullable()`은 값이 null일 수도 있는 객체를 감싸서 다루기 쉽게 만들어 주고, `orElseThrow()`는 만약 안의 값이 null(빈 상태)일 때 지정한 예외(예: `ResponseStatusException` 404 Not Found)를 바로 터뜨려 주는 역할을 해!
* **관련 클래스 및 메서드:** `java.util.Optional`, `Optional.ofNullable()`, `Optional.orElseThrow()`

---

### 5. 서버를 재시작하면 등록한 데이터는 어떻게 되나요? 그 이유는 무엇인가요?
* **답변:** 기존에 등록했던 데이터가 전부 날아가고 초기화돼! 실제 RDB(MySQL 등)가 아니라 자바 실행 메모리 컬렉션(`Map`, `List`)에 임시로 저장해 두었기 때문에, 서버가 종료되면 메모리가 싹 비워지기 때문이야!
* **관련 클래스:** `MemoryBookRepository`

---

## ④ 개발 과정 요약

1. **프로젝트 초기 설정 및 의존성 추가**
  * **내용:** Spring Boot 3.5.5 및 Java 21 환경을 구축하고, 웹 개발 및 편의를 위한 `spring-boot-starter-web`, `lombok` 의존성을 `build.gradle`에 설정함.
  * **관련 클래스:** `build.gradle`
  * **확인 방법:** 인텔리제이에서 Gradle Sync를 실행하여 오류 없이 라이브러리가 다운로드되는지 확인.

2. **도메인 모델 및 DTO 설계**
  * **내용:** 영화 정보를 담는 도메인 객체(`Movie`)와 클라이언트 요청/응답을 분리하기 위한 DTO(`MovieRequest`, `MovieResponse`) 클래스를 작성함.
  * **관련 클래스:** `Movie`, `MovieRequest`, `MovieResponse`
  * **확인 방법:** 컴파일 에러 여부 및 Getter/Setter 동작 확인.

3. **메모리 저장소 및 비즈니스 로직 구현**
  * **내용:** 자바 `Map`과 `List`를 활용해 DB 역할을 대신하는 `MemoryMovieRepository`를 구현하고, `MovieService`에 입력값 검증(`validateRequest`) 및 CRUD 로직을 작성함.
  * **관련 클래스:** `MemoryMovieRepository`, `MovieService`
  * **확인 방법:** 단위 테스트 또는 로컬 실행 후 데이터 저장/조회 테스트 진행.

4. **컨트롤러 구현 및 예외 처리**
  * **내용:** HTTP 요청을 받아 서비스로 전달하고 올바른 응답 상태 코드(`200 OK`, `400 Bad Request`, `404 Not Found`)를 반환하는 `MovieController`를 구현함.
  * **관련 클래스:** `MovieController`
  * **확인 방법:** Postman을 이용해 각 엔드포인트(`GET`, `POST`, `PUT`, `DELETE`) 요청 테스트 수행.

5. **Docker 컨테이너 설정 및 Render 배포**
  * **내용:** Java 21 기반의 멀티스테이지 `Dockerfile`을 작성하고, GitHub 연동을 통해 Render 플랫폼에 웹 서비스를 배포함.
  * **관련 설정 파일:** `Dockerfile`, `build.gradle`
  * **확인 방법:** Render 대시보드의 `Live` 상태 확인 및 배포 URL을 통한 API 호출 테스트.

---

## ⑤ 기능 수정·확장

### A. 영화 입력값 유효성 검증 및 400 Bad Request 예외 처리
* **기능을 추가한 이유:** 빈 제목, 잘못된 개봉 연도, 음수 가격 등 비정상적인 데이터가 등록·수정되는 것을 방지하여 서비스의 안정성을 높이기 위함.
* **수정한 클래스와 메서드:** `MovieService.validateRequest()`
* **테스트에 사용한 요청과 예상 결과:**
  * **요청 (POST):** `title`이 비어 있거나 `price`가 `-1000`인 JSON 데이터 전송
  * **예상 결과:** 잘못된 요청으로 판단하여 `400 Bad Request` 상태 코드와 에러 메시지 반환
* **실제 응답 결과:** 조건 불일치 시 `ResponseStatusException(HttpStatus.BAD_REQUEST, ...)`이 정상 발생하며 400 응답 반환 확인.

### B. 등록된 영화 총 개수 조회 기능 추가
* **기능을 추가한 이유:** 현재 메모리에 저장된 영화의 총 개수를 간편하게 파악할 수 있는 통계 엔드포인트를 제공하기 위함.
* **수정한 클래스와 메서드:** `MovieController.count()`, `MovieService.count()`, `MemoryMovieRepository.count()`
* **테스트에 사용한 요청과 예상 결과:**
  * **요청 (GET):** `/api/movies/count`
  * **예상 결과:** 현재 저장된 영화의 총 개수(예: 등록된 영화가 2개일 때 `2`)를 정수 형태로 반환
* **실제 응답 결과:** `200 OK` 상태 코드와 함께 정확한 영화 개수 데이터(`2`)가 JSON/텍스트로 정상 출력됨.

---

## ⑥ 배포 과정 요약

1. **자신이 수행한 빌드 및 배포 순서**
  * 프로젝트 최상단 루트 경로에 Java 21용 `Dockerfile` 작성 ➔ 변경된 코드를 Git 애드 및 커밋 후 GitHub 저장소에 푸시(`git push origin main`) ➔ Render 대시보드에서 `+ New Web Service` 선택 후 Docker 환경으로 배포 생성 ➔ 빌드 및 `Live` 전환 확인.
2. **배포를 위해 추가하거나 수정한 파일·설정**
  * **추가된 파일:** `Dockerfile` (Eclipse Temurin JDK 21 및 JRE 기반 멀티스테이지 빌드 설정, 포트 8080 개방)
3. **배포 중 발생한 문제와 해결 방법**
  * **문제:** 초기 배포 시 `chmod: cannot access 'gradlew': No such file or directory` 에러가 발생하며 빌드 실패 (`exit code: 1`).
  * **해결:** 깃허브 저장소 루트에 `gradlew` 실행 파일과 래퍼 폴더가 누락되었는지 확인한 후, 누락된 래퍼 파일들을 정상적으로 푸시하고 Render에서 'Clear build cache & deploy'를 실행하여 해결함.
4. **배포 URL로 확인한 요청과 응답**
  * **요청 (GET):** `https://two026web-assign05-j0j6.onrender.com/api/movies`
  * **응답:** 초기 상태의 빈 배열 `[]` 또는 등록된 영화 객체 리스트 JSON이 `200 OK`와 함께 정상 반환됨.

---

## ⑦ Weekly Report

* **Key Learning (직접 구현하며 이해한 내용 3가지):**
  1. **계층형 아키텍처 역할 분리:** Controller, Service, Repository의 책임을 명확히 나누어 비즈니스 로직과 웹 계층을 독립적으로 관리하는 구조를 체득함.
  2. **안전한 예외 처리 (`Optional`):** 자바의 `Optional` 클래스를 활용해 데이터가 존재하지 않을 때 `404 Not Found` 예외를 안전하고 직관적으로 터뜨리는 방법을 학습함.
  3. **실무형 배포 파이프라인 (Docker):** 단순 로컬 실행을 넘어 Docker 컨테이너와 Cloud PaaS(Render)를 연동하여 실제 웹 서비스를 외부 배포하는 전체 흐름을 경험함.
* **Problem & Solution (개발 또는 배포 중 겪은 문제와 해결 과정):**
  * **문제:** Render 배포 중 Gradle Wrapper 실행 파일(`gradlew`)을 찾지 못해 빌드가 중단되는 문제가 발생함.
  * **해결:** Git에 `gradlew` 파일이 포함되지 않았거나 경로가 잘못 지정된 것을 파악하고, 로컬 프로젝트의 래퍼 파일을 정확히 루트 디렉토리에 커밋·푸시한 뒤 캐시를 초기화하고 재배포하여 해결함.
* **Code Review (자신이 작성한 중요한 메서드 1개와 동작 설명):**
  * **메서드:** `MovieService.findById(Long id)`
  * **동작 설명:** 전달받은 영화 ID로 Repository를 조회할 때 반환되는 `Optional<Movie>` 객체에 `orElseThrow()`를 체이닝하여, 데이터가 존재하면 영화 객체를 그대로 반환하고 존재하지 않으면 `ResponseStatusException(HttpStatus.NOT_FOUND)`을 발생시켜 클라이언트에게 명확한 404 상태를 전달하는 안전한 조회 로직임.
* **AI Usage (AI에 질문한 내용, 참고한 답변, 직접 확인·수정한 부분):**
  * **질문 내용:** "Render에서 Java 21 스프링 부트 프로젝트를 Docker로 배포하는 `Dockerfile` 작성법"
  * **참고한 답변:** Eclipse Temurin Java 21 JDK/JRE 이미지를 활용한 멀티스테이지 빌드(`.jar` 빌드 및 실행 분리) 템플릿 코드.
  * **수정·확인 부분:** 우리 프로젝트의 빌드 도구와 포트 설정(`8080`)에 맞춰 스크립트 경로를 직접 검증하고 적용함.
* **Reflection (더 공부하고 싶은 내용 또는 궁금한 점):**
  * 현재는 메모리(`List`/`Map`)를 사용해 서버를 끄면 데이터가 초기화되는데, 이를 H2 데이터베이스나 외부 MySQL과 연동하여 영속성(Persistence)을 갖추는 방법을 더 깊이 공부해보고 싶음.
* **건의사항:**
  * Doker파일과 dependency에 참고자료를 좀 더 알려주시면 감사하겠습니다. 아직 잘 모르겠습니다.