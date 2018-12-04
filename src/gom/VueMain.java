

package gom;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Map; 
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;



public class VueMain extends javax.swing.JFrame {

    public static int ORDREMISSION = 1;
    public static int EMPLOYE = 2;
    public static int SERVICE = 3;
    public static int TRANSPORT = 4;
    public static int LIEU = 5;
    
    
    java.util.Vector modelc = new java.util.Vector();
    Bdd bdd;
  
    String[] columns={ "Id", "Employe", "De se rendre à","Date depart", "Date retour", "nbrJours restant", "Objet",  "Mode transport", "Vu au depart", "ACTIONS" };
         
    DefaultTableModel model;  
    TableRowSorter<TableModel> sorter;
    
    private Admin adm = null;
    
    
    public VueMain(Admin adm) 
    {
        initComponents();
        this.setLocationRelativeTo(null);
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH); 
        
        if(adm == null) dispose();
        else this.adm = adm;
        
        try {
            java.awt.Image img = java.awt.Toolkit.getDefaultToolkit()
            .getImage(VueMain.class.getResource("/Images/logoMaroc.png")); 
             this.setIconImage(img);
        } catch (Exception ex) {
          System.out.println("problem "+ex);
        } 
       
        
         
        // chargement du base de donnes
        bdd = new Bdd(); 
        bdd.chargerData();
        
        
        sorter = new TableRowSorter<>(jTable1.getModel());
        jTable1.setRowSorter(sorter); 
        model = (DefaultTableModel)jTable1.getModel();
         
        // charger les noms des columns
        int i=0, j=0;
        for(;i<columns.length;i++)
            model.addColumn( columns[i] );  
        jTable1.getColumn("ACTIONS").setMinWidth(130); 
         
        // ajout les bottons d'action
        TableColumn column = jTable1.getColumn("ACTIONS"); 
        String imgs[] = { "printt.png","edit.png","del.png"  };
        column.setCellRenderer(new ButtonsRenderer(3,imgs));
        column.setCellEditor(new ButtonsEditor(jTable1, bdd, 3, imgs, VueMain.ORDREMISSION, null));  
       
        
        
        
        // icons buttons
        try {
            java.awt.Image img = java.awt.Toolkit.getDefaultToolkit()
            .getImage(VueMain.class.getResource("/Images/ttc2.png")); 
            jButton1.setIcon(new javax.swing.ImageIcon(img));
        } catch (Exception ex) {
          System.out.println("problem "+ex);
        } 
        
        try {
            java.awt.Image img = java.awt.Toolkit.getDefaultToolkit()
            .getImage(VueMain.class.getResource("/Images/ref.png"))
                    .getScaledInstance(38, 38, java.awt.Image.SCALE_SMOOTH);  
            jButton2.setIcon(new javax.swing.ImageIcon(img));
        } catch (Exception ex) {
          System.out.println("problem "+ex);
        } 
        
        try {
            java.awt.Image img = java.awt.Toolkit.getDefaultToolkit()
            .getImage(VueMain.class.getResource("/Images/editing2.png"))
                          .getScaledInstance(31, 31, java.awt.Image.SCALE_SMOOTH);
            jButton3.setIcon(new javax.swing.ImageIcon(img));
            jButton4.setIcon(new javax.swing.ImageIcon(img));
        } catch (Exception ex) {
          System.out.println("problem "+ex);
        } 
        
        try {
            java.awt.Image img = java.awt.Toolkit.getDefaultToolkit()
            .getImage(VueMain.class.getResource("/Images/excel.png"))
                          .getScaledInstance(38, 38, java.awt.Image.SCALE_SMOOTH);
            jButton5.setIcon(new javax.swing.ImageIcon(img));
        } catch (Exception ex) {
          System.out.println("problem "+ex);
        } 
             
