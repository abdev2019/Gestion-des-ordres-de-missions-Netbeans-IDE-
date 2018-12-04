package gom;
   
import java.util.Calendar;
import java.util.Date;
import java.util.Map.Entry; 
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;




public class VueOrdreMission extends javax.swing.JFrame 
{
    Bdd bdd;
    VueEmploye nve;
    DefaultTableModel model;
    
    boolean mod=false;
    OrdreMission ord=null;
    
     public VueOrdreMission(Bdd bd, TableModel m, OrdreMission o) 
     { 
        initComponents();  
        this.pack(); 
        setLocationRelativeTo(null); 
        bdd = bd;
        model = (DefaultTableModel)m;
        
        init();
        
        int i=0;
        
        for(Entry<Integer,Employe> entry : bdd.employes.entrySet()){    
            jComboBox1.addItem( entry.getValue() );
            if(entry.getValue().getId() == o.getEmploye().getId()) 
                jComboBox1.setSelectedIndex( i );
            i++; 
        }  
        
        i=0;
        for(Entry<Integer,Transport> entry : bdd.transports.entrySet()){    
            jComboBox2.addItem( entry.getValue() );
            if(entry.getValue().getId() == o.getTransport().getId()) 
                jComboBox2.setSelectedIndex( i );
            i++; 
        }  
        
         i=0; int j=0;
        for(Entry<Integer,Lieu> entry : bdd.lieux.entrySet())
        {    
            jComboBox4.addItem( entry.getValue() );
            jComboBox3.addItem( entry.getValue() );
            
            if(entry.getValue().getId() == o.getLieuCible().getId()) 
                jComboBox4.setSelectedIndex( i );
            i++;
            
            if(entry.getValue().getId() == o.getLieuAuDepart().getId()) 
                jComboBox3.setSelectedIndex( j );
            j++;
        }   
        
        jTextPane1.setText( o.getObjet() );
        
        DateLabelFormatter dlf = new DateLabelFormatter();
        Date date=null;
        try{
            date = (Date)dlf.stringToValue(o.getDateDepart());
        }catch(Exception exp){}
        
        jDateChooser1.setDate( date );
        
        try{
            date = (Date)dlf.stringToValue(o.getDateRetour());
        }catch(Exception exp){date=null;}
        jDateChooser2.setDate( date );
        
        
        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED); 
        TitledBorder title;
        title = BorderFactory.createTitledBorder(
                           loweredetched, "Modification");
        title.setTitleJustification(TitledBorder.CENTER);
        jPanel7.setBorder(title); 
        
