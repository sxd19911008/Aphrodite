# 中间件容器创建命令

### Nacos (服务注册中心与配置中心)
Nacos控制台访问地址: http://localhost:8848/nacos (默认账号: nacos/nacos)

```bash
docker run -d \
  --name aphrodite-nacos \
  --restart=unless-stopped \
  -p 8848:8848 \ # Nacos Web/API端口
  -p 9848:9848 \ # Nacos GRPC端口
  -e MODE=standalone \ # 单机（Standalone）模式运行
  -e PREFER_HOST_MODE=hostname \ # 让 Nacos 使用容器的 hostname 作为服务注册地址
  -e TZ=Asia/Shanghai \ # 设置容器时区
  -e NACOS_LOG_LEVEL=ERROR \ # 日志级别为ERROR
  -v aphrodite-nacos-data:/home/nacos/data \ # 数据卷挂载：持久化 Nacos 的配置和注册数据
  -v aphrodite-nacos-logs:/home/nacos/logs \ # 数据卷挂载：持久化 Nacos 的运行日志
  nacos/nacos-server:v2.2.3-slim
```


### Oracle (aphrodite-insurance微服务的Oracle数据库)

```bash
docker run -d \
  --name aphrodite-insurance-oracle \
  --restart=unless-stopped \
  -p 11001:1521 \
  -e ORACLE_PWD=123456 \
  -e ORACLE_PDB=APHRODITE_INSURANCE \ # 创建 service name
  -e ORACLE_CHARACTERSET=AL32UTF8 \ # 字符集
  -v aphrodite_oracle_data:/opt/oracle/oradata \ # 创建并挂在Docker卷
  container-registry.oracle.com/database/free:23.9.0.0-lite-arm64
```


### MySQL (aphrodite-insurance微服务的MySQL数据库)

```bash
docker run -d \
  --name aphrodite-insurance-mysql \
  --restart=unless-stopped \
  -p 11002:3306 \
  -e MYSQL_ROOT_PASSWORD=123456 \ # 密码
  -e MYSQL_ROOT_HOST=% \ # 允许root用户从任何主机（%）连接
  -e MYSQL_DATABASE=aphrodite_insurance \ # 创建初始数据库aphrodite_insurance
  -e MYSQL_CHARACTER_SET_SERVER=utf8mb4 \ # 默认字符集
  -e MYSQL_COLLATION_SERVER=utf8mb4_0900_ai_ci \ # 默认排序规则
  -v aphrodite-mysql-data:/var/lib/mysql \ # 创建并挂载一个名为 aphrodite-mysql-data 的Docker卷到容器的MySQL数据目录，实现数据持久化
  mysql:8.0.36
```


### Redis (Sa-Token分布式Token存储)

```bash
docker run -d \
  --name aphrodite-redis \
  --restart=unless-stopped \
  -p 11003:6379 \
  -v aphrodite-redis-data:/data \ # 卷（Volume）映射：将名为'aphrodite-redis-data'的Docker卷挂载到容器内的 /data 目录，用于数据持久化（AOF持久化）
  redis:7.2-alpine \
  redis-server \ # 指定容器启动时执行 'redis-server' 程序
    --appendonly yes \ # 启用 AOF（Append Only File）持久化
    --loglevel warning \ # 设置日志级别为warning
    --requirepass "888888" # 全局密码
```
