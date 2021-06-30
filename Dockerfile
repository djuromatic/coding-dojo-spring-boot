FROM maven:3.8.1-jdk-11-slim

COPY entrypoint.sh /usr/local/bin/entrypoint.sh
COPY . /usr/src/maven
RUN chmod +x /usr/local/bin/entrypoint.sh

#Build Test Run
WORKDIR /usr/src/maven
RUN mvn install
ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
