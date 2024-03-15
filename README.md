빠른 배포부터 서비스가 커지고 트래픽이 커지는 것을 가정한 프로젝트 입니다. 

Monolithic한 프로젝트에서 점차 MSA 구조를 갖추어 가면서 각 파트에서 어떤 기능과 아키텍처가 필요할지 고민해보려고 합니다.

www.scottcommerce.net

## 1. Monolithic (Developer Monolithic Branch)
빠른 배포를 목표로 한 Monolithic한 구조로 AWS EC2하나에 배포하였습니다.
기능은 간단한 쇼핑몰로서 회원가입, 크레딧 충전, 결제, 상품등록같이 간단한 기능만 가질 예정 입니다.
<br><br>
 유연성: 이후에 EC2를 증설해 트래픽 처리는 가능하도록 Application Load Balancer를 구현하여 놓아서 최소한의 유연성에 대비해 놓았습니다. 또한, AutoScaling도 고려해 볼 수 있습니다.
<br><br>
 보안: 보안을 위해 배포를 하는 ec2를 private subnet에 두어 외부와 직접적인 제한 하였습니다. SSH통신은 Bastion Host Instance를 통해서 그리고 HTTP/HTTPS통신은 Application Load Balancer를 통해서 통신을 하도록 하여 혹시 모를 보안공격에 대비하여 IP를 사전에 차단하거나 배포하는 소스를 보호할 수 있도록 하였습니다. (Bastion Host는 Aws System Manager를 통한 통신으로 변경 예정.)
<br><br>
 속도 및 편의성: 빠른 배포가 목적이므로 EC2 인스턴스 내부에서 Redis나 Mysql을 쓰기보다 관리하기도 쉽고 편리하게 사용이 가능한 AWS서비스(ElasticCache, RDS-Mysql)를 배포 소스에 연동하였습니다. 

![aws drawio (1)](https://github.com/ScottSung7/CommercialPractice/assets/98432596/045f694e-362e-437f-adab-6fe19751a740)

## 2. Multi-Module (Developer Multi-Module Branch)
<br>

![multi-module](https://github.com/ScottSung7/CommercialPractice/assets/98432596/6c9aa6f7-afd8-455e-94f6-a43f80718cc9)



### Account-api
로그인 페이지로 스프링 시큐리티 세션을 이용하여 구현.
고객과 판매자의 로그인 페이지가 다르고 config도 다르다. (두개의 로그인 설정 및 페이지)

1. 고객
  - /accounts/customer/login
  <br>: 고객 로그인
  - /accounts/customer/signup
  <br>: 고객 회원가입
  - /accounts/customer/update
  <br>: 고객 정보 수정
  - /accounts/customer/verify
  <br>: 고객 이메일 인증

2. 판매자
  - /accounts/seller/login
  <br>: 판매자 로그인 
  - /accounts/seller/signup
  <br>: 판매자 회원가입
  - /accounts/seller/update
  <br> 판매자 정보 수정
  - /accounts/seller/verify/{email}
  <br> 판매자 이메일 인증

### Order-api
- 제작 중.
