package Objects;

import java.lang.reflect.Method;

/**
 * Created by Paola Ortega S on 9/17/2017.
 */
public class Grape {
    private Class grapeClass;
    private boolean isSingleton;
    private String id;
    private Method initMethod;
    private Method destroyMethod;

    public Grape(){
        grapeClass = null;
        isSingleton = false;
        id = "";
        initMethod = null;
        destroyMethod = null;

    }

    public Class getGrapeClass() {
        return grapeClass;
    }

    public boolean isSingleton() {
        return isSingleton;
    }

    public String getId() {
        return id;
    }

    public Method getInitMethod() {
        return initMethod;
    }

    public Method getDestroyMethod() {
        return destroyMethod;
    }

    public void setGrapeClass(Class grapeClass) {
        this.grapeClass = grapeClass;
    }

    public void setSingleton(boolean singleton) {
        isSingleton = singleton;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setInitMethod(Method initMethod) {
        this.initMethod = initMethod;
    }

    public void setDestroyMethod(Method destroyMethod) {
        this.destroyMethod = destroyMethod;
    }
}
