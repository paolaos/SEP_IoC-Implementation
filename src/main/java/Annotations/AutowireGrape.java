/**
 * This contains all the annotations used to inject dependencies
 */
package Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is in charge of marking down the methods (either as constructor or setter) that will be wired immediately to its parent grape.
 */
@Target(value = {ElementType.CONSTRUCTOR, ElementType.METHOD, })
@Retention(RetentionPolicy.RUNTIME)
public @interface AutowireGrape {
    String id() default "";
}