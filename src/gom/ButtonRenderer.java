

package gom;


 
 
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;


 






 

class ButtonsPanel extends JPanel 
{
    public final List<JButton> buttons = new ArrayList<>();
    
    public ButtonsPanel(int n, String []imgs) 
    {
        super(new FlowLayout(FlowLayout.CENTER));
        setOpaque(true);

        Dimension d = new Dimension(36,36);

        JButton btns[] = new JButton[n];

        for(int i=0;i<n;i++){
          btns[i] = new JButton(""); 
          btns[i].setFocusable(false);
          btns[i].setRolloverEnabled(false); 
          btns[i].setPreferredSize(d);
          add(btns[i]);  

          if(i<imgs.length){
            try {
                java.awt.Image img = java.awt.Toolkit.getDefaultToolkit()
                .getImage(VueMain.class.getResource("/Images/"+imgs[i])); 
                img = img.getScaledInstance(35, 35, java.awt.Image.SCALE_SMOOTH);
                btns[i].setIcon(new javax.swing.ImageIcon(img));
            } 
            catch (Exception ex) { System.out.println("problem "+ex); } 
          }
          buttons.add(btns[i]);   
        }  
    }  
}






class ButtonsRenderer implements TableCellRenderer 
{
  private final ButtonsPanel panel ;
  
  public ButtonsRenderer(int n, String imgs[]){
        panel = new ButtonsPanel(n, imgs);
  }
  
  @Override public Component getTableCellRendererComponent(
      JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
  {
    panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
    return panel;
  }
}






class PrintAction extends AbstractAction 
{
    private final JTable table;
    TableModel model;
    Bdd bdd;
    
    public PrintAction(JTable table, Bdd bd) {
      //super("");
      this.table = table;
      model = table.getModel();
      bdd = bd;
    }
    
    @Override public void actionPerformed(ActionEvent e) {
      try{
          int id = Integer.parseInt( model.getValueAt(table.getSelectedRow(), 0).toString() ) ;
          bdd.ordreMissions.get(id).imprimerPDF();
      }catch(Exception exp){JOptionPane.showMessageDialog(table, "Impression echoue !" );}
      
    }
}







class EditAction extends AbstractAction 
{
    private final JTable table;
    TableModel model;
    Bdd bdd;
    int obj;
    java.util.ArrayList<javax.swing.JComboBox> jcomboboxs;
    
    public EditAction(JTable table, Bdd bd, int obj, java.util.ArrayList<javax.swing.JComboBox> c ) {
      super("");
      this.table = table;
      model = table.getModel();
      bdd = bd;
      this.obj = obj;
      jcomboboxs = c;
    }
    
    @Override public void actionPerformed(ActionEvent e) {
      try{
            int id = Integer.parseInt( model.getValueAt(table.getSelectedRow(), 0).toString() ) ; 
            
            if (obj == VueMain.ORDREMISSION){
                new VueOrdreMission(bdd, model,bdd.ordreMissions.get(id)).setVisible(true);
            }

            else if (obj == VueMain.EMPLOYE){
                VueEmploye ve = new VueEmploye(bdd, table, bdd.employes.get(id));
                if(jcomboboxs!=null) ve.setComboBox(jcomboboxs.get(0));
                ve.setVisible(true);
            }

            else
            {
                VueNouveau v = new VueNouveau(bdd, obj);
                
                if (obj == VueMain.SERVICE)         v.setObject(bdd.services.get(id));  
                else if (obj == VueMain.TRANSPORT)  v.setObject( bdd.transports.get(id) );  
                else if (obj == VueMain.LIEU)       v.setObject(bdd.lieux.get(id));  

                v.setJTable(table);
                v.setComboboxs(jcomboboxs);
                v.setVisible(true);
            }
            
      }
      catch(Exception exp){ }
      
    }
}








class DelAction extends AbstractAction 
{
    private final JTable table;
    TableModel model;
    Bdd bdd;
    int obj; 
    
