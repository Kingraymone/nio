package base.face;

/**
 * 生命周期接口
 * init、start、stop、destroy
 */
public interface Lifecycle {
    void init();
    void start();
    void stop();
    void destroy();
}