        chargerData();
        initFilter();
    }
   
    private void initFilter(){ 
        ((javax.swing.text.JTextComponent) jComboBox3.getEditor().getEditorComponent())
                .setDocument(new Suggestion(jComboBox3));  
        
         ((javax.swing.text.JTextComponent) jComboBox1.getEditor().getEditorComponent())
                .setDocument(new Suggestion(jComboBox1));  
        
        // FILTRER LES LIGNES PAR DATE DEPART 
        jDateChooser1.addPropertyChangeListener(
                new java.beans.PropertyChangeListener() {
                    public void propertyChange(java.beans.PropertyChangeEvent evt) {
                        
                        DateLabelFormatter dlf = new DateLabelFormatter();
                        String date=null;
                        try{ 
                            date = dlf.valueToString(jDateChooser1.getDate());  
                            sorter.setRowFilter(javax.swing.RowFilter.regexFilter(date, 3));
                        }catch(Exception exp){} 
                    }
                }
        );
        
        // FILTRER LES LIGNES PAR DATE RETOUR 
        jDateChooser2.addPropertyChangeListener(
                new java.beans.PropertyChangeListener() {
                    public void propertyChange(java.beans.PropertyChangeEvent evt) { 
                        DateLabelFormatter dlf = new DateLabelFormatter();
                        String date=null;
                        try{ 
                            date = dlf.valueToString(jDateChooser2.getDate());  
                            sorter.setRowFilter(javax.swing.RowFilter.regexFilter(date, 4));
                        }catch(Exception exp){} 
                    }
                }
        );
        
        // FILTRER LES LIGNES PAR LIEU CIBLE
        jComboBox3.addActionListener(
            new ActionListener() { 
                @Override public void actionPerformed(ActionEvent e) {  
                    if(jComboBox3.getSelectedItem()!=null)
                    sorter.setRowFilter(javax.swing.RowFilter.regexFilter(jComboBox3.getSelectedItem().toString(), 2));
                }
            }
        ); 
        
        // FILTRER LES LIGNES PAR LIEU CIBLE
        jComboBox1.addActionListener(
            new ActionListener() { 
                @Override public void actionPerformed(ActionEvent e) {
                    if(jComboBox1.getSelectedItem()!=null)
                    sorter.setRowFilter(javax.swing.RowFilter.regexFilter(jComboBox1.getSelectedItem().toString(), 1));
                }
            }
        ); 
         
    }

    public void chargerData()
    {
        // charger les donnees(lignes)
        while(model.getRowCount()>0) model.removeRow(0);
        DateLabelFormatter dlf = new DateLabelFormatter();
        int nbr = 0; 
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.getInstance().get(Calendar.YEAR) , 
                Calendar.getInstance().get(Calendar.MONTH), 
                Calendar.getInstance().get(Calendar.DATE)
        );
        date.setTime(calendar.getTimeInMillis());  
        
        for(Map.Entry<Integer,OrdreMission> entry : bdd.ordreMissions.entrySet())
        {
                try{
                    nbr = -daysBetween( 
                            (Date)dlf.stringToValue(entry.getValue().getDateRetour()),
                            date
                    );
                    nbr++;
                }catch(Exception e){nbr = 0;}
                if(nbr<0) nbr = 0;
            
                Object[] row = { entry.getValue().getId(),
                    entry.getValue().getEmploye().getPrenom()+" "+
                    entry.getValue().getEmploye().getNom(),
                    entry.getValue().getLieuCible().getNom(),
                    entry.getValue().getDateDepart(),
                    entry.getValue().getDateRetour(),
                    nbr,
                    entry.getValue().getObjet(),
                    entry.getValue().getTransport().getMode(),
                    entry.getValue().getLieuAuDepart().getNom()
                };
                model.addRow(row);  
        }
        
        // charge de valeurs dans les filters
        jComboBox3.removeAllItems();  
        jComboBox3.addItem( "" );
        for(Map.Entry<Integer,Lieu> entry : bdd.lieux.entrySet())
            jComboBox3.addItem( entry.getValue() );
        
        jComboBox1.removeAllItems();
        jComboBox1.addItem( "" );
        for(Map.Entry<Integer,Employe> entry : bdd.employes.entrySet())
            jComboBox1.addItem( entry.getValue() );
    }
    static public int daysBetween(Date d1, Date d2){
        return (int)((d2.getTime()-d1.getTime())/(1000*60*60*24)); 
    }
    
    
    
    
    
    
    
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 =  new javax.swing.JPanel() {  
            java.awt.Image img = java.awt.Toolkit.getDefaultToolkit().getImage(VueMain.class.getResource("/Images/logoMaroc.png")).getScaledInstance(155, 150, java.awt.Image.SCALE_SMOOTH);
            public void paintComponent(java.awt.Graphics g) 
            { 
                g.drawImage(img, 0, 0, this);  
            }  
        };  ;
        jPanel3 =  new javax.swing.JPanel() {  
            java.awt.Image img = java.awt.Toolkit.getDefaultToolkit().getImage(VueMain.class.getResource("/Images/logoTaroudant.png"));  

            public void paintComponent(java.awt.Graphics g) 
            { 
                g.drawImage(img, 0, 0, 100, 150, this);  
            }  
        };  ;
        jLabel1 = new javax.swing.JLabel(
            "<html><center style=\"font-size:20;"
            + "font-weight:bold\">"
            +"ROYAUME DU MAROC<br>"
            + "MINISTERE DE L'LINTERIEUR<br>"
            + "PROVINCE DE TAROUDANNT<br>"
            + "SECRETARIAT GENERAL<br>"
            + "SERVICE INFORMATIQUE<br>"+"</center></html>"
        );
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jPanel6 = new javax.swing.JPanel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jPanel7 = new javax.swing.JPanel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jPanel4.setBackground(new java.awt.Color(204, 204, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Gestion des Ordres de Mission", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        jTable1.setModel(
            new DefaultTableModel()
            {
                public boolean isCellEditable(int row, int column)
                {
                    return column==9;
                }
            }

        );
        jTable1.setGridColor(new java.awt.Color(204, 204, 255));
        jTable1.setIntercellSpacing(new java.awt.Dimension(4, 2));
        jTable1.setRowHeight(48);
        jTable1.setSelectionBackground(new java.awt.Color(204, 204, 255));
        jTable1.setSelectionForeground(new java.awt.Color(0, 0, 255));
        jScrollPane1.setViewportView(jTable1);

        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(153, 153, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("PAR DATE DE DEPART"));

        jDateChooser1.setFocusable(false);
        jDateChooser1.setRequestFocusEnabled(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel6.setBackground(new java.awt.Color(153, 153, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("PAR DATE DE RETOUR"));

        jDateChooser2.setFocusable(false);
        jDateChooser2.setRequestFocusEnabled(false);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel7.setBackground(new java.awt.Color(153, 153, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("PAR LIEU CIBLE"));

        jComboBox3.setEditable(true);

        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jComboBox3, 0, 141, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel8.setBackground(new java.awt.Color(153, 153, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("EMPLOYE"));
        jPanel8.setPreferredSize(new java.awt.Dimension(194, 54));

        jComboBox1.setEditable(true);

        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton8.setText("Administrateurs");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 909, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(48, 48, 48)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new VueOrdreMission(bdd, model).setVisible(true); 
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        sorter.setRowFilter(null);
        bdd.chargerData();
        chargerData();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        VueConsultation v = new VueConsultation(bdd,VueMain.LIEU);
        v.addComboboxToFill(jComboBox3);
        v.setVisible(true); 
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        VueConsultation v = new VueConsultation(bdd,VueMain.EMPLOYE);
        v.addComboboxToFill(jComboBox1);
        v.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        Excel.enregistrerDevis(jTable1,"Ordres_Mission");
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        new VueAddAdmin(bdd).setVisible(true);
    }//GEN-LAST:event_jButton8ActionPerformed
 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton8;
    private javax.swing.JComboBox<Object> jComboBox1;
    private javax.swing.JComboBox<Object> jComboBox3;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
