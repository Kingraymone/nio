package base.baseface.connector;

import base.baseface.BaseLifecycle;
import base.baseface.container.BaseContainer;
import base.face.Service;
import base.face.connector.Connector;
import base.face.container.Container;

public class BaseConnector extends BaseLifecycle implements Connector {
    // servlet容器
    private BaseContainer container = null;
    // service服务
    private Service service = null;

    @Override
    public Container getContainer() {
        return container;
    }

    @Override
    public void setContainer(Container container) {
        this.container = (BaseContainer) container;
    }

    @Override
    public Service getService() {
        return service;
    }

    @Override
    public void setService(Service service) {
        this.service = service;
    }
}
