package edu.pdx.cs410J.forgie.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.forgie.client.AppointmentBooksService;

import java.util.*;

/**
 * The server-side implementation of the division service
 */
public class AppointmentBooksServiceImpl extends RemoteServiceServlet implements AppointmentBooksService
{
    Map<String, AbstractAppointmentBook> data = new TreeMap<String, AbstractAppointmentBook>();


    /**
     * Returns a list of appointment book owners on the server
     * @return a linked list of strings
     */
    @Override
    public LinkedList<String> getAppointmentBookOwners()
    {

    if(data.size() == 0) return null;
        else
    {
        LinkedList<String> list = new LinkedList<String>();
        for(String key : data.keySet())
        {
            list.add(key);
        }
        return list;
    }
    }


    /**
     * Add an appointment to an existing appointment book or create a new one
     *
     * @param owner  the owner name of an appointment book
     * @param description  the description of an appointment
     * @param start  the start date for an appointment
     * @param end  the end date for an appointment
     */
    @Override
    public void addAppointment(String owner, String description, Date start, Date end)
    {
        Appointment appointment = new Appointment(description, start, end);

        AbstractAppointmentBook appointmentBook = this.data.get(owner);

        if(appointmentBook == null)
            appointmentBook = new AppointmentBook(owner);

        appointmentBook.addAppointment(appointment);
        this.data.put(owner, appointmentBook);
    }


    /**
     * Searches for appointments under a specific name that fall between a specified range
     *
     * @param owner the owner name of an appointment book
     * @param start the start date to search between
     * @param end the end date to search between
     * @return a pretty printed string of the appointments within the range
     */
    @Override
    public String searchAppointmentBook(String owner, Date start, Date end)
    {

        PrettyPrinter prettyPrinter = new PrettyPrinter();

        return prettyPrinter.getAppointmentsBetweenBeginTimeAndEndTime(this.data.get(owner), start, end);
    }


    /**
     * Returns a pretty printed string of the appointment book
     * @param owner the owner name of an appointment book
     * @return a nicely formatted appointment book as a string
     */
    @Override
    public String allAppointments(String owner) {
        PrettyPrinter prettyPrinter = new PrettyPrinter();

        return prettyPrinter.buildAppointmentBookString(this.data.get(owner));
    }
}
