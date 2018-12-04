

package gom;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;



public class Bdd 
{
    Connection c = null;
    PreparedStatement stmt = null;
    
    // copie de base de donnees
    public HashMap<Integer,Personne> personnes = null;
    public HashMap<Integer,Employe> employes = null;
    public HashMap<Integer,Service> services = null;
    public HashMap<Integer,Transport> transports = null;
    public HashMap<Integer,OrdreMission> ordreMissions = null;
    public HashMap<Integer,Lieu> lieux = null;
    
     
    public void chargerData(){
        services      = this.getServices();
        transports    = this.getTransports();
        personnes     = this.getPersonnes();
        employes      = this.getEmployes2();
        lieux         = this.getLieux();
        ordreMissions = this.getOrdresMission(); 
        System.out.println("Base de donnees charged !\n\n\n");
    }
    
    public void creerTables(boolean dropAll){
        Statement stm=null;
        try {
            c    = DriverManager.getConnection("jdbc:sqlite:bddom.db");
            if(dropAll){
                stm = c.createStatement();
                stm.executeUpdate("Drop Table Personne");
                stm.executeUpdate("Drop Table Employe");
                stm.executeUpdate("Drop Table Service");
                stm.executeUpdate("Drop Table Transport");
                stm.executeUpdate("Drop Table OrdreMission");
                stm.executeUpdate("Drop Table Lieu");
                stm.executeUpdate("Drop Table Admin");
            }
        } 
        catch ( Exception e ) {
           System.err.println( "prblem : "+e.getClass().getName() + ": " + e.getMessage() );
        }
        
        try {
            stm = c.createStatement();
        }catch(Exception e){}
           
        
            String sql00 = "CREATE TABLE Admin " +
                          "(id         INTEGER," +
                          " user        varchar," + 
                          " password varchar," + 
                          " email varchar );"; 
            try {
                stm.executeUpdate(sql00);
                System.out.println("Table Admin created !");
            }
            catch(Exception e){ System.out.println("Table Admin not created !"); }
        
        
            String sql0 = "CREATE TABLE Lieu " +
                          "(id         INTEGER PRIMARY KEY AUTOINCREMENT," +
                          " nom        varchar  );"; 
            try {
                stm.executeUpdate(sql0);
                System.out.println("Table Lieu created !");
            }
            catch(Exception e){ System.out.println("Table Lieu not created !"); }
        
            String sql1 = "CREATE TABLE Personne " +
                          "(id         INTEGER PRIMARY KEY AUTOINCREMENT," +
                          " nom        varchar          NOT NULL, " + 
                          " prenom     varchar          NOT NULL, " + 
                          " age        int, " + 
                          " adresse    TEXT);"; 
            try {
                stm.executeUpdate(sql1);
                System.out.println("Table Personne created !");
            }
            catch(Exception e){ System.out.println("Table Personne not created !"); }
            
            
            String sql2 = "CREATE TABLE Employe " +
                          "(id INT  NOT NULL," +
                          " fonction   varchar NOT NULL, " + 
                          " idService  int  NOT NULL, " +  
                          " grade int );";
            try {
                stm.executeUpdate(sql2);
                System.out.println("Table Employe created !");
            }
            catch(Exception e){System.out.println("Table Employe not created !");}
            
    
            String sql3 = "CREATE TABLE Service " +
                          "(id         INTEGER PRIMARY KEY AUTOINCREMENT," +
                          " nom        varchar          NOT NULL);";
            try {
                stm.executeUpdate(sql3);
                System.out.println("Table Service created !");
            }
            catch(Exception e){System.out.println("Table Service not created !");}
           
            String sql4 = "CREATE TABLE Transport " +
                "(id         INTEGER PRIMARY KEY AUTOINCREMENT," +
                " mode        varchar          NOT NULL);";
            
            try {
                stm.executeUpdate(sql4);
                System.out.println("Table Transport created !");
            }catch(Exception e){System.out.println("Table Transport not created !");}
             
            String sql5 = "CREATE TABLE OrdreMission " +
                "("
                    + "id  INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "idEmploye   int          NOT NULL,"
                    + "depart   varchar,"
                    + "retour   varchar,"
                    + "objet    text,"
                    + "idTransport int,"
                    + "idLieu     int,"
                    + "idLieuCible  int);";
            
            try {
                stm.executeUpdate(sql5);
                System.out.println("Table OMission created !");
            }catch(Exception e){System.out.println("Table OMission not created !");}
            
        
            try {
                stm.close();
                c.close();
            }catch(Exception e){}
        
    }
    
    
    // insert data
    public int insertPersonne(Personne p)
    {
        int id=-1;
        
        try {
            c = DriverManager.getConnection("jdbc:sqlite:bddom.db");
       
            stmt = c.prepareStatement(
                "INSERT INTO Personne (nom,prenom,age,adresse) VALUES (?,?,?,?);"
            );  
            
            stmt.setString(1,p.getNom());
            stmt.setString(2,p.getPrenom());
            stmt.setInt(3,p.getAge());
            stmt.setString(4,p.getAdresse());
            
            stmt.executeUpdate();
            stmt.close();  
            
            // get id
            Statement stm = c.createStatement();
            ResultSet rs = stm.executeQuery( "SELECT max(id) as idP FROM Personne");

            while ( rs.next() ) 
                id = rs.getInt("idP");  
            rs.close();
            stm.close();
            c.close();  
            
            System.out.println("Personne inserted !");
        }
        catch(Exception e){id=-1; System.out.println(e.getMessage());};
        
        return id; 
    }
    
