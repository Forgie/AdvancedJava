package edu.pdx.cs410J.forgie;

import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookDumper;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;

/**
 * <code>PrettyPrinter</code> prints out a nicely formatted and readable text file of AppointmentBook.
 *
 * @author Shawn Forgie
 * Date: 7/8/13
 */
public class PrettyPrinter implements AppointmentBookDumper
{
    private String host;
    private int port;
    private StringBuilder sb;

    public PrettyPrinter(String hostName, int portNum)
    {
        this.host = hostName;
        this.port = portNum;
        this.sb = new StringBuilder();
    }

    public PrettyPrinter()
    {
        this.sb = new StringBuilder();

    }


    /**
     * Writes an Appointment Book to a specified file.
     *
     *
     * @param book An appointment book to be stored in a specified file.
     * @throws  IOException    Data cannot be written to the text file.
     */
    public void dump(AbstractAppointmentBook book) throws IOException
    {

        OutputStreamWriter output = null;



        AppointmentBookRestClient client = new AppointmentBookRestClient(host, port);

        HttpRequestHelper.Response response;



        response = client.addKeyValuePair(book.getOwnerName(), buildAppointmentFileString(book));

        System.out.print(response.getContent());

        //AppointmentBookServlet servlet = new AppointmentBookServlet(builder.toString());
                //client.addKeyValuePair(book.getOwnerName(), builder.toString());

        /*
        try {
            output = new OutputStreamWriter(new FileOutputStream(this.File));
            output.write(this.builder.toString());
        } finally {
            if(output != null) output.close();
        } */
    }


    /**
     * <code>print</code> prints out an AppointmentBook in a pretty format.
     *
     * @param book  The AppointmentBook to be displayed.
     */
    public void print(AbstractAppointmentBook book)
    {
        buildAppointmentFileString(book);
        System.out.print(this.sb);
        System.out.println("\n");
    }



    /**
     * <code>buildAppointmentFileString</code> turns the contents of an
     * AppointmentBook into a string that can be written to a file.
     * @param book      An appointment book to be converted into a string.
     */
    public String buildAppointmentFileString(AbstractAppointmentBook book)
    {
        Iterator iterator = book.getAppointments().iterator();
        Appointment element;

        //this.builder.append("OWNER NAME: ");
        //this.builder.append(book.getOwnerName());
        this.sb.append("\t\t");
        this.sb.append(book.getOwnerName());
        this.sb.append("'s Appointments\n");
        int count = 0;

        while(iterator.hasNext())
        {
            element = (Appointment) iterator.next();

            this.sb.append("\n<");
            this.sb.append(++count);
            this.sb.append(">\nDescription:\n\t");
            this.sb.append(element.getDescription());
            this.sb.append("\n\nStarts:    ");
            this.sb.append(element.getBeginTimeString());
            this.sb.append("\nEnds:      ");
            this.sb.append(element.getEndTimeString());
            this.sb.append("\nDuration:  ");
            this.sb.append(element.getDuration());
            this.sb.append(" minutes\n");
        }
        return sb.toString();
    }
}