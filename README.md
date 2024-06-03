# 教师管理系统

在开始之前，请确保你已经满足以下要求：

- **Java:** JDK 19 或更高版本
- **Maven:** 3.6.3 或更高版本
- **MySQL:** 8.0 或更高版本

- ## 依赖

该项目需要以下依赖：

- **Spring Boot:** 3.3.0
- **Spring Data JPA:** 3.3.0
- **Spring Web:** 3.3.0
- **Thymeleaf:** 3.1.2.RELEASE
- **MySQL Connector Java:** 8.0.32
- **JSoup:** 1.14.3
- **Pinyin4j:** 2.5.1

- ## 项目结构

- `controller`: 包含RESTful Web控制器。
- `model`: 包含JPA实体。
- `repository`: 包含JPA仓库。
- `service`: 包含业务逻辑和Web抓取服务。
- `templates`: 包含Thymeleaf模板。

- ###注：运行后请访问：http://localhost:8080/search
