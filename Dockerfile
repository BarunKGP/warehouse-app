#Initial build
FROM amazoncorretto:11 as builder
ARG JAR_FILE=target/*.jar

#Port on which the spring boot app will run
EXPOSE 8080

#Add the jar file to the image
ADD target/demo-spring-app-0.0.1-SNAPSHOT.jar usr/app/demo-spring-docker-app.jar

# COPY h2-1.4.200.jar usr/app
# WORKDIR usr/app/

# CMD [ "java", "-jar", "/h2-1.4.200.jar" ]

#Command to run the app in the image
ENTRYPOINT [ "java", "-jar", "/usr/app/demo-spring-docker-app.jar" ]



