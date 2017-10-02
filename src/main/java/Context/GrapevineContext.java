package Context;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;
import Objects.*;

/**
 * GrapevineContext is the abstract class used to call the generalized container in order to use dependency injection.
 *
 */
public abstract class GrapevineContext {
    /**
     * Identifies all grapes with its name for a faster search
     */
    protected Map<String, Grape> grapes;

    /**
     * Associates grapes id with its seeds
     */
    protected Map<String, List<Seed>> dependencies;

    /**
     * Places singleton grapes with instantiated objects
     */
    protected Map<String, Object> singletonGrapes;

    public GrapevineContext(){
        dependencies = new TreeMap<>();
        singletonGrapes = new TreeMap<>();
        grapes = new TreeMap<>();
    }

    /**
     * Identifies and structures all grapes and dependencies (seeds) found in the classes.
     * Implementation varies based on the source of information.
     */
    protected abstract void growGrapes();

    //method works the same regardless of the source of information, since it works with the three data structures

    /**
     * Method used to extract a grape once the program finished creating and structuring all grapes.
     * @param id the name of the grape that wants to be extracted.
     * @return the grape's object.
     */
    public Object getGrape(String id){
        Grape grape = grapes.get(id);
        Object result = new Object();
        if(grape.isSingleton())
            result =  this.singletonGrapes.get(id);
        else {
            try {
                result = grapes.get(id).getGrapeClass().newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * An object is created with setter parameters
     * @param name the name of the grape.
     * */
    protected void buildWithSetters(String name){
        Object obj=null;
        Class<?> clss;
        if(!dependencies.get(name).get(0).isConstructor()){//Es por set?
            try {
                obj = grapes.get(name).getGrapeClass().newInstance();
                singletonGrapes.put(name,obj);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            clss = obj.getClass();
            int j = 0;
            try {
                Object component = obj;
                Method[] methods = clss.getMethods();
                for(int i = 0; i<methods.length; i++) {
                    Method meth = methods[i];
                    String mname = meth.getName();
                    Class[] types = meth.getParameterTypes();
                    if(mname.startsWith("set") && types.length==1 && meth.getReturnType()==Void.TYPE) {
                        meth.invoke(component, dependencies.get(name).get(j).getValue());//Llamo al set con el parametro q le toca
                        j++;
                    }
                }
            } catch( Exception ex) {
                String msg = "Initialization error";
                throw new RuntimeException( msg, ex);
            }
        }

    }
    /**
     * An instance is created with constructor parameters
     * @param name the name of the grape.
     * */
    protected void buildWithConstructors(String name) {
        Class<?> clss;
        if(dependencies.get(name).get(0).isConstructor()){//Este grape es por constructor?
            clss = grapes.get(name).getGrapeClass();
            try {
                Constructor[] constructors = clss.getDeclaredConstructors();//Obtengo los constructores
                assert constructors.length!=1 : "Component must have single constructor";
                Constructor cons = constructors[0];
                Object[] params = new Object[dependencies.get(name).size()];//Arreglo de los parametros
                for (int i = 0; i<dependencies.get(name).size();i++) {
                    params[i]= dependencies.get(name).get(i).getValue();
                }
                Object component = cons.newInstance(params);
                singletonGrapes.put(name,component);
            } catch( Exception ex) {
                String msg = "Initialization error";
                throw new RuntimeException( msg, ex);
            }
        }
    }
}
