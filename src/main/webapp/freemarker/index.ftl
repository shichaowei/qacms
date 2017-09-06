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
				<label>修复环境（针对蜂贷一个服务多个provider问题）</label>
				<input type="text" name="zkAddress" placeholder="172.30.249.243">
				<input type="submit" value="Submit">
			</form> 
			</#if>
			
			<#if item=="createCallbackStr">
			<form action="/createCallbackStr" method="post">
				<label >生成回调报文</label>
				<input type="text" name="callbackUrl" placeholder="回调地址">
				<input type="text" name="remark" placeholder="remark字段">
				<input type="submit" value="Submit">
			</form>
			</#if>
			
			<#if item=="mock">
			<form action="/mockMessage" method="post">
				<label >mockServer的地址：</label>
				<input type="text" name="mockserverip" placeholder="mockServerIp"><br />
				<label >mock的类型：</label>
				<select name="mockType">
				  <option value="get">get请求</option>
				  <option value="post">post请求</option>
				</select><br />
				<label >mock数据的返回类型：</label>				 			 
				<select name="ContentType">
				  <option value="application/json">application/json</option>
				  <option value="text/xml">text/xml</option>
				</select><br /> 
				
				<label >mock的url：</label>	
				<input type="text" name="checkUrl" placeholder="checkUrl"><br /> 
				<label >mock的数据所含特征值：</label>	
				<input type="text" name="checkParams" placeholder="checkParams"><br /> 
				<label >mock返回的数据：</label>
				<input type="text" name="responseBody" placeholder="responseBody"><br /> 
				<input type="submit" value="Submit">
			</form>
			</#if>
		</#if>	
			
    </body>
</html>