    public int insertEmploye(Employe p){
        int id;
        try{ 
            id = insertPersonne(p);
            System.out.print(id+"\n");
            
            c = DriverManager.getConnection("jdbc:sqlite:bddom.db");
  
            stmt = c.prepareStatement(
                "INSERT INTO Employe (id,fonction,idService,grade) VALUES (?,?,?,?)"
            ); 
            stmt.setInt(1,id);
            stmt.setString(2,p.getFonction());
            stmt.setInt(3,p.getService().getId());
            stmt.setInt(4,p.getGrade());
            
            stmt.executeUpdate();
            
            stmt.close();
            c.close();
            System.out.println("Employe inserted !");
        }
        catch(Exception e){id=-1; System.out.println("Employe not inserted !");};
        return id;
    }
    
    public int insertService(Service s){
        int id=-1;
        try{
            c = DriverManager.getConnection("jdbc:sqlite:bddom.db");
       
            stmt = c.prepareStatement("INSERT INTO Service (nom) VALUES (?)");
            
            stmt.setString(1,s.getName());
            
            stmt.executeUpdate();
            
            Statement stm = c.createStatement();
            ResultSet rs = stm.executeQuery( "SELECT max(id) as idS FROM Service"); 
            while ( rs.next() ) id = rs.getInt("idS"); 
            rs.close();
            stm.close();
            
            stmt.close();
            c.close();
            
            System.out.println("Service inserted !");
        }
        catch(Exception e){System.out.println("Service  not inserted !");};
        return id;
    }
    
    public int insertTransport(Transport t){
        int id=-1;
        try{
            c = DriverManager.getConnection("jdbc:sqlite:bddom.db");
       
            stmt = c.prepareStatement(
                    "INSERT INTO Transport (mode) VALUES (?)"
            ); 
            stmt.setString(1,t.getMode());
                    
            stmt.executeUpdate();
            
            Statement stm = c.createStatement();
            ResultSet rs = stm.executeQuery( "SELECT max(id) as idT FROM Transport"); 
            while ( rs.next() ) id = rs.getInt("idT"); 
            rs.close();
            stm.close();
            
            stmt.close();
            c.close();
            System.out.println("Transport inserted !");
        }
        catch(Exception e){System.out.println("Transport not inserted !");};
        return id;
    }
    
