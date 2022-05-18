FROM maven:3.8-jdk-8 AS builder

ENV debian_frontend=noninteractive

RUN mkdir /app

COPY . /app/

WORKDIR /app

RUN cd /app && mvn clean package

FROM jetty:9.4-jdk8-slim AS runner

ENV debian_frontend=noninteractive

USER root

RUN set -xe \
    && apt update \
    && apt install -y curl fontconfig libfreetype6 \
    && apt autoremove \
    && apt clean

USER jetty

COPY --from=builder /app/target/workcenter.war /var/lib/jetty/webapps/ROOT.war