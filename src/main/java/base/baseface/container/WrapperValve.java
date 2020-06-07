package base.baseface.container;

import base.face.connector.Request;
import base.face.connector.Response;
import core.process.StaticProcess;

public class WrapperValve extends BaseValve {
    @Override
    public void invoke(Request request, Response response) {
        super.invoke(request, response);
    }
}
