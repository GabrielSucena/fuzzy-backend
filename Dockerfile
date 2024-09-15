FROM openjdk:21
WORKDIR app
COPY target/courses-0.0.1-SNAPSHOT.jar app.jar
COPY Wallet Wallet
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]