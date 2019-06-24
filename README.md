# Getting Started

### 개발 환경
- Eclipse IDE for Enterprise Java Developers : Version: 2019-03 (4.11.0), Build id: 20190314-1200
- OS: Windows 10, v.10.0, x86_64 / win32
- Java version: 1.8.0_151
- maven
- spring boot 2.1.6 
- DB : HSQLDB (in memory)
- front-end : vue.js 이용
- library : pom.xml 참조


### 컴파일 방법
# mvn package -DskipTests

* 참고 test는 mock를 이용하지 않고 실제 DB용 이므로 skip 한다.


### 실행방법
# java -jar simplenotice-0.0.1-SNAPSHOT.jar
## http://localhost:8080 접속

* 실행시 schema.sql, data.sql 실행으로 자동으로 데이타 26개 추가됨.
* 별도 로그인 없음. 글 작성시 비밀번호 입력 (수정,삭제 시 사용)
* 초기 모든 글의 비밀번호는 "abc" 임. 
 
