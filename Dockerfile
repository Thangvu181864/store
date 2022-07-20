FROM maven:latest
RUN mkdir /build
WORKDIR /build
COPY . .
ENV DB_HOST=db_host
ENV DB_PORT=1433
ENV DB_NAME=db_name
ENV DB_USERNAME=db_username
ENV DB_PASSWORD=db_password
EXPOSE 8080
CMD ["mvn","spring-boot:run"]