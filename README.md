# SWEngineering

## 소프트웨어공학(02) 팀프로젝트 6조

### 구성원

|                     조장                      |                 팀원                 |                  팀원                  |                  팀원                   |
| :-------------------------------------------: | :----------------------------------: | :------------------------------------: | :-------------------------------------: |
| [송학현](https://github.com/alanhakhyeonsong) | [김주영](https://github.com/KimJooY) | [신재훈](https://github.com/newjaehun) | [정윤찬](https://github.com/dbscks2140) |
|               BackEnd & Android               |               Android                |                Android                 |                 Android                 |

### Used Stack

- Client: Android
- BackEnd: Spring Boot, Spring Data JPA, H2 Database(test), MySQL(배포용), Docker

Spring framework로 만든 Restful API 서버와 이를 이용한 Android 앱의 구조입니다.

![system structure](https://user-images.githubusercontent.com/60968342/143846151-31c9fe60-f6ed-4d69-b1b9-63e60bda0099.png)

## 개발과정에서 Repository를 사용하기 위한 Git/GitHub 설정 Guide

### git config 설정

1. 깃 설치 후 사용환경 설정

```bash
git config --global user.email "you@example.com"
git config --global user.name "Your Name"
```

2. git clone

![스크린샷 2021-11-29 오후 1 46 20](https://user-images.githubusercontent.com/60968342/143810432-335add9c-efc3-466f-b113-5d5042c241e1.png)

clone 에서 링크 복사하면 됩니다.

```bash
git clone (복사한주소)

// 예시
git clone https://github.com/chosunMotherBird/SWEngineering.git
```

3. git remote 설정

로컬 환경에서 작업할 수 있도록 remote 저장소를 연결

- 원격 저장소 등록

```bash
git remote add (저장소 별칭) (저장소 url)
git remote add hakhyeon https://github.com/chosunMotherBird/SWEngineering.git
```

- git remote 확인 git remote 설정이 제대로 되었는지 확인하는 방법은 아래 명령어 입력

```bash
git remote -v
/*
origin  https://github.com/chosunMotherBird/SWEngineering.git (fetch)
origin  https://github.com/chosunMotherBird/SWEngineering.git (push)
*/
```

### commit & push

1. git 저장소 생성

```bash
git init
```

2. git branch 생성

```bash
git branch 브랜치이름
//예시
git branch hakhyeon
```

3. branch 이동

```bash
git chekcout 이동할브랜치이름
//예시
git checkout 6mn12j
```

4. git add

```bash
git add .
```

5. git commit

```bash
git commit -m "커밋 메시지"
```

6. git push : add된 로컬 파일들을 원격 저장소로 push

```bash
git push (저장소 별칭) (브랜치이름)
//예시
git push origin hakhyeon
```

7. pull :현재 원격저장소의 커밋을 로컬로 가져옴

```bash
git pull (저장소 별칭) (브랜치이름)
/* 주로 아래 방법 처럼 하면 됩니다. 본인 origin으로 등록된 repository의 master브랜치 에서 pull 땡겨옴 */
git pull origin master
```

8. PR 보내기

push 작업까지 한 뒤 main 또는 master 브런치로 Pull Request를 보내려고 하는데, push까지 완료하면 자동으로 레포지토리 상에 알림이 뜨는데 PR 요청을 보내는 창을 보고 그대로 누르고 PR을 보내면 됩니다.

// 단 요청만 보내고 승인은 제가 하겠습니다.

## 📌 참고

본인 작업은 반드시 본인 branch 에서 작업하고 add, commit, push를 해야 함.

타인의 branch는 절대 손대지 말것!
