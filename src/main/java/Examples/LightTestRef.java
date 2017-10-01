package Examples;

import Annotations.*;

@GrapeAnnotation
public class LightTestRef {
    private String switc;

    @AutowireGrape(id = "etekState")
    public void setState(EtekcityTest etek){
        this.switc  = etek.getState();
    }
    public void on(){
        System.out.println(switc);
    }
    public void init(){
    }
    @DestroyMethod
    public void destroy(){

    }

}
