FROM maven:latest
RUN mkdir /build
WORKDIR /build
COPY . .
EXPOSE 8080
CMD ["mvn","spring-boot:run"]