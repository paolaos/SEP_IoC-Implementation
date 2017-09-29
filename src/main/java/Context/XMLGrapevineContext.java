package Context; /**
 * Created by Paola Ortega S on 9/17/2017.
 */

import nu.xom.*;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.LinkedList;

import Objects.*;


public class XMLGrapevineContext extends GrapevineContext {

    private String argument;

    public XMLGrapevineContext(String argument){
        this.argument = argument;
        this.growGrapes();
    }

    /**
     * This method parses the XML file associated with the program
     * where DI will be applied, categorizes and stores all instantiated
     * beans and seeds.
     */
    public void growGrapes(){
        try {
            Builder parser = new Builder(); //new parser is created
            Document doc = parser.build(argument); //the parsed tokens are passed to a XOM document (in order to manipulate the elements accordingly)
            Element root = doc.getRootElement(); //the root element of the document is selected
            if(root.getQualifiedName().equals("grapes"))
                listChildren(root); //goes onto the categorizing method

            else
                System.err.print("The root element of the XML file has to be <grapes>");

        }
        catch (ParsingException ex) {
            System.out.println(argument + " is not well-formed.");
            System.out.println(ex.getMessage());
        }
        catch (IOException ex) {
            System.out.println(
                    "Due to an IOException, the parser could not print "
                            + argument
            );
        }
    }

    /**
     * Recursively takes every single element from the XML file (in preorder), determines the current
     * tag's name and decides what to do with it based on its type.
     * @param current
     */
    private void listChildren(Node current) {
        if(current instanceof Element){ //finds the element tags in the XML document with XOM parser
            Element temp = (Element) current;
            switch(temp.getQualifiedName()) {
                case ("grapes"): //grapes tag doesn't do anything in particular except instantiating the file
                    break;

                case ("grape"):
                    createGrape(temp);
                    break;

                case ("seed"):
                    createSeed(temp);
                    break;

                default:
                    throw new IllegalArgumentException("Invalid input in XML " ); //any other element type is not supported in our XML formatted file
            }
        }

        for (int i = 0; i < current.getChildCount(); i++) {
            listChildren(current.getChild(i)); //recursive call traverses the XML document in preorder
        }

    }

