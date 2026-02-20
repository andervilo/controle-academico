# Dockerfile Template (multi-stage)

FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
# mvn -q -DskipTests=false test package  (ou gradle)
# ajuste para seu build

FROM eclipse-temurin:21-jre
WORKDIR /app
RUN useradd -r -u 10001 appuser
USER appuser
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENV JAVA_OPTS=""
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar app.jar"]
