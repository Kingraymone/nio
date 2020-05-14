package proxy.imp;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class MyProxy {
    protected MyInvocation invocation;

    public MyProxy(MyInvocation invocation) {
        this.invocation = invocation;
    }

    public static Object newProxyInstance(Class<?>[] interfaces, MyClassLoader loader, MyInvocation invocation) {
        byte[] clazz = generate(interfaces);
        String name = interfaces[0].getName();
        //获得包名
        String packageName = name.substring(0, name.lastIndexOf(".") + 1);
        packageName = packageName.replace(".", "/");
        String path = System.getProperty("user.dir") + "/src/main/java/" + packageName + "$Proxy0.java";
        File file = new File(path);
        //生成.java源文件
        FileOutputStream fos = null;
        if (!file.exists()) {
            try {
                file.createNewFile();
                fos = new FileOutputStream(file);
                fos.write(clazz);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        //从程序中调用Java编程语言编译器的接口
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        //文件管理器基于java.io.File
        StandardJavaFileManager standardFileManager = javaCompiler.getStandardFileManager(null, null, null);
        System.out.println("加载源码路径：" + file.getPath());
        Iterable javaFileObjects = standardFileManager.getJavaFileObjects(file.getPath());
        JavaCompiler.CompilationTask task = javaCompiler.getTask(null, standardFileManager, null, null, null, javaFileObjects);
        //编译
        try {
            task.call();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                standardFileManager.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            //类加载器加载生成Class对象
            Class<?> aClass = loader.findClass(file.getPath());
            //调用构造器生成代理对象
            Constructor<?> declaredConstructor = aClass.getDeclaredConstructor(MyInvocation.class);
            return declaredConstructor.newInstance(invocation);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] generate(Class<?>[] interfaces) {
        String name = interfaces[0].getName();
        //获得包名
        String packageName = name.substring(0, name.lastIndexOf(".") + 1);
        StringBuffer sb = new StringBuffer();
        sb.append("package proxy;\n");
        sb.append("import proxy.imp.MyProxy;\n");
        sb.append("import java.lang.reflect.Method;\n");
        sb.append("import proxy.imp.MyInvocation;\n");
        sb.append("import java.lang.reflect.UndeclaredThrowableException;\n");
        sb.append("\n");
        sb.append("public final class $Proxy0 extends MyProxy implements ").append(name.substring(name.lastIndexOf(".") + 1)).append(" {\n");
        //生成构造方法
        sb.append("\tpublic $Proxy0(MyInvocation h){\n");
        sb.append("\t\tsuper(h);\n");
        sb.append("\t}\n");
        //生成代理的方法
        Method[] methods = interfaces[0].getMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            String returnType = method.getReturnType().getSimpleName();
            sb.append("\t").append("@Override\n");
            sb.append("\t").append("public final ").append(returnType).append(" ").append(methodName).append("(");
            Parameter[] parameters1 = method.getParameters();
            StringBuffer params = new StringBuffer();
            int count = 0;
            for (Parameter parameter : parameters1) {
                sb.append(parameter.getType().getSimpleName()).append(" var").append(count++).append(",");
                params.append(parameter.getType().getSimpleName()).append(".class,");
            }
            //删除方法参数中最后一个，
            if (parameters1.length > 0) {
                sb.deleteCharAt(sb.length() - 1);
                params.deleteCharAt(params.length() - 1);
            }
            sb.append("){\n");
            //加载接口方法
            sb.append("\t\t").append("try{\n");
            sb.append("\t\t\t").append("Method m =").append("Class.forName(\"").append(name).append("\").getMethod(\"").append(methodName);
            sb.append("\"");
            if (params.length() > 0) {
                sb.append(",").append(params.toString());
            }
            sb.append(");\n");
            sb.append("\t\t\t");
            if (!"void".equals(returnType)) {
                sb.append("return (").append(returnType).append(")");
            }
            sb.append("super.invocation.invoke(this,m,new Object[]{");
            int count2=0;
            while (count2 < count) {
                sb.append("var").append(count2);
                if (++count2 < count) {
                    sb.append(",");
                }
            }
            sb.append("});\n");
            sb.append("\t\t}catch (Throwable e){\n");
            sb.append("\t\t\tthrow new UndeclaredThrowableException(e);\n");
            sb.append("\t\t}\n");
            sb.append("\t}\n\n");
        }
        sb.append("}");
        System.out.println(sb.toString());
        return sb.toString().getBytes();
    }
}
