package com.fengdai.qa.dao.admin;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fengdai.qa.constants.DataSourceConsts;
import com.fengdai.qa.dao.DS;
import com.fengdai.qa.meta.CallbackInfo;

@DS(value=DataSourceConsts.DEFAULT)
@Mapper
public interface FengdaiCallbackInfoDao {


	public int addCallbackInfo(CallbackInfo callbackInfo);

	public List<CallbackInfo> getAllCallbakInfo();

}
