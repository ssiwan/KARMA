FROM openjdk:8-jre-alpine
RUN mkdir -p /app
ADD KarmaSpringboot/target/KarmaSpringboot-*.jar /app/boot.jar

EXPOSE 8080
ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app/boot.jar" ]
