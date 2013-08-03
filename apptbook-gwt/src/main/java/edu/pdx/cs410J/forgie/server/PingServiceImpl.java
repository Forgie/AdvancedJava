package edu.pdx.cs410J.forgie.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.forgie.client.AppointmentBook;
import edu.pdx.cs410J.forgie.client.Appointment;
import edu.pdx.cs410J.forgie.client.PingService;

/**
 * The server-side implementation of the division service
 */
public class PingServiceImpl extends RemoteServiceServlet implements PingService
{
    public AbstractAppointmentBook ping()
    {
        AppointmentBook book = new AppointmentBook();
        book.addAppointment( new Appointment() );
        return book;
    }
}
