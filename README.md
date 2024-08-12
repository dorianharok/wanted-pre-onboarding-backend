# 채용 공고 관리 시스템

이 프로젝트는 원티드 프리온보딩 백엔드 인턴십 과제로 제작된 채용 공고 관리 시스템입니다.

## 기술 스택

- Java 17
- Spring Boot 3.3.2
- Spring Data JPA
- MySQL
- Lombok
- JUnit 5

## 요구사항 분석 및 구현 내용

1. 채용공고를 등록합니다.
    - `JobPostingController`의 `createJobPosting` 메소드에서 구현
    - 채용 포지션, 보상금, 내용, 사용 기술, 회사 ID를 입력받아 등록

2. 채용공고를 수정합니다.
    - `JobPostingController`의 `editJobPosting` 메소드에서 구현
    - 채용공고 ID, 회사 ID 이외의 모든 필드를 수정 가능

3. 채용공고를 삭제합니다.
    - `JobPostingController`의 `deleteJobPosting` 메소드에서 구현

4. 채용공고 목록을 가져옵니다.
    - `JobPostingController`의 `getJobPostings` 메소드에서 구현

5. 채용 상세 페이지를 가져옵니다.
    - `JobPostingController`의 `getJobPostingDetails` 메소드에서 구현
    - 채용내용, 회사가 올린 다른 채용공고 포함

6. 채용공고에 지원합니다.
    - `JobApplicationController`의 `applyForJob` 메소드에서 구현
    - 사용자는 1회만 지원 가능

## API 명세

| Method | URL | 설명 |
|--------|-----|------|
| POST | /api/v1/job-postings | 채용공고 등록 |
| PUT | /api/v1/job-postings/{id} | 채용공고 수정 |
| DELETE | /api/v1/job-postings/{id} | 채용공고 삭제 |
| GET | /api/v1/job-postings | 채용공고 목록 조회 |
| GET | /api/v1/job-postings/{id} | 채용공고 상세 조회 |
| POST | /api/v1/job-applications | 채용공고 지원 |

## 추가 정보

- `데이터베이스`: 프로젝트는 MySQL 데이터베이스를 사용합니다. 실행 전 반드시 MySQL 서버가 실행 중이어야 하며, 적절한 데이터베이스와 사용자 권한이 설정되어 있어야 합니다.
- `예외 처리`: BusinessException과 ErrorCode를 사용하여 비즈니스 로직 관련 예외를 처리합니다.
