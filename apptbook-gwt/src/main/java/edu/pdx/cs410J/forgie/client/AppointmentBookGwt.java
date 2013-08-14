package edu.pdx.cs410J.forgie.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;


/**
 * A basic GWT class that makes sure that we can send an appointment book back from the server
 */
public class AppointmentBookGwt implements EntryPoint {
  @Override
  public void onModuleLoad()
  {

      //AppointmentBookUI ui = new AppointmentBookUI();

      HTML moreInfo;
      //Panel suggestPanel = getOwnerPanel();

      TabPanel tabPanel = new DecoratedTabPanel();

      moreInfo = new HTML("README button");
      tabPanel.add(moreInfo, "Help");

      tabPanel.add(new CommonPanel(true),"Add");

      tabPanel.add(new CommonPanel(false), "Search");

      tabPanel.add(new ViewAllAppointmentsPanel(), "AppointmentBook Viewer");

      tabPanel.selectTab(0);
      tabPanel.ensureDebugId("TabPanel");


      RootPanel rootPanel = RootPanel.get();
      rootPanel.add(tabPanel, -1,-1);
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
            PingServiceAsync async = GWT.create( AppointmentBooksService.class );
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