FROM maven:3.9.11-amazoncorretto-21-al2023 AS build
WORKDIR /build

COPY . .

RUN mvn clean package -DskipTests

FROM amazoncorretto:21.0.5
WORKDIR /app

COPY --from=build /build/target/*.jar ./app.jar

EXPOSE 8080
EXPOSE 9090

ENV ENV_URL_DATABASE=''
ENV ENV_USERNAME=''
ENV ENV_PASSWORD=''
ENV GOOGLE_ID_CLIENT=''
ENV GOOGLE_CLIENT_SECRET=''
ENV SPRING_PROFILE_ACTIVE='prod'
ENV TZ='America/Sao_Paulo'

ENTRYPOINT ["java", "-jar", "app.jar"]