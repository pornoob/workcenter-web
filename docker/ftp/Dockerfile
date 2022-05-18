FROM ubuntu:20.04

ENV debian_frontend=noninteractive

RUN apt update && \
    apt install -y vsftpd libpam-pwdfile

RUN set -xe \
    && mkdir -p /srv/ftp/vsftpd \
    && ln -sf /usr/share/zoneinfo/America/Santiago /etc/localtime \
    && addgroup vsftpd \
    && adduser --no-create-home --gecos "" --ingroup vsftpd vsftpd \
    && chown -R vsftpd:vsftpd /srv/ftp/ \
    && touch /var/log/vsftpd.log \
    && chown -R vsftpd:vsftpd /var/log/ \
    && chown -R vsftpd:vsftpd /var/run/

COPY ./docker/ftp/vsftpd.conf /etc/vsftpd.conf
COPY ./docker/ftp/ftp.passwd /etc/vsftpd/ftp.passwd
COPY ./docker/ftp/vsftpd.pamfile /etc/pam.d/vsftpd

WORKDIR /srv/ftp
USER vsftpd

CMD ["tail", "-f", "/dev/null"]
