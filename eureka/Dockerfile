FROM openjdk:17-slim
COPY eureka/build/libs/eureka.jar eureka.jar
ENTRYPOINT ["java","-DSpring.profiles.active=prod","-jar","eureka.jar"]
