package connector.httpserver;


import connector.utils.Constant;
import connector.utils.StatusCode;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;


public class Response {
    private String protocol;
    private Request request;
    private SocketChannel output;
    private String status = "OK";
    private StringBuffer sb = new StringBuffer();
    private HashMap<String, String> head = new HashMap<>(8);

    public Response(SocketChannel sc, Request request) {
        this.output = sc;
        this.request = request;
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


    public void responseStatic() {
        String relative = Constant.WEB_ROOT + Constant.FILESEPARATOR + "src" + Constant.FILESEPARATOR + "main" + Constant.FILESEPARATOR + "resources";
        String resource = relative + Constant.FILESEPARATOR + request.getUri().substring(1) + ".html";
        System.out.println("请求文件为：" + resource);
        File file = new File(resource);
        //响应头部生成
        createResponseBody();
        try {
            if (file.exists()) {
                FileChannel fc = FileChannel.open(file.toPath(), StandardOpenOption.READ);
                sb.append("Content-length:").append(fc.size());
                sb.append(Constant.LINESEPARATOR).append(Constant.LINESEPARATOR);
                ByteBuffer bb = ByteBuffer.allocate((int) (sb.length() + fc.size() + 64));
                bb.put(sb.toString().getBytes());
                fc.read(bb);
                bb.flip();
                output.write(bb);
            } else {
                FileChannel fc = FileChannel.open(new File(relative + Constant.FILESEPARATOR + "404.html").toPath(), StandardOpenOption.READ);
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

    public void responseServlet() {

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

    public Request getRequest() {
        return request;
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
}
