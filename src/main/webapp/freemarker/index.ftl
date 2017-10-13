<!DOCTYPE html>
<html lang="en-US">
<#include "/include/head.ftl">
    <body>
    <script src="js/a.js" type="text/javascript"></script>
    <#include "/include/header.ftl">
	  <br />
	   <#if !item?has_content>
		   <div class="n-result">
		        <h3>暂无内容！</h3>
		    </div>
	   <#else>

		<#if item=="changetime">
		   <form action="/changetime" method="post">
		   		<select name="changetimetype" id='changetime'>
				  <option value="changeDubbotime">修改dubbo时间</option>
				  <option value="changeDubboDbtime">修改dubbo/db时间</option>
				  <option value="changeDubboResttime">修改dubbo/rest时间</option>
				  <option value="changeDubboRestDbtime">修改dubbo/rest/db时间</option>
				  <option value="restAllTime">重置dubbo/rest/db时间</option>
				</select>
				<div id="detail">
					<input type="text" name="date" placeholder="2017/07/04">
					<input type="text" name="time" placeholder="9:00:00">
				</div>
				<input type="submit" value="Submit">
			</form> 
			</#if>
			
			<#if item=="fixenv">
		   <form action="/fixenv" method="post">
				<label>修复环境（针对蜂贷一个服务多个provider问题）</label>
				<input type="text" name="zkAddress" placeholder="10.200.141.33">
				<input type="submit" value="Submit">
			</form> 
			</#if>
			
			<#if item=="createCallbackStr">
			<form action="/createCallbackStr" method="post">
				<label >生成回调报文</label>
				<select name="type">
				  <option value="virRemark">通过remark字段模拟回调</option>
				  <option value="virRelateId">通过relateId字段模拟回调</option>
				</select>
				<input type="text" name="callbackUrl" value="http://10.200.141.36:8080/Mqnotify/notify/transfer" readonly style="background:#CCCCCC">
				<input type="text" name="fieldDetail" placeholder="字段内容">
				<input type="submit" value="Submit">
			</form>
			</#if>
			
			<#if item=="mock">
			<form action="/mockMessage" method="post">
				<label >mockServer的地址：</label>
				<input type="text" name="mockserverip" value="10.200.141.37" readonly style="background:#CCCCCC"><br />
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
			
			<#if item=="deleteUserInfo">
			<form action="/deleteUserInfo" method="post">
				<label >要删除的的类型：</label>
				<select name="deleteType">
				  <option value="deleteAllLoanByLoginname">删除指定用户所有的申请单(包括授信)</option>
				  <!--
				  <option value="deleteAllLoanWithoutCreditByLoginname">删除指定用户所有的申请单(不包括授信)--暂不提供</option>
				  -->
				  <option value="deleteUserByLoginname">根据登录名删除用户(用户信息全部删除-慎用)</option>
				  <option value="deleteLoanByLoanName">根据借款名称删除指定的申请单</option>
				  <option value="deleteLoanByLoanId">根据借款申请id删除指定的申请单</option>
				  <option value="changeSQDToLoanning">根据借款名称修改为待放款，绕开签约</option>
				  <option value="changeProcessSQDToLoanning">根据借款名称把放款中修改为待放款</option>
				</select><br />
				<label >指定的参数特征值：</label>	
				<input type="text" name="param" placeholder="18667906998"><br /> 
				<input type="submit" value="Submit">
			</form>
			</#if>
		</#if>	
			
    </body>
</html>