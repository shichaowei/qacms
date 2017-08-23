<html lang="en-US">
    <head>
        <meta charset="UTF-8">
        <title>欢迎信息</title>
    </head>
    <body>
	   <p>welcome ${userName}</p>
	   <p>last operationUserName is :${lastoperaName}</p>
	   <p>last operationOperaType is :${lastoperaType}</p>
	   <p>last operationTime is :${lastoperaTime}</p>
	   <form action="/changedubbotime" method="post">
			<input type="radio" name="changetimetype" value="changeDubbotime" >修改dubbo时间
			<input type="radio" name="changetimetype" value="changeDubboDbtime">修改dubbo/db时间
			<input type="radio" name="changetimetype" value="changeDubboResttime">修改dubbo/rest时间
			<input type="radio" name="changetimetype" value="changeDubboRestDbtime">修改dubbo/rest/db时间
			<input type="text" name="date" placeholder="2017/07/04">
			<input type="text" name="time" placeholder="9:00:00">
			<input type="submit" value="Submit">
		</form> 
		<br />
	   <form action="/fixenv" method="post">
			<input type="radio" name="fixenv" value="fixenv" >修复环境（针对蜂贷一个服务多个provider问题）
			<input type="text" name="zkAddress" placeholder="172.30.249.243">
			<input type="submit" value="Submit">
		</form> 
		<br />
		<form action="/createCallbackStr" method="post">
			<input type="radio" name="createCallbackStr" value="createCallbackStr" >生成回调报文
			<input type="text" name="remark" placeholder="remark字段">
			<input type="submit" value="Submit">
		</form>
		
		<br />
		<form action="/mockMessage" method="post">
			<input type="radio" name="mockMessage" value="mockMessage" >mock数据
			<input type="text" name="mockserverip" placeholder="mockServerIp">
			<select name="ContentType">
			  <option value="application/json">application/json</option>
			  <option value="text/xml">text/xml</option>
			</select>
			<input type="text" name="checkUrl" placeholder="checkUrl">
			<input type="text" name="checkPostParams" placeholder="checkPostParams">
			<input type="text" name="checkGetParams" placeholder="checkGetParams">
			<input type="text" name="responseBody" placeholder="responseBody">
			<input type="submit" value="Submit">
		</form>
		
    </body>
</html>