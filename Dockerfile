# Этап 1: Создание слоев
FROM openjdk:21-jdk-slim AS layers
WORKDIR /application
COPY target/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

# Этап 2: Запуск приложения
FROM openjdk:21-jdk-slim
VOLUME /tmp
RUN useradd -ms /bin/bash spring-user
USER spring-user

EXPOSE 8080

COPY --from=layers /application/dependencies/ ./dependencies/
COPY --from=layers /application/spring-boot-loader/ ./spring-boot-loader/
COPY --from=layers /application/snapshot-dependencies/ ./snapshot-dependencies/
COPY --from=layers /application/application/ ./application/

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
