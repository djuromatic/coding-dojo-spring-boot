
FROM maven:3.8.1-jdk-11-slim

COPY entrypoint.sh /usr/local/bin/entrypoint.sh
RUN chmod +x /usr/local/bin/entrypoint.sh

#Start application
WORKDIR /usr/src/maven
ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
CMD ["bash"]