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

    protected void growGrapes(){
        try {
            Builder parser = new Builder();
            Document doc = parser.build(argument);
            Element root = doc.getRootElement();
            listChildren(root);
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

    private void listChildren(Node current) {
        if(current instanceof Element){
            Element temp = (Element) current;
            switch(temp.getQualifiedName()) {
                case ("grapes"):
                    break;

                case ("grape"):
                    createGrape(temp);
                    break;

                case ("seed"):
                    createSeed(temp);
                    break;

                default:
                    throw new IllegalArgumentException("Invalid input in XML " );
            }
        }

        for (int i = 0; i < current.getChildCount(); i++) {
            listChildren(current.getChild(i));
        }

    }

    private void createGrape(Element temp) {
        Grape grape = new Grape();
        for(int i = 0; i < temp.getChildCount(); i++) {
            Attribute attribute = temp.getAttribute(i);
            String name = attribute.getQualifiedName();
            String value = attribute.getValue();
            switch (name) {
                case("id"):
                    grape.setId(value);
                    break;

                case ("class"):
                    try {
                        grape.setGrapeClass(Class.forName(value));

                    } catch (ReflectiveOperationException e) {
                        e.printStackTrace();

                    }
                    break;

                case ("scope"):
                    if(value.equals("singleton"))
                        grape.setSingleton(true);

                    else
                        grape.setSingleton(false);

                    break;

                case ("initMethod"):
                    if (grape.getGrapeClass() != null) {
                        Class grapeClass = grape.getGrapeClass();
                        try {
                            grape.setInitMethod(grapeClass.getMethod(value, null)); //todo considerar parametros!!
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
                            grape.setDestroyMethod(grapeClass.getMethod(value, null)); //todo considerar parametros!!
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

        if(grape.isSingleton()) {
            try {
                super.singletonGrapes.put(grape, grape.getGrapeClass().newInstance());

            } catch (IllegalAccessException i) {
                i.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }

        }

        super.grapes.put(grape.getId(), grape);
    }

    private void createSeed(Element temp) {
        Seed seed = new Seed();
        for(int i = 0; i < temp.getChildCount(); i++) {
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
        super.dependencies.put(parentGrape, seed);

    }


}
