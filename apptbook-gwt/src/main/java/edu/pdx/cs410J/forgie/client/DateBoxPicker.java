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
 * <code>DateBoxPicker</code> initializes a  date box picker that is common
 * between the add and search tabs
 *
 * @author Shawn
 * Date: 8/3/13
 *
 */
public class DateBoxPicker extends Composite {
    DateBox dateBox;
    public DateBoxPicker(final String ident)
    {
        DateTimeFormat dateFormat = DateTimeFormat.getFormat("MM/dd/yyyy h:mm a");
        dateBox = new DateBox();
        dateBox.setFormat(new DateBox.DefaultFormat(dateFormat));
        dateBox.setWidth("1.75in");
        dateBox.setTitle("Select the date from the drop-down datebox and enter the desired " + ident + " time.");

        initWidget(dateBox);
    }

    /**
     * Gets the date from the datebox
     * @return a date formatted as MM/dd/yyyy h:mm a
     */
    public Date getDateValue()
    {
        return dateBox.getValue();
    }

    /**
     * Clears the date value in the datebox
     */
    public void clearDateValue()
    {
        dateBox.setValue(null);
    }
}