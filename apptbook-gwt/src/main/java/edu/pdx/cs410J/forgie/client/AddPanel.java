package edu.pdx.cs410J.forgie.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.Collection;

/**
 * @author Shawn
 * Date: 8/3/13
 *
 */
public class AddPanel extends Composite {

    OwnerDropList dropList;
    String nameSelected;
    public AddPanel()
    {

        CommonPanel panel = new CommonPanel("Select a name or enter a new one to add an appointment.");


        initWidget(panel);

    }







            /*
        dropList = new OwnerDropList();

        nameSelected = dropList.getSelection();


        //if(nameSelected != null )//&& !nameSelected.equals("Add"))//get the existing appointment book



        DockPanel dockPanel = new DockPanel();
        dockPanel.add(dropList, DockPanel.NORTH);
        dockPanel.add(new DateBoxPicker("Start"), DockPanel.WEST);
        dockPanel.add(new DateBoxPicker("End"), DockPanel.EAST);
        /*
        dockPanel.addAttachHandler(new ClickHandler(){
            public void onClick(ClickEvent event)
            {
              dropList.fireEvent(event);
            }
        }); */

}
