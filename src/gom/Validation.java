

package gom;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;




public class Validation 
{
    public static String validerPrenom(String prenom)
    {
        String res = prenom.substring(0, 1).toUpperCase() + prenom.substring(1).toLowerCase();
        return res;
    } 
}


  


class CharFilter extends javax.swing.text.DocumentFilter 
{ 
    char last='_';
    
    int field=0;
    
    public CharFilter(){
    }
    
    public CharFilter(int f){
        field = f;
    }
    
    @Override
    public void replace(FilterBypass fb, int i, int i1, String string, AttributeSet as) throws BadLocationException {
        
        if(field == 1 || field==3)
            string = string.toUpperCase();
        else if ((field == 2) && fb.getDocument().getLength() == 0) 
        {
            StringBuilder sb = new StringBuilder(string);
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            string = sb.toString();
        } 
        
        if(string.length()>0)
        {
            if(last == ' ' && string.charAt(string.length() - 1)==' ') return;
            last = string.charAt(string.length() - 1);
        } 
        
        for (int n = string.length(); n > 0; n--) {
            char c = string.charAt(n - 1); 
            if (Character.isAlphabetic(c) || c == ' ' || ( field==3 && ( "-,.".contains(c+"") ) ) 
            ) 
            { 
                super.replace(fb, i, i1, String.valueOf(c), as);
            }  
        }
    }

    @Override
    public void remove(FilterBypass fb, int i, int i1) throws BadLocationException {
        super.remove(fb, i, i1);
    }

    @Override
    public void insertString(FilterBypass fb, int i, String string, AttributeSet as) throws BadLocationException {
        super.insertString(fb, i, string.toUpperCase(), as); 
    }
}