    public DelAction(JTable table, Bdd bd, int obj) {
      super("");
      this.table = table;
      model = table.getModel();
      bdd = bd;
      this.obj = obj;
    }
    
    @Override public void actionPerformed(ActionEvent e) {
        
    int dialogResult = javax.swing.JOptionPane.showConfirmDialog (table, "Vous allez supprimer "+(
                (obj==VueMain.EMPLOYE)?"l'employe":(obj==VueMain.ORDREMISSION)?"l'ordre de mission":
                (obj==VueMain.SERVICE)?"la service":(obj==VueMain.TRANSPORT)?"le transport": "le lieu"
            )+" numero "+model.getValueAt(table.getSelectedRow(), 0)+".\nContinuer ?","Confiramtion",javax.swing.JOptionPane.YES_NO_OPTION);
    if(dialogResult == javax.swing.JOptionPane.YES_OPTION)
    {
        try{
            int id = Integer.parseInt( model.getValueAt(table.getSelectedRow(), 0).toString() ) ;  
            ((DefaultTableModel)model).removeRow( table.getSelectedRow() ); 

              if(obj==VueMain.ORDREMISSION)   {
                  bdd.supprimerOrdreDeMission(id);
                  bdd.ordreMissions.remove(id);
              } 
              else if(obj==VueMain.EMPLOYE)   {
                  bdd.supprimerEmploye(id);
                  bdd.employes.remove(id);
              }     
              else if(obj==VueMain.SERVICE)   {
                  bdd.supprimerService(id);
                  bdd.services.remove(id);
              }     
              else if(obj==VueMain.TRANSPORT) {
                  bdd.supprimerTransport(id);
                  bdd.transports.remove(id);
              }   
              else if(obj==VueMain.LIEU)      {
                  bdd.supprimerLieu(id);
                  bdd.lieux.remove(id);
              } 
        }
        catch(Exception exp){ }
    }
      
      
    }
}


 




class ButtonsEditor extends AbstractCellEditor implements TableCellEditor 
{
    private final ButtonsPanel panel;
    private final JTable table;
    private Object o;
    
    private class EditingStopHandler extends MouseAdapter implements ActionListener {
      @Override public void mousePressed(MouseEvent e) {
        Object o = e.getSource();
        if (o instanceof TableCellEditor) {
          actionPerformed(null);
        } else if (o instanceof JButton) {
          ButtonModel m = ((JButton) e.getComponent()).getModel();
          if (m.isPressed() && table.isRowSelected(table.getEditingRow()) && e.isControlDown()) {
            panel.setBackground(table.getBackground());
          }
        }
      }
      @Override public void actionPerformed(ActionEvent e) {
        EventQueue.invokeLater(new Runnable() {
          @Override public void run() {
            fireEditingStopped();
          }
        });
      }
    }

    public ButtonsEditor(JTable table, Bdd bdd, int n, String imgs[], int obj,
            java.util.ArrayList<javax.swing.JComboBox> jcomboboxs) {
      super();
      panel = new ButtonsPanel(n, imgs);
      this.table = table;
      
      if(n==3){
            panel.buttons.get(0).addActionListener(new PrintAction(table, bdd));
            panel.buttons.get(1).addActionListener(new EditAction(table, bdd,VueMain.ORDREMISSION,jcomboboxs));
            panel.buttons.get(2).addActionListener(new DelAction(table, bdd, VueMain.ORDREMISSION));
      }else{ 
            panel.buttons.get(0).addActionListener(new EditAction(table, bdd,obj,jcomboboxs));
            panel.buttons.get(1).addActionListener(new DelAction(table, bdd, obj)); 
      }
 
    }
    
    @Override public Component getTableCellEditorComponent(
        JTable table, Object value, boolean isSelected, int row, int column) {
        panel.setBackground(table.getSelectionBackground());
      o = value;
      return panel;
    }
    
    @Override public Object getCellEditorValue() {
      return o;
    }

}