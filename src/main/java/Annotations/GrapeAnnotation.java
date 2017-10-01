package Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is a delimitator in order to find the classes that use grapes and seeds as dependency injectors in the container.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface GrapeAnnotation {

}



/*
@interface Annotations.GrapeAnnotation {
    String id();
    String name();
    String scope();
    String initMethod();
    String destroyMethod();
    String property();
}

@interface SeedAnnotation {
    String name();
    String type();
    String isReferenced();
    String value();
} */