        mod = true;
        ord = o; 
    }
    
      
    public VueOrdreMission(Bdd bd, DefaultTableModel m) { 
        initComponents();  
        this.pack(); 
        setLocationRelativeTo(null);
        bdd = bd;
        model = m;
        
        
        for(Entry<Integer,Employe> entry : bdd.employes.entrySet()){    
            jComboBox1.addItem( entry.getValue() ); 
        }   
        
        
        for(Entry<Integer,Transport> entry : bdd.transports.entrySet()){    
            jComboBox2.addItem( entry.getValue() );
        }  
        
        for(Entry<Integer,Lieu> entry : bdd.lieux.entrySet()){    
            jComboBox4.addItem( entry.getValue() );
            jComboBox3.addItem( entry.getValue() );
        }  
        
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.getInstance().get(Calendar.YEAR) , 
                Calendar.getInstance().get(Calendar.MONTH), 
                Calendar.getInstance().get(Calendar.DATE)
        );
        date.setTime(calendar.getTimeInMillis());
        jDateChooser1.setDate(date);
        jDateChooser2.setDate(date); 
        
        init();
    }
    
    public void init(){ 
        this.getContentPane().setBackground(new java.awt.Color(188,188,255));
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH); 
        try {
            java.awt.Image img = java.awt.Toolkit.getDefaultToolkit()
            .getImage(VueMain.class.getResource("/Images/logoMaroc.png")); 
             this.setIconImage(img);
        } catch (Exception ex) {
          System.out.println("problem "+ex);
        } 
        
        try {
            java.awt.Image img = java.awt.Toolkit.getDefaultToolkit()
            .getImage(VueMain.class.getResource("/Images/addTr3.png"))
                    .getScaledInstance(38, 38, java.awt.Image.SCALE_SMOOTH); 
            jButton5.setIcon(new javax.swing.ImageIcon(img)); 
        } 
        catch (Exception ex) {
          System.out.println(ex);
        } 
        
        try {
            java.awt.Image img = java.awt.Toolkit.getDefaultToolkit()
            .getImage(VueMain.class.getResource("/Images/addEmp.png"))
                    .getScaledInstance(34, 36, java.awt.Image.SCALE_SMOOTH);
            jButton3.setIcon(new javax.swing.ImageIcon(img)); 
        } 
        catch (Exception ex) {
          System.out.println(ex);
        } 
        
        try {
            java.awt.Image img = java.awt.Toolkit.getDefaultToolkit()
            .getImage(VueMain.class.getResource("/Images/addLieu3.png"))
                    .getScaledInstance(34, 30, java.awt.Image.SCALE_SMOOTH);
            jButton4.setIcon(new javax.swing.ImageIcon(img)); 
        } 
        catch (Exception ex) {
          System.out.println(ex);
        } 
        
        try {
            java.awt.Image img = java.awt.Toolkit.getDefaultToolkit()
            .getImage(VueMain.class.getResource("/Images/editing2.png"))
                          .getScaledInstance(38, 38, java.awt.Image.SCALE_SMOOTH);
            jButton7.setIcon(new javax.swing.ImageIcon(img));
            jButton8.setIcon(new javax.swing.ImageIcon(img));
            jButton9.setIcon(new javax.swing.ImageIcon(img));
        } catch (Exception ex) {
          System.out.println("problem "+ex);
        } 
         
         
        
        ((javax.swing.text.JTextComponent) jComboBox1.getEditor().getEditorComponent())
                .setDocument(new Suggestion(jComboBox1)); 
        ((javax.swing.text.JTextComponent) jComboBox2.getEditor().getEditorComponent())
                .setDocument(new Suggestion(jComboBox2)); 
        ((javax.swing.text.JTextComponent) jComboBox3.getEditor().getEditorComponent())
                .setDocument(new Suggestion(jComboBox3));
        ((javax.swing.text.JTextComponent) jComboBox4.getEditor().getEditorComponent())
                .setDocument(new Suggestion(jComboBox4)); 
         
    }
     
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel7 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jPanel3 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jPanel4 = new javax.swing.JPanel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jPanel8 = new javax.swing.JPanel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton5 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Nouveau Ordre");
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(0, 204, 204));
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel7.setBackground(new java.awt.Color(188, 188, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nouveau Ordre", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Trebuchet MS", 1, 18))); // NOI18N
        jPanel7.setMinimumSize(new java.awt.Dimension(500, 450));
        jPanel7.setLayout(new java.awt.GridBagLayout());

        jPanel5.setBackground(new java.awt.Color(188, 188, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Objet", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPanel5.setToolTipText("Objet");

        jTextPane1.setBorder(null);
        jTextPane1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jScrollPane1.setViewportView(jTextPane1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.ipadx = 276;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(54, 60, 0, 16);
        jPanel7.add(jPanel5, gridBagConstraints);

        jPanel3.setBackground(new java.awt.Color(188, 188, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Employé", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPanel3.setMinimumSize(new java.awt.Dimension(327, 0));

        jComboBox1.setEditable(true);
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton3.setPreferredSize(new java.awt.Dimension(38, 38));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 81;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(54, 16, 0, 0);
        jPanel7.add(jPanel3, gridBagConstraints);

        jPanel6.setBackground(new java.awt.Color(188, 188, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "De se rendre à", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jComboBox4.setEditable(true);
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 16, 0, 0);
        jPanel7.add(jPanel6, gridBagConstraints);

        jPanel1.setBackground(new java.awt.Color(188, 188, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Date de Depart", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jDateChooser1.setDate(new Date());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipady = 16;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 16, 0, 0);
        jPanel7.add(jPanel1, gridBagConstraints);

        jPanel4.setBackground(new java.awt.Color(188, 188, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Date de retour", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jDateChooser2.setDate(jDateChooser1.getDate());

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jDateChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.ipady = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 16, 0, 0);
        jPanel7.add(jPanel4, gridBagConstraints);

        jButton1.setText("Enregistrer");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.ipadx = 12;
        gridBagConstraints.ipady = 21;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(27, 166, 44, 0);
        jPanel7.add(jButton1, gridBagConstraints);

        jButton2.setText("Annuler");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.ipady = 21;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(27, 18, 44, 0);
        jPanel7.add(jButton2, gridBagConstraints);

        jPanel2.setBackground(new java.awt.Color(188, 188, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Lieu de depart", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jComboBox3.setEditable(true);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridheight = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 16, 44, 0);
        jPanel7.add(jPanel2, gridBagConstraints);

        jPanel8.setBackground(new java.awt.Color(188, 188, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Mode de transport", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jComboBox2.setEditable(true);
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jButton5.setPreferredSize(new java.awt.Dimension(38, 38));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 60, 0, 16);
        jPanel7.add(jPanel8, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 248;
        gridBagConstraints.ipady = 119;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 18, 0);
        getContentPane().add(jPanel7, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        VueNouveau v = new VueNouveau(bdd,VueMain.TRANSPORT);
        v.addComboboxToFill(jComboBox2);
        v.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    
    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String obj = jTextPane1.getText();

        Employe emp=null;
        Lieu lieuCible=null;
        Transport trsp=null;
        

        if(jComboBox1.getSelectedIndex()>=0)
            emp  = (Employe)jComboBox1.getSelectedItem();

        if(jComboBox4.getSelectedIndex()>=0)
            lieuCible = (Lieu)jComboBox4.getSelectedItem();

        if(jComboBox2.getSelectedIndex()>=0)
            trsp = (Transport)jComboBox2.getSelectedItem();

 
        Lieu vuAuDepart = (Lieu)jComboBox3.getSelectedItem();

        
        if(!mod) ord = new OrdreMission();
        
        ord.setEmploye(emp);
        ord.setLieuCible(lieuCible);
        ord.setObjet(obj);
        
        DateLabelFormatter dlf = new DateLabelFormatter();
        String dateDepart=null, dateRetour=null; 
        try{
            dateDepart = (String)dlf.valueToString( jDateChooser1.getDate() );
        }catch(Exception exp){} 
        
        try{
            dateRetour = (String)dlf.valueToString( jDateChooser2.getDate() );
        }catch(Exception exp){}  
        ord.setDateDepart( dateDepart );
        ord.setDateRetour( dateRetour );
        ord.setTransport(trsp);
        ord.setLieuAuDepart(vuAuDepart);

        int dialogResult = javax.swing.JOptionPane.showConfirmDialog (this, "Confirmer ?","Confiramtion",javax.swing.JOptionPane.YES_NO_OPTION);
        if(dialogResult == javax.swing.JOptionPane.YES_OPTION)
        {
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();

            calendar.set(Calendar.getInstance().get(Calendar.YEAR) , 
                    Calendar.getInstance().get(Calendar.MONTH), 
                    Calendar.getInstance().get(Calendar.DATE)
            );
            date.setTime(calendar.getTimeInMillis());   
            int nbr=0;
            nbr = -VueMain.daysBetween( 
                jDateChooser2.getDate(),
                date
            ); 
            nbr++;
            if(nbr<0) nbr = 0;
            
                
            if(!mod)
            {
                bdd.insertOrdreMission(ord);

                bdd.ordreMissions = bdd.getOrdresMission();

                ord = (OrdreMission)bdd.ordreMissions.values().toArray()[bdd.ordreMissions.values().size()-1];
                  
                Object[] row = { 
                            ord.getId(),
                            ord.getEmploye().getPrenom()+" "+
                            ord.getEmploye().getNom(),
                            ord.getLieuCible().getNom(),
                            ord.getDateDepart(),
                            ord.getDateRetour(),
                            nbr,
                            ord.getObjet(),
                            ord.getTransport().getMode(),
                            ord.getLieuAuDepart().getNom()
                        };
                model.addRow(row);
            }
            else{
                bdd.modifierOrdreDeMission(ord);
                int n=model.getRowCount();
                for(int i=0;i<n;i++)
                {
                    try{
                        int id = Integer.parseInt( model.getValueAt(i, 0).toString() );
                        if( id == ord.getId() )
                        {
                            model.setValueAt(ord.getEmploye().getPrenom()+" "+ord.getEmploye().getNom(), i, 1);
                            model.setValueAt(ord.getLieuCible().getNom(), i, 2);
                            model.setValueAt(ord.getDateDepart(), i, 3);
                            model.setValueAt(ord.getDateRetour(), i, 4); 
                            model.setValueAt(nbr, i, 5);
                            model.setValueAt(ord.getObjet(), i, 6);
                            model.setValueAt(ord.getTransport().getMode(), i, 7);
                            model.setValueAt(ord.getLieuAuDepart().getNom(), i, 8);

                            break;
                        }
                    }catch(Exception exp){}
                }
            } 
            dispose();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        nve = new VueEmploye(bdd, jComboBox1);
        nve.setFocusable(true);
        nve.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed

    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        VueNouveau v = new VueNouveau(bdd, VueMain.LIEU );  
        v.addComboboxToFill(jComboBox3);
        v.addComboboxToFill(jComboBox4);
        v.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        VueConsultation v = new VueConsultation(bdd,VueMain.EMPLOYE);
        v.addComboboxToFill(jComboBox1);
        v.setVisible(true);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        VueConsultation v = new VueConsultation(bdd,VueMain.LIEU);
        v.addComboboxToFill(jComboBox4);
        v.addComboboxToFill(jComboBox3);
        v.setVisible(true);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        VueConsultation v = new VueConsultation(bdd,VueMain.TRANSPORT);
        v.addComboboxToFill(jComboBox2);
        v.setVisible(true);
    }//GEN-LAST:event_jButton9ActionPerformed

    
    
    
    
    
     

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<Employe> jComboBox1;
    private javax.swing.JComboBox<Transport> jComboBox2;
    private javax.swing.JComboBox<Lieu> jComboBox3;
    private javax.swing.JComboBox<Lieu> jComboBox4;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
}
