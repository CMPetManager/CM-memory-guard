FROM openjdk:17-alpine as builder
WORKDIR temp

ARG JAR_FILE=target/*.jar


COPY ${JAR_FILE} memory-guard.jar

RUN java -Djarmode=layertools -jar --enable-preview memory-guard.jar extract


FROM openjdk:17-alpine


ARG IMAGE_VERSION="0.0.1-SNAPSHOT"
ARG IMAGE_NAME="CatchTheMomentApp"


LABEL version=${IMAGE_VERSION} name=${IMAGE_NAME}

WORKDIR /home/memory/memory-guard


COPY --from=builder temp/dependencies/ ./
COPY --from=builder temp/snapshot-dependencies/ ./
COPY --from=builder temp/spring-boot-loader/ ./
COPY --from=builder temp/application/ ./


EXPOSE 8084

ARG JAVA_OPTS=""


ENTRYPOINT ["java","-Dspring.profiles.active=prod","org.springframework.boot.loader.JarLauncher"]