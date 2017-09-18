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
            listChildren(root, null);
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

    private void listChildren(Node current, Node previous) {
        if(current instanceof Element){
            Element temp = (Element) current;
            switch(temp.getQualifiedName()) {
                case ("grapes"):
                    break;
                case ("grape"):
                    //sacarlos atributos que tiene el tag
                    //validar que están los obligatorios
                    //meter todos los atributos que hay a un new grape
                    //poner el grape en una lista
                    //poner el grape en el hashmap
                    break;
                case ("seed"):
                    //sacar los atributos que tiene el tag
                    //validar que están los obligatorios
                    //meter todos los atributos que hay a un new seed
                    //poner el seed en el hashmap del grape existente
                    break;

                default:
                    throw new IllegalArgumentException("Invalid input in XML " );
            }
        }

        for (int i = 0; i < current.getChildCount(); i++) {
            listChildren(current.getChild(i), current);
        }

    }



}
