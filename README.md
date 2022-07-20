## Đồ án: Lập trình hướng đối tượng


**Yêu cầu hệ thông cài đặt sẵn [Docker](https://www.docker.com/get-started/) và [docker-compose](https://docs.docker.com/compose/)**

Tải xuống mã nguồn của dự án:

```console
git clone https://github.com/Thangvu181864/store.git
```
Di chuyển vào trong thư mục vừa tải xuống

```console
cd store
```

Thực hiện build image 

```console
docker build -t store-spring .
```

Chỉnh sửa nội dung file ```docker-compose.yaml```


```console
version: '3.9'

services:
  sqlserver:
    container_name: sqlserver
    image: mcr.microsoft.com/mssql/server:2019-latest
    user: root
    hostname: sqlserver
    ports:
      - 1433:1433
    volumes:
      - ./data:/var/lib/mssqlql/data
    environment:
      - ACCEPT_EULA=Y
      - MSSQL_PID=Express
      - SA_PASSWORD=Thangvu_14042001
  web:
    container_name: web
    image: store-spring:latest
    ports:
      - 8080:8080
    environment:
      - DB_HOST=sqlserver
      - DB_PORT=1433
      - DB_NAME=store
      - DB_USERNAME=sa
      - DB_PASSWORD=Thangvu_14042001
    depends_on:
      - sqlserver
```

Khởi chạy container

```console
docker-compose up -d
```