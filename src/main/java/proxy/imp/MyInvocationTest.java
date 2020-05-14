package proxy.imp;

import proxy.Log;

import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

public class MyInvocationTest implements MyInvocation {
    Log log;

    public MyInvocationTest(Log log) {
        this.log = log;
    }

    @Override
    public Object invoke(MyProxy proxy, Method method, Object... args) {
        try {
            System.out.println("开始代理！");
            Object invoke = method.invoke(log, args);
            System.out.println("结束代理！");
            return invoke;
        } catch (Throwable e) {
            throw new UndeclaredThrowableException(e);
        }
    }
}
