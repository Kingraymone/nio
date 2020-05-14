package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class InvocationHandlerImp implements InvocationHandler {
    Log log;
    Response res;
    public InvocationHandlerImp(Log l , Response r){
        log=l;
        res=r;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("开始代理");
        Method[] logs = log.getClass().getMethods();
        Method[] responses = res.getClass().getMethods();
        for(Method t:logs){
            if(t.getName().equals(method.getName())){
                return method.invoke(log,args);
            }
        }
        for(Method t:responses){
            if(t.getName().equals(method.getName())){
                return method.invoke(res,args);
            }
        }
        System.out.println("代理结束");
        return null;
    }
}
