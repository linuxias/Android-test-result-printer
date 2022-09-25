FROM ubuntu:latest

RUN apt-get -y update && apt-get install -qy python

#COPY entrypoint.sh /entrypoint.sh

#ENTRYPOINT ["/entrypoint.sh"]
