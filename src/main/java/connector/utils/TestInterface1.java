package connector.utils;

import java.io.IOException;


/**
 * @Description
 * @Package connector.utils
 * @author wangqi26959
 * @date 2020-04-13
 * @since jdk1.8
 */
public interface TestInterface1 {
    /**
     * @description 注解字段
     */
    int i = 1;
    String a = "test";

     void test();

    /**
     * @author wangqi26959
     * @description 默认注解方法
     * @param
     * @return
     */
    default void test2() throws IOException {
        System.out.println(a);
    }
}
