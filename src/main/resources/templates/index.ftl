<!DOCTYPE html>
<html lang="en-US" >
<#include "/include/head.ftl">
    <body >
    <div >
		<#include "/include/menu.ftl">
		<#include "/include/userinfo.ftl">
		  <div class="col-lg-offset-1 col-lg-9 bianjie">
		   <#if !item?has_content>
					<h3>请点击目录!</h3>
		   <#else>

			<#if item=="changetime">
			   <form role="form"   action="/api/changetime" method="post">
					<div class="form-group">
						<label for="name" class="control-label">修改类型:</label>
							<select  class="form-control" name="changetimetype" id='changetime'>
							  <option value="changeDubbotime">修改dubbo时间</option>
							  <option value="changeDubboDbtime">修改dubbo/db时间</option>
							  <option value="changeDubboResttime">修改dubbo/rest时间</option>
							  <option value="changeDubboRestDbtime">修改dubbo/rest/db时间</option>
							  <option value="restAllTime">重置dubbo/rest/db时间</option>
							</select>
						</div>
					
					<div id="detail">
						<div class="form-group" >
								<label for="name" class="control-label">要修改的日期和时间：</label>
								<input class=" form-control" type="text"  name="date" placeholder="2017/07/04">
								<input class="form-control" type="text"  name="time" placeholder="9:00:00">		
						</div>
					</div>
					<button type="submit" class="btn btn-default  ">提交</button>
				</form> 
				</#if>
				
			<#if item=="newchangetime">
			   <form role="form"   action="/api/newchangetime" method="post">
					<div class="form-group">
						<label for="name" class="control-label">修改类型:</label>
							<select  class="form-control" name="changetimetype" id='changetime'>
							  <option value="changeServicetime">修改service时间</option>
							  <option value="changServiceDbtime">修改service/db时间</option>
							  <option value="restAllTime">重置service/db时间</option>
							</select>
					</div>
					<div class="form-group">
						<label for="name" class="control-label">jobid类型:</label>
							<select  class="form-control" name="jobid" >
							  <option value="1022">普通账单更新</option>
							  <option value="1023">信用卡账单更新</option>
							  <option value="1019">信用卡自动还款</option>
							  <option value="1013">普通自动还款</option>
							  <option value="28">RoundCheckBatchJob</option>
							  <option value="29">RoundCheckExecuteJob</option>
							  <option value="*">只改时间</option>
							</select>
					</div>
					
					<div id="detail">
						<div class="form-group" >
								<label for="name" class="control-label">要修改的日期和时间：</label>
								<input class=" form-control" type="text"  name="date" placeholder="2017/07/04">
								<input class="form-control" type="text"  name="time" placeholder="9:00:00">		
						</div>
					</div>
					<button type="submit" class="btn btn-default  ">提交</button>
				</form> 
				</#if>
				
			<#if item=="fixenv">
			
			   <form role="form"  action="/api/fixenv" method="post">
					<div class="form-group text-left">
						<label class="control-label">修复环境（针对蜂贷一个服务多个provider问题---慎用）</label>
						<input type="text"  class="form-control input-group" name="zkAddress" placeholder="10.200.141.33">
					</div>
					<button type="submit" class="btn btn-default">提交</button>
				</form> 

				
				</#if>
				
				<#if item=="createCallbackStr">
				<form role="form" action="/api/createCallbackStr" method="post">
					<div class="form-group">
						<label>生成回调报文：</label>
						<select name="type" class="form-control">
						  <option value="virRelateId">通过relateId字段模拟回调（loanapplyid&&so on）</option>
						  <option value="virRemark">通过remark字段模拟回调</option>
						</select>
					</div>
					<div class="form-group">
						<label>资金服回调的环境：</label>
						<select name="callbackEnv" class="form-control">
						  <option value="fengdaiold">2.0环境</option>
						  <option value="fengdainew">3.0环境</option>
						</select>
						<!--
						http://10.200.141.52:8080/Mqnotify/notify/transfer
						<input type="text" class="form-control" name="callbackUrl" value="http://10.200.141.36:8080/Mqnotify/notify/transfer" readonly style="background:#CCCCCC">
						-->
					</div>
						<div class="form-group">
						<label>回调的状态：</label>
						<select name="callbackstatus" class="form-control">
						  <option value="PAY_SUCCESS">成功</option>
						  <option value="PAY_FALIED">失败</option>
						</select>
					</div>
					<div class="form-group">
						<label>mcbusiness特征值：</label>
						<input type="text" class="form-control" name="fieldDetail" placeholder="字段内容">
					</div>
					<button type="submit" class="btn btn-default">提交</button>
				</form>
				</#if>
				
				<#if item=="mock">
				<form role="form" action="/api/addmockrule" method="post" enctype="multipart/form-data">
					<div class="form-group">
						<label >mockServer的地址：</label>
						<input type="text" class="form-control" name="mockserverip" value="10.200.141.37" readonly style="background:#CCCCCC"><br />
					</div>
					<div class="form-group">
					<label >mock的类型：</label>
						<select class="form-control" name="mockType">
						  <option value="get">get请求</option>
						  <option value="post">post请求</option>
						</select>
					</div>
					<div class="form-group">
						<label >mock数据的返回类型：</label>				 			 
						<select class="form-control" name="ContentType">
						  <option value="application/json">application/json</option>
						  <option value="text/xml">text/xml</option>
						</select>
					</div>
					<div class="form-group">
						<label >mock的url：</label>	
						<input class="form-control" type="text" name="checkUrl" placeholder="checkUrl">
					</div>
					<div class="form-group">
						<label >mock的数据所含特征值：</label>	
						<input class="form-control" type="text" name="checkParams" placeholder="checkParams">
					</div>
					<div class="form-group">
						<label >mock返回的数据延时时间：</label>	
						<input class="form-control" type="text" name="mockdelaytime" placeholder="checkParams">
					</div>
					<div class="form-group">
						<label >mock返回的数据：</label>
						<input class="form-control" type="text" name="responseBody" placeholder="responseBody">
					</div>
					<div class="form-group">
						<label >mock返回的数据(文件)：</label>	
						<input class="form-control" type="file" name="responseBodyFile">
					</div> 
					<button type="submit" class="btn btn-default">提交</button>
				</form>
				</#if>
				
				<#if item=="gethttpinterface">
				<form role="form" action="/api/gethttpinterface" method="post" >
					
					<div class="form-group">
						<label >http请求的类型：</label>
						<select class="form-control" name="requestType">
						  <option value="get">get请求</option>
						  <option value="post">post请求</option>
						</select>
					</div>
					
					<div class="form-group">
						<label >http请求的url：</label>	
						<input class="form-control" type="text" name="requestUrl" placeholder="requestUrl">
					</div>
					
					<div class="form-group">
						<label >http的请求类型：</label>				 			 
						<select class="form-control" name="requestContentType">
						  <option value="application/x-www-form-urlencoded">application/x-www-form-urlencoded</option>
						  <option value="application/json">application/json</option>
						</select>
					</div>
					
					<div class="form-group">
						<label >token：</label>				 			 
						<input class="form-control" type="text" name="token" placeholder="token的值">
					</div>
					
					<div class="form-group">
						<label >http请求的body：</label>	
						<input class="form-control" type="text" name="requestBody" placeholder="checkParams">
					</div>
				
					<button type="submit" class="btn btn-default">提交</button>
				</form>
				</#if>
				
				<#if item=="deleteUserInfo">
				<form action="/api/deleteUserInfo" method="post">
					<div class="form-group">
					<label >使用的数据库：</label>
					<select class="form-control" name="deleteMode" id="deleteModeType">
					  <option value="OLD">2.0环境</option>
					  <option value="NEW">3.0环境</option>
					</select>
					<label >要删除的的类型：</label>
					<select class="form-control" name="deleteType" id="fengdaiDeleteType">
					  <option value="deleteAllLoanByLoginname">删除指定用户所有的申请单(包括授信)</option>
					  <!--
					  <option value="deleteAllLoanWithoutCreditByLoginname">删除指定用户所有的申请单(不包括授信)--暂不提供</option>
					  -->
					  <option value="deleteUserByLoginname">根据登录名删除用户(用户信息全部删除-慎用)</option>
					  <option value="deleteLoanByLoanName">根据借款名称删除指定的申请单</option>
					  <option value="deleteLoanByLoanId">根据借款申请id删除指定的申请单</option>
					  <option value="changeSQDToLoanning">根据借款名称修改为待放款，绕开签约</option>
					  <option value="changeProcessSQDToLoanning">根据借款名称把放款中修改为待放款</option>
					  <option value="changeUserAmount">修改用户账号余额</option>
					</select>
					</div>
					<div class="form-group">
						<label >指定的参数特征值：</label>	
						<input id="fengdaiusername" class="form-control" type="text" name="param" placeholder="18667906998">
					</div>
					<button type="submit" class="btn btn-default">提交</button>
				</form>
				</#if>
				
				
				
				
			</#if>	
			</div>
		</div>
		    <script src="/static/js/a.js" type="text/javascript"></script>
    </body>
</html>