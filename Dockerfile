FROM openjdk:11

WORKDIR /app/wisefee/

ARG JAR_PATH=../build/libs/
ARG RESOURCES_PATH=../build/resources/main/

COPY ${JAR_PATH}/*.jar /app/wisefee/wisefee.jar

ARG SPRING_PROFILES_ACTIVE=prod
ENV SPRING_PROFILES_ACTIVE=$SPRING_PROFILES_ACTIVE

ENTRYPOINT ["java", "-jar", "wisefee.jar"]