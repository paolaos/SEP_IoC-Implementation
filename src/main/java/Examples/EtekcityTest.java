/**
 * Basic functional examples are set here in order the test the program's functionality
 */
package Examples;

import Annotations.*;

@GrapeAnnotation
public class EtekcityTest {
    private String switc;
    @AutowireGrape(id="elState")
    public void setState(String state){
        switc = state;
    }
    public String getState(){
        return switc;
    }
    @InitMethod
    public void init(){
    }
}

