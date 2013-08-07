package edu.pdx.cs410J.forgie.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Shawn
 * Date: 8/3/13
 * Time: 10:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class HomePanel extends SimplePanel {

    public HomePanel(){}

    public DockPanel getHomePanel()
    {
        final ListBox listBox = new ListBox();

        PingServiceAsync async = GWT.create(PingService.class);
        async.getAppointmentBooks( new AsyncCallback<Collection<AbstractAppointmentBook>>()
        {

            public void onFailure( Throwable ex )
            {
                Window.alert(ex.toString());
            }

            public void onSuccess(Collection<AbstractAppointmentBook>  apptbks) {
                for(AbstractAppointmentBook apptbk : apptbks)
                {
                    listBox.addItem(apptbk.getOwnerName());
                }
            }
        });
        listBox.addItem("Select and Owner");
        listBox.addItem("Add New");
        listBox.addItem("Jack");
        listBox.addItem("Smooth Operator");
        listBox.setTitle("Owners with Appointment Books");
        listBox.setWidth("2in");



        //listBox.



        DockPanel dockPanel = new DockPanel();

        DecoratorPanel decoratorPanel = new DecoratorPanel();
        HTML homeText = new HTML("Select an existing Owner");

        decoratorPanel.setWidget(listBox);

        dockPanel.add(homeText, DockPanel.NORTH);
        dockPanel.add(decoratorPanel, DockPanel.SOUTH);

        //homeText = new HTML("Or go to the Add tab to add a new appointment book");
        //dockPanel.add(homeText, DockPanel.WEST);

        return dockPanel;
    }
}
