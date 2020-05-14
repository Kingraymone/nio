package proxy;

import sun.misc.ProxyGenerator;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Test {

    public static void main(String[] args) {
        byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy", new Class[]{Log.class,Response.class});

        try {
            File file = new File("proxy.class");
            if(!file.exists()){
                try {
                    boolean newFile = file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        String path = System.getProperty("user.dir")+"\\src\\main\\java\\Threadtest11.java";
        System.out.println(path);
        Iterable iterable = fileManager.getJavaFileObjects(path);
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, iterable);
        task.call();
        try {
            fileManager.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
