package edu.pdx.cs410J.forgie.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.util.LinkedList;


/**
 * <code>ViewAllAppointmentsPanel</code> initializes a widget to display all appointments of a selected owner
 * @author Shawn
 * Date: 8/3/13
 *
 */
public class ViewAllAppointmentsPanel extends Composite {
    AppointmentBooksServiceAsync async;
    SuggestBox suggestBox;
    MultiWordSuggestOracle oracle;

    /**
     * Initialize the View Appointment Book Tab
     */
    public ViewAllAppointmentsPanel()
    {
        async = GWT.create(AppointmentBooksService.class);

        DecoratorPanel decoratorPanel = new DecoratorPanel();
        final AbsolutePanel absolutePanel = new AbsolutePanel();
        absolutePanel.setSize("800px", "650px");
        absolutePanel.ensureDebugId("All Appointments AbsolutePanel");

        getMultiWordSuggestOracle();

        suggestBox = new SuggestBox(oracle);

        absolutePanel.add(suggestBox, 10, 50);

        Button updateButton = new Button();
        updateButton.setText("Update Owner List");

        updateButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                getMultiWordSuggestOracle();
                suggestBox = new SuggestBox(oracle);
                absolutePanel.add(suggestBox, 10, 50);
            }
        });

        absolutePanel.add(updateButton, 10, 10);

        Button viewAppointmentsButton = new Button();
        viewAppointmentsButton.setText("View AppointmentBook");
        viewAppointmentsButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final String owner = suggestBox.getText();
                final TextArea  textArea = getTextArea(65, 20);

                textArea.setReadOnly(true);
                absolutePanel.add(textArea, 100, 150);


                if(owner != null && !owner.isEmpty())
                {
                    async.allAppointments(owner, new AsyncCallback<String>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            Window.alert(owner + " does not have an appointment book.");
                        }

                        @Override
                        public void onSuccess(String result) {
                            if (result.equals(null) || result.isEmpty())
                                Window.alert(owner + " does not have any appointments");
                            else textArea.setText(result);
                        }
                    });

                } else Window.alert("Please fill out blank fields!");
            }
        });

        absolutePanel.add(viewAppointmentsButton, 10, 100);


        decoratorPanel.setWidget(absolutePanel);
        initWidget(decoratorPanel);
    }


    /**
     * Creates a text area to display the appointment book
     *
     * @param w     The width of the text area
     * @param h     The height of the text area
     * @return      returns a new text area
     */
    private TextArea getTextArea(int w, int h)
    {
        TextArea textArea = new TextArea();
        textArea.setCharacterWidth(w);
        textArea.setVisibleLines(h);

        return textArea;
    }


    /**
     * Populate an oracle with names from the list of appointment books stored on the server
     */
    private void getMultiWordSuggestOracle()
    {
        this.oracle = new MultiWordSuggestOracle();


        async.getAppointmentBookOwners(new AsyncCallback<LinkedList<String>>() {
            @Override
            public void onFailure(Throwable ex) {
                Window.alert(ex.toString());
            }
            @Override
            public void onSuccess(LinkedList<String> names) {
                if (names == null) {
                    // Window.alert("No appointment books available please add one!");
                    return;
                }
                for (String name : names) {
                    oracle.add(name);
                }
            }
        });
    }

}
