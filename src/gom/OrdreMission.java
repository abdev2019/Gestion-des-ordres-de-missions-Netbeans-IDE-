

package gom;
 
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter; 
import java.io.FileOutputStream;  


public class OrdreMission 
{
    
    private static int nbr = 1000;
    
    private int id;
    private Employe employe;
    private String depart;
    private String retour;
    private String objet;
    private Transport transport;
    private Lieu vuAuDepart;
    private Lieu lieuCible;
    
    public OrdreMission(){
        id = nbr++;
    }
    
    public OrdreMission(int id, Employe e, String dep, String ret, String obj, Transport trs, Lieu vuAuDepart, Lieu are){
        this.id = id;
        employe = e;
        depart = dep;
        retour = ret;
        objet = obj;
        transport = trs;
        this.vuAuDepart = vuAuDepart;
        lieuCible = are;
    }
    
    public int getId(){
        return id;
    }
    
    public void setEmploye(Employe e){
        employe = e;
    }
    
    public void setTransport(Transport t){
        transport = t;
    }
    
    public void setObjet(String o){
        objet = o;
    }
    
    public void setLieuAuDepart(Lieu l){
        vuAuDepart = l;
    }
    
    public void setDateDepart(String d){
        depart = d;
    }
    
    public void setDateRetour(String d){
        retour = d;
    }
    
    public Employe getEmploye(){
        return employe;
    }
    
    public Transport getTransport(){
        return transport;
    }
    
    public String getObjet(){
        return objet;
    }
    
    public Lieu getLieuAuDepart(){
        return vuAuDepart;
    }
    
    public String getDateDepart(){
        return depart;
    }
    
    public String getDateRetour(){
        return retour;
    }
    
    public int getNombreJourReste(){
       return 0;
    }
    
    public void setLieuCible(Lieu p){
        lieuCible = p;
    }
    
    
    public Lieu getLieuCible(){
        return lieuCible;
    }
    
    
    public String afficher()
    {
        String res;
        res  = "ORDRE DE MISSION N:"+id+"\n";
        res += 
        "PRESCRIT A Monsieur : "+employe.getPrenom()+" "+employe.getNom()+"\n"+
        "FONCTION            : "+employe.getFonction()+"\n"+
        "DE SE RENDRE A      : "+lieuCible.getNom()+"\n"+ 
        "DATE DEPPART        : "+(depart)+"\n"+
        "DATE RETOUR         : "+(retour)+"\n"+
        "OBJET DE LA MISSION : "+objet+"\n"+
        "MODE TRANSPORT      : "+transport.getMode()+"\n"+
        "--------------------------------------------------\n"+
        "VU AU DEPART        : "+vuAuDepart.getNom()+"\n";
        return res;
    }
    
    
    
    
 
    
    public void imprimerPDF()
    { 
        String chemin;

        javax.swing.JFileChooser chooser = new javax.swing.JFileChooser("JFileChooser");
        chooser.setApproveButtonText("Enregistrer ici"); 
        chooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);

        if (chooser.showOpenDialog(null) == javax.swing.JFileChooser.APPROVE_OPTION)
        {
            chemin = chooser.getSelectedFile().getAbsolutePath();

            Font blueFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, null); 
            Document document = new Document();
            try
            {
               PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(chemin+"\\Ordre_Mission_"+getId()+".pdf"));
               document.open(); 

             // En-tete 
             // icons
             Paragraph p1 = new Paragraph("ROYAUME DU MAROC\nMINISTERE DE L'LINTERIEUR\nPROVINCE DE TAROUDANNT\nSECRETARIAT GENERAL\nSERVICE INFORMATIQUE\n", blueFont);
             p1.setAlignment(Element.ALIGN_CENTER);
/*
             PdfPTable table1 = new PdfPTable(3);
             table1.setWidthPercentage(100);
             float[] columnWidths1 = {1f, 1f, 1f};
             table1.setWidths( columnWidths1 );

             PdfPCell cell1 = new PdfPCell(), cell2 = new PdfPCell(), cell3 = new PdfPCell();
                 cell1.setBorder(0); 
                 cell2.setBorder(0);  
                 cell3.setBorder(0);  

             try{
                 Image img1 = Image.getInstance(VueOrdreMission.class.getResource("/Images/logoMaroc.png"));
                 Image img2 = Image.getInstance(VueOrdreMission.class.getResource("/Images/logoTaroudant.png"));
                 img2.setAlignment(Element.ALIGN_RIGHT);
                 img1.scaleToFit(80,80);
                 img2.scaleToFit(80,80);
                 cell1.addElement(img1);
                 cell3.addElement(img2);
             }catch(Exception e){}
             cell2.addElement(p1);

             table1.addCell(cell1);
             table1.addCell(cell2);
             table1.addCell(cell3);


             document.add( table1 );
*/
             
             PdfPTable table1 = new PdfPTable(2);
             table1.setWidthPercentage(100);
             float[] columnWidths1 = {0.50f,1f};
             table1.setWidths( columnWidths1 );

