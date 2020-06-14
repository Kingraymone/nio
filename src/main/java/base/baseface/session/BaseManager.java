package base.baseface.session;

import base.face.container.Context;
import base.face.session.Manager;
import base.face.session.Session;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.concurrent.ConcurrentHashMap;

public class BaseManager implements Manager {
    // sessions
    private ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();
    // context容器
    private Context context;
    // session最大活动时间
    private int maxActive;
    // sessionIdLength
    private int length = 16;

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void add(Session session) {
        sessions.put(session.getId(), session);
    }

    @Override
    public Session createSession() {
        BaseSession session = new BaseSession(this);
        String id = null;
        do {
            id = generateSessionId();
        } while (sessions.get(id) != null);
        session.setId(id);
        sessions.put(id, session);
        return session;
    }

    @Override
    public Session findSession(String id){
        return sessions.get(id);
    }

    @Override
    public Session[] findSessions() {
        return sessions.values().toArray(new Session[0]);
    }

    @Override
    public void removeSession(Session session) {
        sessions.remove(session.getId());
    }

    @Override
    public int getMaxActive() {
        return maxActive;
    }

    @Override
    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    @Override
    public void load() {

    }

    @Override
    public void unload() {

    }

    private String generateSessionId() {
        SecureRandom srandom = new SecureRandom();
        byte[] random = new byte[length];
        srandom.nextBytes(random);
        StringBuilder buffer = new StringBuilder(length * 2 + 20);
        for (int j = 0; j < length; j++) {
            byte b1 = (byte) ((random[j] & 0xf0) >> 4);
            byte b2 = (byte) (random[j] & 0x0f);
            if (b1 < 10)
                buffer.append((char) ('0' + b1));
            else
                buffer.append((char) ('A' + (b1 - 10)));
            if (b2 < 10)
                buffer.append((char) ('0' + b2));
            else
                buffer.append((char) ('A' + (b2 - 10)));
        }
        return buffer.toString();
    }
}
