<!DOCTYPE html>
<html>
<#include "/include/head.ftl">
<body>
<#include "/include/header.ftl">
<div class="resultbox">
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
	    <ul>
	        <li>请求方的地址与时间:${x.requestip}--${x.createtime}；请求方发过来的内容：${x.callbackinfo} </li>
	        <p>--------------------我是分隔符---------------------</p>
	     </ul>
	    </#list>
	
	</#if>
</div>
</body>
</html>

