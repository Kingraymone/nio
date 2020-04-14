package connector.httpserver;

import lombok.Data;
import connector.utils.Constant;
import connector.utils.StatusCode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;

@Data
public class Response {
    private Request request;
    private SocketChannel output;
    private String status = "OK";
    private StringBuffer sb = new StringBuffer();

    public Response(SocketChannel sc) {
        this.output = sc;
    }

    public void responseResult() {
        if (output == null || request == null) {
            return;
        }
        try {
            createBase();//创建响应主体
            File file = new File(Constant.WEB_ROOT + "/src/main/resources" + request.getUri());
            ByteBuffer bb = ByteBuffer.allocate(2048);
            bb.put(sb.toString().getBytes(Constant.DEFAULTCHARSET));
            bb.flip();
            output.write(bb);
            if (file.exists()) {
                FileChannel fileChannel = new FileInputStream(file).getChannel();
                fileChannel.transferTo(0, fileChannel.size(), output);
            } else {
                FileChannel fileChannel = FileChannel.open(Paths.get(Constant.WEB_ROOT + "/src/main/resources/404.html"));
                fileChannel.transferTo(0, fileChannel.size(), output);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void responseStatic(){

    }
    public void responseServlet(){

    }
    public void responseError(){

    }

    public void createBase() {
        if (output == null || request == null) {
            return;
        }
        sb.append("HTTP/1.1 ");
        for (StatusCode statusCode : StatusCode.values()) {
            if (statusCode.name().equals(status)) {
                sb.append(statusCode.toString());
                break;
            }
        }
        sb.append("\r\n");
        //响应头部
        sb.append("content-type:text/html");
        sb.append("\r\n");
        //空行
        sb.append("\r\n");
    }
}
