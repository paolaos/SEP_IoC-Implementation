import jdk.nashorn.internal.objects.annotations.Getter;
import sun.java2d.pipe.OutlineTextRenderer;

import java.lang.annotation.*;

public class AnnotationsGrapevineContext extends GrapevineContext {

    public void fillAnnotations(Class annotated)  {
        Annotation[] annotations = annotated.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof OurAnnotation/*la clase que va a ser las annotations*/) {
                /*Por cada una de las siguientes cosas, tenemos que ir metiendo de a pocos en los grapes*/
                ((OurAnnotation) annotation).id();
                ((OurAnnotation) annotation).name();
                ((OurAnnotation) annotation).scope();
                ((OurAnnotation) annotation).initMethod();
                ((OurAnnotation) annotation).destroyMethod();
                ((OurAnnotation) annotation).property();
            } else if (annotation instanceof OurLittleAnnotation) {
                /*Lo mismo por este lado*/
                ((OurLittleAnnotation) annotation).name();
                ((OurLittleAnnotation) annotation).value();
                ((OurLittleAnnotation) annotation).type();
                ((OurLittleAnnotation) annotation).isReferenced();
            }
        }
    }
    @Override
    protected void growGrapes() {

    }
}
