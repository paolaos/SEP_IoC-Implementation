/**
 * Created by Paola Ortega S on 9/17/2017.
 */

import nu.xom.*;

import java.io.IOException;


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
    protected void growGrapes(){
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
        Grape grape = new Grape(); //new instance of Grape
        for(int i = 0; i < temp.getChildCount(); i++) { //iterates through all the attributes placed by the user
            Attribute attribute = temp.getAttribute(i); //instantiates a specific attribute (eg. id = "1")
            String name = attribute.getQualifiedName(); //takes the attribute name (eg. id)
            String value = attribute.getValue(); //takes the attribute value (eg. "1")
            switch (name) {
                case("id"):
                    grape.setId(value);
                    break;

                case ("class"):
                    try {
                        grape.setGrapeClass(Class.forName(value)); //creates a new Class based on the attribute's value

                    } catch (ReflectiveOperationException e) {
                        e.printStackTrace();

                    }
                    break;

                case ("scope"):
                    if(value.equals("singleton")) {
                        try {
                            grape.setSingleton(true);
                            super.singletonGrapes.put(grape, grape.getGrapeClass().newInstance()); //create a new instance of the grape and store it in its map

                        } catch (IllegalAccessException il) {
                            il.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        }

                    }
                    else
                        grape.setSingleton(false);

                    break;

                case ("initMethod"):
                    if (grape.getGrapeClass() != null) {
                        Class grapeClass = grape.getGrapeClass();
                        try {
                            grape.setInitMethod(grapeClass.getMethod(value, null)); //todo no sé si deberíamos considerar parámetros
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.err.print("Grape parameters not in correct order. Class should be before methods. ");
                    }
                    break;

                case ("destroyMethod"):
                    if (grape.getGrapeClass() != null) {
                        Class grapeClass = grape.getGrapeClass();
                        try {
                            grape.setDestroyMethod(grapeClass.getMethod(value, null));
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.err.print("Grape parameters not in correct order. Class should be before methods. ");
                    }
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
        for(int i = 0; i < temp.getChildCount(); i++) { //same principle as createGrape
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
                        e.printStackTrace();
                    }
                    break;

                case ("isReferenced"):
                    seed.setRef(Boolean.getBoolean(value));
                    break;

                case ("value"):
                    if(seed.getSeedClass() != null) {
                        seed.setValue(value);
                    } else {
                        System.err.print("Seed parameters not in correct order. Type should be before values.");
                    }
                    break;

                default:
                    System.err.print("Invalid parameter " + name);
                    break;

            }

        }

        Element parent = (Element) temp.getParent();
        Grape parentGrape = grapes.get(parent.getQualifiedName());
        super.dependencies.put(parentGrape, seed); //map should store seeds that belong to the same grape TODO revisar estructura

    }


}