    public int insertOrdreMission(OrdreMission om){
        int id=-1;
        try{
            c = DriverManager.getConnection("jdbc:sqlite:bddom.db");
       
            PreparedStatement stmt = c.prepareStatement(
                "INSERT INTO OrdreMission "
                + "(idEmploye,depart,retour,objet, idTransport, idLieu, idLieuCible) "
                + "VALUES (?,?,?,?,?,?,?)"
            ); 
            
            stmt.setInt(1,om.getEmploye().getId());
            stmt.setString(2,om.getDateDepart());
            stmt.setString(3,om.getDateRetour());
            stmt.setString(4,om.getObjet());
            stmt.setInt(5,om.getTransport().getId());
            stmt.setInt(6,om.getLieuAuDepart().getId());
            stmt.setInt(7,om.getLieuCible().getId());
            
            stmt.executeUpdate();
            
            Statement stm = c.createStatement();
            ResultSet rs = stm.executeQuery( "SELECT max(id) as idO FROM OrdreMission"); 
            while ( rs.next() ) id = rs.getInt("idO"); 
            rs.close();
            stm.close();
            
            stmt.close();
            c.close();
            System.out.println("Ordre Mission inserted !");
        }catch(Exception e){System.out.println("Ordre Mission not inserted !");};
        return id;
    }
    
    public int insertLieu(String lll){
        int id=-1;
        try{
            c = DriverManager.getConnection("jdbc:sqlite:bddom.db");
       
            stmt = c.prepareStatement("INSERT INTO Lieu (nom) VALUES (?)");
            
            stmt.setString(1,lll);
            
            stmt.executeUpdate();
            
            Statement stm = c.createStatement();
            ResultSet rs = stm.executeQuery( "SELECT max(id) as idL FROM Lieu"); 
            while ( rs.next() ) id = rs.getInt("idL"); 
            rs.close();
            stm.close();
            
            stmt.close();
            c.close();
            System.out.println("Lieu inserted !");
        }
        catch(Exception e){System.out.println("Lieu  not inserted !");};
        return id;
    }
    
    public void insertAdmin(int idEmploye, String user, String pass, String email){
        try{
            c = DriverManager.getConnection("jdbc:sqlite:bddom.db");
       
            stmt = c.prepareStatement("INSERT INTO Admin (id,user,password, email) VALUES (?,?,?,?)");
            
            stmt.setInt(1,idEmploye);
            stmt.setString(2,user);
            stmt.setString(3,pass);
            stmt.setString(4,email);
            
            stmt.executeUpdate();  
            stmt.close();
            c.close();
            
            System.out.println("Admin inserted !");
        }
        catch(Exception e){System.out.println("Admin  not inserted !");};
    }
    
    
    