             PdfPCell cell1 = new PdfPCell(p1), cell2 = new PdfPCell();
             cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
             cell2.setBorder(0);
             cell1.setBorder(0);
             table1.addCell(cell1);
             table1.addCell(cell2);
             table1.setSpacingAfter(20);
             document.add(table1);
             



             Paragraph titre = new Paragraph("ORDRE DE MISSION" , FontFactory.getFont(FontFactory.TIMES_BOLD, 26, Font.NORMAL, new CMYKColor(0, 0, 255, 255)));
             titre.setSpacingAfter(30);
             titre.setAlignment(Element.ALIGN_CENTER);
             document.add( titre );


             // body 
             PdfPCell cellules1[] = new PdfPCell[7], cellules2[] = new PdfPCell[7]; 

             cellules1[0] = new PdfPCell(new Paragraph( "IL EST PRESCRIT A MONSIEUR"));
             cellules1[1] = new PdfPCell(new Paragraph("FONCTION"));
             cellules1[2] = new PdfPCell(new Paragraph("DE SE RENDRE A")); 
             cellules1[3] = new PdfPCell(new Paragraph("DATE DE DEPPART"));
             cellules1[4] = new PdfPCell(new Paragraph("DATE DE RETOUR"));
             cellules1[5] = new PdfPCell(new Paragraph("OBJET DE LA MISSION"));
             cellules1[6] = new PdfPCell(new Paragraph("MODE TRANSPORT"));  

             cellules2[0] = new PdfPCell(new Paragraph( (employe.getPrenom()+" "+employe.getNom()).toUpperCase() ));
             cellules2[1] = new PdfPCell(new Paragraph(employe.getFonction().toUpperCase()+" "+employe.getGrade()+"Grade"));
             cellules2[2] = new PdfPCell(new Paragraph( lieuCible.getNom().toUpperCase() ));
             cellules2[3] = new PdfPCell(new Paragraph((depart)));
             cellules2[4] = new PdfPCell(new Paragraph((retour)));
             cellules2[5] = new PdfPCell(new Paragraph( objet.toUpperCase() )); 
             cellules2[6] = new PdfPCell(new Paragraph(transport.getMode().toUpperCase()));


             PdfPTable table2 = new PdfPTable(3);
             table2.setWidthPercentage(100); 

             float[] columnWidths = {0.6f,0.1f, 1f};
             table2.setWidths(columnWidths);

             PdfPCell tmp = new PdfPCell(new Paragraph( ":"));
             tmp.setBorder(0); tmp.setHorizontalAlignment(Element.ALIGN_CENTER);
             for(int i = 0;i<7;i++)
             {
                 cellules1[i].setHorizontalAlignment(Element.ALIGN_LEFT);
                 cellules2[i].setHorizontalAlignment(Element.ALIGN_LEFT);

                 cellules1[i].setBorder(0);
                 cellules2[i].setBorder(0); 

                 cellules1[i].setPaddingBottom(20);
                 cellules2[i].setPaddingBottom(20);

                 table2.addCell(cellules1[i]);
                 table2.addCell(tmp);
                 table2.addCell(cellules2[i]);
             }

             document.add(table2);


             PdfPTable line = new PdfPTable(1);
             line.setWidthPercentage(80);
             line.addCell( new PdfPCell() ); 
             line.setSpacingAfter(10);
             document.add(line);


             PdfPTable table3 = new PdfPTable(2);
             table3.setWidthPercentage(100);
             float[] columnWidths2 = {1.45f, 1f};
             table3.setWidths( columnWidths2 );

             PdfPCell cells[]  = new PdfPCell[6];
             for(int i=0;i<6;i++){
                cells[i] = new PdfPCell();
                cells[i].setBorder(0); 
                if(i>1)cells[i].setPaddingTop(60);
             }
                 
             cells[0].addElement(new Paragraph("VU AU DEPART\n"+vuAuDepart.getNom()+", LE"));
             cells[1].addElement(new Paragraph(vuAuDepart.getNom()+", LE: \nLE GOUVERNEUR DE LA PROVINCE"));
             Paragraph p = new Paragraph("Vu à L'arrivée\nA...................................");
             cells[2].addElement(p);
             cells[3].addElement(p);
             cells[4].addElement(p);
             cells[5].addElement(new Paragraph("Vu au retour\nA..................................."));
             
             for(int i=0;i<6;i++) table3.addCell(cells[i]);
             document.add( table3 ); 


             document.close();
             writer.close();  
             
            javax.swing.JOptionPane.showMessageDialog(null,
                     "Impression reuissite !" );
            
            if ( java.awt.Desktop.isDesktopSupported() ) {
                    java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
                    if (desktop.isSupported(java.awt.Desktop.Action.OPEN)) {
                        try {
                            desktop.open(new java.io.File(chemin+"\\Ordre_Mission_"+getId()+".pdf"));
                        } 
                        catch (java.io.IOException e) { }
                    }
                }
            
            } 
            catch (Exception e) { e.printStackTrace(); }  
        } 
    } 
    
     
}
