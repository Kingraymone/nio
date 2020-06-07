package base.baseface.container;

import base.baseface.BaseLifecycle;
import base.face.connector.Request;
import base.face.connector.Response;
import base.face.container.Valve;

public class BaseValve extends BaseLifecycle implements Valve {
    private Valve next;

    @Override
    public Valve getNext() {
        return next;
    }

    @Override
    public void setNext(Valve valve) {
        this.next=valve;
    }

    @Override
    public void invoke(Request request, Response response) {
        System.out.println("钩子处理！");
    }

    @Override
    public void backgroundProcess() {

    }
}
