/**
 * Created by Paola Ortega S on 9/17/2017.
 */
import nu.xom.*;

import java.io.IOException;


public class Main {

   /* public static void main(String[] args) {
        GrapevineContext context = new XMLGrapevineContext(args[0]);

    } */

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Usage: java nu.xom.samples.NodeLister URL");
            return;
        }

        Builder builder = new Builder();

        try {
            Document doc = builder.build(args[0]);
            Element root = doc.getRootElement();
            listChildren(root, 0);
        }
        // indicates a well-formedness error
        catch (ParsingException ex) {
            System.out.println(args[0] + " is not well-formed.");
            System.out.println(ex.getMessage());
        }
        catch (IOException ex) {
            System.out.println(ex);
        }

    }


    public static void listChildren(Node current, int depth) {

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


    private static void printSpaces(int n) {

        for (int i = 0; i < n; i++) {
            System.out.print(' ');
        }

    }

}




