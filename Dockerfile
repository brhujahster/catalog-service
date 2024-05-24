FROM eclipse-temurin:17 as builder
WORKDIR workspace
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} catalog-service.jar
RUN java -Djarmode=layertools -jar catalog-service.jar extract
ENTRYPOINT ["java", "-jar", "catalog-service.jar"]

FROM eclipse-temurin:17
run useradd spring
USER spring
WORKDIR workspace
COPY --from=builder workspace/dependencies/ ./
COPY --from=builder workspace/spring-boot-loader ./
COPY --from=builder workspace/snapshot-dependencies ./
COPY --from=builder workspace/application ./
ENTRYPOINT ["java", "-jar", "org.springframework.boot.loader.launcher"]
