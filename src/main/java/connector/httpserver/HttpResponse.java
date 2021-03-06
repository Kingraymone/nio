package connector.httpserver;


import base.face.connector.Response;
import connector.utils.Constant;
import connector.utils.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;


public class HttpResponse implements Response {
    private String protocol;
    private HttpRequest httpRequest;
    private SocketChannel output;
    private String status = "OK";
    private StringBuffer sb = new StringBuffer();
    private HashMap<String, String> head = new HashMap<>(8);
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String redirect = null;
    private String forword = null;

    public HttpResponse(SocketChannel sc, HttpRequest httpRequest) {
        this.output = sc;
        this.httpRequest = httpRequest;
        head.put("Server", "nio http");
        head.put("Content-type", "text/html;charset=UTF-8");
        this.protocol = "HTTP/1.1";
    }

    public void setHead(String key, String value) {
        head.put(key, value);
    }

    public String getHead(String key) {
        return head.get(key);
    }

    @SuppressWarnings("null")
    public void responseStatic() {
        String uri = forword == null ? httpRequest.getUri() : forword;
        logger.info("请求头部uri为：{}", uri);
        File file = new File("resources/" + uri);
        //响应头部生成
        createResponseBody();
        try {
            logger.info("测试路径：" + new File("").getCanonicalPath());
            if (!"/".equals(httpRequest.getUri()) && file.exists()) {
                logger.info("请求路径为：{}", file.getAbsolutePath());
                FileChannel fc = FileChannel.open(file.toPath(), StandardOpenOption.READ);
                sb.append("Content-length:").append(fc.size());
                sb.append(Constant.LINESEPARATOR).append(Constant.LINESEPARATOR);
                ByteBuffer bb = ByteBuffer.allocate((int) (sb.length() + fc.size() + 64));
                bb.put(sb.toString().getBytes());
                fc.read(bb);
                bb.flip();
                output.write(bb);
            } else {
                file = new File("resources/404.html");
                logger.info("请求路径为：{}", file.getAbsolutePath());
                FileChannel fc = FileChannel.open(file.toPath(), StandardOpenOption.READ);
                sb.append("Content-length:").append(fc.size());
                sb.append(Constant.LINESEPARATOR).append(Constant.LINESEPARATOR);
                ByteBuffer bb = ByteBuffer.allocate((int) (sb.length() + fc.size() + 64));
                bb.put(sb.toString().getBytes());
                fc.read(bb);
                bb.flip();
                output.write(bb);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String str) {
        try {
            createResponseBody();
            sb.append("Content-length:").append(str.getBytes().length);
            sb.append(Constant.LINESEPARATOR).append(Constant.LINESEPARATOR);
            ByteBuffer bb = ByteBuffer.allocate((int) (sb.length() + str.getBytes().length + 64));
            bb.put(sb.toString().getBytes());
            bb.put(str.getBytes());
            bb.flip();
            output.write(bb);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createResponseBody() {
        sb.append(protocol).append(" ");
        for (StatusCode statusCode : StatusCode.values()) {
            if (statusCode.name().equals(status)) {
                sb.append(statusCode.toString());
                break;
            }
        }
        sb.append(Constant.LINESEPARATOR);
        for (Map.Entry<String, String> entry : head.entrySet()) {
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append(Constant.LINESEPARATOR);
        }
    }

    public String getProtocol() {
        return protocol;
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    public SocketChannel getOutput() {
        return output;
    }

    public String getStatus() {
        return status;
    }

    public StringBuffer getSb() {
        return sb;
    }

    public HashMap<String, String> getHead() {
        return head;
    }

    public void setRedirect(String html){
        this.redirect=html;
    }

    @Override
    public void forword(String html) {
        this.forword=html;
    }
}
