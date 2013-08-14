package edu.pdx.cs410J.forgie.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.Window;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;


/**
 * @author Shawn
 *         Date: 8/9/13
 */
public class CommonPanel extends Composite {
    MultiWordSuggestOracle oracle;
    DecoratorPanel absolutePanelWrapper;
    AbsolutePanel absolutePanel;
    DateBoxPicker startDateBoxPicker;
    DateBoxPicker endDateBoxPicker;
    SuggestBox suggestBox;
    HTML panelText;
    AppointmentBooksServiceAsync async;



    public CommonPanel(boolean ident)
    {
        async = GWT.create(AppointmentBooksService.class);

        loadCommon();

        if(ident) loadAdd();
        else loadSearch();

        absolutePanelWrapper = new DecoratorPanel();
        absolutePanelWrapper.setWidget(absolutePanel);

        initWidget(absolutePanelWrapper);
    }

    private void loadCommon()
    {
        absolutePanel = new AbsolutePanel();
        absolutePanel.setSize("800px", "650px");
        absolutePanel.ensureDebugId("AbsolutePanel");

        getMultiWordSuggestOracle();

        suggestBox = new SuggestBox(oracle);

        Button updateButton = new Button();
        updateButton.setText("Update Owner List");

        updateButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                getMultiWordSuggestOracle();
                suggestBox = new SuggestBox(oracle);
                absolutePanel.add(suggestBox, 100, 120);

            }
        });

        absolutePanel.add(updateButton, 10, 10);
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
    }

    private void loadAdd()
    {
        addAddPanelItems(absolutePanel);
    }

    private void loadSearch()
    {
        addSearchPanelItems(absolutePanel);
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
            @Override
            public void onClick(ClickEvent event)
            {
                final String owner = suggestBox.getValue();
                String description = textArea.getText();
                Date start = startDateBoxPicker.getDateValue();
                Date end = endDateBoxPicker.getDateValue();

                if(start != null && end != null && owner != null && description != null && !description.equals("") && !owner.equals(""))
                {

                    async.addAppointment(owner, description, start, end, new AsyncCallback<Boolean>()
                    {
                        @Override
                        public void onFailure(Throwable caught)
                        {
                            Window.alert(caught.toString());
                        }
                        @Override
                        public void onSuccess(Boolean result)
                        {
                            if(!result)
                                Window.alert("Appointment could not be added");
                            else
                            {
                                Window.alert("Appointment has been added to " + owner + "'s appointment book.");
                                oracle.add(owner);
                            }
                        }
                    });

                } else Window.alert("Please fill out blank fields!");

                //getMultiWordSuggestOracle();
               // suggestBox = new SuggestBox(oracle);
                textArea.setValue("");
                suggestBox.setText("");
                startDateBoxPicker.clearDateValue();
                endDateBoxPicker.clearDateValue();



            }
        });

    }



    private void addSearchPanelItems(final AbsolutePanel absolutePanel) {
        Button selectButton = new Button("Search");

        absolutePanel.add(selectButton, 100, 240);

        selectButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final String owner = suggestBox.getText();

                final TextArea textArea = getTextArea(65, 20);
                textArea.setText("Appointments between dates");
                textArea.setReadOnly(true);
                absolutePanel.add(textArea, 100, 265);

                Date start = startDateBoxPicker.getDateValue();
                Date end = endDateBoxPicker.getDateValue();

                if(start != null && end != null && owner != null && !owner.isEmpty())
                {
                    async.searchAppointmentBook(owner, start, end, new AsyncCallback<String>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            Window.alert(owner + " does not have an appointment book.");
                        }
                        @Override
                        public void onSuccess(String result) {
                            if (result.equals(null) || result.isEmpty())
                                Window.alert(owner + " does not have any appointments between the dates selected.");
                            else textArea.setText(result);
                        }
                    });

                } else Window.alert("Please fill out blank fields!");
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



    private void getMultiWordSuggestOracle()
    {
        oracle = new MultiWordSuggestOracle();

        async.getAppointmentBookOwners(new AsyncCallback<LinkedList<String>>() {
            @Override
            public void onFailure(Throwable ex) {
                Window.alert(ex.toString());
            }
            @Override
            public void onSuccess(LinkedList<String> names) {
                if (names != null) {
                    for (String name : names) {
                        oracle.add(name);
                    }
                }
            }
        });

    }

}


