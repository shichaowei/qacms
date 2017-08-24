<!DOCTYPE html>
<html lang="en-US">
<#include "/include/head.ftl">
    <body>
    <#include "/include/header.ftl">
	  <br />
	   <#if !item?has_content>
		   <div class="n-result">
		        <h3>暂无内容！</h3>
		    </div>
	   <#else>
		   <#if item=="changetime">
		   <form action="/changetime" method="post">
		   		<select name="changetimetype">
				  <option value="changeDubbotime">修改dubbo时间</option>
				  <option value="changeDubboDbtime">修改dubbo/db时间</option>
				  <option value="changeDubboResttime">修改dubbo/rest时间</option>
				  <option value="changeDubboRestDbtime">修改dubbo/rest/db时间</option>
				</select>
				<input type="text" name="date" placeholder="2017/07/04">
				<input type="text" name="time" placeholder="9:00:00">
				<input type="submit" value="Submit">
			</form> 
			</#if>
			
			<#if item=="fixenv">
		   <form action="/fixenv" method="post">
				<input type="radio" name="fixenv" value="fixenv" >修复环境（针对蜂贷一个服务多个provider问题）
				<input type="text" name="zkAddress" placeholder="172.30.249.243">
				<input type="submit" value="Submit">
			</form> 
			</#if>
			
			<#if item=="createCallbackStr">
			<form action="/createCallbackStr" method="post">
				<input type="radio" name="createCallbackStr" value="createCallbackStr" >生成回调报文
				<input type="text" name="remark" placeholder="remark字段">
				<input type="submit" value="Submit">
			</form>
			</#if>
			
			<#if item=="mock">
			<form action="/mockMessage" method="post">
				<input type="text" name="mockserverip" placeholder="mockServerIp"><br /> 
				<select name="ContentType">
				  <option value="application/json">application/json</option>
				  <option value="text/xml">text/xml</option>
				</select><br /> 
				<input type="text" name="checkUrl" placeholder="checkUrl"><br /> 
				<input type="text" name="checkPostParams" placeholder="checkPostParams"><br /> 
				<input type="text" name="checkGetParams" placeholder="checkGetParams"><br /> 
				<input type="text" name="responseBody" placeholder="responseBody"><br /> 
				<input type="submit" value="Submit">
			</form>
			</#if>
		</#if>	
			
    </body>
</html>