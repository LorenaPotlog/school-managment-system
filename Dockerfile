FROM openjdk:17-alpine
WORKDIR /school-app
COPY target/school-0.0.1.jar /school-app
EXPOSE 8080
ENTRYPOINT ["java","-jar", "school-0.0.1.jar"]