FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# mvn/wrappper and pom
COPY ../../luxjobstats-api/mvnw .
COPY ../../luxjobstats-api/.mvn .mvn
COPY ../../luxjobstats-api/pom.xml .

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY ../../luxjobstats-api/src ./src

RUN ./mvnw package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

# copy jar from builder
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
