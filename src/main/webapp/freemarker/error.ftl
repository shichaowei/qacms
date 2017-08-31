<html lang="en-US">
<#include "/include/head.ftl">
    <body>
	   <#include "/include/header.ftl">
	   <#if ex?exists>
	   		<p>something is wrong</p>
	   		<p>${ex}</p>
	   <#else>
	   		<p>未登录或者用户名密码错误</p>
	   </#if>
    </body>
</html>