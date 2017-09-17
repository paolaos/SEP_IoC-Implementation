import java.util.Map;

/**
 * Created by Paola Ortega S on 9/17/2017.
 */
public abstract class GrapevineContext {
    protected Map<String, Grape> dependencies;
    protected Map<Grape, Object> singletonGrapes;

    protected abstract void growGrapes();

    public Class getGrape(String id){
        return dependencies.get(id).getGrapeClass();
    }

}
