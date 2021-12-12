FROM maven:3.8-jdk-8 AS builder

ENV debian_frontend=noninteractive

RUN mkdir /app

COPY . /app/

WORKDIR /app

RUN cd /app && mvn clean package

FROM jetty:9.4-jdk8-slim AS runner
COPY --from=builder /app/target/workcenter.war /var/lib/jetty/webapps/workcenter.war