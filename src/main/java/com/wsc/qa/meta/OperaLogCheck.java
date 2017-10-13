package com.wsc.qa.meta;

import com.google.common.base.Preconditions;

public class OperaLogCheck {

	public static void checkOperLog(OperaLog operaLog)throws Exception{
		Preconditions.checkNotNull(operaLog.username, "operaLog的name为null");
		Preconditions.checkNotNull(operaLog.opertype, "operaLog的opertype为null");
		Preconditions.checkNotNull(operaLog.opertime, "operaLog的opertime为null");
		}


}
