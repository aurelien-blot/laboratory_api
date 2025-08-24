# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-11 AS builder
WORKDIR /app

# Copier pom.xml et télécharger les dépendances
COPY pom.xml .
RUN mvn dependency:go-offline

# Copier le code source et compiler
COPY src src
RUN mvn clean package -DskipTests

# Stage 2: Run on Tomcat (même JDK que le build)
FROM tomcat:9.0.96-jdk11-temurin
WORKDIR /usr/local/tomcat

# Nettoyer webapps par défaut
RUN rm -rf webapps/*

# Copier le WAR généré
COPY --from=builder /app/target/*.war webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]