package edu.pdx.cs410J.forgie.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;

import java.awt.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Shawn
 * Date: 8/2/13
 * Time: 5:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchPanel extends SimplePanel {
    DockPanel panel;
    public SearchPanel()
    {
        panel = new DockPanel();
        HTML panelText = new HTML("Select the dates to search from");
        panel.add(panelText, DockPanel.NORTH);

        DatePicker datePicker = new DatePicker();
        final Label text = new Label();

        datePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
            public void onValueChange(ValueChangeEvent<Date> event) {
                Date date = event.getValue();
                String dateString = DateTimeFormat.getMediumDateFormat().format(date);
                text.setText(dateString);
            }
        });

        //DateTimeFormat dateFormat = DateTimeFormat.getLongDateFormat();
        DateBox dateBox = new DateBox();
       // dateBox.setFormat(new DateBox.DefaultFormat(dateFormat));

        //panel.add(datePicker, DockPanel.CENTER);
        panel.add(dateBox, DockPanel.EAST);

    }

    public DockPanel getPanel()
    {

        return panel;
    }


}
