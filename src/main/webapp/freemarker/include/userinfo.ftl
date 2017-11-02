<div class="container col-lg-offset-1 col-lg-9 bianjie">
	   <p>welcome ${userName}</p>
	   <#if lastoperaInfo?exists && lastoperaInfo?has_content>
	   <p>last operationUserName is :${lastoperaInfo.username}</p>
	   <p>last operationOperaType is :${lastoperaInfo.opertype}</p>
	   <p>last operationTime is :${lastoperaInfo.opertime}</p>
	   </#if>
</div>