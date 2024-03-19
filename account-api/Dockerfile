FROM openjdk:17-slim
COPY build/libs/account-api.jar account-api.jar
ENTRYPOINT ["java","-DSpring.profiles.active=prod","-jar","account-api.jar"]