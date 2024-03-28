FROM openjdk:17-slim
COPY build/libs/account-api.jar account-api.jar
ENTRYPOINT ["java","-DSpring.profiles.active=docker","-jar","account-api.jar"]