빠른 배포부터 서비스가 커지고 트래픽이 커지는 것을 가정한 프로젝트 입니다. 

Monolithic한 프로젝트에서 점차 MSA 구조를 갖추어 가면서 각 파트에서 어떤 기능과 아키텍처가 필요할지 고민해보려고 합니다.

http://www.scottcommerce.net/swagger-ui/index.html
<br> 상태: OFF
<br><br> 유의사항 
<br> 1. 비용 이슈로 한정적으로 오픈 하고 있습니다. (평일: 9am - 6pm)
<br> 2. 해외 IP에서는 접근이 불가합니다. 
<br> 3. a-test-controller에서 jwt토큰을 받아서 Authorize 버튼 클릭해 등록 후 확인 가능합니다. (sign-up에서만 요구하지 않음.)


## 1. Monolithic (Developer Monolithic Branch)
빠른 배포를 목표로 한 Monolithic한 구조로 AWS EC2하나에 배포하였습니다.
기능은 간단한 쇼핑몰로서 회원가입, 크레딧 충전, 결제, 상품등록같이 간단한 기능만 가질 예정 입니다. <br><br>

기술 선택 이유:
#### A. 배 포:  AWS EC2 + Gradle + Application Load Balancer
- 아직 AWS 사용에 익숙하지 않아 **빠른 배포**라는 상황에 맞게 배우는데 시간을 많이 들이기 보다 SSH 접속 후 로컬과 동일하게 git clone하여 Gradle로 배포를 빠르게 진행 하였습니다. 
- ALB를 두어 필요시 서버를 빠르게 **Scale out** 할 수 있도록 하였습니다.

#### B. 보 안: Bastion Host + Spring Security  + ACM + AWS Shield + Application Load Balancer

- Bastion Host에서 private subnet에 접근 하도록하여 **외부 SSH 접속으로 부터 application을 보호 **하였습니다.
- ALB를 통해 HTTP/HTTPS 요청을 받아 **외부 HTTP/HTTPS 요청으로 부터 application을 보호** 하였습니다.
- Spring Security를 통해 다양한 웹 공격에 편리하게 방어하며 빠르게 로그인 기능을 구축하였습니다.
- AWS의 ACM을 통해 간편하게 사설 SSL/TLS X.509를 받아 프로젝트를  **HTTPS**로 구축할 수 있었습니다.
- AWS 네트워크에는 AWS Shield가 적용되어 있어 DDOS 공격 등에서 부터 보호를 받을 수 있었습니다. <br><br>

