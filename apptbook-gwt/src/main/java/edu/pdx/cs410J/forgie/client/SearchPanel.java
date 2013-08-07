package edu.pdx.cs410J.forgie.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.google.gwt.user.client.Window;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Shawn
 * Date: 8/2/13
 * Time: 5:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchPanel extends SimplePanel {
    public SearchPanel()
    {}

    public DockPanel getPanel()
    {
        DockPanel panel;
        panel = new DockPanel();
        panel.setSpacing(4);
        panel.setHorizontalAlignment(DockPanel.ALIGN_CENTER);

        HTML panelText = new HTML("Select the dates to search from");
        panel.add(panelText, DockPanel.NORTH);

        panelText = new HTML("Starting:");
        panel.add(panelText, DockPanel.WEST);

        panel.add(new DateBoxPicker().createDateBoxPicker(), DockPanel.WEST);
        panelText = new HTML("\tEnding:");
        panel.add(panelText, DockPanel.CENTER);
        panel.add(new DateBoxPicker().createDateBoxPicker(), DockPanel.EAST);
        return panel;
    }


}
