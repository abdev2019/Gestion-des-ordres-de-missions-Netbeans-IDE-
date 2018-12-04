

package gom;
 
public class Employe extends Personne
{
    private String fonction;
    private Service service;
    private int grade;
    
    
    public Employe(){ } 
    
    public Employe(Employe e){
        super(e);
        fonction = e.fonction;
        service = e.service;
        grade = e.grade; 
    } 
    
    public Employe(String n, String p, int a, String adr, String f, Service s, int g){
        super(0, n, p, a, adr);
        service = s;
        fonction = f;
        grade = g;
    }
    
    public Employe(int id ,  String n, String p, int a, String adr, String f, Service s, int g){
        super(id, n, p, a, adr);
        this.id = id;
        service = s;
        fonction = f;
        grade = g;
    } 
     

    public void setFonction(String f){
        fonction = f;
    }

    public void setService(Service s){
        service = s;
    } 

    public String getFonction(){
        return fonction;
    }

    public Service getService(){
        return service;
    }
    
    public void setGrade(int g){
        grade = g;
    } 

    public int getGrade(){
        return grade;
    }

    public void afficher(){
        System.out.println("------Employe------");
        super.afficher();
        System.out.println(
                "\nFonction  : "+fonction
                +"\nGrade     ! "+grade
                +"\nService   : "+service.getName()
        );
    }
    
    @Override
    public String toString()
    {
        return prenom+" "+nom;
    }
}
