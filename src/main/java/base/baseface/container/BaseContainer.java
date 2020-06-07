package base.baseface.container;

import base.baseface.BaseLifecycle;
import base.face.connector.Request;
import base.face.connector.Response;
import base.face.container.Container;
import base.face.container.Pipeline;

public class BaseContainer extends BaseLifecycle implements Container {
    // 管道流
    private Pipeline pipeline = new BasePipeline();
    // 父容器
    private Container parent = null;
    // 子容器
    private Container[] children = new Container[0];
    // 子容器锁
    private final Object lock = new Object();

    /**
     * 连接器处理完毕有调用的方法
     *
     * @param request
     * @param response
     */
    public void invoke(Request request, Response response) {

    }

    @Override
    public Pipeline getPipeline() {
        return pipeline;
    }

    @Override
    public Container getParent() {
        return parent;
    }

    @Override
    public void setParent(Container parent) {
        this.parent = parent;
    }

    @Override
    public void addChild(Container container) {
        synchronized (lock) {
            Container[] result = new Container[children.length + 1];
            System.arraycopy(children, 0, result, 0, children.length);
            result[children.length] = container;
            children = result;
        }
    }

    @Override
    public void findChild(Container container) {

    }

    @Override
    public Container[] findChildren() {
        return children;
    }

    @Override
    public void removeChild() {

    }
}
