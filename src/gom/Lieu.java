

package gom;



public class Lieu {
    private int id;
    private String nom;
    
    public Lieu(){
        this.nom = ""; 
    }
    
    public Lieu(int id, String nom){
        this.nom = nom;
        this.id  = id;
    }
    
    public int getId(){ return id; }
    public String getNom(){ return nom; }
    public void setNom(String n){nom = n;}
    
    @Override
    public String toString()
    {
        return nom;
    }
}
