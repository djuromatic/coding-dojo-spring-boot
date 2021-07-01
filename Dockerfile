FROM maven:3.8.1-jdk-11-slim

RUN apt-get update && apt-get install -y dos2unix
COPY entrypoint.sh /usr/local/bin/entrypoint.sh
COPY . /usr/src/maven
RUN dos2unix /usr/local/bin/entrypoint.sh && chmod +x /usr/local/bin/entrypoint.sh

#Build Test Run
WORKDIR /usr/src/maven
RUN mvn install
ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
