import java.util.List;

/**
 * Created by Paola Ortega S on 9/17/2017.
 */
public class Grape {
    private Class grapeClass;
    private boolean isSingleton;
    private String id;
    private Object initMethod;
    private Object destroyMethod;
    private List<Seed> list;

    public Grape(Class grapeClass, boolean isSingleton, String id, Object initMethod, Object destroyMethod, List<Seed> list){
        this.grapeClass = grapeClass;
        this.isSingleton = isSingleton;
        this.id = id;
        this.initMethod = initMethod;
        this.destroyMethod = destroyMethod;
        this.list = list;
    }

    public Class getGrapeClass() {
        return grapeClass;
    }
}
