package base.face.session;


import base.face.container.Context;

import java.io.IOException;

public interface Manager {
    // 容器
    Context getContext();
    void setContext(Context context);

    // session相关
    void add(Session session);
    Session createSession();
    Session findSession(String id);
    Session[] findSessions();
    void removeSession(Session session);

    // 存活时间
    int getMaxActive();
    void setMaxActive(int maxActive);

    // session持久化
    void load();
    void unload();
}
