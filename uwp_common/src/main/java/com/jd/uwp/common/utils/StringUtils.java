package com.jd.uwp.common.utils;

/**
 * StringUtil, 字符串工具类, 一些方便的字符串工具方法. 浏览器编码
 */
public class StringUtils {

    /**
     * 方法说明： 判断字符串是否为空, 如果为 null 或者长度为0, 均返回 true CreateTime Apr 18, 2009
     * 2:07:23 PM
     *
     * @param sourceStr 输入的字符串
     * @return 为 null 或者长度为0, 均返回 true 否则返回false
     */
    public static boolean isEmpty(String sourceStr) {
        return (sourceStr == null || sourceStr.length() == 0);
    }

    /**
     * 逻辑词汇
     *
     * @param bool
     * @return
     */
    public static boolean isTrue(String bool) {
        if (bool == null) {
            return false;
        } else {
            if (bool.trim().equals("true") || bool.trim().equals("yes")) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 方法说明： 根据传入的字符串，返回字符串后面的数字 CreateTime Apr 22, 2009 5:17:22 PM
     *
     * @param sourceStr 源字符串
     * @return 字符串后面的数字
     */
    public static int getFieldId(String sourceStr) {
        if (isEmpty(sourceStr)) {
            return 0;
        }
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < sourceStr.length(); i++) {
            char charAt = sourceStr.charAt(i);

            if (Character.isDigit(charAt)) {
                sBuffer.append(charAt);
            }
        }
        if (sBuffer != null && sBuffer.length() > 0) {
            return Integer.parseInt(sBuffer.toString());
        } else {
            return -1;
        }
    }

    /**
     * 方法说明： 检验字符串是否为空, 如果是, 则返回给定的出错信息 CreateTime Apr 18, 2009 2:08:45 PM
     *
     * @param sourceStr 源字符串
     * @param errorMsg  出错信息
     * @return 空串返回出错信息
     */
    public static String isEmpty(String sourceStr, String errorMsg) {
        if (isEmpty(sourceStr)) {
            return errorMsg;
        }
        return "";
    }

    /**
     * 方法说明： 过滤URL中的参数中的特殊字符 CreateTime Apr 18, 2009 5:58:14 PM
     *
     * @param src 需编码的字符串
     * @return 编码后的字符串
     */
    public static String escape(String src) {
        int i;
        char j;
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length() * 6);
        for (i = 0; i < src.length(); i++) {
            j = src.charAt(i);
            if (Character.isDigit(j) || Character.isLowerCase(j)
                    || Character.isUpperCase(j))
                tmp.append(j);
            else if (j < 256) {
                tmp.append("%");
                if (j < 16)
                    tmp.append("0");
                tmp.append(Integer.toString(j, 16));
            } else {
                tmp.append("%u");
                tmp.append(Integer.toString(j, 16));
            }
        }
        return tmp.toString();
    }

    /**
     * 方法说明： 完整过滤URL中的参数中的特殊字符 CreateTime Apr 18, 2009 6:00:57 PM
     *
     * @param src 需编码的字符串
     * @return 编码后的字符串
     */
    public static String escapeSrc(String src) {
        return escape(escape(src));
    }

    /**
     * 方法说明： 解析被过滤的参数 CreateTime Apr 18, 2009 6:53:01 PM
     *
     * @param src 需解析的字符串
     * @return 解析后的参数
     */
    public static String unescape(String src) {
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length());
        int lastPos = 0, pos = 0;
        char ch;
        while (lastPos < src.length()) {
            pos = src.indexOf("%", lastPos);
            if (pos == lastPos) {
                if (src.charAt(pos + 1) == 'u') {
                    ch = (char) Integer.parseInt(src
                            .substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                } else {
                    ch = (char) Integer.parseInt(src
                            .substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            } else {
                if (pos == -1) {
                    tmp.append(src.substring(lastPos));
                    lastPos = src.length();
                } else {
                    tmp.append(src.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        return tmp.toString();
    }

    /**
     * 方法说明： 完整解析被编码的参数 CreateTime Apr 18, 2009 6:55:31 PM
     *
     * @param src 解析被编码的参数
     * @return 解析后的参数
     */
    public static String unescapeSrc(String src) {
        return unescape(unescape(src));
    }

    /**
     * 方法说明：
     * 将',",\r\n替换成数据库内可存储的字符
     * CreateTime Apr 18, 2009 2:49:53 PM
     *
     * @param sourceStr 源字符串
     * @return 滤除后的字符串
     */
    public static String toQuoteMark(String sourceStr) {
        sourceStr = StringReplace.replaceStr(sourceStr, "'", "&#39;");
        sourceStr = StringReplace.replaceStr(sourceStr, "\"", "&#34;");
        sourceStr = StringReplace.replaceStr(sourceStr, "\r\n", "\n");
        return sourceStr;
    }

    public static void main(String[] args) {
        //System.out.println(unescapeSrc(escapeSrc("组")));
    }

    /**
     * 首字母大写
     *
     * @param str
     * @return
     */
    public static String firstToUpper(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}