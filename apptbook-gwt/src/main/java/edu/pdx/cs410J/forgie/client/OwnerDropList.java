package edu.pdx.cs410J.forgie.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;

import java.util.Collection;

/**
 * @author Shawn
 * Date: 8/8/13
 *
 */
public class OwnerDropList extends Composite {

    //String nameSelected;
    final ListBox dropBox;

    public OwnerDropList()
    {
        dropBox = new ListBox();
        dropBox.addItem("Add");
        dropBox.addItem("Jim Bob");


        AppointmentBooksServiceAsync async = GWT.create(AppointmentBooksService.class);
        async.getAppointmentBookOwners(new AsyncCallback<Collection<String>>() {
            public void onFailure(Throwable ex) {
                Window.alert(ex.toString());
            }

            public void onSuccess(Collection<String> names) {
                if (names == null) {
                    Window.alert("No appointment books available please add one!");
                    return;
                }
                for (String name : names) {
                    dropBox.addItem(name);
                }
            }
        });




        dropBox.ensureDebugId("OwnerDropDown");
        initWidget(dropBox);
    }

    public String getSelection()
    {
        int num = dropBox.getSelectedIndex();

        return dropBox.getItemText(num);
    }

    public HandlerRegistration addAttachHandler(ClickHandler clickHandler) {
        return addDomHandler(clickHandler, ClickEvent.getType());
    }
}
