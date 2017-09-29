package Examples;
public class LightTestRef {
    private String switc;
    public void setState(EtekcityTest etek){
        this.switc  = etek.getState();
    }
    public void on(){
        System.out.println(switc);
    }
    public void init(){
    }

}
