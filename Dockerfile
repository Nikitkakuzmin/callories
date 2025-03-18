FROM openjdk:17-oracle
LABEL maintainer="nik"
COPY build/libs/callories-0.0.1-SNAPSHOT.jar callories.jar
ENTRYPOINT ["java", "-jar", "callories.jar"]