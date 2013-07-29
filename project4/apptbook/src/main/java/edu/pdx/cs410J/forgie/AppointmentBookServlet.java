package edu.pdx.cs410J.forgie;

import edu.pdx.cs410J.AbstractAppointmentBook;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AppointmentBookServlet extends HttpServlet
{
      private final Map<String, AbstractAppointmentBook> data = new TreeMap<String, AbstractAppointmentBook>();

      AbstractAppointmentBook appointmentBook;

    /**
     * Get an appointment book or a range of appointments from the server
     * @param request The request from the client with the information to search for an appointment book or appointments within a given range
     * @param response The message to be sent to the client
     * @throws ServletException The serverlet has encountered something it cannot handle
     * @throws IOException The response cannot be generated
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType("text/plain");

        String query = request.getQueryString();
        if(query != null)
            if(query.endsWith("=") || query.equals("owner"))
            {
                missingRequiredParameter(response, "name");
                return;
            }
            String name = getParameter("owner", request);
            if(name != null)
            {
                String begin = getParameter("beginTime", request);
                if(begin != null)
                {
                    String end = getParameter("endTime", request);
                    if(end != null)
                    {
                        searchOwnersAppointmentBookForDates(response, name, begin, end);
                    }else missingRequiredParameter(response, "endTime");

                }else{
                    String end = getParameter("endTime", request);
                    if(end != null)
                    {
                        missingRequiredParameter(response, "beginTime");
                    } else writeAppointmentBook(name, response);
                }

            }else writeAllOwners(response);
    }


    /**
     * Post an appointment to an appointment book on the server from a client
     * @param request The request from the client with the information to make an appointment with
     * @param response The message to be sent to the client
     * @throws ServletException The serverlet has encountered something it cannot handle
     * @throws IOException The response cannot be generated
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String name = getParameter("owner", request);
        if(name != null)
        {
            String description = getParameter("description", request);
            if(description != null)
            {
                String beginTime = getParameter("beginTime", request);
                if(beginTime != null)
                {
                    String endTime = getParameter("endTime", request);
                    if(endTime != null)
                    {
                        addAppointment(response, name, description, beginTime, endTime);
                        response.setStatus( HttpServletResponse.SC_OK);

                    }else missingRequiredParameter(response, "endTime");

                }else missingRequiredParameter(response, "beginTime");

            }else missingRequiredParameter(response, "description");

        }else missingRequiredParameter(response, "owner");
    }




    /**
     * Adds an appointment to the collection of appointments stored in an appointment book on the server
     * checks that the dates are in the correct format to be added
     * @param response The message to be sent to the client
     * @param name The name of an owner of an appointment book
     * @param description The description of the new appointment
     * @param beginTime The start time of the new appointment
     * @param endTime The end time of the new appointment
     * @throws IOException The response could not be generated
     */
    private void addAppointment(HttpServletResponse response, String name, String description, String beginTime, String endTime) throws IOException
    {
        PrintWriter pw = response.getWriter();
        Date start, finish;
        String s, e;

        try{
            start =  checkDateTimeFormat(beginTime);
            finish = checkDateTimeFormat(endTime);

            s = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(start);
            e = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(finish);

            start = checkDateTimeFormat(s);
            finish = checkDateTimeFormat(e);
        }catch(ParseException ex)
        {
            pw.println(ex.getMessage());
            pw.flush();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        initializeAppointmentBook(name);
        Appointment appointment = new Appointment(description, start, finish);
        appointmentBook.addAppointment(appointment);

        this.data.put(name, appointmentBook);
    }


    /**
     * Retrieves an existing appointment book from the server if there is one for a specified owner
     * @param name The name of the owner
     */
    private void initializeAppointmentBook(String name)
    {
        appointmentBook = this.data.get(name);
        if(appointmentBook != null) return;
        appointmentBook = new AppointmentBook(name);
    }


    /**
     * Writes a message that there is a parameter missing from the request
     * @param response The message to the client specifying the error
     * @param key The parameter that is missing from the request
     * @throws IOException The response could not be generated
     */
    private void missingRequiredParameter( HttpServletResponse response, String key ) throws IOException
    {
        PrintWriter pw = response.getWriter();
        pw.println( Messages.missingRequiredParameter(key));
        pw.flush();
        
        response.setStatus( HttpServletResponse.SC_PRECONDITION_FAILED );
    }



    /**
     * Writes the contents of an appointment book out to a client using <code>PrettyPrinter</code>
     * @param name The owner of the appointment book
     * @param response The message to the client specifying the result
     * @throws IOException The response could not be generated
     */
    private void writeAppointmentBook( String name, HttpServletResponse response ) throws IOException
    {
        appointmentBook = this.data.get(name);
        PrintWriter pw = response.getWriter();


        if(appointmentBook != null)
        {
            PrettyPrinter prettyPrinter = new PrettyPrinter();
            String apptbook = prettyPrinter.buildAppointmentBookString(appointmentBook);

            pw.println(apptbook);
            pw.flush();
            response.setStatus(HttpServletResponse.SC_OK);
        }else {
            pw.println("No appointments for " + name);
            pw.flush();
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }


    /**
     * Writes a message to the server specifying what was searched for
     * @param name The name of an appointment books owner
     * @param begin The beginning of the range searched between
     * @param end The ending of the range searched between
     * @param response The message specifying the search
     * @param search The result of the search, all the appointments within the given range
     * @throws IOException The response could not be generated
     */
    private void writeOwnerAndInfo(String name, String begin, String end, HttpServletResponse response, String search) throws IOException
    {
        PrintWriter pw = response.getWriter();

        pw.println(Messages.ownerAndInfo(name, begin, end, search));
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);
    }


    /**
     * Writes the names of the owners with appointment books stored on the server
     * @param response The message specifying the owners names
     * @throws IOException The response could not be generated
     */
    private void writeAllOwners( HttpServletResponse response) throws IOException
    {
        PrintWriter pw = response.getWriter();
        pw.println(Messages.getMappingCount(data.size()));

        for(Map.Entry<String, AbstractAppointmentBook> entry : this.data.entrySet())
        {
            pw.println(Messages.formatAppointmentBook(entry.getKey()));
        }
    }


    /**
     * Get a value from the URL that coincides with a given parameter
     * @param param The parameter in question
     * @param request The URL that has been requested
     * @return The contents of the value
     */
    private String getParameter(String param, HttpServletRequest request)
    {
      String value = request.getParameter(param);

      if (value == null || "".equals(value)) {
        return null;

      } else {
        return value;
      }
    }



    /**
     * Searches for appointments in an appointment book that are within a given date range
     * @param response The result of the search, contains a code and message
     * @param name The owner of an appointment book to be searched
     * @param begin The beginning of the range to be searched
     * @param end The ending of the range to be searched
     * @throws IOException The response could not be established
     */
    private void searchOwnersAppointmentBookForDates(HttpServletResponse response, String name, String begin, String end) throws IOException
    {
        initializeAppointmentBook(name);
        PrintWriter pw = response.getWriter();

        //Need to make begin and end strings into proper date format.
        Date start, finish;
        String s, e;

        try{
            start = checkDateTimeFormat(begin);
            finish = checkDateTimeFormat(end);

            s = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(start);
            e = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(finish);

            start = checkDateTimeFormat(s);
            finish = checkDateTimeFormat(e);
        }catch(ParseException ex)
        {
            pw.println(ex.getMessage());
            pw.flush();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        PrettyPrinter prettyPrinter = new PrettyPrinter();

        String search = prettyPrinter.getAppointmentsBetweenBeginTimeAndEndTime(appointmentBook, start, finish);

        if(search != null && !search.equals(""))
        {
            writeOwnerAndInfo(name, s, e, response, search);

        }else{
            pw.println(Messages.noAppointmentsBetweenTimes(name, s, e));
            pw.flush();
        }
        response.setStatus(HttpServletResponse.SC_OK);

    }


    /**
     * Checks that a date and time match the format of mm/dd/yyyy hh:mm am/pm.
     *
     * @param dateTime  The date/time that needs to be checked.
     * @throws ParseException The date could not be parsed into proper format
     */
    public Date checkDateTimeFormat(String dateTime) throws ParseException
    {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy h:mm a");
        format.setLenient(false);

        return format.parse(dateTime);
    }


}