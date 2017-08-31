<div class="n-head">
    <div class="g-doc f-cb">
     	<#if userName?exists && userName?has_content>
		    <div class="n-result">
	    	   <p>welcome ${userName}</p>
	    	   <#if lastoperaInfo?exists && lastoperaInfo?has_content>
			   <p>last operationUserName is :${lastoperaInfo.username}</p>
			   <p>last operationOperaType is :${lastoperaInfo.opertype}</p>
			   <p>last operationTime is :${lastoperaInfo.opertime}</p>
			   </#if>
	    	    <ul class="nav">
		            <li><a href="/index?item=fixenv">修复环境</a></li>
		            <li><a href="/index?item=createCallbackStr">通过remark字段构造回调报文</a></li>
		            <li><a href="/index?item=mock">mock平台建设</a></li>
		            <li><a href="/index?item=changetime">修改时间</a></li>
		            <li><a href="/index?item=logout">登出</a></li>
		        </ul>
	    	</div>
	    	<#else>
	    	 <p><a href="/index?item=login">登录</a></p>
	    </#if>
        
    </div>
</div>