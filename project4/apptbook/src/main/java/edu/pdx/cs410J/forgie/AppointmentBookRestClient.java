package edu.pdx.cs410J.forgie;

import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;

/**
 * A helper class for accessing the rest client
 */
public class AppointmentBookRestClient extends HttpRequestHelper
{
    private static final String WEB_APP = "apptbook";
    private static final String SERVLET = "appointments";

    private final String url;


    /**
     * Creates a client to the appointment book REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port
     */
    public AppointmentBookRestClient( String hostName, int port )
    {
        this.url = String.format( "http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET );
    }


    public Response addKeyValuePair( String owner, String apptBook ) throws IOException
    {
        return post( this.url, "owner", owner, "appointmentBook" , apptBook);
    }

    public Response getAppointmentsBetweenDates(String owner, String begin, String end) throws IOException
    {
        return get(this.url, "owner", owner, "beginTime", begin, "endTime", end);
    }

    public Response addAppointment(String owner, String appointment) throws IOException
    {
        return post(this.url, "owner", owner, "appointment", appointment);
    }
}
