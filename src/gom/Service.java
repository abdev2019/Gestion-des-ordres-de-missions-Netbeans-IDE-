
package gom;
 

public class Service 
{    
    private int id;
    private String name;
    
    public Service(String name){
        this.name = name;
    }
    
    public Service(int id, String name){
        this.id = id;
        this.name = name;
    }
    
    public void modifierService(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    public int getId(){
        return id;
    }
    
    public void afficher(){
        System.out.println("Service "+name);
    }
    
    @Override
    public String toString()
    {
        return name;
    }
    
}
