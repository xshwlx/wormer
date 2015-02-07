package com.jd.uwp.log;

import com.jd.uwp.common.utils.DateUtil;

import java.util.Date;

/**
 * 日志模板
 * User: xushiheng
 * Date: 14-12-12
 * Time: 下午2:44
 */
public class LogMessage {

    public static String logTemplate = "";

    public static String createLogMsg(String msg, String className, String methodName, String line) {
        if (logTemplate.length() == 0) {
            logTemplate = "[##DateTime #ClassName #MethodName(#Line)#] #msg";
        }
        String date = DateUtil.format.format(new Date());
        logTemplate.replace("#DateTime", date)
                .replace("#ClassName", className)
                .replace("#MethodName", methodName)
                .replace("#line", line);
        return logTemplate;
    }

}
