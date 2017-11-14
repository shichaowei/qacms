# qacms
QA搭建平台
提供的通用功能：mock平台 第三方模拟 http请求 时间更改 删除测试数据

使用jenknis持续集成，增加测试平台黑盒代码覆盖率检测
1.下载jacoco
2.tomcat启动参数:-javaagent:/data/jacoco/lib/jacocoagent.jar=includes=com.wsc.*,output=tcpserver,port=8044,address=10.200.141.38
-Xverify:none
3.build.xml指定jacoco地址
4.jenknis增加ant dump -buildfile ./build.xml
5.jenknis增加Record JaCoCo coverage report



