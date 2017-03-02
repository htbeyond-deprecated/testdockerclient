FROM java:8-jre
MAINTAINER Sukkyo Suh <suktheman@htbeyond.com>

ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir /webapps
COPY test-docker-client-0.0.1-SNAPSHOT.war /webapps/test-docker-client-0.0.1-SNAPSHOT.war

ENTRYPOINT java -Xmx256m -Xss256k -jar /webapps/test-docker-client-0.0.1-SNAPSHOT.war && bash