    // get data
    public HashMap<Integer,Personne>      getPersonnes(){
        HashMap<Integer,Personne> res = new HashMap<>();
        
        try{
            c = DriverManager.getConnection("jdbc:sqlite:bddom.db");
            c.setAutoCommit(false);

            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Personne");
            
            while ( rs.next() ) { 
               
               res.put( rs.getInt("id") ,
                       new Personne( 
                               rs.getInt("id") ,
                               rs.getString("nom"), 
                               rs.getString("prenom"), 
                               rs.getInt("age"), 
                               rs.getString("adresse")
                       ) 
               );
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
           System.out.println("probleme : "+e.getMessage());
        }
        
        return res;
    }
    
    public HashMap<Integer,Employe>       getEmployes(){
        HashMap<Integer,Employe> res = new HashMap<>(); 
        
        try{
            c = DriverManager.getConnection("jdbc:sqlite:bddom.db");
            c.setAutoCommit(false);

            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT "
                    + "Personne.id as \"idP\","
                    + "Personne.nom as \"nomP\","
                    + "prenom,"
                    + "age,"
                    + "adresse,"
                    + "fonction,"
                    + "Service.id as \"idS\","
                    + "Service.nom as \"nomS\","
                    + "idService,"
                    + "grade,"
                    + "Employe.id as idE "
                    + "FROM Employe, Personne , Service "
                    + "where idS=idService and idP=idP" 
            );
            

            while ( rs.next() ) {
               int      id       = rs.getInt("idP");
               String   nom      = rs.getString("nomP");
               String   prenom   = rs.getString("prenom");
               int      age      = rs.getInt("age");
               String   address  = rs.getString("adresse");
               String   fonction = rs.getString("fonction"); 
               Service  service  = new Service(rs.getInt("idS") ,rs.getString("nomS"));
               
               res.put( id,
                       new Employe( id , nom, prenom, age, address, fonction, service, rs.getInt("grade") ) 
               );
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
           System.out.println("probleme : "+e.getMessage());
        }
        
        return res;
    }
    public HashMap<Integer,Employe>       getEmployes2(){
        HashMap<Integer,Employe> res = new HashMap<>(); 
        
        try{
            c = DriverManager.getConnection("jdbc:sqlite:bddom.db");
            c.setAutoCommit(false);

            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Employe" 
            );
            

            while ( rs.next() ) {
                int      id       = rs.getInt("id");
                String   fonction = rs.getString("fonction"); 
               
                if(
                        services.get(rs.getInt("idService")) == null ||
                        personnes.get(id) == null
                ) continue;
                    
                res.put( id,
                        new Employe( 
                                id , 
                                personnes.get(id).getNom(), 
                                personnes.get(id).getPrenom(), 
                                personnes.get(id).getAge(), 
                                personnes.get(id).getAdresse(), 
                                fonction, 
                                services.get(rs.getInt("idService")), 
                                rs.getInt("grade") 
                        ) 
                );
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
           System.out.println("probleme : "+e.getMessage());
        }
        
        return res;
    }

    
    public HashMap<Integer,OrdreMission>  getOrdresMission(){
        HashMap<Integer,OrdreMission> res = new HashMap<>();

        try{
            c = DriverManager.getConnection("jdbc:sqlite:bddom.db");
            c.setAutoCommit(false);

            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM OrdreMission;" );

            while ( rs.next() ) {
               int      id       = rs.getInt("id");  
               
               res.put( id,
                       new OrdreMission( 
                               id , 
                               employes.get(rs.getInt("idEmploye"))==null?
                                       new Employe():employes.get(rs.getInt("idEmploye")), 
                               rs.getString("depart"), 
                               rs.getString("retour"), 
                               rs.getString("objet"), 
                               transports.get(rs.getInt("idTransport"))==null?
                                       new Transport():transports.get(rs.getInt("idTransport")), 
                               lieux.get( rs.getInt("idLieu") )==null?
                                       new Lieu():lieux.get( rs.getInt("idLieu") ), 
                               lieux.get(rs.getInt("idLieuCible"))==null?
                                       new Lieu():lieux.get(rs.getInt("idLieuCible"))
                       ) 
               );
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
           System.out.println(e.getMessage());
        }

        return res;
    }
    
    public HashMap<Integer,Service>       getServices(){
        HashMap<Integer,Service> res = new HashMap<>();

            try{
                c = DriverManager.getConnection("jdbc:sqlite:bddom.db");
                c.setAutoCommit(false);

                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery( "SELECT * FROM Service" );
                        
                while ( rs.next() ) {
                   int   id  = rs.getInt("id"); 
                   String     nom   = rs.getString("nom"); 

                   res.put( id,
                           new Service( id , nom) 
                   );
                }
                rs.close();
                stmt.close();
                c.close();
            } catch ( Exception e ) {
               System.out.println(e.getMessage());
            }

            return res;
    } 
    
    public HashMap<Integer,Transport>     getTransports(){
        HashMap<Integer,Transport> res = new HashMap<>();

            try{
                c = DriverManager.getConnection("jdbc:sqlite:bddom.db");
                c.setAutoCommit(false);

                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery( "SELECT * FROM Transport" );
                        
                while ( rs.next() ) {
                   int      id    = rs.getInt("id"); 
                   String     mode   = rs.getString("mode"); 

                   res.put( id, 
                           new Transport( id , mode) 
                   );
                }
                rs.close();
                stmt.close();
                c.close();
            } catch ( Exception e ) {
               System.out.println(e.getMessage());
            }

            return res;
    } 
    
    public HashMap<Integer,Lieu>          getLieux(){
        HashMap<Integer,Lieu> res = new HashMap<>();

            try{
                c = DriverManager.getConnection("jdbc:sqlite:bddom.db");
                c.setAutoCommit(false);

                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery( "SELECT * FROM Lieu" );
                        
                while ( rs.next() ) {
                   int        id    = rs.getInt("id"); 
                   String     nom   = rs.getString("nom");  
                   res.put( id, new Lieu(id, nom) );
                }
                
                rs.close();
                stmt.close();
                c.close();
            } catch ( Exception e ) {
               System.out.println(e.getMessage());
            }

            return res;
    } 
    
    
    public void supprimerOrdreDeMission(int id){
        try{
            c = DriverManager.getConnection("jdbc:sqlite:bddom.db");
       
            stmt = c.prepareStatement("delete from OrdreMission where id="+id);  
            stmt.executeUpdate();
            
            stmt.close();
            c.close(); 
        }
        catch(Exception e){System.out.println("Suppression impossible !");};
    }
    public void supprimerEmploye(int id){
        try{
            c = DriverManager.getConnection("jdbc:sqlite:bddom.db");
       
            stmt = c.prepareStatement("delete from Employe where id="+id); 
            stmt.executeUpdate();
            stmt = c.prepareStatement("delete from Personne where id="+id);
            stmt.executeUpdate();
            
            stmt.close();
            c.close(); 
        }
        catch(Exception e){System.out.println("Suppression impossible !");};
    }
    public void supprimerService(int id){
        try{
            c = DriverManager.getConnection("jdbc:sqlite:bddom.db");
       
            stmt = c.prepareStatement("delete from Service where id="+id);  
            stmt.executeUpdate();
            
            stmt.close();
            c.close(); 
        }
        catch(Exception e){System.out.println("Suppression impossible !");};
    }
    public void supprimerTransport(int id){
        try{
            c = DriverManager.getConnection("jdbc:sqlite:bddom.db");
       
            stmt = c.prepareStatement("delete from Transport where id="+id);  
            stmt.executeUpdate();
            
            stmt.close();
            c.close(); 
        }
        catch(Exception e){System.out.println("Suppression impossible !");};
    }
    public void supprimerLieu(int id){
        try{
            c = DriverManager.getConnection("jdbc:sqlite:bddom.db");
       
            stmt = c.prepareStatement("delete from Lieu where id="+id);  
            stmt.executeUpdate();
            
            stmt.close();
            c.close(); 
        }
        catch(Exception e){System.out.println("Suppression impossible !");};
    }
    public void supprimerAdmin(int id){
        try{
            c = DriverManager.getConnection("jdbc:sqlite:bddom.db");
       
            stmt = c.prepareStatement("delete from Admin where id="+id);  
            stmt.executeUpdate();
            
            stmt.close();
            c.close(); 
        }
        catch(Exception e){System.out.println("Suppression impossible !");};
    }
    
    
    
    
    
    
    
    
    
    public void modifierOrdreDeMission(OrdreMission om){
        try{
            c = DriverManager.getConnection("jdbc:sqlite:bddom.db");  
            stmt = c.prepareStatement(
                    "update OrdreMission set "
                    + "idEmploye=?, depart=?, retour=?, objet=?, idTransport=?, idLieu=?, idLieuCible=? "
                    + " where id="+om.getId()
            );  
            stmt.setInt(1,om.getEmploye().getId());
            stmt.setString(2,om.getDateDepart());
            stmt.setString(3,om.getDateRetour());
            stmt.setString(4,om.getObjet());
            stmt.setInt(5,om.getTransport().getId());
            stmt.setInt(6,om.getLieuAuDepart().getId());
            stmt.setInt(7,om.getLieuCible().getId());
            stmt.executeUpdate();
            
            stmt.close();
            c.close(); 
        }
        catch(Exception e){System.out.println("Modification impossible !");};
    }
   
    public void modifierPersonne(Personne per){
        try{
            c = DriverManager.getConnection("jdbc:sqlite:bddom.db"); 
            
            
            
            stmt = c.prepareStatement("update Personne "
                    + "set nom=? , "
                    + "prenom=?, "
                    + "age=? , adresse=?"
                    + " where id="+per.getId()); 
            
            stmt.setString(1,per.getNom());
            stmt.setString(2,per.getPrenom());
            stmt.setInt(3,per.getAge());
            stmt.setString(4,per.getAdresse());
            
            stmt.executeUpdate(); 
            
            stmt.close();
            c.close(); 
        }
        catch(Exception e){System.out.println("Modification impossible !");};
    }
    
    public void modifierEmploye(Employe emp){
        try{
            modifierPersonne(emp);
                    
            c = DriverManager.getConnection("jdbc:sqlite:bddom.db"); 
             
            
            stmt = c.prepareStatement("update Employe "
                    + "set fonction=? , "
                    + "idService=?, "
                    + "grade=? "
                    + " where id="+emp.getId()); 
            
            stmt.setString(1,emp.getFonction());
            stmt.setInt(2,emp.getService().getId());
            stmt.setInt(3,emp.getGrade());
            
            stmt.executeUpdate(); 
            
            stmt.close();
            c.close(); 
        }
        catch(Exception e){System.out.println("Modification impossible !");};
    }
    
    public void modifierService(Service s){
        try{
            c = DriverManager.getConnection("jdbc:sqlite:bddom.db");
       
            stmt = c.prepareStatement("update Service set nom=? where id="+s.getId());  
            stmt.setString(1, s.getName());
            stmt.executeUpdate();
            
            stmt.close();
            c.close(); 
        }
        catch(Exception e){System.out.println("Modification impossible !");};
    }
    public void modifierTransport(Transport tr){
        try{
            c = DriverManager.getConnection("jdbc:sqlite:bddom.db");
       
            stmt = c.prepareStatement("update Transport set mode=? where id="+tr.getId());
            stmt.setString(1,tr.getMode());
            stmt.executeUpdate();
            
            stmt.close();
            c.close(); 
        }
        catch(Exception e){System.out.println("Modification impossible !");};
    }
    public void modifierLieu(Lieu ll){
        try{
            c = DriverManager.getConnection("jdbc:sqlite:bddom.db");
       
            stmt = c.prepareStatement("update Lieu set nom=? where id="+ll.getId());  
            stmt.setString(1, ll.getNom());
            stmt.executeUpdate();
            
            stmt.close();
            c.close(); 
        }
        catch(Exception e){System.out.println("Modification impossible !");};
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    public HashMap<Integer,Admin>     getAdmins(){
        HashMap<Integer,Admin> res = new HashMap<>();

            try{
                c = DriverManager.getConnection("jdbc:sqlite:bddom.db");
                c.setAutoCommit(false);

                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery( "SELECT * FROM Admin" );
                
                if(employes==null)
                    employes = getEmployes();
                
                while ( rs.next() ) {
                   int        id    = rs.getInt("id"); 
                   String     user   = rs.getString("user"); 
                   String     pass   = rs.getString("password"); 
                   
                   if(employes.get(id) != null)
                   res.put( id, 
                           new Admin( employes.get(id) , user, pass, rs.getString("email")) 
                   );
                }
                rs.close();
                stmt.close();
                c.close();
            } catch ( Exception e ) {
               System.out.println(e.getMessage());
            }

            return res;
    } 
    
    // authentification
    public Admin connecter(String user, String password){
        Admin adm = null;
        try{
            c = DriverManager.getConnection("jdbc:sqlite:bddom.db");
            String selectSQL = "SELECT id,email FROM Admin WHERE user=? and password=?";
            PreparedStatement preparedStatement = c.prepareStatement(selectSQL);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            int id=-1;
            String email="";
            while(rs.next())  {
                id = rs.getInt("id");
                email = rs.getString("email");
            }
            if(id!=-1 && employes!=null){
                adm = new Admin(employes.get(id),user,password, email );
            } 
            
            rs.close();
            preparedStatement.close();
            c.close();
        }
        catch(Exception e){ System.out.println("problem : "+e.getMessage()); return null; }
        
        return adm;
    } 
    
    
}
