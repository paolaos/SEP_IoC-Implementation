import java.util.*;

/**
 * Created by Paola Ortega S on 9/17/2017.
 */
public abstract class GrapevineContext {
    protected Map<String, Grape> grapes;
    protected HashMap<Grape, Seed> dependencies;
    protected Map<Grape, Object> singletonGrapes;

    public GrapevineContext(){
        dependencies = new LinkedHashMap<>();
        singletonGrapes = new LinkedHashMap<>();
        grapes = new LinkedHashMap<>();
    }

    protected abstract void growGrapes();

    public Class getGrape(String id){
        return grapes.get(id).getGrapeClass();
    }

}
