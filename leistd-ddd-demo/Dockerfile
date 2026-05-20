FROM hub.mingyuanyun.com/java/eclipse-temurin:21-jre
WORKDIR /app
EXPOSE 8080

ENV JAVA_OPTS=''

COPY permission-start/target/*.jar app.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar"]