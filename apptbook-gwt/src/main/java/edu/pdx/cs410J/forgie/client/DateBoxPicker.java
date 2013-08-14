package edu.pdx.cs410J.forgie.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Shawn
 * Date: 8/3/13
 *
 */
public class DateBoxPicker extends Composite {
    DateBox dateBox;
    public DateBoxPicker(final String ident)
    {


//        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MM/dd/yyyy h:mm a");
//        dateTimeFormat.setLenient(false);

        DateTimeFormat dateFormat = DateTimeFormat.getFormat("MM/dd/yyyy h:mm a");
        dateBox = new DateBox();
        dateBox.setFormat(new DateBox.DefaultFormat(dateFormat));
        dateBox.setWidth("1.75in");
        dateBox.setTitle("Select the date from the drop-down datebox and enter the desired " + ident + " time.");

        initWidget(dateBox);
    }

    public Date getDateValue()
    {
        return dateBox.getValue();
    }

    public void clearDateValue()
    {
        dateBox.setValue(null);
    }


}






             /*
        datePicker = new DatePicker();
        datePicker.setTitle(ident + " date selection box");
        //final Label text = new Label();
        final HTML text = new HTML("Nothing here yet");
           */

/*        dateBox.addValueChangeHandler(new ValueChangeHandler<Date>()
        {
            public void onValueChange(ValueChangeEvent< Date > event)
            {
                Date date = event.getValue();
                DateFormat format = new SimpleDateFormat("MM/dd/yyyy h:mm a");
                format.setLenient(false);
                String dateString = DateTimeFormat.getFormat("MM/dd/yyyy h:mm a").format(date);

                try {
                    Date dateParse = format.parse(dateString);
                } catch(ParseException ex) {
                    Window.alert(ident + " date/time format is incorrect: " + dateString
                            + "\nShould be in the form: mm/dd/yyyy h:mm am/pm)");
                }
                text.setText(dateString);
            }
        });
*/