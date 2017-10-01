package Examples;

import Annotations.*;

@GrapeAnnotation
public class LightTestSetter {
    private String switc;
    @AutowireGrape(id = "switchName")
    public void setState(String switc){
        this.switc  = switc;
    }
    public void on(){
        System.out.println(switc);
    }
    @InitMethod
    public void init(){
        System.out.println("Init method after initialization");
    }
}
