<html lang="en-US">
<#include "/include/head.ftl">
    <body>
	   <#include "/include/menu.ftl">
	   <#if ex?exists>
	   		<p>${url} ***is wrong</p>
	   		<pre>${ex}</pre>
	   <#else>
	   		<p>未登录或者用户名密码错误</p>
	   </#if>
    </body>
</html>