package base.baseface.session;

import base.face.session.Manager;
import base.face.session.Session;

import java.time.Instant;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BaseSession implements Session {
    // 管理器
    protected transient Manager manager;
    // session是否有效
    protected volatile boolean isValid = false;
    // session唯一id
    protected String id;
    // session创建时间
    protected long createTime = 0L;
    // session上次访问时间
    protected long lastAccessTime = createTime;
    // session的属性
    protected transient Map<String, Object> notes = new ConcurrentHashMap<>();

    // 构造方法
    public BaseSession(Manager manager){
        this.createTime=Instant.now().toEpochMilli();
        this.lastAccessTime=createTime;
        this.manager=manager;
    }
    @Override
    public Manager getManager() {
        return manager;
    }

    @Override
    public void setManager(Manager manager) {
        this.manager=manager;
    }

    @Override
    public void setId(String id) {
        this.id=id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void expire() {
        this.isValid=false;
        // manger移除session
    }

    @Override
    public void access() {
        this.lastAccessTime= Instant.now().toEpochMilli();
    }

    @Override
    public long getLastAccessTime() {
        return lastAccessTime;
    }

    @Override
    public void setValid(boolean valid) {
        this.isValid=valid;
    }

    @Override
    public boolean isValid() {
        return isValid;
    }

    @Override
    public Object getAttribute(String name) {
        return notes.get(name);
    }

    @Override
    public void setAttribute(String name, Object value) {
        notes.put(name,value);
    }

    @Override
    public void removeAttribute(String name) {
        notes.remove(name);
    }
}
