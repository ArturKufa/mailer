FROM openjdk:11.0.5-stretch
ADD /build/libs/mailer-search-0.0.1-SNAPSHOT.jar .
EXPOSE 8081
ENV ID=0
ENV TIME_DELAY=0
CMD java -jar mailer-search-0.0.1-SNAPSHOT.jar ${ID} ${TIME_DELAY}
