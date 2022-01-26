Spring Boot 学习示例
=========================

![Spring Boot 2.5.9](https://img.shields.io/badge/Spring%20Boot-2.1.18-brightgreen.svg)
![Mysql 5.6](https://img.shields.io/badge/Mysql-5.6-blue.svg)
![JDK 1.8](https://img.shields.io/badge/JDK-1.8-brightgreen.svg)
![Maven](https://img.shields.io/badge/Maven-3.5.0-yellowgreen.svg)
![license](https://img.shields.io/badge/license-MPL--2.0-blue.svg)

Spring Boot 使用的各种示例，以最简单、最实用为标准，此开源项目中的每个示例都以最小依赖，最简单为标准，帮助初学者快速掌握 Spring Boot 各组件的使用。

---

## 升级日志：

### 2022-01-26

将工程使用的Spring-Boot版本从2.1.18升级为2.5.9，主要有以下较大变化：

- `spring-boot-starter-test`默认不包含`junit4`依赖，如需使用，需要自主添加依赖
    ```xml
    <dependency>
            <groupId>org.junit.vintage</groupId>
            <artifactId>junit-vintage-engine</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.hamcrest</groupId>
                    <artifactId>hamcrest-core</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
    ```
- `spring-boot-starter-web`默认不包含`hibernate-validator`依赖，需自主添加依赖
  ```xml
  <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
  ```