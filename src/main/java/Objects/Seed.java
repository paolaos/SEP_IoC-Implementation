package Objects;

/**
 * Created by Paola Ortega S on 9/17/2017.
 */
/**
 * Created by Paola Ortega S on 9/17/2017.
 */
public class Seed {
    private Class seedClass;
    private String id;
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

    public String getid(){
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
