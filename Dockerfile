FROM eclipse-temurin:17-jdk
#COPY blog-framework/target/blog-framework-1.0-SNAPSHOT.jar blog-framework.jar
COPY blog-front/target/blog-front-1.0-SNAPSHOT.jar blog-front.jar

#CMD java -jar blog-framework.jar
CMD java -jar blog-front.jar

# ENTRYPOINT指容器启动时要执行的命令，这个设置常用于调试或监控容器内正在运行的各种进程。
# top: 显示运行时系统的进程列表和资源使用情况
# -b 表示以“批处理模式”运行 top，即 将输出以文本格式打印到控制台，而不是以交互式界面展示
#ENTRYPOINT ["top", "-b"]