package com.fengdai.qa.web.controller;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.fengdai.qa.annotation.OperaLogComment;
import com.fengdai.qa.constants.CommonConstants.ErrorCode;
import com.fengdai.qa.constants.CommonConstants.opertype;
import com.fengdai.qa.constants.DataSourceConsts;
import com.fengdai.qa.dao.DataSourceContextHolder;
import com.fengdai.qa.dao.fengdainew.FengdaiBusinessDao;
import com.fengdai.qa.exception.BusinessException;
import com.fengdai.qa.meta.MongodbfieldInfo;
import com.mongodb.DBObject;

@Controller
public class MongodbController {

	private static final Logger logger = LoggerFactory.getLogger(MongodbController.class);

	@Resource
    private MongoTemplate mongoTemplate;
	@Resource
	private FengdaiBusinessDao fengdaiBusinessDao;

	@RequestMapping({ "/api/getmongodbinfo" })
	@OperaLogComment(remark = opertype.getmongodbinfo)
	public String createCallbackStr(@RequestParam("env") String env,@RequestParam("loanapplyid") String loanapplyid,
			HttpServletRequest request, ModelMap map, HttpServletResponse response) {
		if ("fengdainew".equals(env)) {
			// 切换数据源
			DataSourceContextHolder.setDB(DataSourceConsts.fengdai3);
		} else if ("fengdainewonline".equals(env)) {
			DataSourceContextHolder.setDB(DataSourceConsts.fengdai3online);
		} else {
			throw new BusinessException(ErrorCode.ERROR_ILLEGAL_DB, "非法db名称");
		}
//		DataSourceContextHolder.setDB(DataSourceConsts.fengdai3);
		String mongodbid = fengdaiBusinessDao.getmongodbid(loanapplyid);
//		Set<String> collectionNames = mongoTemplate.getDb().getCollectionNames();
//		// 打印出test中的集合
//        for (String name : collectionNames) {
//              System.out.println("collectionName==="+name);
//        }
        DBObject var =mongoTemplate.getDb().getCollection("loan_apply_ext").findOne(mongodbid);
//        for(String var1:var.keySet()) {
//        	System.out.println("key is------"+var1);
//        	System.out.println("values is---"+var.get(var1));
//        }
        System.out.println(JSONObject.toJSONString(var.get("fields")));
        JSONArray fieldStr = JSONArray.parseArray(JSONObject.toJSONString(var.get("fields")));
        int fieldnum= fieldStr.size();
        ArrayList<MongodbfieldInfo> fieldInfos = new ArrayList<>();
        for(int i=0;i<fieldnum;i++) {
        	String field_id=JSONPath.eval(fieldStr, "$["+i+"].field_id").toString();
        	String fieldname=fengdaiBusinessDao.getfieldname(field_id);
        	String fieldvalue=JSONPath.eval(fieldStr, "$["+i+"].value").toString();
        	String datasource=JSONPath.eval(fieldStr, "$["+i+"].data_source").toString();
        	String fieldtype=JSONPath.eval(fieldStr, "$["+i+"].field_type").toString();
        	String code=JSONPath.eval(fieldStr, "$["+i+"].code").toString();
        	MongodbfieldInfo filedinfo=  new MongodbfieldInfo().setField_id(field_id).setFieldname(fieldname).setFieldvalue(fieldvalue).
        			setDatasource(datasource).setFieldtype(fieldtype).setCode(code);
        	fieldInfos.add(filedinfo);
        	logger.info("--{}--{}--{}--{}--{}--{}",field_id,fieldname,fieldvalue,datasource,fieldtype,code);
        }
        map.addAttribute("MongodbfieldInfos", fieldInfos);
		return "display";
	}

}
