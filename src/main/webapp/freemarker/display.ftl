<!DOCTYPE html>
<html>
<#include "/include/head.ftl">
<body>
<#include "/include/header.ftl">
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

</body>
</html>

