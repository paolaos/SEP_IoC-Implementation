package Context;

import java.util.*;
import Objects.*;

/**
 * Created by Paola Ortega S on 9/17/2017.
 */
public abstract class GrapevineContext {
    protected Map<String, Grape> grapes; //identifies all grapes with its name for a faster search
    protected Map<String, Seed> dependencies; //associates grapes id with its seeds
    protected Map<String, Object> singletonGrapes; //places singleton grapes with instantiated objects

    public GrapevineContext(){
        dependencies = new TreeMap<>();
        singletonGrapes = new TreeMap<>();
        grapes = new TreeMap<>();
    }

    protected abstract void growGrapes(); //implementation varies based on the source of information

    //method works the same regardless of the source of information, since it works with the three data structures
    public Object getGrape(String id){
        Grape grape = grapes.get(id);
        Object result = new Object();
        if(grape.isSingleton())
            result =  this.singletonGrapes.get(id);

        else {
            try {
                result = grapes.get(id).getGrapeClass().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return result;

    }

}
