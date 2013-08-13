package edu.pdx.cs410J.forgie.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.Collection;
import java.util.Date;

/**
 * The client-side interface to the ping service
 */
public interface AppointmentBooksServiceAsync {

  /**
   * Return the current date/time on the server
   */
  //void ping(AsyncCallback<AbstractAppointmentBook> async);

    void getAppointmentBookOwners(AsyncCallback<Collection<String>> async);


    void addAppointment(String owner, String description, Date start, Date end, AsyncCallback<Boolean> async);
}
