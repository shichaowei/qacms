
     <#if userName?exists &&userName?has_content>
   <div class="col-lg-2 bianjie" >
	    <ul class="nav nav-stacked ">
            <li><a href="/index?item=fixenv">修复环境</a></li>
            <li><a href="/index?item=createCallbackStr">构造资金服回调报文发送给mqnotify</a></li>
            <li><a href="/index?item=addmockrule">增加mock规则</a></li>
            <li><a href="/index?item=displaymockrules">mock操作台</a></li>
            <li><a href="/index?item=gethttpinterface">接口测试(只接受json/html的返回值)</a></li>
            <li><a href="/index?item=changetime">修改时间-用于2.0</a></li>
            <li><a href="/index?item=newchangetime">修改时间-用于3.0</a></li>
            <li><a href="/index?item=deleteUserInfo">修改fengdai用户信息</a></li>
            <li><a href="/index?item=getcallbackInfo">回调服务器收到的请求(回调请求服务器地址:ip:port/callbackloop)</a></li>
            <li><a href="/index?item=logout">登出</a></li>
        </ul>
    </div>
      </#if>
	    	
