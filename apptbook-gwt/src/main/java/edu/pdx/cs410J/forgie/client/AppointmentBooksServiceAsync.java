package edu.pdx.cs410J.forgie.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

/**
 * The client-side interface to the ping service
 */
public interface AppointmentBooksServiceAsync {

  /**
   * Return the current date/time on the server
   */

    void getAppointmentBookOwners(AsyncCallback<LinkedList<String>> async);

    void addAppointment(String owner, String description, Date start, Date end, AsyncCallback<Boolean> async);

    void searchAppointmentBook(String owner, Date start, Date end, AsyncCallback<String> async);

    void allAppointments(String owner, AsyncCallback<String> async);
}
