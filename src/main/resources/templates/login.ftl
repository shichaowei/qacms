<!DOCTYPE HTML>
<html lang="en-US">
<#include "/include/head.ftl">
    <body>
    <div class="container bianjie col-lg-6">
    		<form  role="form" action="api/login" method="post">
    			<div class="form-group mycenter">
	    			<label for="name" class="control-label">userName:</label>
			  		<input class="form-control" type="text" name="userName" />
		  		</div>
	  			<div class="form-group mycenter">
	  				<label for="name" class="control-label">userPassword:</label>
	  				<input class="form-control" type="password" name="userPassword" />
	  			</div>
	  			<div class="form-group mycenter">
	  				<button type="submit" class="btn btn-default">登录</button>
				</div>
			</form>
			<div class="mycenter form-group">
			<p class="help-block">没有账号？<a ui-sref="register" href="/register">免费注册</a></p>
			</div>
			</div>
    </body>
</html>