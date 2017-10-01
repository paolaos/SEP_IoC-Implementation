package Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines the scope of the object. There are two options: Singleton is used to store an initialized instance of the grape, prototype for the opposite.
 */
@Target(value = { ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface Scope {
    String scope() default "prototype";
}
