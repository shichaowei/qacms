<!DOCTYPE html>
<html>
<#include "/include/head.ftl">
<body>

<div class="col-lg-10">
			<#if resultmsg?has_content>
				<div class="n-result">
					<pre id="resultmsg">${resultmsg}</pre>
				</div>
			</#if>
    		<form  role="form" action="/api/logtome" method="post">
    			<div class="form-group">
    				<label for="name" class="control-label">transferServerIp:</label>
    				<input class ="form-control" name="ipaddress"></input>
				</div>
    			<div class="form-group">
    				<label for="name" class="control-label">租期时长（min）:</label>
    				<input class ="form-control" name="time"></input>
				</div>
    			<div class="form-group">
    				<label for="name" class="control-label">监听日志动作:</label>
    				<select class="form-control" name="type">
				      <option value="start">开始监听日志</option>
				      <option value="stop">结束监听日志</option>
				    </select>
				</div>
	  			<div class="form-group">
	  				<button type="submit" class="btn btn-primary btn-check" data-loading-text="提交中...">提交</button>
				</div>
			</form>	
			<a href="/api/downloadlog">下载</a>
</div>

<script src="/static/js/a.js" type="text/javascript"></script>
</body>
</html>

