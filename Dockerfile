FROM bellsoft/liberica-openjdk-alpine-musl
COPY ./target/WebApplication-0.0.1-SNAPSHOT.jar .
CMD ["java","-jar","WebApplication-0.0.1-SNAPSHOT.jar"]