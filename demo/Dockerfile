# Étape 1 : Utiliser une image Maven pour la compilation
FROM maven:3.9.4-eclipse-temurin-17 AS build

# Copier le code source dans le conteneur
COPY . /app

# Définir le répertoire de travail
WORKDIR /app

# Compiler l'application
RUN mvn clean package -DskipTests

# Étape 2 : Utiliser une image Java pour exécuter l'application
FROM eclipse-temurin:17-jdk-alpine

# Copier le fichier JAR compilé depuis l'étape précédente
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port sur lequel l'application fonctionne
EXPOSE 9090

# Démarrer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
