
package gom;
 


import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import jxl.CellView;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
 


public class Excel 
{
    public static void enregistrerDevis(JTable modelTab, String nomFichier)
    { 

        String chemin;

        JFileChooser chooser = new JFileChooser("JFileChooser");
        chooser.setApproveButtonText("Enregistrer ici");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
        {
            chemin = chooser.getSelectedFile().getAbsolutePath();

            try 
            { 
                WritableWorkbook workBook = Workbook.createWorkbook(new File(chemin + "\\" + nomFichier + ".xls"));

                WritableSheet sheet = workBook.createSheet("Feuille 1", 0);

                Label label;
                WritableCellFormat cellFormat;

                cellFormat = new WritableCellFormat();
                cellFormat.setBackground(Colour.GREY_25_PERCENT);
                cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

                for(int col = 0 ; col < modelTab.getColumnCount()-1; col++)
                {
                    label = new Label(col, 0, (String) modelTab.getColumnName(col), cellFormat);
                    sheet.addCell(label);
                }

                WritableCellFormat cellFormat2 = new WritableCellFormat();
                cellFormat2.setBackground(Colour.WHITE);
                cellFormat2.setBorder(Border.ALL, BorderLineStyle.THIN);
                cellFormat2.setAlignment(Alignment.CENTRE);

                int dernLig = 0, k=1;

                for(int lig = 0 ; lig < modelTab.getRowCount(); lig++)
                { 
                    for(int col = 0 ; col < modelTab.getColumnCount()-1 ; col++)
                    {  
                        label = new Label(col, k,  modelTab.getValueAt(lig, col)+"", cellFormat2); 
                        sheet.addCell(label);
                    }
                    
                    for(int col = 0 ; col < modelTab.getColumnCount()-1 ; col++)
                    { 
                        label = new Label(col, k+1,  "", cellFormat2); 
                        sheet.addCell(label);
                        sheet.mergeCells(col, k, col, k+1);
                    }
                    k+=2;
                   
                    dernLig = lig+1;
                }

                CellView cell = new CellView();

                for(int col = 1 ; col < sheet.getColumns() ; col++)
                {
                    cell = sheet.getColumnView(col);
                    cell.setAutosize(true); 
                    sheet.setColumnView(col, cell);
                }

                workBook.write();
                // On ferme le fichier Excel
                workBook.close();

                if ( Desktop.isDesktopSupported() ) {
                    Desktop desktop = Desktop.getDesktop();
                    if (desktop.isSupported(Desktop.Action.OPEN)) {
                        try {
                            desktop.open(new File(chemin + "\\" + nomFichier + ".xls"));
                        } 
                        catch (IOException e) { }
                    }
                } 
            } 
            catch (IOException e) {} 
            catch (WriteException e) {}
        }
    }

}

