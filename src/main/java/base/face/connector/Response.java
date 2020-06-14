package base.face.connector;

import java.util.HashMap;

public interface Response {
    public void write(String str);
    public HashMap<String, String> getHead();
    public void setRedirect(String html);
    public void forword(String html);
    public void responseStatic();
}
