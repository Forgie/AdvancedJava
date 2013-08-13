package edu.pdx.cs410J.forgie.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.Window;
import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;


/**
 * @author Shawn
 *         Date: 8/9/13
 */
public class CommonPanel extends Composite {

    DateBoxPicker startDateBoxPicker;
    DateBoxPicker endDateBoxPicker;
    SuggestBox suggestBox;


    public CommonPanel(String message)
    {
        final AbsolutePanel absolutePanel = new AbsolutePanel();
        absolutePanel.setSize("800px", "650px");
        absolutePanel.ensureDebugId("AbsolutePanel");

        MultiWordSuggestOracle oracle = getMultiWordSuggestOracle();

        suggestBox = new SuggestBox(oracle);

        HTML panelText = new HTML(message);
        DecoratorPanel dp = new DecoratorPanel();
        dp.add(panelText);
        absolutePanel.add(dp, 200, 10);

        panelText = new HTML("Owner:");
        absolutePanel.add(panelText, 100, 90);

        absolutePanel.add(suggestBox, 100, 120);

        panelText = new HTML("Starting:");
        absolutePanel.add(panelText, 100, 170);

        startDateBoxPicker = new DateBoxPicker("Start");
        absolutePanel.add(startDateBoxPicker, 100, 190);

        panelText = new HTML("Ending:");
        absolutePanel.add(panelText, 500, 170);
        endDateBoxPicker = new DateBoxPicker("End");
        absolutePanel.add(endDateBoxPicker, 500, 190);

        if(message.contains("add")) addAddPanelItems(absolutePanel);
        else addSearchPanelItems(absolutePanel);

        DecoratorPanel absolutePanelWrapper = new DecoratorPanel();
        absolutePanelWrapper.setWidget(absolutePanel);

        initWidget(absolutePanelWrapper);
    }



    private void addAddPanelItems(AbsolutePanel absolutePanel) {
        HTML panelText = new HTML("Description:");
        final Button selectButton = new Button("Add Appointment");
        final TextArea textArea = getTextArea(65,5);

        absolutePanel.add(panelText, 100, 260);
        absolutePanel.add(textArea, 100, 280);
        absolutePanel.add(selectButton, 565, 400);


        selectButton.addClickHandler(new ClickHandler()
        {
            public void onClick(ClickEvent event)
            {
                String owner = suggestBox.getText();
                String description = textArea.getText();
                Date start = startDateBoxPicker.getDateValue();
                Date end = endDateBoxPicker.getDateValue();
                if(start != null && end != null && owner != null && description != null)
                {
                    if(!validateDates(start, end)) return;
                } else
                {
                    Window.alert("Please fill out blank fields!");
                    return;
                }



                AppointmentBooksServiceAsync async = GWT.create( AppointmentBooksService.class );
                async.addAppointment(owner, description, start, end, new AsyncCallback<Boolean>()
                {
                    public void onFailure(Throwable caught)
                    {
                        Window.alert(caught.toString());
                    }

                    public void onSuccess(Boolean result)
                    {
                        if(!result)
                            Window.alert("Appointment could not be added");
                    }
                });
            }
        });


    }



    private void addSearchPanelItems(final AbsolutePanel absolutePanel) {
        Button selectButton = new Button("Search");

        absolutePanel.add(selectButton, 100, 240);

        selectButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                TextArea textArea = getTextArea(65, 20);
                textArea.setText("Appointments between dates");
                textArea.setReadOnly(true);
                absolutePanel.add(textArea, 100, 265);
            }
        });
    }


    private TextArea getTextArea(int w, int h)
    {
        TextArea textArea = new TextArea();
        textArea.setCharacterWidth(w);
        textArea.setVisibleLines(h);

        return textArea;
    }



    private MultiWordSuggestOracle getMultiWordSuggestOracle()
    {
        final MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();


        AppointmentBooksServiceAsync async = GWT.create(AppointmentBooksService.class);
        async.getAppointmentBookOwners(new AsyncCallback<Collection<String>>() {
            public void onFailure(Throwable ex) {
                Window.alert(ex.toString());
            }

            public void onSuccess(Collection<String> names) {
                if (names == null) {
                    // Window.alert("No appointment books available please add one!");
                    return;
                }
                for (String name : names) {
                    oracle.add(name);
                }
            }
        });
        oracle.add("Jimmy");

        return oracle;
    }


    private boolean validateDates(Date start, Date end)
    {
        String s = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(start);
        String e = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(end);

        DateFormat format = new SimpleDateFormat("MM/dd/yyyy h:mm a");
        format.setLenient(false);

        Date date;
        try {
            date = format.parse(s);
            date = format.parse(e);
        } catch(ParseException ex) {
            Window.alert("date/time format is incorrect: " + s
                    + "\nShould be in the form: mm/dd/yyyy h:mm am/pm)");
            return false;
        }

        return true;

    }

}


