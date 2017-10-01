package Examples;

import Annotations.*;

@GrapeAnnotation
public class LightTestConstructor {
    private String switc;
    @AutowireGrape
    @Scope(scope = "singleton")
    public LightTestConstructor(String switc){
        this.switc  = switc;
    }
    public void on(){
        System.out.println(switc);
    }
    public void init(){
    }
}
