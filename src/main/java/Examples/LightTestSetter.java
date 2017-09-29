package Examples;
public class LightTestSetter {
    private String switc;
    public void setState(String switc){
        this.switc  = switc;
    }
    public void on(){
        System.out.println(switc);
    }
    public void init(){
    }
}
