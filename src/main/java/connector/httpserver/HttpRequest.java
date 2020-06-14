package connector.httpserver;

import base.face.connector.Request;
import base.face.container.Context;
import base.face.session.Session;
import connector.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashMap;


public class HttpRequest implements Request {
    private SocketChannel input;
    private String uri;
    private String protocol;
    private String method;
    private HashMap<String, String> head = new HashMap<>(8);
    private HashMap<String, String> params = new HashMap<>(8);
    private static final int BUFFER_SIZE = 8196;
    private Context context;
    private Session session = null;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public HttpRequest(SocketChannel sc) {
        this.input = sc;
    }

    public HttpRequest() {
    }

    public String praseRequest() {
        ByteBuffer bb = ByteBuffer.allocate(BUFFER_SIZE);
        try {
            int num = input.read(bb);
            if (num > 0) {
                logger.debug("读取请求内容成功！");
                return new String(bb.array(), 0, bb.limit());
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int parseHead(String param) throws Exception {
        String separator = System.getProperty("line.separator");
        int index = param.indexOf(separator);
        String line = param.substring(0, index);
        String[] heads = line.split(" ");
        if (heads.length != 3) {
            logger.error("解析头部失败！");
            throw new Exception("解析头部失败！");
        } else {
            this.method = heads[0];
            this.uri = heads[1];
            this.protocol = heads[2];
        }
        return index;
    }

    public int parseProperties(String param) {
        int last = param.indexOf(Constant.LINESEPARATOR);
        String line = param.substring(0, last);
        while (line.contains(":")) {
            String[] properties = line.split(":");
            head.put(properties[0], properties[1]);
            int offset = Constant.LINESEPARATOR.equals("\n") ? 1 : 2;
            int cur = param.indexOf(Constant.LINESEPARATOR, last + offset);
            line = param.substring(last + offset, cur);
            last = cur;
        }
        return last;
    }

    public void parseParams(String params) {
        if (params == null) {
            return;
        }
        params = params.trim();
        if (params.equals("")) {
            return;
        }
        int index = 0;
        while (params.charAt(index) == '\n' || params.charAt(index) == '\r') {
            index++;
            if (index >= params.length()) {
                return;
            }
        }
        String substr = params.substring(index);
        if (substr.contains("&") || substr.contains("=")) {
            String[] kvs = substr.split("&");
            for (String kv : kvs) {
                String[] param = kv.split("=");
                if (param.length > 1) {
                    this.params.put(param[0], param[1]);
                } else {
                    this.params.put(param[0], "");
                }
            }
        }
    }

    public void parse(String param) throws Exception {
        int index = parseHead(param);
        while (param.charAt(index) == '\n' || param.charAt(index) == '\r') {
            index++;
        }
        String properties = param.substring(index);
        int index2 = parseProperties(properties);
        String params = properties.substring(index2);
        parseParams(params);
        logger.info("解析请求参数完成！");
    }

    public String getUri() {
        return uri;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getMethod() {
        return method;
    }

    public HashMap<String, String> getHead() {
        return head;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Session getSession() {
        // 为连接创建Session
        if (session == null) {
            return context.getManager().createSession();
        } else {
            return session;
        }
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
