# Stage 1: Build the application
FROM maven:latest as builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

# Copie le reste du projet
COPY src src

# Construit le package sans exécuter les tests pour accélérer le processus
RUN mvn package -DskipTests

# Stage 2: Setup the application image
FROM tomcat:9-jdk11-openjdk-slim

# Supprime le répertoire webapps par défaut pour éviter les conflits
RUN rm -rf /usr/local/tomcat/webapps/*

# Copie le fichier WAR dans le répertoire webapps de Tomcat
COPY --from=builder /app/target/*.war /usr/local/tomcat/webapps/

EXPOSE 8080
CMD ["catalina.sh", "run"]
