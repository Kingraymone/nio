package base.baseface.container.valve;

import base.baseface.BaseLifecycle;
import base.face.connector.Request;
import base.face.connector.Response;
import base.face.container.Container;
import base.face.container.Valve;

public class BaseValve extends BaseLifecycle implements Valve {
    private Valve next;
    Container container;

    @Override
    public Valve getNext() {
        return next;
    }

    @Override
    public void setNext(Valve valve) {
        this.next = valve;
    }

    @Override
    public void invoke(Request request, Response response) {
        if (next != null) {
            next.invoke(request, response);
        }
    }

    @Override
    public void backgroundProcess() {

    }

    @Override
    public Container getContainer() {
        return container;
    }

    @Override
    public void setContainer(Container container) {
        this.container = container;
    }
}
