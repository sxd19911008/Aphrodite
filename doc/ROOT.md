# Aphrodite 项目简介

---

### 技术栈 (Technology Stack)

| 技术/框架 | 版本/组件 | 用途说明 |
| :--- | :--- | :--- |
| **核心框架** | | |
| Spring Boot | 2.7.18 | 项目基础框架，简化Spring应用开发。 |
| Spring Cloud | 2021.0.8 | 提供微服务治理的全面解决方案。该版本与Boot 2.7.x兼容。 |
| Spring Cloud Alibaba | 2021.0.5.0 | 阿里巴巴的微服务解决方案，与Spring Cloud 2021.0.x 兼容。 |
| **环境依赖** | | |
| JDK | 17 | 长期支持（LTS）版本的Java开发环境。 |
| Maven | 3.8+ | 项目构建与依赖管理工具。 |
| **服务治理** | | |
| Nacos | 2.2.x | 作为服务注册中心 (Service Discovery) 和配置中心 (Configuration Center)。 |
| **网关** | | |
| Spring Cloud Gateway | - | 基于Spring 5+、Boot 2+ 和 Project Reactor 的响应式API网关。 |
| **认证与授权** | | |
| Sa-Token | 1.37.0 | 轻量级Java权限认证框架，提供身份认证、权限校验等功能。 |
| Redis | - | 用于Sa-Token的分布式Token存储。 |
| **数据访问** | | |
| Spring Data JPA | - | 简化数据持久化层开发 (类似MyBatis-Plus)。 |
| MySQL | 8.0+ | 主流的关系型数据库，数据持久化。 |
| **其他** | | |
| Lombok | - | 简化Java代码，如自动生成Getter/Setter。 |
| Apache JEXL | 3.3 | 表达式语言引擎，用于保险保费计算的规则引擎。 |

### 项目结构 (Project Structure)

项目将采用Maven父子模块结构，便于统一管理依赖版本和插件。

```plaintext
aphrodite (父项目)
│
├── pom.xml                   # 父POM文件，定义公共依赖版本、插件和模块
│
├── aphrodite-gateway/        # 网关服务模块 (端口: 9000)
│   ├── src/main/java
│   └── pom.xml
│
├── aphrodite-auth/           # 认证授权服务模块 (端口: 9001)
│   ├── src/main/java
│   └── pom.xml
│
├── aphrodite-order/          # 订单服务模块 (端口: 9002)
│   ├── src/main/java
│   └── pom.xml
│
├── aphrodite-insurance/      # 保险服务模块 (端口: 9003)
│   ├── src/main/java
│   └── pom.xml
│
├── commons # 公共模块，非微服务
    ├── aphrodite-common/          # 通用公共模块
    │   ├── src/main/java
    │   └── pom.xml
    ├── aphrodite-common-web/      # 微服务公共模块
    │   ├── src/main/resources
    │   └── pom.xml
    └── aphrodite-common-security/ # 公共安全鉴权模块
        ├── src/main/java
        └── pom.xml
```

### swagger文档地址

```declarative
http://localhost:{端口号}/swagger-ui/index.html
```