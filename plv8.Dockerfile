FROM amazoncorretto:11-alpine
RUN apk --no-cache add curl
WORKDIR /opt/app
COPY build//libs//plv8-1.0-SNAPSHOT.jar app.jar
EXPOSE 8384
EXPOSE 5000
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5000","-jar","-Dspring.profiles.active=docker","app.jar"]