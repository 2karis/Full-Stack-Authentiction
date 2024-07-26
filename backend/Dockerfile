FROM  maven:3.9.6-eclipse-temurin-21-alpine

WORKDIR /app

COPY pom.xml /app/pom.xml

RUN mvn dependency:go-offline

COPY src /app/src

RUN rm /app/src/main/resources/application.properties

COPY config/application.properties /app/src/main/resources

RUN mvn install -DskipTests

CMD ["mvn", "spring-boot:run"]