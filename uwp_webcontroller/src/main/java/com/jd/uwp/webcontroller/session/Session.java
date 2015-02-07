package com.jd.uwp.webcontroller.session;

/**
 * session
 *
 * @author xushiheng
 * @date 2014/12/22
 */
public class Session {
    /**
     * session time out
     */
    public static final long OVERTIME = 30 * (1000 * 60);
    /**
     * session ID
     */
    private String sessionId;
    /**
     * loginTime
     */
    private long loginTime = System.currentTimeMillis();
    /**
     * last actived time
     */
    private long lastActivedTime = System.currentTimeMillis();

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * session timeout
     *
     * @throws Exception
     */
    public void activate() throws Exception {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - lastActivedTime > OVERTIME)
            throw new Exception("Session overtime .");
        this.lastActivedTime = currentTimeMillis;
    }

    /**
     * check user is active or not
     *
     * @return
     */
    public boolean isActive() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - lastActivedTime > OVERTIME)
            return false;
        return true;
    }


    private Object userVO;

    public Object getUser() {
        return userVO;
    }

    public void setUser(Object userVO) {
        this.userVO = userVO;
    }


}
