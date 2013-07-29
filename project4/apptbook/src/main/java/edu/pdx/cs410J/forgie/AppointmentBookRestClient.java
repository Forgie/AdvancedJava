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



    /**
     * Creates a request to the REST service searching for appointments between two dates
     * @param owner The name of an owner
     * @param begin The beginning time to search between
     * @param end The ending time to search between
     * @return The response from the server, containing a code and the search results
     * @throws IOException Parameters were not correct and the request could not be made
     */
    public Response getAppointmentsBetweenDates(String owner, String begin, String end) throws IOException
    {
        return get(this.url, "owner", owner, "beginTime", begin, "endTime", end);
    }



    /**
     * Creates a request to the REST service to post an appointment to an appointment book
     * @param owner The name of the owner
     * @param desc The description of an appointment
     * @param begintime The start time of an appointment
     * @param endtime The end time of an appointment
     * @return The response form the server, containing a status code
     * @throws IOException Parameters were not correct and the request could not be made
     */
    public Response addAppointment(String owner, String desc, String begintime, String endtime) throws IOException
    {
        return post(this.url, "owner", owner, "description", desc, "beginTime", begintime, "endTime", endtime);
    }
}
