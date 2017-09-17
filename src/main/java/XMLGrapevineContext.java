/**
 * Created by Paola Ortega S on 9/17/2017.
 */

import nu.xom.*;

import javax.print.Doc;
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
            listChildren(root, 0);
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

    private void listChildren(Node current, int depth) {

        printSpaces(depth);
        String name = "";
        if (current instanceof Element) {
            Element temp = (Element) current;
            name = ": " + temp.getQualifiedName();
        }
        System.out.println(current.getClass().getName() + name);
        for (int i = 0; i < current.getChildCount(); i++) {
            listChildren(current.getChild(i), depth+1);
        }

    }

    private void printSpaces(int n) {

        for (int i = 0; i < n; i++) {
            System.out.print(' ');
        }

    }

}
