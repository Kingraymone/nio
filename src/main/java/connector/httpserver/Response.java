package connector.httpserver;


import connector.utils.Constant;
import connector.utils.StatusCode;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @SuppressWarnings("null")
    public void responseStatic() {
        System.out.println("请求头部uri为："+request.getUri());
        File file = new File("resources/"+request.getUri());
        //响应头部生成
        createResponseBody();
        try {
            if (!"/".equals(request.getUri())&&file.exists()) {
                System.out.println("请求路径为："+file.getAbsolutePath());
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
                System.out.println("请求路径为："+file.getAbsolutePath());
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
