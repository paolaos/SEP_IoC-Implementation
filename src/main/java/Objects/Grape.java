/**
 * The two building blocks of the GrapeVine Container System
 */
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

    /**
     * Whether the user identified the autowired as type, name or not using autowired at all
     * */
    private String autowiring;

    public Grape(){
        grapeClass = null;
        isSingleton = false;
        id = "";
        initMethod = null;
        destroyMethod = null;
        autowiring = "no";
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

    public String getAutowiring() { return autowiring; }

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

    public void setAutowiring(String autowiring) { this.autowiring = autowiring; }
}
