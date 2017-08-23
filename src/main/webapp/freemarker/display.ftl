<!DOCTYPE html>
<html>
<body>
<#include "/include/header.ftl">
	<#if callbackStr?has_content>
		    <div class="n-result">
		        <pre id="callbackStr"></pre>
		    </div>
	</#if>

</body>
</html>
<script language="javascript">  
document.getElementById('callbackStr').innerHTML = JSON.stringify(${callbackStr},null,2);    
</script>