![aws drawio (1)](https://github.com/ScottSung7/CommercialPractice/assets/98432596/045f694e-362e-437f-adab-6fe19751a740)

## 2. Multi-Module (Developer Multi-Module Branch)
서비스의 종류가 늘어나면서 코드 관리의 편의성 위해 프로젝트를 Multi-Module로 구성하면서 포트번호로 API를 나누어 배포하여 약간의 MSA구조를 가미하였습니다. <br><br>

기술 선택 이유: 
#### A. 배 포:  AWS EC2 + Docker + Docker Compose + Gradle(Multi-Module) + Application Load Balancer
- API 추가에 관리 복잡성이 증가하지 않게 Gradle을 통해 **멀티 모듈**로 나누어 각 API를 관리합니다. 
- 처음에는 중앙 모듈에 의존성을 두고 필요한 라이브러리만 추가 했지만 각 모듈별로 의존성이 하나의 모듈에 응집되어 관리 복잡성이 증가한다 판단하여** 의존성을 모듈별로 따로 관리**하는 것으로 변경 하였습니다.
- 배포의 편의성을 위해 Docker를 통하여 각 모듈은 도커 컨테이너로 빌드 되어 관리됩니다.
- 이후 Docker Compose를 사용, 하나씩 docker pull할 필요없이 git clone 후 쉽게 배포 가능하게 하였습니다. 
- Chat-API의 경우 ALB의 **Sticky Session**을 이용하여 연결을 유지하였습니다.

#### B. 보 안: AWS ParameterStore + AWS WAF
- 처음에는 AWS S3를 통해 env파일을 가져와 빌드때 이용 후 삭제 하였으나 이후 **AWS ParameterStore**을 통해 한 번의 등록으로 각 모듈의 설정 정보들을 편하고 안전하게 관리하였습니다.
- 비정상적 접속이 감지 되어 AWS WAF를 통해 해외 IP등에 Block-List를 만들어 차단 할수 있었습니다. <br><br>

![multi-module2 drawio drawio](https://github.com/ScottSung7/CommercialPractice/assets/98432596/52e13c35-0144-4b8c-b982-6c51e7d8a025)


<br>

## 3. Micro Service Architecture (Developer Micro-Service Branch)
서비스가 커져 감에 따라 하나의 거대한 Multi-Module 또는 Monolithic 구조에서 서비스 하나하나 별로 관리하며 내부 통신을 효율적으로 하는 방법으로 변화되어 가게 됩니다. 다만, MSA 적용시 내부 통신이 복잡해지고 새로운 구조에 알맞는 기술들을 배우는 등의 비용도 고려하여 도입을 결정하여야 한다. <br><br>

기술 선택 이유: 

#### A. 배 포:  AWS ECS + ALB + AWS API Gateway + CI/CD (Code Pipeline) 

- 배포 과정을 자동화 하고자 AWS ECS와 Code Pipeline을 사용하였습니다.
- Code Pipeline을 통해 코드의 변경사항이 있을 시에 서버를 죽이지 않고 **무중단 배포**를 편리하게 할 수 있습니다.
- API마다 ECS 클러스터 서비스 만들고 모두 **AutoScaling**을 사용하여 늘어나는 트래픽에 서버가 죽지 않고 유연하게 대응하고 있습니다.

#### B. 내부/외부 통신 : Spring Cloud (Eureka & Feign) + AWS API Gateway + ALB + Kafka + CloudFront
- 각 API마다 ALB를 두어 API Gateway를 통해 요청이 들어온 주소에 대해 해당 ALB로 찾아가 주도록 하였습니다.
- IP주소 관리를 위해 Spring Eureka 서버를 두어 각 API의 AutoScaling 된 서버들의 IP 주소를 관리하고 **Feign Client를 통해 각 API와 내부 통신을 쉽게** 가능 하도록 하였습니다.
- MSA 구조에서는 내부 통신이 많을 것을 생각하여 통신 속도를 향상 시키기 위해 HTTP 1.1 보다 빠른 gRPC를 도입 중입니다. (진행 중)
- CloudFront를 이용하여 AWS Edge 서버에 캐시를 두어 클라이언트의 접속 속도를 빠르게 하였습니다. 
- 채팅의 경우 Sticky Session 사용시 특정 서버로 트래픽이 몰릴 수 있다고 생각하여 **Kafka를 통한 메시지 브로커 방식**으로 변경 중입니다. (진행 중) <br><br>

![MSA drawio](https://github.com/ScottSung7/CommercialPractice/assets/98432596/dd5b7cd5-2b9f-4aa1-a8cc-884384afd93d)

## API 명세 (위의 링크에서 Swagger UI로 확인 및 간단한 테스트도 가능합니다.)

### Account-api
0. 테스트용 계정 및 JWT 토큰 생성 (a-tester-controller)
- (POST) /test/create/customer : 구매자 회원가입 및 JWT 토큰 생성.
- (POST) /test/login/customer : 구매자 토큰 재발급.
- (POST) /test/create/customer : 판매자 회원가입 및 JWT 토큰 생성.
- (POST) /test/create/customer : 판매자 토큰 재발급.

2. 고객 CRUD (sign-up-controller)
- (POST) /accounts/customer/signup : 고객 회원가입.
- (PUT) /accounts/customer/update : 고객 정보 수정.
- (GET) /accounts/customer/verify/{email} : 고객 이메일 인증.

3. 판매자 CRUD (sign-up-controller)
- (POST) /accounts/seller/signup : 판매자 회원가입.
- (PUT) /accounts/seller/update : 판매자 정보 수정.
- (GET) /accounts/seller/verify/{email} : 판매자 이메일 인증.

4. 고객 정보 (account-info-controller)
- (POST) /accounts/customer: 고객 정보 조회.
- (POST) /accounts/seller: 판매자 정보 조회.

5. 고객 예치금 (balance-controller)
- (POST) /accounts/customer/balance: 고객 예치금 추가.
- (POST) /accounts/customer/balance/check: 고객 예치금 확인.

### Order-api
1. 상품 등록 CRUD (seller-product-controller)
- (POST) /seller/product: 상품 추가
- (PUT)  /seller/product: 상품 정보 변경
- (DELETE) /seller/product: 상품 삭제
- (POST) /seller/product/item: 상품 세부 아이템 추가
- (PUT) /seller/product/item: 상품 세부 아이템 변경
- (DELETE) /seller/product/item: 상품 세부 아이템 삭제
- (POST) /seller/product/myproducts: 판매자가 등록한 상품 전체 조회

2. 상품 검색 (search-controller)
- (GET) /search/product: 상품만 이름으로 검색
- (GET) /search/product/detail: 상품의 세부 아이템 조회 

4. 장바구니 저장 (customer-cart-controller)
- (GET) /customer/cart : 카트 내역 가져오기
- (POST) /customer/cart : 카트 내역 추가
- (DELETE) /customer/cart : 카트 비우기

### Chat-api
1. 방 만들기 (room-controller)
- (GET) /chat/{id} : 방 입장하기
- (GET) /chat/{id}/userlist : 방의 참여 유저 리스트 가져오기
- (POST) /chat/invite : 방 만들며 유저 초대하기
- (POST) /chat/addParticipants : 유저 추가

2. 유저 정보 가져오기 (user-info-controller)
- (POST) /chat-info/myInfo : 나의 정보 가져오기
- (POST) /chat-info/roomList: 내가 소속된 방 확인
- (POST) /chat-info/userInfo : 유저 정보 가져오기 

4. 유저 정보 검색 (user-search-controller) <내부 통신 - account-api>
- (POST) /chat-search/customer : 구매자 검색
- (POST) /chat-search/seller : 판매자 검색
- (POST) /chat-search/user : 전체 유저 검색
  
  

