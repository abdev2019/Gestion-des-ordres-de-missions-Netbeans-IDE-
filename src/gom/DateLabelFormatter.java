

package gom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JFormattedTextField.AbstractFormatter;


public class DateLabelFormatter extends AbstractFormatter {

    private String datePattern = "dd/MM/yyyy";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            java.text.DateFormat df = new java.text.SimpleDateFormat("dd/MM/yyyy");         
            return df.format((java.util.Date)value);  
        }

        return "";
    }
}
