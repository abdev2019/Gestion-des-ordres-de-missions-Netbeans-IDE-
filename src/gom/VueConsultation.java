

package gom;

import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;



public class VueConsultation extends javax.swing.JFrame {

 
    Bdd bdd;
    int table;
    
    java.util.ArrayList<javax.swing.JComboBox> jcomboboxs;
    
    
    public VueConsultation( Bdd bd ,int table ) 
    {
        initComponents();
        setLocationRelativeTo(null);
        try {
            java.awt.Image img = java.awt.Toolkit.getDefaultToolkit()
            .getImage(VueMain.class.getResource("/Images/logoMaroc.png")); 
             this.setIconImage(img);
        } catch (Exception ex) {
          System.out.println("problem "+ex);
        } 
        bdd = bd;
        this.table = table;
        jcomboboxs = null;
         
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel(); 
        
        String columns[]={};
        String title="";
        
        if(table==VueMain.SERVICE || table==VueMain.LIEU)
        {
            String tmp[] = { "ID", "NOM" };
            columns = tmp;
        }
        else if(table==VueMain.EMPLOYE){
            String tmp1[] = { "ID", "NOM", "PRENOM", "AGE", "ADRESSE", "FONCTION","GRADE", "SERVICE" };
            columns = tmp1;
            title = "Employes"; 
        }
        else if(table==VueMain.TRANSPORT){
           String tmp3[] = { "ID", "MODE" };
           columns = tmp3;
           title = "Transports";  
        }   
        
        
        for(int i=0;i<columns.length;i++)
            model.addColumn( columns[i] );
        model.addColumn("ACTIONS");
        jTable1.getColumn("ACTIONS").setMinWidth(95);
        
        TableColumn column = jTable1.getColumn("ACTIONS");
        String imgs[] = { "edit.png","del.png"  };
        column.setCellRenderer(new ButtonsRenderer(2,imgs));
        column.setCellEditor(new ButtonsEditor(jTable1, bdd,2,imgs, table,jcomboboxs ));
        
        
        if(table==VueMain.EMPLOYE)
        for(Map.Entry<Integer,Employe> entry : bdd.employes.entrySet())
        {
                Object[] row = { entry.getValue().getId(), 
                    entry.getValue().getNom(),
                    entry.getValue().getPrenom(),
                    entry.getValue().getAge(),
                    entry.getValue().getAdresse(),
                    entry.getValue().getFonction(),
                    entry.getValue().getGrade(),
                    entry.getValue().getService().getName()
                };
                model.addRow(row); 
        }
        
        else if(table==VueMain.SERVICE){
        for(Map.Entry<Integer,Service> entry : bdd.services.entrySet())
        {
                Object[] row = { 
                    entry.getValue().getId(), 
                    entry.getValue().getName()
                };
                model.addRow(row); 
        }
        title = "Services";
        }
        
        else if(table==VueMain.TRANSPORT)
        for(Map.Entry<Integer,Transport> entry : bdd.transports.entrySet())
        {
                Object[] row = { 
                    entry.getValue().getId(), 
                    entry.getValue().getMode()
                };
                model.addRow(row); 
        }
        
        else if(table==VueMain.LIEU){
        for(Map.Entry<Integer,Lieu> entry : bdd.lieux.entrySet())
        {
                Object[] row = { 
                    entry.getValue().getId(), 
                    entry.getValue().getNom()
                };
                model.addRow(row); 
        }
        title = "Lieux";
        } 
        
        
        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED); 
        TitledBorder tit;
        tit = BorderFactory.createTitledBorder(
                           loweredetched, ""+title);
        tit.setTitleJustification(TitledBorder.CENTER);
        jPanel2.setBorder(tit); 
        
        
        try {
            java.awt.Image img = java.awt.Toolkit.getDefaultToolkit()
            .getImage(VueMain.class.getResource("/Images/ttc2.png"));
            jButton2.setIcon(new javax.swing.ImageIcon(img));
        } catch (Exception ex) {
          System.out.println("problem "+ex);
        }  
        
    }
    
    public void addComboboxToFill(javax.swing.JComboBox c){
        if(jcomboboxs != null)
            jcomboboxs.add(c); 
        else {
            jcomboboxs = new java.util.ArrayList<>();
            jcomboboxs.add(c); 
        }
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);

        jPanel1.setBackground(new java.awt.Color(188, 188, 255));

        jPanel2.setBackground(new java.awt.Color(188, 188, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Employes"));
        jPanel2.setMinimumSize(new java.awt.Dimension(613, 0));

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(new DefaultTableModel()
            {
                public boolean isCellEditable(int row, int column)
                {
                    return column==jTable1.getColumnCount()-1;
                }
            }
        );
        jTable1.setRowHeight(48);
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Fermer");
        jButton1.setPreferredSize(new java.awt.Dimension(67, 38));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setPreferredSize(new java.awt.Dimension(38, 38));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(table == VueMain.EMPLOYE){
            VueEmploye ve = new VueEmploye(bdd,jTable1); 
            if(jcomboboxs!=null) ve.setComboBox(jcomboboxs.get(0));
            ve.setVisible(true);
        }
        else {
            VueNouveau v = new VueNouveau(bdd,table);
            v.setJTable(jTable1);
            v.setComboboxs(jcomboboxs);
            v.setVisible(true);
        } 
    }//GEN-LAST:event_jButton2ActionPerformed

 
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
