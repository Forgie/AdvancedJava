package edu.pdx.cs410J.forgie.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;

//import java.awt.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Shawn
 * Date: 8/3/13
 * Time: 3:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class DateBoxPicker {
    DatePicker datePicker;

    public DateBoxPicker()
    {

    }

    public DateBox createDateBoxPicker()
    {

        datePicker = new DatePicker();
        //final Label text = new Label();
        final HTML text = new HTML("Nothing here yet");

        datePicker.addValueChangeHandler(new ValueChangeHandler<Date>()
        {
            public void onValueChange(ValueChangeEvent< Date > event)
            {
                Date date = event.getValue();

                String dateString = DateTimeFormat.getFormat("MM/dd/yyyy h:mm a").format(date);

                text.setText(dateString);
            }
        });

        DateTimeFormat dateFormat = DateTimeFormat.getFormat("MM/dd/yyyy h:mm a");


        DateBox dateBox = new DateBox();
        dateBox.setFormat(new DateBox.DefaultFormat(dateFormat));
        dateBox.setWidth("2.5in");
        dateBox.setTitle("Select the date from the drop-down datebox and enter the desired time.");


        return dateBox;

    }
}
