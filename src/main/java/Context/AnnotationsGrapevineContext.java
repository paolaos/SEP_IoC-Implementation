package Context;

import Annotations.*;
import Objects.Grape;
import org.reflections.Reflections;

import java.io.File;
import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;

public class AnnotationsGrapevineContext extends GrapevineContext {
    String path;
    Reflections reflections;

    public void fillAnnotations(Class annotatedClass)  {
        //se tratan primero los annotations que crean beans


        Method[] methods = annotatedClass.getMethods();
        for(Method method : methods) {
            Annotation[] annotations = method.getDeclaredAnnotations();

            if(annotations.length > 0) {



                /*for(Annotation annotation : annotations) {
                    Grape grape = new Grape();
                    if(annotation.annotationType() == AutowireGrape.class) {

                    } else if(annotation.annotationType() == AutowireGrapeByName.class) {
                        AutowireGrapeByName autowireGrapeByName = (AutowireGrapeByName) annotation;
                        String name = autowireGrapeByName.id();

                    } else if(annotation.annotationType() == DestroyMethod.class) {

                    } else if(annotation.annotationType() == GrapeAnnotation.class) {

                    } else if(annotation.annotationType() == InitMethod.class) {

                    } else if(annotation.annotationType() == Scope.class) {

                    } else System.err.print("Annotation doesn't exist");

                }*/
            }
        }
    }

    public AnnotationsGrapevineContext(String path){
        if(new File(path).exists())
            this.path = path;

        else System.err.print("Invalid path. ");


    }

    @Override
    public void growGrapes() {
        reflections = new Reflections(path);
        Set<Class<? extends Object>> classSet = reflections.getSubTypesOf(Object.class);

        for(Class specificClass : classSet) {
            if(specificClass.isAnnotationPresent(GrapeAnnotation.class))
                this.createGrapeAnnotation(specificClass);
        }
    }

    @Override
    public void buildWithSetters() {

    }

    @Override
    public void buildWithConstructors() {

    }

    private void createGrapeAnnotation(Class currentClass){
        String className = currentClass.getSimpleName();
        Reflections currentClassReflectionTool = new Reflections(path + "/" + className);
        Set<Method> grapeMethods = currentClassReflectionTool.getMethodsAnnotatedWith(GrapeAnnotation.class);

        Iterator<Method> iterator = grapeMethods.iterator();
        Method currentMethod = iterator.next();

        Grape grape = new Grape();
        Class methodClass = currentMethod.getDeclaringClass();
        grape.setGrapeClass(methodClass);

        Scope scope = currentMethod.getAnnotation(Scope.class);
        if(scope != null) {
            if(scope.scope().equals("Singleton") || scope.scope().equals("singleton")) {
                grape.setSingleton(true);
                try {
                    super.singletonGrapes.put(grape.getId(), grape.getGrapeClass().newInstance());

                } catch (IllegalAccessException il) {
                    il.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }

            }
            else if(scope.scope().equals("Prototype") || scope.scope().equals("prototype")) {
                grape.setSingleton(false);
            }
            else System.err.print("Error in scope: value not accepted. ");

        }

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
        super.grapes.put(className, grape);
    }


}
