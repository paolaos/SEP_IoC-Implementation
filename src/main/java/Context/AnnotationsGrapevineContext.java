package Context;

import Annotations.*;
import Objects.Grape;
import Objects.Seed;
import org.reflections.Reflections;

import java.io.File;
import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 * This class is used when the user wants to create DI through annotations.
 */
public class AnnotationsGrapevineContext extends GrapevineContext {
    /**
     * The path where the package with all classes is located. Passed as an argument
     */
    String path;

    /**
     * Through the use of the external library "Java Reflections", it is possible to extract and manipulate annotations and other generic components.
     */
    Reflections reflections;


    public AnnotationsGrapevineContext(String path){
        if(new File(path).exists()) {
            this.path = path;
            growGrapes();
        }
        else System.err.print("Invalid path. ");

    }

    /**
     * This method scans iteratively each class, verifies if it is marked with a @GrapeAnnotation.
     * In case it is marked, a grape instance is recorded and all dependencies (marked with @AutowireGrape) are collected.
     */
    @Override
    protected void growGrapes() {
        reflections = new Reflections(path);
        Set<Class<? extends Object>> classSet = reflections.getSubTypesOf(Object.class);

        for(Class specificClass : classSet) {
            if(specificClass.isAnnotationPresent(GrapeAnnotation.class)) {
                String className = specificClass.getSimpleName();
                Reflections currentClassReflectionTool = new Reflections(path + "/" + className);
                Grape grape = createGrapeAnnotation(currentClassReflectionTool, specificClass);
                autowireDependencies(currentClassReflectionTool, grape);
            }
        }
    }

    /**
     * A new grape object is created, all properties are set based on the user's parameters and the new instance is returned to the user.
     * @param currentClassReflectionTool a Reflections instance used in order to easily extract and manipulate generic objects.
     * @param className the current class that is being scanned.
     * @return the instance of the freshly created grape.
     */
    private Grape createGrapeAnnotation(Reflections currentClassReflectionTool, Class className){
        Set<Method> grapeMethods = currentClassReflectionTool.getMethodsAnnotatedWith(GrapeAnnotation.class);

        if(grapeMethods.size() > 1)
            System.err.print("There should be at most one Grape instance per class. ");

        Iterator<Method> iterator = grapeMethods.iterator();
        Method currentMethod = iterator.next();

        Grape grape = new Grape();
        grape.setGrapeClass(className);
        grape.setId(className.getSimpleName());

        Scope scope = currentMethod.getAnnotation(Scope.class);

        if(scope.scope().equals("Singleton") || scope.scope().equals("singleton")) {
            grape.setSingleton(true);
            try {
                super.singletonGrapes.put(grape.getId(), grape.getGrapeClass().newInstance());

            } catch (IllegalAccessException  | InstantiationException e) {
                e.printStackTrace();
            }
        }
        else if(scope.scope().equals("Prototype") || scope.scope().equals("prototype")) {
            grape.setSingleton(false);
        }
        else System.err.print("Error in scope: value not accepted. ");


        Set<Method> initMethods = currentClassReflectionTool.getMethodsAnnotatedWith(InitMethod.class);
        if(initMethods.size() > 0) {
            iterator = initMethods.iterator();
            Method initMethod = iterator.next();
            grape.setInitMethod(initMethod);

        }

        Set<Method> destroyMethods = currentClassReflectionTool.getMethodsAnnotatedWith(DestroyMethod.class);
        if(initMethods.size() > 0) {
            iterator = destroyMethods.iterator();
            Method destroyMethod = iterator.next();
            grape.setInitMethod(destroyMethod);

        }
        super.grapes.put(className.getSimpleName(), grape);

        return grape;
    }

    /**
     * All dependencies defined by the user are caught and inmediately wired to the grape instance.
     * @param currentClassReflectionTool a Reflections instance used in order to easily extract and manipulate generic objects.
     * @param grape the "parent object" that has dependencies respective to this method
     */
    private void autowireDependencies(Reflections currentClassReflectionTool, Grape grape) {
        super.dependencies.computeIfAbsent(grape.getId(), V-> new LinkedList<>());
        Set<Method> autowireGrapeSet = currentClassReflectionTool.getMethodsAnnotatedWith(AutowireGrape.class);
        Iterator<Method> iterator = autowireGrapeSet.iterator();

        while (iterator.hasNext()) {/*Puede haber mas de una cosa con autowired*/
            Seed seed = new Seed();
            Method trialMethod = iterator.next();
            seed.setSeedClass(trialMethod.getParameterTypes()[0]);

            seed.setIsConstructor(!trialMethod.getName().startsWith("set"));

            if(seed.isConstructor())
                buildWithConstructors(grape.getId());

            else buildWithSetters(grape.getId());


            String id = trialMethod.getAnnotation(AutowireGrape.class).id();
            if(!id.equals(""))
                seed.setId(id);

            else seed.setId(trialMethod.getParameterTypes()[0].getSimpleName());

            super.dependencies.get(grape.getId()).add(seed);

        }
    }


}
