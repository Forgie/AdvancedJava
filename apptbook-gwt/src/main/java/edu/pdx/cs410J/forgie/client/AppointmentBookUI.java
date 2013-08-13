package edu.pdx.cs410J.forgie.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Shawn
 * Date: 8/8/13
 * Time: 3:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class AppointmentBookUI extends Composite{

    String nameSelected;

    public AppointmentBookUI()
    {
        HTML moreInfo;
        //Panel suggestPanel = getOwnerPanel();

        TabPanel tabPanel = new DecoratedTabPanel();

        moreInfo = new HTML("README button");
        tabPanel.add(moreInfo, "Help");

        tabPanel.add(new AddPanel(), "Add");

        tabPanel.add(new SearchPanel(), "Search");

        moreInfo = new HTML("Nothing here yet");
        tabPanel.add(moreInfo, "View Appointments");

        tabPanel.selectTab(0);
        tabPanel.ensureDebugId("TabPanel");

        initWidget(tabPanel);
    }


    private Panel getHelpMenuTab(HTML moreInfo)
    {
        DecoratorPanel decoratorPanel = new DecoratorPanel();
        decoratorPanel.add(moreInfo);

        AbsolutePanel absolutePanel = new AbsolutePanel();
        absolutePanel.setSize("1000px", "1000px");
        absolutePanel.add(moreInfo);
        return decoratorPanel;
    }


    /*
    private Panel getOwnerPanel() {
        final ListBox dropBox = new ListBox();
        dropBox.addItem("Add");


        PingServiceAsync async = GWT.create(AppointmentBooksService.class);
        async.getAppointmentBookOwners( new AsyncCallback<Collection<String>>()
        {
            public void onFailure( Throwable ex )
            {
                Window.alert(ex.toString());
            }

            public void onSuccess(Collection<String>  names) {
                if(names == null) {
                    Window.alert("No appointment books available please add one!");
                    return;
                }
                for(String name : names)
                {
                    dropBox.addItem(name);
                }
            }
        });

        dropBox.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                int num = dropBox.getSelectedIndex();
                nameSelected = dropBox.getItemText(num);

            }
        });

        dropBox.ensureDebugId("OwnerDropDown");
        Panel ownerPanel = new VerticalPanel();
        ownerPanel.add(new HTML("Select an owner:"));
        ownerPanel.add(dropBox);

        return ownerPanel;
    }

     */


    private void getNameFromDropDown(final ListBox listBox) {

        listBox.addChangeHandler(new ChangeHandler() {
            public void onChange(ChangeEvent event) {
                int n = listBox.getSelectedIndex();

                nameSelected = new String(listBox.getItemText(n));

                if(!nameSelected.equals("Add") && !nameSelected.equals("Select an Owner"))
                {
                    Window.alert(nameSelected);

                }   else nameSelected = null;

            }
        });

    }



}
