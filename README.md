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
기능은 간단한 쇼핑몰로서 회원가입, 크레딧 충전, 결제, 상품등록같이 간단한 기능만 가질 예정 입니다. <br>
#### A. 유연성:
 - 이후에 EC2를 증설해 트래픽 처리는 가능하도록 **Application Load Balancer를** 구현하여 놓아서 최소한의 유연성에 대비해 놓았습니다. 또한, AutoScaling도 고려해 볼 수 있습니다.
#### B. 배포: 
- SSH통신은 **Bastion Host Instance**를 통해서 소스를 바로 배포하도록 하였습니다. ECS보다 EC2에서 바로 배포하는게 익숙하다고 생각하여 이렇게 구성하였습니다.(Bastion Host는 Aws System Manager를 통한 통신으로 변경 예정.)
#### C. 보안: 
 - 보안을 위해 배포를 하는 ec2를 **private subnet**에 두어 외부와 직접적인 제한 하였습니다. 
#### D. 사용 편의성:
  - 빠른 배포가 목적이므로 EC2 인스턴스 내부에서 Redis나 Mysql을 쓰기보다 관리하기도 쉽고 편리하게 사용이 가능한 **AWS서비스**(ElasticCache, RDS-Mysql)를 배포 소스에 연동하였습니다. 

![aws drawio (1)](https://github.com/ScottSung7/CommercialPractice/assets/98432596/045f694e-362e-437f-adab-6fe19751a740)

## 2. Multi-Module (Developer Multi-Module Branch)
서비스의 종류가 늘어나면서 코드 관리의 편의성 위해 프로젝트를 Multi-Module로 구성하면서 포트번호로 API를 나누어 배포하여 약간의 MSA구조를 가미하였습니다. <br>
#### A. 유연성:
  - ECS를 통하여 배포하며 **AutoScaling**을 통해 유연하게 트래픽에 대응할 수 있도록 하였습니다.
#### B. 배포:
 - 편의를 위해 각 API마다 **도커 컨테이너**로 만들어서 ECS를 통해 배포하였습니다. <br>
 - 아직은 크기가 크지 않으므로 ECS의 **각각의 EC2에 각자 할당받은 포트번호**로 배포를 하여 필요시 전체 크기를 넓혀가며 Auto-Scaling을 하고 각 EC2로 Load Balancing을 받습니다.
 - **Open Feign**을 통해 내부 HTTP 통신을 합니다. 
#### C. 보안:
 - ECS Instance들을 private subnet에 두는 것을 넘어서 **WAF**를 통해서 특정 지역이나 IP가 접속하지 못하도록 막고 있고 기본으로 제공되는 **Shield**를 통해 DDOS공격에 대비하고 있습니다.
#### D. 사용 편의성: 
 - **Multi-Module**구조로 만들어서 하나의 큰 Monlithic프로젝트가 아니라 하나의 API로 관리를 할수 있도록 하였습니다.<br>
 - **ECS**를 이용하여 배포를 하여서 컨테이너 관리를 손쉽게 할수 있습니다.<br>
 - Code Commit과 Code Build를 통한 **CI/CD를 자동화** 시키고 ECR을 통해서 손쉽게 배포하도록 구성하였습니다.

![multi-module2 drawio drawio](https://github.com/ScottSung7/CommercialPractice/assets/98432596/52e13c35-0144-4b8c-b982-6c51e7d8a025)


<br>

## 3. Micro Service Architecture (Developer Micro-Service Branch)
서비스가 커져 감에 따라 하나의 거대한 Multi-Module 또는 Monolithic 구조에서 서비스 하나하나 별로 관리하며 내부 통신을 효율적으로 하는 방법으로 변화되어 가게 됩니다. 다만, MSA 적용시 내부 통신이 복잡해지고 새로운 구조에 알맞는 기술들을 배우는 등의 비용도 고려하여 도입을 결정하여야 한다. 

#### A. 유연성:
- 각 서비스별로 Auto-scaling하여 운영되어서 Multi-Module의 경우처럼 요청이 늘어 났을때 서비스 전체를 키워야 하지 않아서 **각 서비스별로 유연하게 증감** 시킬 수 있습니다. 
- 서비스 별로 나누어 관리를 하게 되어 추상화된 인터페이스처럼 필요시 서비스별로 추가 제거가 쉽다.
#### B. 배포: 
- MSA의 구제에 알맞게 각 서비스마다 ECS 클러스터를 붙여서 각 서비스 단위로 관리를 합니다.
- **API Gateway**를 통해 외부에서 오는 요청을 각 서비스로 연결을 해줍니다.
- 요청이 많아 질것을 대비하여 내부 통신의 속도와 효율을 높이기 위하여 ** 필요시  HTTP 보다 다 빠른 gRPC**를 사용합니다.
- 채팅의 경우 Sticky Session 사용시 트래픽이 몰릴 수 있어 Kafaka를 사용하는 방법으로 변경 중입니다.
- 관리하는 환경파일이 많아지게 되어 AWS Parameter Store를 통해 환경 변수를 관리합니다.
#### C. 보안: 
-  CDN를 이용하여 **AWS의 Edge 서버에서의 캐싱을 통해 향상된 속도와 함께 AWS의 보안 서비스**를 받습니다.
- API Gateway를 두어 각 서비스별 연결 및 백엔드 자원에 대한 보호를 할 수 있으나 **병목지점**이 될 수 있습니다. 
#### D. 사용 편의성:
- 기본적으로 AWS의 서비스를 이용하지만 Jenkins, Kubernates를 이용하여 **추후 다른 클라우드 및 CI/CD 서비스와의 연계 또는 레거시한 프로젝트들과의 연계**도 가능 합니다. 
- 작은 단위로 관리가 되기에 유지보수가 쉬우며 빌드나 테스트의 속도도 빠르고 각 서비스별로 할 수 있어 덜 복잡합니다. <br>
![MSA drawio](https://github.com/ScottSung7/CommercialPractice/assets/98432596/dd5b7cd5-2b9f-4aa1-a8cc-884384afd93d)

## API 명세

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
  
  

