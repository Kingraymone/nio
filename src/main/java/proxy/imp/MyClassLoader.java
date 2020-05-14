package proxy.imp;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class MyClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String className = name.substring(0,name.lastIndexOf("."))+".class";
        File file = new File(className);
        System.out.println("类加载路径："+file.getPath());
        if (file.exists()) {
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
                 BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            ) {
                byte[] bytes = new byte[1024];
                int len;
                while ((len = bis.read(bytes)) != -1) {
                    bos.write(bytes, 0, len);
                }
                //传入全限制名称
                return defineClass("proxy.$Proxy0",bos.toByteArray(), 0, bos.size());
            } catch (Exception e) {

            }
        }
        return super.findClass(name);
    }
}
