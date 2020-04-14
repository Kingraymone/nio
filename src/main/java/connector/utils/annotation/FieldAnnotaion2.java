package connector.utils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.LOCAL_VARIABLE})
public @interface FieldAnnotaion2 {
    String friend() default "king";
    int height();
}
