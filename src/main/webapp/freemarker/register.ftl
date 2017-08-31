<!DOCTYPE HTML>
<html lang="en-US">
<#include "/include/head.ftl">
    <head>
        <meta charset="UTF-8">
        <title>注册</title>
    </head>
    <body>
    		<form action="api/register" method="post">
	  			<p>userName: <input type="text" name="userName" /></p>
	  			<p>userPassword: <input type="password" name="userPassword" /></p>
	  			<input type="submit" value="注册" />
			</form>
    </body>
</html>