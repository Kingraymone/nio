package base.face.connector;

import base.face.container.Context;
import base.face.session.Session;

import java.util.HashMap;

public interface Request {
    public String getUri();
    public HashMap<String, String> getHead();
    public HashMap<String, String> getParams();

    public void setContext(Context context);

    public Session getSession();
}
