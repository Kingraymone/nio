package base.baseface.container.valve;

import base.face.connector.Request;
import base.face.connector.Response;

public class TestValve extends BaseValve {
    @Override
    public void invoke(Request request, Response response) {
        System.out.println("钩子测试！");
        super.invoke(request, response);
    }
}
