package swt6.spring.basics.ioc.util;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import java.lang.annotation.*;

@Qualifier
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    Type type() default Type.STANDARD;
    public enum Type {STANDARD, FILE}
}
