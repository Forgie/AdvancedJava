package edu.pdx.cs410J.forgie.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

/**
 * A GWT remote service that returns a dummy appointment book
 */
@RemoteServiceRelativePath("books")
public interface AppointmentBooksService extends RemoteService {

  /**
   * Returns the current date and time on the server
   */
  //public AbstractAppointmentBook ping();

    public LinkedList<String> getAppointmentBookOwners();

    public Boolean addAppointment(String owner, String description, Date start, Date end);

    public String searchAppointmentBook(String owner, Date start, Date end);

    public String allAppointments(String owner);
}
