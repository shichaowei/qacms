<div class="n-head">
    <div class="g-doc f-cb">
     	<#if userName?exists>
		    <div class="n-result">
	    	    <p>welcome ${userName}</p>
	    	</div>
	    </#if>
        <ul class="nav">
            <li><a href="/">首页</a></li>
            <li><a href="/index?item=fixenv">修复环境</a></li>
            <li><a href="/index?item=createCallbackStr">通过remark字段构造回调报文</a></li>
            <li><a href="/index?item=mock">mock平台建设</a></li>
            <li><a href="/index?item=changetime">修改时间</a></li>
            <li><a href="/index?item=logout">登出</a></li>
        </ul>
    </div>
</div>