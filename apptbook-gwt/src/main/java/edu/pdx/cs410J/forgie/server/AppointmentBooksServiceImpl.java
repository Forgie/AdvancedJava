package edu.pdx.cs410J.forgie.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.forgie.client.Appointment;
import edu.pdx.cs410J.forgie.client.AppointmentBook;
import edu.pdx.cs410J.forgie.client.AppointmentBooksService;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * The server-side implementation of the division service
 */
public class AppointmentBooksServiceImpl extends RemoteServiceServlet implements AppointmentBooksService
{
    Map<String, AbstractAppointmentBook> data = new TreeMap<String, AbstractAppointmentBook>();
      /*
    public AbstractAppointmentBook ping()
    {
        AppointmentBook book = new AppointmentBook();
        book.addAppointment( new Appointment() );
        return book;
    }
     */
    public Collection<String> getAppointmentBookOwners()
    {
        if(data.size() == 0) return null;
        else return data.keySet();
    }

    public boolean addAppointment(String owner, String description, Date start, Date end) {

        Appointment appointment = new Appointment(description, start, end);

        AbstractAppointmentBook appointmentBook = this.data.get(owner);
        if(appointmentBook == null)
            appointmentBook = new AppointmentBook(owner);

        appointmentBook.addAppointment(appointment);
        this.data.put(owner, appointmentBook);



        return true;
    }

    //Maybe needed?
    public void putAppointmentBooks(Collection<AbstractAppointmentBook> books)
    {
        for(AbstractAppointmentBook book : books)
        {
            data.put(book.getOwnerName(), book);
        }
    }
}
