<!DOCTYPE html>
<html>
<#include "/include/head.ftl">
<body>

<div class="col-lg-10">
    		<form  role="form" action="api/sourcetome" method="post">
    			<div class="form-group">
    				<label for="name" class="control-label">transferServerIp:</label>
    				<input class ="form-control" name="ipaddress"></input>
				</div>
	  			<div class="form-group">
	  				<label for="name" class="control-label">projectName:</label>
					<select  class="form-control" name="path">
					    <option value="fengdai-dubbo-funds-test">fengdai-dubbo-funds-test</option>
						<option value="fengdai-product">fengdai-product</option>
						<option value="fengdai-system">fengdai-system</option>
						<option value="fengdai-rest-funds-callback-test">fengdai-rest-funds-callback-test</option>
						<option value="fengdai-finance">fengdai-finance</option>
						<option value="fengdai-dock">fengdai-dock</option>
						<option value="fengdai-report">fengdai-report</option>
						<option value="fengdai-operation">fengdai-operation</option>
						<option value="fengdai-quartz">fengdai-quartz</option>
						<option value="fengdai-shop">fengdai-shop</option>
						<option value="fengdai-rest-funds-test">fengdai-rest-funds-test</option>
						<option value="fengdai-loan">fengdai-loan</option>
						<option value="fengdai-core-funds-test">fengdai-core-funds-test</option>
						<option value="fengdai-user">fengdai-user</option>
						<option value="fengdai-parent">fengdai-parent</option>
						<option value="fengdai-common">fengdai-common</option>
						<option value="fengdai-dubbo-funds-client-test">fengdai-dubbo-funds-client-test</option>
					</select>
	  			</div>
	  			<div class="form-group">
	  				<button type="submit" class="btn btn-default">提交</button>
				</div>
			</form>
</div>


</body>
</html>

