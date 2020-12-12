# Spring Boot App
Web tải lên và đọc dữ liệu từ file excel.
Các chức năng: 
- Tải lên file excel
- Đọc và chỉnh sửa dữ liệu của file
- Lưu lại và tải file excel xuống

## Hướng dẫn cài đặt

Yêu cầu máy đã cài các phần mềm: Java SDK, IntellJ (có thể thay thế), Git, MySQL

Bước 1: Clone project: ```git clone https://github.com/hoangkhoi-uet/springboot-first-app.git```

Bước 2: Đăng nhập, tạo cơ sở dữ liệu và bảng trên MySQL

```
mysql -u root -p;
CREATE DATABASE IF NOT EXIST spring_app;
use spring_app;
CREATE TABLE employee (
id INT(10) UNSIGNED,
first_name VARCHAR(30),
last_name VARCHAR(30)
);
```

Bước 3: Chỉnh sửa đường dẫn

```springboot-first-app\src\main\resources\application.properties```

- Sửa ```spring.datasource.username``` và ```spring.datasource.password``` của cơ sở dữ liệu tương ứng. 

- Sửa ```file.upload-dir``` tương ứng

```springboot-first-app\src\main\java\com\springboot\app\controller\EmployeeController.java```

- Sửa ```uploadPath``` tương ứng

Bước 4: Khởi động
- Khởi động MySQL

- Khởi động SpringbootFirstAppApplication

Bước 5: Truy cập ```http://localhost:8080/```
- Thực hiện ```Choose File``` và ```Submit``` để tải file lên

- Read File để đọc file

- Chỉnh sửa file

Bước 6: Để download file đã sửa, chọn ```Save to file```


