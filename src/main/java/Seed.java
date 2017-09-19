/**
 * Created by Paola Ortega S on 9/17/2017.
 */
public class Seed {
    private Class seedClass;
    private String id;
    private Object value;
    private boolean isRef;

    public Seed(){
        seedClass = null;
        id = "";
        value = null;
        isRef = false;
    }


    public Class getSeedClass() {
        return seedClass;
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

    public void setRef(boolean ref) {
        isRef = ref;
    }
}
