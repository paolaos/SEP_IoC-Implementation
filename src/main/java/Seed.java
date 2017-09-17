/**
 * Created by Paola Ortega S on 9/17/2017.
 */
public class Seed {
    private Class seedClass;
    private String id;
    private Object value;
    private boolean isRef;

    public Seed(Class seedClass, String id, Object value, boolean isRef){
        this.seedClass = seedClass;
        this.id = id;
        this.value = value;
        this.isRef = isRef;
    }
}
