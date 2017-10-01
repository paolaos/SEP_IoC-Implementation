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

