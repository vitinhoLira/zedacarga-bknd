FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/*.jar app.jar
COPY src/main/resources/keystore.p12 keystore.p12
EXPOSE 8443
ENTRYPOINT ["java","-jar","/app.jar"]
CMD ["java", "-jar", "/app.jar", "--server.ssl.key-store=keystore.p12", "--server.ssl.key-store-password=122604Fqv@", "--server.ssl.key-store-type=PKCS12"]
