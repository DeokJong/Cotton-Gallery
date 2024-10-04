
# 프로젝트 시작 가이드

## 1. 프로젝트 소개

본 프로젝트는 **도커(Docker)** 기반의 컨테이너화된 백엔드 서버를 활용하여 로컬 개발 환경을 보다 간편하게 구축하는 것을 목표로 합니다.

## 2. 사전 요구사항

프로젝트를 시작하기 전에 아래의 도구들이 설치되어 있어야 합니다.

- [Docker](https://www.docker.com/get-started) (버전 XX 이상)
- [Docker Compose](https://docs.docker.com/compose/) (버전 XX 이상)
- [Git](https://git-scm.com/downloads) (버전 XX 이상)
- Java Development Kit (JDK XX)
- PostgreSQL 클라이언트 (선택 사항)

## 3. 프로젝트 클론 및 설정

## 4. 서버 실행 방법

### **4.0 Docker Compose로 Build**

도커로 모든 서비스를 빌드하고 실행:

```bash
docker-compose up --build
```

성공적으로 컨테이너가 실행되면, 애플리케이션 서버는 http://localhost:8080에서 접근 가능합니다.

### **4.1 Docker Compose 실행**

도커로 이미 빌드된 시스템을 실행:

```bash
docker-compose up
```

성공적으로 컨테이너가 실행되면, 애플리케이션 서버는 http://localhost:8080에서 접근 가능합니다.

### **4.2 Docker 컨테이너 확인 및 로그 확인**

실행 중인 컨테이너 상태를 확인:

```bash
docker ps
```

로그를 확인하려면:

```bash
docker-compose logs app
```

## 5. 개발 중 유용한 명령어

컨테이너 중지:

```bash
docker-compose down
```

컨테이너 재시작:

```bash
docker-compose restart
```

개별 컨테이너 진입:

```bash
docker exec -it <container_name> /bin/bash
```

## 6. 추가 설정 및 참고

데이터베이스 마이그레이션: 데이터베이스 변경 사항이 있을 경우, 필요한 스크립트나 마이그레이션 툴을 사용하세요.
환경 변수 설정: .env 파일을 사용해 민감한 정보 및 환경 설정을 관리하세요.

# Git Branch 전략

본 프로젝트는 Git 브랜치 전략을 통해 코드 품질과 협업 효율성을 높이기 위해 아래와 같은 브랜치 전략을 따릅니다.

## 1. 브랜치 구조

### 1.1 **Main 브랜치**

- `main` 브랜치는 **배포 가능한 최신 안정 버전**을 유지합니다.
- 코드 병합 시, 모든 테스트가 통과하고, 코드 리뷰가 완료된 상태여야 합니다.
- 직접적으로 이 브랜치에 커밋하지 않으며, 배포 시에만 사용됩니다.

### 1.2 **Develop 브랜치**

- `develop` 브랜치는 **기능 개발이 완료된 통합 브랜치**입니다.
- 새로운 기능, 버그 수정 등은 모두 이 브랜치에서 관리됩니다.
- `feature` 브랜치가 이곳에 병합되며, 주기적으로 `main` 브랜치에 병합됩니다.

### 1.3 **Feature 브랜치**

- `feature/{이름}` 브랜치는 특정 기능 개발을 위해 사용됩니다.
- `develop` 브랜치로부터 생성하며, 작업 완료 후 `develop` 브랜치로 병합됩니다.
- 브랜치 이름은 기능의 내용을 명확히 나타내는 이름으로 설정합니다.
  - 예시: `feature/user-authentication`, `feature/shopping-cart`

### 1.4 **Hotfix 브랜치**

- `hotfix/{이름}` 브랜치는 배포된 버전에서 **긴급한 버그 수정**을 위한 브랜치입니다.
- `main` 브랜치로부터 생성되며, 수정이 완료되면 `main`과 `develop` 브랜치로 병합됩니다.
- 긴급한 수정 사항이 있을 경우에만 이 브랜치를 사용합니다.
  - 예시: `hotfix/login-issue`

### 1.5 **Release 브랜치**

- `release/{버전명}` 브랜치는 **다음 배포 버전**을 준비하기 위한 브랜치입니다.
- 기능 개발이 완료된 후, QA와 최종 테스트를 거치기 위해 사용됩니다.
- 릴리스가 완료되면 `main`과 `develop` 브랜치에 병합됩니다.
  - 예시: `release/1.0.0`

---

## 2. 브랜치 생성 및 병합 규칙

### 2.1 **브랜치 생성**

- 브랜치 생성 시, 아래와 같은 명명 규칙을 따릅니다:
  - `feature/{기능명}`
  - `hotfix/{버그명}`
  - `release/{버전명}`
  
  예시:

  ```bash
  git checkout -b feature/user-authentication
  ```

### 2.2 **Pull Request(PR)**

- 각 브랜치 작업이 완료되면, 해당 브랜치를 `develop` 또는 `main`에 병합하기 위해 **Pull Request**를 생성합니다.
- PR 생성 시, 코드 리뷰어를 지정하고, 리뷰가 완료될 때까지 병합하지 않습니다.
- PR 작성 시에는 **작업한 내용**과 **해결된 이슈**를 명확히 설명해야 합니다.

### 2.3 **병합(Merge)**

- 코드 리뷰 및 테스트가 완료된 후, 병합을 진행합니다.
- 병합시 develop branch로 merge 할 때는 PR을 통해 Merge Commit 을 남기면서 병합합니다.
- 병합시 main branch로 merge 할 때는 PR을 통해 Squash and Merge 방식으로 병합합니다.

---

## 3. Git Flow 전략 vs GitHub Flow 전략

### Git Flow 전략

- 복잡한 프로젝트 관리에 적합하며, 기능별로 브랜치를 관리합니다.
- `main`, `develop`, `feature`, `release`, `hotfix` 브랜치를 사용하여 체계적으로 관리합니다.

### GitHub Flow 전략

- 배포 빈도가 잦은 프로젝트에서 사용되며, 단순한 브랜치 구조를 가집니다.
- `main` 브랜치와 **feature 브랜치**만을 사용하여 빠르게 배포하는 것이 핵심입니다.

---

## 4. 브랜치 전략 요약

- **Main**: 배포 가능한 안정된 코드.
- **Develop**: 기능 개발이 통합된 브랜치.
- **Feature**: 특정 기능을 개발하는 브랜치.
- **Hotfix**: 배포 후 긴급한 버그 수정 브랜치.
- **Release**: 배포 전 최종 테스트를 위한 브랜치.

이 브랜치 전략을 따름으로써 팀 간의 원활한 협업과 코드 관리의 일관성을 유지할 수 있습니다.
