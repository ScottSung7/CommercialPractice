FROM openjdk:17-slim
COPY build/libs/account-api.jar account-api.jar
ENTRYPOINT ["java","-DSpring.profiles.acti만ve=prod","-jar","account-api.jar"]