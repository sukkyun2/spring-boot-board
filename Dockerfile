FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /workspace/app

COPY . .

RUN chmod 777 ./mvnw
RUN ./mvnw clean package
RUN java -Djarmode=layertools -jar target/board-0.0.6.jar extract --destination target/extracted
RUN mkdir -p target/extracted && (cd target/extracted; jar -xf ../board-0.0.6.jar)

FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG EXTRACTED=/workspace/app/target/extracted
COPY --from=build ${EXTRACTED}/dependencies/ ./
COPY --from=build ${EXTRACTED}/spring-boot-loader/ ./
COPY --from=build ${EXTRACTED}/snapshot-dependencies/ ./
COPY --from=build ${EXTRACTED}/application/ ./

ARG PINPOINT_VERSION
ARG AGENT_ID
ARG APP_NAME

ENV JAVA_OPTS="-javaagent:/pinpoint-agent/pinpoint-bootstrap-${PINPOINT_VERSION}.jar -Dpinpoint.agentId=${AGENT_ID} -Dpinpoint.applicationName=${APP_NAME} -Dspring.profiles.active=${SPRING_PROFILES}"

CMD java ${JAVA_OPTS} org.springframework.boot.loader.JarLauncher