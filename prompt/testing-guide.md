```bash
# 确保在项目根目录
# 1. 首先构建整个项目，确保所有模块都已安装
mvn -s /Users/ethan/IDE_plugin/maven_repository/Aphrodite/settings.xml clean install

# 2. 在指定模块中执行测试
mvn -s /Users/ethan/IDE_plugin/maven_repository/Aphrodite/settings.xml -pl <模块名称> test -Dtest=<com.完整包名.测试类名>
```

**重要提示:** 在执行单个模块的测试前，请务必确认已在项目根目录成功运行过 `mvn clean install`，以保证所有模块间的依赖都已正确安装到本地仓库。
