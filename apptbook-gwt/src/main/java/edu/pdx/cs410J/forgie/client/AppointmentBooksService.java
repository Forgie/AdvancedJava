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
     * Returns a list of appointment book owners
     * @return a linkedlist of strings
     */
    public LinkedList<String> getAppointmentBookOwners();


    /**
     * Posts an appointment to the server and returns true if the appointment was valid and succesfully added.
     *
     * @param owner  the owner name of an appointment book
     * @param description  the description of an appointment
     * @param start  the start date for an appointment
     * @param end  the end date for an appointment
     */
    public void addAppointment(String owner, String description, Date start, Date end);


    /**
     * Searches an appointment book for appointments between the
     * given date and times for a specific owner and returns the string to be displayed.
     *
     * @param owner the owner name of an appointment book
     * @param start the start date to search between
     * @param end the end date to search between
     * @return the pretty printed appointments between the desired dates
     */
    public String searchAppointmentBook(String owner, Date start, Date end);


    /**
     * Returns all the appointmments for a specific owner as a string to be displayed
     *
     * @param owner the owner name of an appointment book
     * @return the pretty printed appointment book
     */
    public String allAppointments(String owner);
}
