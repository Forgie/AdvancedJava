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
     * Returns a list of appointment book owners
     */
    void getAppointmentBookOwners(AsyncCallback<LinkedList<String>> async);

    /**
     * Adds an appointment to the server under the specified owner
     */
    void addAppointment(String owner, String description, Date start, Date end, AsyncCallback<Void> async);


    /**
     * Returns a string of pretty printed appointments between a desired range
     */
    void searchAppointmentBook(String owner, Date start, Date end, AsyncCallback<String> async);


    /**
     * Returns a string of all appointments in an appointment book for a desired owner name
     */
    void allAppointments(String owner, AsyncCallback<String> async);
}
