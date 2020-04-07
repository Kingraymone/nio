package httpserver;

import lombok.Data;
import utils.Constant;
import utils.StatusCode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Data
public class Response {
    private Request request;
    private SocketChannel output;
    private String status = "OK";
    private StringBuffer sb = new StringBuffer();
    public Response(SocketChannel sc){
        this.output = sc;
    }
    public void responseResult(){
        if(output==null||request==null){
            return ;
        }
        try {
            createBase();//创建响应主体
            File file = new File(HttpServer.WEB_ROOT+"/src/main/resources"+request.getUri());
            ByteBuffer bb = ByteBuffer.allocate(2048);
            bb.put(sb.toString().getBytes(Constant.DEFAULTCHARSET));
            bb.flip();
            output.write(bb);
            if(file.exists()){
                FileChannel fileChannel  = new FileInputStream(file).getChannel();
                fileChannel.transferTo(0,fileChannel.size(),output);
            }else{
                FileChannel fileChannel  = FileChannel.open(Paths.get(HttpServer.WEB_ROOT+"/src/main/resources/404.html"));
                fileChannel.transferTo(0,fileChannel.size(),output);
            }
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void createBase(){
        if(output==null||request==null){
            return ;
        }
        sb.append("HTTP/1.1 ");
        for(StatusCode statusCode : StatusCode.values()){
            if(statusCode.name().equals(status)){
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
