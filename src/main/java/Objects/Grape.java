package Objects;

import java.lang.reflect.Method;

/**
 * Grape is the object that is used to identify the objects that will be used for dependency injection. In Spring, it is the equivalent to a Bean instance.
 */
public class Grape {
    /**
     * The type of object that will be used.
     */
    private Class grapeClass;

    /**
     * Whether the user identified the object as a single instance or not.
     */
    private boolean isSingleton;

    /**
     * The name that *this will be referenced as by both the user and the program
     */
    private String id;

    /**
     * The method that will be invoked once an object's instance is created.
     */
    private Method initMethod;

    /**
     * The method that will be invoked once an object's instance will be destroyed.
     */
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
