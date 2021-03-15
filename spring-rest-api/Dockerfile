
# Base Alpine Linux based image with OpenJDK JRE only
FROM openjdk:11-jre-slim-buster
# copy application WAR (with libraries inside)
COPY build/libs/spring-rest-api-*.jar /app.jar
# specifying a default profile to be used in case no other is specified at runtime
ENV SPRING_PROFILE_ACTIVE=default
# specify default command
CMD ["java", "-jar", "-Dspring.profiles.active=${SPRING_PROFILE_ACTIVE}", "/app.jar"]