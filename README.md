# LinkInspector
基于爬虫的网站页面异常链接检测工具
# 使用方法
1. 执行mvn package -Dmaven.test.skip=true
2. 生成的link-inspector.jar
3. 自行编辑link-inspector.jar同级文件夹下的config.json添加网站种子链接和登陆用户名密码
4. 使用java -jar link-inspector.jar调用
5. 自动生成报告在link-inspector.jar同级的文件夹下面