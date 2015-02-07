package com.jd.uwp.webcontroller.session;

import com.jd.uwp.common.utils.StringUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 会话管理器
 */
public class SessionManager {

    private Map<String, Session> adminClients = new ConcurrentHashMap<String, Session>();

    private static ThreadLocal<String> tl = new InheritableThreadLocal<String>();
    /**
     * singleton
     */
    private static SessionManager manager = new SessionManager();

    private SessionManager() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(Session.OVERTIME / 2);
                    } catch (InterruptedException e) {
                        Thread.interrupted();
                    }
                    for (Iterator<Entry<String, Session>> it = adminClients.entrySet().iterator(); it.hasNext(); ) {
                        Entry<String, Session> e = it.next();
                        Session session = getSession(e.getKey());
                        if (session == null)
                            continue;
                        if (session.isActive())
                            continue;
                        it.remove();
                    }
                }
            }
        }).start();
    }

    public static SessionManager getManager() {
        return manager;
    }

    public void newCheckedClient(String id, Session session) {
        if (!StringUtils.isEmpty(id)) {
            this.adminClients.put(id, session);
        }
    }

    public Session getSession(String id) {
        if (!StringUtils.isEmpty(id)) {
            return adminClients.get(id);
        } else {
            return null;
        }
    }

    public void releaseSession(String id) {
        adminClients.remove(id);
    }

    public boolean isLoginUser(String id) {
        return adminClients.containsKey(id);
    }

    /**
     * @return
     * @desc 当前请求的SessionID
     * @author lige
     */
    public static String getSessionID() {
        return tl.get();
    }

    /**
     * @return
     * @desc 当前请求的Session
     * @author lige
     */
    public static Session getSession() {
        return getManager().getSession(getSessionID());
    }

    /**
     * @param sessionID
     * @desc 当前请求的SessionID
     * @author lige
     */
    public static void setSessionID(String sessionID) {
        tl.set(sessionID);
    }
}
