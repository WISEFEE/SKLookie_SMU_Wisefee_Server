FROM openjdk:11

WORKDIR /app/wisefee/

ARG JAR_PATH=../build/libs/

COPY ${JAR_PATH}/*.jar /app/wisefee/wisefee.jar

ENV PROFILE dev

ENTRYPOINT ["java", "-jar", "wisefee.jar"]