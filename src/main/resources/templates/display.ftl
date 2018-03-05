<!DOCTYPE html>
<html>
<#include "/include/head.ftl">
<body>
<div>
	<#include "/include/menu.ftl">
	<#include "/include/userinfo.ftl">
	<div class="container col-lg-offset-1 col-lg-9 bianjie">
		<#if callbackStr?has_content>
				<div class="n-result">
					<pre id="callbackStr">${callbackStr}</pre>
				</div>
		</#if>
		<#if mockRuleStr?has_content>
				<div class="n-result">
					<pre id="mockRuleStr">${mockRuleStr}</pre>
				</div>
		</#if>
		
		<#if gethttpinterface?has_content>
				<div class="n-result">
					<p id="mockRuleStr">${gethttpinterface}</p>
				</div>
		</#if>
		<#if servernowtime?has_content>
				<div class="n-result">
					<pre id="servernowtime">${servernowtime}</pre>
				</div>
		</#if>
		
		<#if resultmsg?has_content>
				<div class="n-result">
					<pre id="servernowtime">${resultmsg}</pre>
				</div>
		</#if>
		
		<#if callbackinfolist?has_content>
		
			<#list callbackinfolist as x>
			<ul class="list-group">
				<li class="list-group-item">请求方的地址与时间:${x.requestip}--${x.createtime}；请求方发过来的内容：${x.callbackinfo} </li>
				<p>--------------------我是分隔符---------------------</p>
			 </ul>
			</#list>
		
		</#if>
		
		<#if MongodbfieldInfos?has_content>
		<div class=table-responsive">
			<table class="table table-striped table-condensed text-nowrap">
			<thead>
				<tr>
					<th>字段id</th>
					<th>字段名称</th>
					<th>字段来源</th>
					<th>字段类型</th>
					<th>因子code</th>
					<th>字段值</th>
				</tr>
			</thead>
			<tbody>
				<#list MongodbfieldInfos as x>
						<tr>
							<th>${x.field_id}</th>
							<th>${x.fieldname}</th>
							<th>${x.datasource}</th>
							<th>${x.fieldtype}</th>
							<th>${x.code}</th>
							<th class="col-md-3">${x.fieldvalue}</th>
						</tr>
				</#list>
			</tbody>
			</table>
			</div>
		</#if>
		

		
		
		
		
	</div>
	</div>
</body>
</html>

