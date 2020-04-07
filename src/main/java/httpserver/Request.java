package httpserver;

import lombok.Getter;
import utils.Constant;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

@Getter
public class Request {
    private SocketChannel input;
    private String uri;

    public Request(SocketChannel sc) {
        this.input = sc;
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

    @Override
    public String toString() {
        return getUri();
    }
}
