

package gom;



public class Admin extends Employe
{ 
    String user;
    String password;
    private String email;
    
    public Admin(){ 
        user = "";
        password = "";
        email = "";
    } 
    
    public Admin(Employe e, String u, String pass, String em){ 
        super(e);
        user = u;
        password = pass;
        email = em;
    } 
    
    public String getEmail(){
        return email;
    }
    
    public String getUserName(){
        return user;
    }
    
    public String getPassword(){
        return password;
    }
    
}
