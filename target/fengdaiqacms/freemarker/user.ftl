<html lang="en-US">
    <head>
        <meta charset="UTF-8">
        <title>欢迎信息</title>
    </head>
    <body>
	   <p>welcome ${userName}</p>
	   <form action="/fengdaiqacms/cms/changedubbotime" method="post">
			<input type="radio" name="changetimetype" value="changeDubbotime" >修改dubbo时间
			<input type="radio" name="changetimetype" value="changeDubboDbtime">修改dubbo/db时间
			<input type="radio" name="changetimetype" value="changeDubboResttime">修改dubbo/rest时间
			<input type="radio" name="changetimetype" value="changeDubboRestDbtime">修改dubbo/rest/db时间
			<input type="text" name="date" placeholder="2017/07/04">
			<input type="text" name="time" placeholder="9:00:00">
			<input type="submit" value="Submit">
		</form> 
    </body>
</html>