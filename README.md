# 🌡️ On-do

# 💡 프로젝트 소개

Ondo 프로젝트는 온도와 켜지다(ON)라는 중의적인 의미를 내포한 소셜 네트워크 서비스입니다.

사용자는 자신의 감정을 온도로 표현하여 다른 사용자들과 교감할 수 있습니다.

On기능을 사용해 친구를 팔로우 할 수 있습니다.

# 🚀STACK

Environment


![인텔리제이](   https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![](https://img.shields.io/badge/Gradle-02303a?style=for-the-badge&logo=gradle&logoColor=white)
![](https://img.shields.io/badge/Postman-ff6c37?style=for-the-badge&logo=postman&logoColor=white)
![깃허브](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)
![깃이그노어](https://img.shields.io/badge/gitignore.io-204ECF?style=for-the-badge&logo=gitignore.io&logoColor=white)
![깃](https://img.shields.io/badge/GIT-E44C30?style=for-the-badge&logo=git&logoColor=white)

Development

![스프링부트](https://img.shields.io/badge/SpringBoot-6db33f?style=for-the-badge&logo=springboot&logoColor=white)
![자바](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)

Communication

![슬랙](  https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white)
![노션](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white)

# 🏗️ 프로젝트 설계

### [Wireframe](https://app.diagrams.net/#G1RcLdWvKKMh_7lyUwlDD0fZw9KPu_snqT#{%22pageId%22:%2203018318-947c-dd8e-b7a3-06fadd420f32%22})

### [API Document](https://documenter.getpostman.com/view/37564576/2sAXjNWqFm)

### [ERD diagram](https://www.erdcloud.com/d/HENWahcE3QRN2q6Yk)

# ⚒️프로젝트 기능 정리

회원가입

    아이디, 이름, 비밀번호를 입력해 가입할 수 있습니다.
    아이디는 이메일 형식이며 중복된 아이디나 탈퇴한 아이디는 가입이 불가능합니다.
    비밀번호는 최소 8글자 이상이며 영문 대소문자, 숫자, 특수문자를 모두 포함해야 합니다.

회원탈퇴

    사용자는 비밀번호를 입력하여 탈퇴할 수 있습니다.
    탈퇴한 아이디로의 재가입은 불가능합니다.

프로필 조회

    프로필을 조회할 수 있습니다.
    다른 사용자의 프로필 조회 시 민감한 정보는 표시되지 않습니다.

프로필 수정

    비밀번호를 입력하여 본인의 사용자 정보를 수정할 수 있습니다.
    현재 비밀번호와 동일한 비밀번호로는 수정이 불가능합니다.

친구 등록

    다른 사용자를 On(팔로우) 할 수 있습니다.
    사용자 본인은 팔로우 할 수 없습니다.
    이미 팔로우하고 있는 사용자를 다시 팔로우할 수 없습니다.

친구 조회

    자신이 팔로우 하고있는 사용자들을 조회할 수 있습니다.
    팔로우하고 있는 사용자들의 Id와 이름이 조회됩니다.

친구 삭제

    팔로우를 취소할 수 있습니다.

게시물 작성

    게시물을 작성할 수 있습니다.
    자신의 상태를 나타내는 온도와 제목 내용을 입력하여 게시물을 작성합니다.

게시물 수정, 삭제

    사용자는 작성한 게시물을 수정, 삭제할 수 있습니다.
    
뉴스피드 조회

    팔로우한 사용자들의 최신 게시물을 조회할 수 있습니다.
    게시물은 10개 단위로 페이지네이션되어 조회됩니다.