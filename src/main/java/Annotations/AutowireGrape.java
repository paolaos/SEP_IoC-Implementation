package Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Paola Ortega S on 9/28/2017.
 */
@Target(value = {ElementType.CONSTRUCTOR, ElementType.METHOD, })
@Retention(RetentionPolicy.RUNTIME)
public @interface AutowireGrape {

}