package connector.utils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.LOCAL_VARIABLE})
public @interface FieldAnnotaion1 {
    String male() default "男";
    String female() default "女";
}
