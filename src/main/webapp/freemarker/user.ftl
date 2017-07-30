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
	   <form action="/fengdaiqacms/cms/changedubbotime" method="post">
			<input type="radio" name="changetimetype" value="changeDubbotime" >修改dubbo时间
			<input type="radio" name="changetimetype" value="changeDubboDbtime">修改dubbo/db时间
			<input type="radio" name="changetimetype" value="changeDubboResttime">修改dubbo/rest时间
			<input type="radio" name="changetimetype" value="changeDubboRestDbtime">修改dubbo/rest/db时间
			<input type="text" name="date" placeholder="2017/07/04">
			<input type="text" name="time" placeholder="9:00:00">
			<input type="submit" value="Submit">
		</form> 
		<br />
	   <form action="/fengdaiqacms/cms/fixenv" method="post">
			<input type="radio" name="fixenv" value="fixenv" >修复环境（针对蜂贷一个服务多个provider问题）
			<input type="text" name="zkAddress" placeholder="172.30.249.243">
			<input type="submit" value="Submit">
		</form> 
		
	   <form action="/fengdaiqacms/cms/parseSrc" method="post">
			<input type="radio" name="parseSrc" value="parseSrc" >源码分析
			<input type="text" name="classRootDir" placeholder="class的根目录">
			<input type="text" name="modifyclass" placeholder="修改的classname:com.wsc.testbcel.testbcel.BaseProgrammer">
			<input type="text" name="modifymethod" placeholder="修改的methodname:docoding">
			<input type="submit" value="Submit">
		</form> 
		
		
    </body>
</html>