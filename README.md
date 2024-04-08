빠른 배포부터 서비스가 커지고 트래픽이 커지는 것을 가정한 프로젝트 입니다. 

Monolithic한 프로젝트에서 점차 MSA 구조를 갖추어 가면서 각 파트에서 어떤 기능과 아키텍처가 필요할지 고민해보려고 합니다.

http://www.scottcommerce.net/swagger-ui/index.html
<br> 상태: ON
<br>** account-api 작동중 (order-api 수정 중)
<br><br> 유의사항 
<br> 1. 비용 이슈로 한정적으로 오픈 하고 있습니다. (평일: 9am - 6pm)
<br> 2. 해외 IP에서는 접근이 불가합니다. 
<br> 3. a-test-controller에서 jwt토큰을 받아서 Authorize 버튼 클릭해 등록 후 확인 가능합니다. (sign-up에서만 요구하지 않음.)
<br> 4. 현재는 비용 이슈로 EC2에 docker-compose를 이용해 띄워져 있으며 필요시 각 단계별 구성을 포트폴리오 제출 또는 현장시연 가능합니다.

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

![multi-module drawio](https://github.com/ScottSung7/CommercialPractice/assets/98432596/a6ae1da5-9697-421f-85ce-0ce5cad70134)

<br>

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
- 상세 URI 추가 예정

### Chat-api
- 상세 URI 추가 예정

