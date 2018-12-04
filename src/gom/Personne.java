

package gom;


public class Personne 
{
    private static int nbr=1000;
    
    protected int id;
    protected String nom;
    protected String prenom;
    protected int age;
    protected String adresse;
    
    
    public Personne(){
        id = nbr++;
        nom = "";
        prenom = "";
        age = 25;
        adresse = "";
    }
    
    public Personne(Personne p){
        id = p.id;
        nom = p.nom;
        prenom = p.prenom;
        age = p.age;
        adresse = p.adresse;
    }
    
    public Personne(int id, String n, String p, int a, String adr){
        nom = n;
        prenom = p;
        age = a;
        adresse = adr;
        this.id = id;
    }
    
    
    public int getId(){
        return id;
    }

    public String getNom(){
        return nom;
    }
    
    public String getPrenom(){
        return prenom;
    }
    
    public int getAge(){
        return age;
    }
    
    public String getAdresse(){
        return adresse;
    }
    
    public void setAge(int a){
        age = a;
    }
    
    public void setNom(String n){
        nom = n;
    }
    
    public void setPrenom(String p){
        prenom = p;
    }
    
    public void setAdresse(String a){
        adresse = a;
    }
    
    
    public void afficher(){
        System.out.println(
                   "Id      : "+id
                +"\nNom     : "+nom
                +"\nPrenom  : "+prenom
                +"\nAge     : "+age
                +"\nAdresse : "+adresse
        );
    }
            
}

