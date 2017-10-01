/**
 * Created by Paola Ortega S on 9/17/2017.
 */

import Context.*;

import Examples.*;


public class Main {
    private static final int test = 0;//Cambiarlo para distintas pruebas
   public static void main(String[] args) {
        GrapevineContext context = new XMLGrapevineContext("Prueba.xml");

       switch(test) {//Pruebas XML
           case 0://Setter
               LightTestSetter obj = (LightTestSetter) context.getGrape("LightTestSetter");
               obj.on();
               break;
           case 1://Constructor
               LightTestConstructor obj2 = (LightTestConstructor) context.getGrape("LightTestConstructor");
               obj2.on();
               break;
           case 2://Reference
               LightTestRef obj3 = (LightTestRef) context.getGrape("LightTestRef");
               obj3.on();
               break;
       }


    }

   /*public static void main(String[] args) {
       GrapevineContext context = new AnnotationsGrapevineContext("/Examples");

       switch (test) {
           case 0: //Setter with reference by name
               LightTestSetter obj = (LightTestSetter) context.getGrape("elState");
               break;

           case 1: //Constructor with reference by type
               LightTestConstructor obj2 = (LightTestConstructor) context.getGrape("String");
               break;

       }
   }*/



}




