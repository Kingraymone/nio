package proxy.imp;

import java.lang.reflect.Method;

public interface MyInvocation {
    public Object invoke(MyProxy proxy, Method method,Object ...args);
}
