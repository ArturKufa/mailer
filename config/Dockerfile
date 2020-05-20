FROM openjdk:11.0.5-stretch
ADD /build/libs/config-0.0.1-SNAPSHOT.jar .
EXPOSE 8888
CMD java -jar config-0.0.1-SNAPSHOT.jar

HEALTHCHECK --interval=30s --timeout=30s CMD curl -f http://localhost:8888/actuator/health || exit 1