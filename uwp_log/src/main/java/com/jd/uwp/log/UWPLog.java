package com.jd.uwp.log;

import org.apache.log4j.Logger;

/**
 * 平台日志
 * 支持（1）不同WEB APP输出不同日志文件，日志命名规范UWP_APPName.log
 * （2）平台统一日志输出
 * User: xushiheng
 * Date: 14-12-12
 * Time: 下午2:47
 */
public class UWPLog {

    //应用名称 不同的应用建立不同的日志文件夹
    private String appName;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    private int logType = LogConstant.LOGTYPE_CONSOLE;

    public int getLogType() {
        return logType;
    }

    public void setLogType(int logType) {
        this.logType = logType;
    }

    /**
     * 类名
     */
    private String className = Thread.currentThread().getStackTrace()[2].getClassName();
    /**
     * 调用方法名
     */
    private String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
    /**
     * 调用行数
     */
    private int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();

    private Logger log = Logger.getLogger(className);

    public void debug(String msg) {
        log.debug(LogMessage.createLogMsg(msg, className, methodName, lineNumber + ""));
    }

    public void info(String msg) {
        log.info(LogMessage.createLogMsg(msg, className, methodName, lineNumber + ""));
    }

    public void error(String msg) {
        log.error(LogMessage.createLogMsg(msg, className, methodName, lineNumber + ""));
    }

    public static UWPLog getLog() {
        return new UWPLog();
    }

    /**
     * 初始化日志配置信息
     */
    public void initLog() {

    }
}
