# Stage 1: Build (Java 21)
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn -q -B dependency:go-offline

COPY src src
RUN mvn -q -B clean package -DskipTests

# Stage 2: Run (Tomcat 10 + JDK21)
FROM tomcat:10.1-jdk21-temurin
WORKDIR /usr/local/tomcat

# Nettoyer les apps par défaut
RUN rm -rf webapps/*

# Copier le WAR généré
COPY --from=builder /app/target/*.war webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
