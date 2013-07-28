package edu.pdx.cs410J.forgie;

import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.ParserException;

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
      private final Map<String, String> data = new TreeMap<String, String>();
      AbstractAppointmentBook appointmentBook;


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
                    } else writeValue(name, response);
                }

            }else writeAllMappings(response);
    }





    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String name = getParameter("owner", request);
        if(name == null)
        {
            missingRequiredParameter(response, "owner");
            return;
        }

        initializeAppointmentBook(response, name);

        String appt = getParameter("appointment", request);
        if(appt == null)
        {
            missingRequiredParameter(response, "appointment");
            return;
        }

        addAppointment(response, appt);
        PrettyPrinter prettyPrinter = new PrettyPrinter();
        String apptbook = prettyPrinter.buildAppointmentFileString(appointmentBook);

        this.data.put(name, apptbook);

        response.setStatus( HttpServletResponse.SC_OK);
    }





    private void initializeAppointmentBook(HttpServletResponse response, String name) throws IOException
    {
        String apptbook = this.data.get(name);
        if(apptbook != null){
            TextParser textParser = new TextParser(apptbook);

            try{
                appointmentBook = textParser.parse();
            }catch (ParserException ex){
                malformattedAppointment(response);
            }
        }else appointmentBook = new AppointmentBook(name);
    }




    private void malformattedAppointment(HttpServletResponse response) throws  IOException
    {
        PrintWriter pw = response.getWriter();
        pw.println(Messages.malformattedAppointment());
    }



    private void missingRequiredParameter( HttpServletResponse response, String key ) throws IOException
    {
        PrintWriter pw = response.getWriter();
        pw.println( Messages.missingRequiredParameter(key));
        pw.flush();
        
        response.setStatus( HttpServletResponse.SC_PRECONDITION_FAILED );
    }



    private void writeSearch(String search, HttpServletResponse response) throws IOException
    {
        PrintWriter pw = response.getWriter();

            pw.println(Messages.searchMessage(search));
            pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);
    }



    private void writeValue( String name, HttpServletResponse response ) throws IOException
    {
        String value = this.data.get(name);
        PrintWriter pw = response.getWriter();

        if(value != null)
        {
            pw.println(value);
            pw.flush();
            response.setStatus( HttpServletResponse.SC_OK );
        } else {
            pw.println(Messages.noAppointmentBookForOwner(name));
            pw.flush();
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }




    private void writeOwnerAndInfo(String name, String begin, String end, HttpServletResponse response, String search) throws IOException
    {
        PrintWriter pw = response.getWriter();

        pw.println(Messages.ownerAndInfo(name, begin, end, search));
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);
    }




    private void writeAllMappings( HttpServletResponse response) throws IOException
    {
        PrintWriter pw = response.getWriter();

        pw.println(Messages.getMappingCount( data.size() ));

        for (Map.Entry<String, String> entry : this.data.entrySet()) {
           pw.println(Messages.formatAppointmentBook(entry.getKey()));
        }

        pw.flush();


        response.setStatus( HttpServletResponse.SC_OK );

    }



    private String getParameter(String name, HttpServletRequest request)
    {
      String value = request.getParameter(name);
      if (value == null || "".equals(value)) {
        return null;

      } else {
        return value;
      }
    }




    private void addAppointment(HttpServletResponse response, String appt)throws IOException
    {
        String [] tokens = appt.split("~");
        String description = tokens[0];
        String beginDate = tokens[1];
        String endDate = tokens[2];
        Appointment appointment;

        try{
            appointment = new Appointment(description, checkDateTimeFormat(beginDate), checkDateTimeFormat(endDate));
        }catch (ParseException ex){
            throw new IOException (ex.getMessage());
        }

        appointmentBook.addAppointment(appointment);

        response.setStatus(HttpServletResponse.SC_OK);
    }



    /**
     * Checks that a date and time match the format of mm/dd/yyyy hh:mm am/pm.
     *
     * @param dateTime  The date/time that needs to be checked.
     */
    public Date checkDateTimeFormat(String dateTime) throws ParseException
    {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy h:mm a");
        format.setLenient(false);

        return format.parse(dateTime);
    }







    public String getAppointmentsBetweenBeginTimeAndEndTime(Date beginDate, Date endDate)
    {
        Iterator iterator = appointmentBook.getAppointments().iterator();
        StringBuilder sb = new StringBuilder();
        Appointment element;
        int count = 0;

        while(iterator.hasNext())
        {
            element = (Appointment) iterator.next();
            if(element.getBeginTime().compareTo(beginDate) >= 0)
            {
                if(element.getEndTime().compareTo(endDate) <= 0)
                {
                    sb.append("\n<");
                    sb.append(++count);
                    sb.append(">\nDescription:\n\t");
                    sb.append(element.getDescription());
                    sb.append("\n\nStarts:    ");
                    sb.append(element.getBeginTimeString());
                    sb.append("\nEnds:      ");
                    sb.append(element.getEndTimeString());
                    sb.append("\nDuration:  ");
                    sb.append(element.getDuration());
                    sb.append(" minutes\n");
                }
            }
        }

        return sb.toString();
    }


    private void searchOwnersAppointmentBookForDates(HttpServletResponse response, String name, String begin, String end) throws IOException {
        initializeAppointmentBook(response, name);
        PrintWriter pw = response.getWriter();
        //Need to make begin and end strings into proper date format.
        Date start;
        Date finish;
        String s;
        String e;
        try{
            start =  checkDateTimeStringFormat(response, "beginTime", begin);
            finish = checkDateTimeStringFormat(response, "EndTime", end);


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




        String search = getAppointmentsBetweenBeginTimeAndEndTime(start, finish);


        if(search != null && !search.equals(""))
        {

            writeOwnerAndInfo(name, s, e, response, search);

            // writeSearch(search, response);
        }else{
            pw.println(Messages.noAppointmentsBetweenTimes(name, s, e));
        }

    }




    /**
     * Checks that a date and time match the format of mm/dd/yyyy hh:mm am/pm
     *
     * @param string    Identify if this is the start date/time or end date/time.
     * @param dateTime  The date/time that needs to be checked.
     */
    static Date checkDateTimeStringFormat(HttpServletResponse response, String string, String dateTime)throws ParseException
    {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy h:mm a");
        format.setLenient(false);

        date = format.parse(dateTime);

        return date;
    }

}