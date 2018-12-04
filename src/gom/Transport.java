

package gom;



public class Transport 
{    
    public int id;
    private String mode;
    
    
    public Transport(){
        mode = "";
    }
    
    public Transport(String m){
        mode = m;
    }
    
    public Transport(int i, String m){
        id = i;
        mode = m;
    }
    
    public void setMode(String m){
        this.mode = m;
    }
    
    public String getMode(){
        return mode;
    }
    
    public int getId(){
        return id;
    }
    
    public void afficher(){
        System.out.println("Transport T"+id+" \nMode : "+mode);
    }
    
    @Override
    public String toString()
    {
        return "Transport "+mode;
    }
}
