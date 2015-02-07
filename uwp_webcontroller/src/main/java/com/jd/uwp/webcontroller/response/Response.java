package com.jd.uwp.webcontroller.response;

import java.io.Serializable;
import java.util.Map;

/**
 * 数据响应类
 *
 * @author xushiheng
 */
public class Response implements Serializable {

    private static final long serialVersionUID = 4640025693961313645L;
    /**
     * 响应状态
     */
    private int code = 1;
    /**
     * 消息
     */
    private String message;
    /**
     * 数据
     */
    private Object data;
    /**
     * 参数
     */
    private Map<?, ?> params;
    /**
     * 数据总条目数
     */
    private int dataCount = 0;
    /**
     * 总页数
     */
    private int pageCount = 0;
    /**
     * 当前页
     */
    private int pageIndex = 0;
    /**
     * 每页数据
     */
    private int pageSize = 0;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Map<?, ?> getParams() {
        return params;
    }

    public void setParams(Map<?, ?> params) {
        this.params = params;
    }

    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
