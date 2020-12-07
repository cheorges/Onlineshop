# Build Application with Java 11.
FROM gradle:6.7.1-jdk11-openj9 AS builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

# Build Application Docker Image for docker-compose.
FROM tomee:11-jre-8.0.4-plume
COPY config/postgresql-42.2.14.jar /usr/local/tomee/lib/
COPY config/tomee.xml /usr/local/tomee/conf/
COPY --from=builder /home/gradle/src/ear/build/libs/ /usr/local/tomee/webapps/
ENV DB_PORT "5432"
ENV DB_NAME "postgres"
ENV DB_USERNAME "postgres"
ENV DB_PASSWORD "postgres"
