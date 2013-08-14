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



    @Override
    public Boolean addAppointment(String owner, String description, Date start, Date end)
    {
        System.out.println("adding 34");
        Appointment appointment = new Appointment(description, start, end);

        AbstractAppointmentBook appointmentBook = this.data.get(owner);

        if(appointmentBook == null)
            appointmentBook = new AppointmentBook(owner);

        appointmentBook.addAppointment(appointment);
        this.data.put(owner, appointmentBook);
        System.out.print(this.data.size() + "line 44");

        return true;
    }

    @Override
    public String searchAppointmentBook(String owner, Date start, Date end)
    {

        PrettyPrinter prettyPrinter = new PrettyPrinter();

        return prettyPrinter.getAppointmentsBetweenBeginTimeAndEndTime(this.data.get(owner), start, end);
    }

    @Override
    public String allAppointments(String owner) {
        PrettyPrinter prettyPrinter = new PrettyPrinter();

        return prettyPrinter.buildAppointmentBookString(this.data.get(owner));
    }
}
