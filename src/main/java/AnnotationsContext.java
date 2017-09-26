import jdk.nashorn.internal.objects.annotations.Getter;

import java.lang.annotation.*;

public class AnnotationsContext extends GrapevineContext {

    public void fillAnnotations(Class annotated)  {
        Annotation[] annotations = annotated.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof OurAnnotation/*la clase que va a ser las annotations*/) {
                String estoDeberiaSerId = ((OurAnnotation) annotation).id();
            }
        }
    }
    @Override
    protected void growGrapes() {

    }
}
