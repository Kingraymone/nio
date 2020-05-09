package connector.httpserver;

import connector.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashMap;


public class Request {
    private SocketChannel input;
    private String uri;
    private String protocol;
    private String method;
    private HashMap<String,String> head = new HashMap<>(8);
    private static final int BUFFER_SIZE = 8196;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public Request(SocketChannel sc) {
        this.input = sc;
    }

    public String praseRequest(){
        ByteBuffer bb = ByteBuffer.allocate(BUFFER_SIZE);
        try {
            int num=input.read(bb);
            if(num>0){
                logger.debug("读取请求内容成功！");
                return new String(bb.array(),0,bb.limit());
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void parseHead(String param) throws Exception {
        String separator = System.getProperty("line.separator");
        String line = param.substring(0,param.indexOf(separator));
        String[] heads = line.split(" ");
        if(heads.length!=3){
            logger.error("解析头部失败！");
            throw new Exception("");
        }else{
            this.method=heads[0];
            this.uri=heads[1];
            this.protocol=heads[2];
        }
    }
    public void parse() {
        if (input == null) {
            return;
        }
        ByteArrayOutputStream bis = null;
        try {
            ByteBuffer bb = ByteBuffer.allocate(2048);
            bis = new ByteArrayOutputStream(2048);
            int len;
            while ((len = input.read(bb)) > 0) {
                bb.flip();//读写模式转换
                bis.write(bb.array(), 0, bb.limit());
            }
            uri=parseUri(bis.toString(Constant.DEFAULTCHARSET));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String parseUri(String requestLine) {
        int index1, index2;
        if ((index1 = requestLine.indexOf(Constant.BLANKSPACE)) != -1) {
            if ((index2 = requestLine.indexOf(Constant.BLANKSPACE, index1 + 1)) != -1) {
                return requestLine.substring(index1 + 1, index2);
            }
        }
        return null;
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
}
