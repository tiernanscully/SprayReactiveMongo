
# Dockerized spray application

# Pull base image
FROM lemur17/lemur-base:2.11

# Copy project source code into image
COPY . /usr/src/lemur

# Define working directory
WORKDIR /usr/src/lemur

# Ccmpile sbt to download dependencies (so we don't need to do it when starting the server)
RUN sbt compile

# Expose port
EXPOSE 8080

# Start spray can server on default
CMD ["sbt","run"]