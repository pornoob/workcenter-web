FROM debian:bullseye

ENV debian_frontend=noninteractive

RUN apt update && \
    apt install -y vsftpd libpam-pwdfile

RUN mkdir -p /srv/ftp/vsftp && \
    ln -sf /usr/share/zoneinfo/America/Santiago /etc/localtime && \
    addgroup vsftp && adduser vsftp -M --gecos "" --ingroup vsftp --no-create-home && \
    chown -R vsftp:vsftp /srv/ftp/ && \
    service enable vsftp

COPY ./docker/ftp/vsftpd.conf /etc/vsftpd.conf
COPY ./docker/ftp/ftp.passwd /etc/vsftpd/ftp.passwd
COPY ./docker/ftp/vsftpd.pamfile /etc/pam.d/vsftpd

WORKDIR /srv/ftp
USER vsftpd

CMD ["tail", "-f", "/dev/null"]