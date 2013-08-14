package edu.pdx.cs410J.forgie.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;


/**
 * A basic GWT class that makes sure that we can send an appointment book back from the server
 */
public class AppointmentBookGwt implements EntryPoint {
    DecoratorPanel decoratorPanelWrapper;
    boolean hidden;

    private static final String readme = "   Project5 provides a UI for an appointment book program. A user can select " +
                                         "the tab of their choice to either Add to an appointment book, Search an " +
                                         "appointment book for appointments within a given range or View All appointments " +
                                         "for a given owner. It should be noted that anytime a new appointment book has " +
                                         "been added they will need to update the owner list to be provided a suggestion " +
                                         "that contains the new owner. I struggled with this program and some things are " +
                                         "still buggy Nullexceptions are thrown by the RPC but are not detrimental to " +
                                         "operation (as far as I can tell). Dates are not fully error checked and messages " +
                                         "are not clear when the user enters a malformatted date. I enjoyed this challenge " +
                                         "but as you noted in class it is easy for the code to begin to feel bulky and " +
                                         "it was hard for me to know where to put widgets and how to break up the program. " +
                                         "As a result I created unwieldly code and found myself too far in to restart and " +
                                         "not enough time if I wanted to. So sadly this is what I have and it does not " +
                                         "look fantastically creative but trust me it took a lot of effort. Thank you Dave " +
                                         "I enjoyed your class a lot and learned much more!";


    /**
     * Initializes the root panel with Tab panel containing the Menu, Add, Search and View All Appointments
     */
  @Override
  public void onModuleLoad()
  {
      decoratorPanelWrapper = new DecoratorPanel();
      hidden = true;

      TabPanel tabPanel = new DecoratedTabPanel();

      setHelpTab();

      tabPanel.add(decoratorPanelWrapper, "Help");

      tabPanel.add(new CommonPanel(true),"Add");

      tabPanel.add(new CommonPanel(false), "Search");

      tabPanel.add(new ViewAllAppointmentsPanel(), "AppointmentBook Viewer");

      tabPanel.selectTab(0);
      tabPanel.ensureDebugId("TabPanel");


      RootPanel rootPanel = RootPanel.get();
      rootPanel.add(tabPanel, -1,-1);
  }


    /**
     * Initialize a widget for the Help tab just contains a button
     * that can be clicked to hide or show readme
     *
     */
  private void setHelpTab()
  {
      final AbsolutePanel absolutePanel = new AbsolutePanel();
      absolutePanel.setSize("800px", "650px");

      final HTML README = new HTML(readme);
      README.setWordWrap(true);
      README.setWidth("500px");


      final Button readmeButton = new Button();
      readmeButton.setText("View README");

      readmeButton.addClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent event)
          {
            absolutePanel.add(README, 100, 50);

            if(hidden)
            {
                readmeButton.setText("Hide README");
                README.setVisible(true);
            }
            else
            {
                readmeButton.setText("View README");
                README.setVisible(false);
            }

              hidden = !hidden;
          }
      });

      absolutePanel.add(readmeButton, 10, 10);

      decoratorPanelWrapper.setWidget(absolutePanel);
  }
}