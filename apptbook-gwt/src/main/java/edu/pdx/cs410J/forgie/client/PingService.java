package edu.pdx.cs410J.forgie.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import edu.pdx.cs410J.AbstractAppointmentBook;

/**
 * A GWT remote service that returns a dummy appointment book
 */
@RemoteServiceRelativePath("ping")
public interface PingService extends RemoteService {

  /**
   * Returns the current date and time on the server
   */
  public AbstractAppointmentBook ping();

}