    /**
     * REQ: Tag has to be grape type.
     * Creates an instance of a grape, then fills grape iteratively with the attributes within temp.
     * If it is marked as a singleton, a new instance is created and placed in the singletonGrape maps.
     * @param temp current Element to be instantiated as grape.
     */
    private void createGrape(Element temp) {
        Grape grape = new Grape(); //new instance of Objects.Grape
        for(int i = 0; i < temp.getAttributeCount(); i++) { //iterates through all the attributes placed by the user
            Attribute attribute = temp.getAttribute(i); //instantiates a specific attribute (eg. id = "1")
            String name = attribute.getQualifiedName(); //takes the attribute name (eg. id)
            String value = attribute.getValue(); //takes the attribute value (eg. "1")
            switch (name) {
                case("id"):
                    grape.setId(value);
                    break;

                case ("class"):
                    try {
                        grape.setGrapeClass(Class.forName("Examples."+value)); //creates a new Class based on the attribute's value

                    } catch (ReflectiveOperationException e) {
                        e.printStackTrace();

                    }
                    break;

                case ("scope"):
                    if(value.equals("singleton")) {
                        //try {
                            grape.setSingleton(true);
                            super.singletonGrapes.put(grape.getId(), null); //create a new instance of the grape and store it in its map

                        /*} catch (IllegalAccessException il) {
                            il.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        }*/
                    }
                    else
                        grape.setSingleton(false);

                    break;

                case ("init-method"):
                    if (grape.getGrapeClass() != null) {
                        Class grapeClass = grape.getGrapeClass();
                        try {
                            grape.setInitMethod(grapeClass.getMethod(value, null));
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.err.print("Objects.Grape parameters not in correct order. Class should be before methods. ");
                    }
                    break;

                case ("destroy-method"):
                    if (grape.getGrapeClass() != null) {
                        Class grapeClass = grape.getGrapeClass();
                        try {
                            grape.setDestroyMethod(grapeClass.getMethod(value, null));
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.err.print("Objects.Grape parameters not in correct order. Class should be before methods. ");
                    }
                    break;

                case ("property"):
                    break;

                default:
                    System.err.print("Invalid parameter " + name);
                    break;

            }
        }


        super.grapes.put(grape.getId(), grape);
    }

    /**
     * REQ: Tag has to be seed type.
     * Creates an instance of a seed, then fills seed iteratively with the attributes within temp.
     * The seed is added onto the dependencies map.
     * @param temp current Element to be instantiated as seed.
     */
    private void createSeed(Element temp) {
        Seed seed = new Seed();
        for(int i = 0; i < temp.getAttributeCount(); i++) { //same principle as createGrape
            Attribute attribute = temp.getAttribute(i);
            String name = attribute.getQualifiedName();
            String value = attribute.getValue();

            switch (name) {
                case ("name"):
                    seed.setId(value);
                    break;

                case ("type"):
                    try {
                        seed.setSeedClass(Class.forName(value));
                    } catch (ReflectiveOperationException e) {
                        Class className = this.isPrimitive(value);
                        if(className == null)
                            e.printStackTrace();
                        else
                            seed.setSeedClass(className);
                    }
                    break;

                case("constructor"):
                    seed.setIsConstructor(Boolean.valueOf(value));
                    break;

                case ("isReferenced"):
                    seed.setRef(Boolean.valueOf(value)); //TODO considerar que se hace si es true
                    break;

                case ("value"):
                    if(seed.getSeedClass() != null) {
                        seed.setValue(value);
                    } else {
                        System.err.print("Objects.Seed parameters not in correct order. Type should be before values.");
                    }
                    break;

                default:
                    System.err.print("Invalid parameter " + name);
                    break;

            }

        }

        Element parent = (Element) temp.getParent();
        Grape parentGrape = grapes.get(parent.getAttributeValue("id"));
        if(super.dependencies.get(parentGrape.getId())==null){
            super.dependencies.put(parentGrape.getId(),new LinkedList<Seed>());
        }
        super.dependencies.get(parentGrape.getId()).add(seed); //map should store seeds that belong to the same grape TODO revisar estructura
    }

    private Class isPrimitive(String className) {
        Class result = null;
        switch (className) {
            case ("Byte"):
            case ("byte"):
                result = Byte.class;
                break;

            case ("Short"):
            case ("short"):
                result = Short.class;
                break;

            case ("Integer"):
            case ("int"):
                result = Integer.class;
                break;

            case ("Long"):
            case ("long"):
                result = Long.class;
                break;

            case ("Float"):
            case ("float"):
                result = Float.class;
                break;

            case ("Double"):
            case ("double"):
                result = Double.class;
                break;

            case ("Boolean"):
            case ("boolean"):
                result = Boolean.class;
                break;

            case ("Character"):
            case ("char"):
                result = Character.class;
                break;
            case("String"):
                result = String.class;
                break;

        }
        return result;
    }



    public void buildWithSetters(){
        Object o=null;
        Class<?> c;
        for (String st: singletonGrapes.keySet()) {
            if(dependencies.get(st).get(0).isConstructor()==false){
                try {
                    o = grapes.get(st).getGrapeClass().newInstance();
                    singletonGrapes.put(st,o);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                c = o.getClass();
                int j = 0;
                try {
                    Object component = o;
                    Method[] methods = c.getMethods();
                    for(int i = 0; i<methods.length; i++) {
                        Method m = methods[i];
                        String mname = m.getName();
                        Class[] types = m.getParameterTypes();
                        if(mname.startsWith("set") && types.length==1 && m.getReturnType()==Void.TYPE) {
                            m.invoke(component, dependencies.get(st).get(j).getValue());
                            j++;
                        }
                    }
                } catch( Exception ex) {
                    String msg = "Initialization error";
                    throw new RuntimeException( msg, ex);
                }
            }
        }
    }

    public void buildWithConstructors() {
        Object o=null;
        Class<?> c;
        for (String st: singletonGrapes.keySet()) {
            if(dependencies.get(st).get(0).isConstructor()==true){
                c = grapes.get(st).getGrapeClass();
                try {
                    Constructor[] constructors = c.getDeclaredConstructors();
                    assert constructors.length!=1 : "Component must have single constructor";
                    Constructor cc = constructors[0];
                    Object[] params = new Object[dependencies.get(st).size()];
                    for (int i = 0; i<dependencies.get(st).size();i++) {
                        params[i]= dependencies.get(st).get(i).getValue();
                    }
                    Object component = cc.newInstance(params);
                    singletonGrapes.put(st,component);
                } catch( Exception ex) {
                    String msg = "Initialization error";
                    throw new RuntimeException( msg, ex);
                }
            }
        }
    }
}
