package Objects;

/**
 * Created by Paola Ortega S on 9/17/2017.
 */

/**
 * A seed consists of a dependency from a Grape. One grape can have multiple seeds.
 */
public class Seed {
    /**
     * The type of object that will be used.
     */
    private Class seedClass;

    /**
     * The name that *this will be referenced as by both the user and the program
     */
    private String id;

    /**
     * The instance that is stored
     */
    private Object value;
    private boolean isRef;
    private boolean isCons;

    public Seed(){
        seedClass = null;
        id = "";
        value = null;
        isRef = false;
        isCons=false;
    }


    public void setSeedClass(Class seedClass) {
        this.seedClass = seedClass;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setIsConstructor(boolean cons){
        isCons =cons;
    }

    public void setRef(boolean ref) {
        isRef = ref;
    }

    public Class getSeedClass() {
        return seedClass;
    }

    public String getId(){
        return id;
    }

    public Object getValue(){
        return value;
    }

    public boolean isConstructor(){
        return isCons;
    }

    public boolean isRef(){
        return isRef;
    }
}
