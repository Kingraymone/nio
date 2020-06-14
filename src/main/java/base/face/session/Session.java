package base.face.session;

/**
 * session接口
 */
public interface Session {
    // 管理器
    Manager getManager();

    void setManager(Manager manager);

    // 唯一标识
    void setId(String id);

    String getId();

    // 有效期
    void expire(); // 过期

    void access(); // 修改访问时间

    long getLastAccessTime(); // 获得上次访问时间

    // 是否有效
    void setValid(boolean valid);

    boolean isValid();

    // session中属性
    Object getAttribute(String name);

    void setAttribute(String name, Object value);

    void removeAttribute(String name);

}
