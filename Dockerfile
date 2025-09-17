# Usar imagem oficial do OpenJDK 21
FROM eclipse-temurin:21-jdk

# Diretório de trabalho no container
WORKDIR /app

# Copiar o arquivo pom.xml e baixar dependências (cache)
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline -B

# Copiar o código fonte
COPY src src

# Compilar o projeto
RUN ./mvnw clean package -DskipTests

# Executar o .jar
CMD ["java", "-jar", "target/GerenciamentoDeTarefas-0.0.1-SNAPSHOT.jar"]