

package gom;

 

public class VueNouveau extends javax.swing.JFrame 
{ 
    Bdd bdd; 
    java.util.ArrayList<javax.swing.JComboBox> jComboBoxs; 
    int table;
    Object obj;
    javax.swing.JTable jTable;
     
      
    public VueNouveau(Bdd bd, int table) {
        initComponents();
        setLocationRelativeTo(null);
        try {
            java.awt.Image img = java.awt.Toolkit.getDefaultToolkit()
            .getImage(VueMain.class.getResource("/Images/logoMaroc.png")); 
             this.setIconImage(img);
        } catch (Exception ex) {
          System.out.println("problem "+ex);
        } 
        bdd         = bd; 
        this.table  = table;
        jTable       = null;
        jComboBoxs  = null; 
        obj         = null;
        detectObj();
        
        // filter
        ((javax.swing.text.AbstractDocument) jTextField1.getDocument()).setDocumentFilter(
                new CharFilter(3)
        );
    }  
    
    
    public void setComboboxs(java.util.ArrayList<javax.swing.JComboBox> c){
        jComboBoxs = c;
    }
    
    public void addComboboxToFill(javax.swing.JComboBox c){
        if(jComboBoxs != null)
            jComboBoxs.add(c); 
        else {
            jComboBoxs = new java.util.ArrayList<>();
            jComboBoxs.add(c); 
        }
    }
    public void setJTable(javax.swing.JTable m){
        jTable = m;
    }
    public void setObject(Object o){
        obj = o;
        detectObj2();
    }
    
    
    private void detectObj(){
        if(table==VueMain.LIEU){
            setTitre("Nouveau Lieu", 1);
            setTitre("Nom du lieu", 2);
        }
        
        else if(table==VueMain.TRANSPORT){
            setTitre("Nouveau Transport", 1);
            setTitre("Mode Transport", 2);
        }
        
        else if(table==VueMain.SERVICE){
            setTitre("Nouveau Service", 1);
            setTitre("Nom de service", 2);
        }
    }
    private void detectObj2(){
        setTitre("Modification", 1); 
        if(table==VueMain.LIEU){ 
            setTitre("Nom du lieu", 2);
            jTextField1.setText( ((Lieu)obj).getNom() );
        }
        
        else if(table==VueMain.TRANSPORT){ 
            setTitre("Mode Transport", 2);
            jTextField1.setText( ((Transport)obj).getMode());
        }
        
        else if(table==VueMain.SERVICE){ 
            setTitre("Nom de service", 2);
            jTextField1.setText( ((Service)obj).getName());
        }
    }
 
    
    
    public void setTitre(String t, int panel){
        javax.swing.border.Border loweredetched = javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED); 
        javax.swing.border.TitledBorder title;
        title = javax.swing.BorderFactory.createTitledBorder(
                           loweredetched, t);
        title.setTitleJustification(
                ((panel==1)?javax.swing.border.TitledBorder.CENTER:javax.swing.border.TitledBorder.LEFT)
        );
        ((panel==1)?jPanel1:jPanel2).setBorder(title); 
        if(panel==1) setTitle(t);
    }
     
    private void nouveauLieu(){
        int id = bdd.insertLieu(  jTextField1.getText()  );
        bdd.lieux = bdd.getLieux(); 
        
        if(jComboBoxs!=null){
            for(javax.swing.JComboBox jc : jComboBoxs)
                jc.addItem( bdd.lieux.get(id) ); 
        }
        
        if(jTable!=null){
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel)jTable.getModel();
            Object rowData[] = { id,jTextField1.getText()  };
            model.addRow(rowData);
        }
    } 
    private void nouveauTransport(){
        Transport tr = new Transport( jTextField1.getText() );
        int id = bdd.insertTransport(tr); 
        bdd.transports = bdd.getTransports();
        
        if(jComboBoxs!=null){
            for(javax.swing.JComboBox jc : jComboBoxs)
                jc.addItem( bdd.transports.get(id) ); 
        }
        
        if(jTable!=null){
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel)jTable.getModel();
            Object rowData[] = { id,jTextField1.getText()  };
            model.addRow(rowData);
        }
    } 
    private void nouveauService(){
        Service srv = new Service( jTextField1.getText() ); 
        int id = bdd.insertService(srv);
        bdd.services = bdd.getServices();
        
        if(jComboBoxs!=null){
            for(javax.swing.JComboBox jc : jComboBoxs)
                jc.addItem( bdd.services.get(id) ); 
        }
        
        if(jTable!=null){
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel)jTable.getModel();
            Object rowData[] = { id,jTextField1.getText()  };
            model.addRow(rowData);
        }
    }
    
    private void modifier(){
        int id=-1;
        if(table==VueMain.LIEU)
        {
            Lieu l = (Lieu)obj;
            l.setNom(jTextField1.getText());
            bdd.modifierLieu(l);
            id = l.getId();
            if(jComboBoxs!=null){ 
                for(javax.swing.JComboBox jc : jComboBoxs){
                    jc.removeAllItems();
                    for(java.util.Map.Entry<Integer,Lieu> entry : bdd.lieux.entrySet())
                        jc.addItem( entry.getValue() ); 
                }
            }
        }
        
        else if(table==VueMain.TRANSPORT)
        {
            Transport tr = (Transport)obj;
            tr.setMode(jTextField1.getText());
            bdd.modifierTransport(tr);
            id = tr.getId();
            if(jComboBoxs!=null){ 
                for(javax.swing.JComboBox jc : jComboBoxs){
                    jc.removeAllItems();
                    for(java.util.Map.Entry<Integer,Transport> entry : bdd.transports.entrySet())
                        jc.addItem( entry.getValue() ); 
                }
            }
        }
        
        else if(table==VueMain.SERVICE)
        {
            Service s = (Service)obj;
            s.modifierService(jTextField1.getText());
            bdd.modifierService(s);
            id = s.getId();
            if(jComboBoxs!=null){ 
                for(javax.swing.JComboBox jc : jComboBoxs){
                    jc.removeAllItems();
                    for(java.util.Map.Entry<Integer,Service> entry : bdd.services.entrySet())
                        jc.addItem( entry.getValue() ); 
                }
            }
        } 
        
        if(jTable!=null){  
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel)jTable.getModel();
            model.setValueAt(jTextField1.getText(), jTable.getSelectedRow(), 1);
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(188, 188, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(385, 224));

        jPanel2.setBackground(new java.awt.Color(188, 188, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Nom", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButton1.setText("Annuler");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Enregistrer");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // 1 : lieu  2 : transport  3 : service
        int dialogResult = javax.swing.JOptionPane.showConfirmDialog (this, "Confirmer ?","Confiramtion",javax.swing.JOptionPane.YES_NO_OPTION);
        if(dialogResult == javax.swing.JOptionPane.YES_OPTION)
        {
            if(obj==null){
                if(table==VueMain.LIEU) nouveauLieu();
                else if(table==VueMain.TRANSPORT) nouveauTransport();
                else if(table==VueMain.SERVICE) nouveauService();
            }
            else modifier(); 
            
            dispose();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed
 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
