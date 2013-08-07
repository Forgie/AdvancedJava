package edu.pdx.cs410J.forgie.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.*;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;
import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;


import java.util.Collection;

/**
 * A basic GWT class that makes sure that we can send an appointment book back from the server
 */
public class AppointmentBookGwt implements EntryPoint {

  public void onModuleLoad()
  {
      TabLayoutPanel tabPanel = new TabLayoutPanel(1, Unit.CM);

      tabPanel.add(new HomePanel().getHomePanel(), "Home");

      HTML homeText = new HTML("Nothing here yet");

      tabPanel.add(homeText, "Add");

      SearchPanel searchContainer = new SearchPanel();

      tabPanel.add(searchContainer.getPanel(), "Search");



      // Add a tab
      HTML moreInfo = new HTML("Nothing here yet");
      tabPanel.add(moreInfo, "View Appointments");

      // Return the content
      tabPanel.selectTab(0);
      tabPanel.ensureDebugId("TabPanel");



      RootPanel rootPanel = RootPanel.get();
      rootPanel.add(tabPanel, -1, -1);
  }


}


/*
 /**
   * The constants used in this Content Widget.

public static interface CwConstants extends Constants {
    String cwTabPanelDescription();

    String cwTabPanelName();

    String cwTabPanelTab0();

    String cwTabPanelTab2();

    String[] cwTabPanelTabs();
}

/**
 * An instance of the constants.

private final CwConstants constants;

    /**
     * Initialize this example.

    @Override
    public Widget onInitialize() {
        // Create a tab panel
        TabLayoutPanel tabPanel = new TabLayoutPanel(2.5, Unit.EM);
        tabPanel.setAnimationDuration(1000);
        tabPanel.getElement().getStyle().setMarginBottom(10.0, Unit.PX);

        // Add a home tab
        String[] tabTitles = constants.cwTabPanelTabs();
        HTML homeText = new HTML(constants.cwTabPanelTab0());
        tabPanel.add(homeText, tabTitles[0]);

        // Add a tab with an image
        SimplePanel imageContainer = new SimplePanel();
        imageContainer.setWidget(new Image(Showcase.images.gwtLogo()));
        tabPanel.add(imageContainer, tabTitles[1]);

        // Add a tab
        HTML moreInfo = new HTML(constants.cwTabPanelTab2());
        tabPanel.add(moreInfo, tabTitles[2]);

        // Return the content
        tabPanel.selectTab(0);
        tabPanel.ensureDebugId("cwTabPanel");

        return tabPanel;
    }
*/






    /*
    Button buttonPing = new Button("Ping Server");

    buttonPing.addClickHandler(new ClickHandler()
    {
        public void onClick( ClickEvent clickEvent )
        {
            PingServiceAsync async = GWT.create( PingService.class );
            async.ping( new AsyncCallback<AbstractAppointmentBook>()
            {

                public void onFailure( Throwable ex )
                {
                    Window.alert(ex.toString());
                }

                public void onSuccess( AbstractAppointmentBook book )
                {
                    StringBuilder sb = new StringBuilder( book.toString() );
                    Collection<AbstractAppointment> appts = book.getAppointments();
                    for ( AbstractAppointment appt : appts ) {
                        sb.append(appt);
                        sb.append("\n");
                    }
                    Window.alert( sb.toString() );
                }
            });
        }
    });


    Button buttonCalendar = new Button("");


      RootPanel rootPanel = RootPanel.get();
      rootPanel.add(buttonPing);
      rootPanel.add(buttonCalendar);
   */