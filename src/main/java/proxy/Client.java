package proxy;

import proxy.imp.MyClassLoader;
import proxy.imp.MyInvocationTest;
import proxy.imp.MyProxy;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.lang.reflect.Proxy;

public class Client {
    public static void main(String[] args) {
        MyInvocationTest myInvocationTest = null;
        try {
            myInvocationTest = new MyInvocationTest(new Server());
            Log log = (Log)MyProxy.newProxyInstance(new Class[]{Log.class}, new MyClassLoader(), myInvocationTest);
            log.log(1000);
            System.out.println(log.test(1,"···"));
        } catch (Exception e) {
        }
    }
}
