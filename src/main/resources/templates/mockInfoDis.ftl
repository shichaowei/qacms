<!DOCTYPE html>
<html>
<#include "/include/head.ftl">
<body>
<div>
	<#include "/include/menu.ftl">
	<#include "/include/userinfo.ftl">

		
	<div class="container col-lg-offset-1 col-lg-9 bianjie">
		<table class="table" border=1>
			<tr>
				<th>mock的时间</th>
				<th>mock的操作人</th>
				<th>mock的type</th>
				<th>mock的URL</th>
				<th>mock的特征值</th>
				<th>mock的延时时间</th>
				<th>mock的返回值</th>
				<th>操作</th>
			</tr>
			<#list mockrules as rule>
				<tr>
				<td>${rule.mocktime}</td>
				<td>${rule.opername}</td>
				<td>${rule.mockType}</td>
				<td>${rule.checkUrl}</td>
				<td>${rule.checkParams}</td>
				<td>${rule.delaytime}</td>
				<td><input value=${rule.responseBody} readonly></input></td>
				<td><a href="deletemockrules?id=${rule.id}">删除</a></td>
				</tr>
			</#list>
		</table>
		<a  class="btn btn-primary" href="startmock">开始mock数据</a>
		<a  class="btn btn-primary" href="resetmock">重置mock数据</a>
	</div>
	
	<#if resultmsg?has_content>
		<div class="n-result">
			<pre id="servernowtime">${resultmsg}</pre>
		</div>
	</#if>
	
	</div>
</body>
</html>

