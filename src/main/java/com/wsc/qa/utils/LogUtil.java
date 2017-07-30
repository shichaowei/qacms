package com.wsc.qa.utils;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class LogUtil {
    private final  Logger log;


	public  LogUtil(final Class<?> loggerName) {
		log=LoggerFactory.getLogger(loggerName);
	}

    /**
     * 记录Info级别日志。
     *
     * @param message the message object.
     */
    public  void logInfo(Object message) {
        log.info("[INFO] " + message);
    }
    
  


}
