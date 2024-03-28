FROM openjdk:17-slim
COPY build/libs/eureka.jar eureka.jar
ENTRYPOINT ["java","-DSpring.profiles.active=prod","-jar","eureka.jar"]
