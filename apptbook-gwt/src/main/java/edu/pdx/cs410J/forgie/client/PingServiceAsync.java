package edu.pdx.cs410J.forgie.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.Collection;

/**
 * The client-side interface to the ping service
 */
public interface PingServiceAsync {

  /**
   * Return the current date/time on the server
   */
  //void ping(AsyncCallback<AbstractAppointmentBook> async);

  void getAppointmentBooks(AsyncCallback<Collection<AbstractAppointmentBook>> async);
}
