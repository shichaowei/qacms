<div class="container col-lg-offset-1 col-lg-9 bianjie">
	   <#if userName?exists &&userName?has_content &&lastoperaInfo?exists && lastoperaInfo?has_content>
	   <p>welcome ${userName}</p>
	   <p>last operationUserName is :${lastoperaInfo.username}</p>
	   <p>last operationOperaType is :${lastoperaInfo.opertype}</p>
	   <p>last operationTime is :${lastoperaInfo.opertime}</p>
	   </#if>
</div>