package edu.pdx.cs410J.forgie.server;

import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Iterator;

/**
 * <code>PrettyPrinter</code> prints out a nicely formatted and readable text file of AppointmentBook.
 *
 * @author Shawn Forgie
 * Date: 7/8/13
 */
public class PrettyPrinter implements AppointmentBookDumper
{
    private StringBuilder sb;
    private String File;

    public PrettyPrinter(String fileName)
    {
        this.File = fileName;
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

        buildAppointmentBookString(book);

        try {
            output = new OutputStreamWriter(new FileOutputStream(this.File));
            output.write(this.sb.toString());
        } finally {
            if(output != null) output.close();
        }
    }


    /**
     * <code>print</code> prints out an AppointmentBook in a pretty format.
     *
     * @param book  The AppointmentBook to be displayed.
     */
    public void print(AbstractAppointmentBook book)
    {
        buildAppointmentBookString(book);
        System.out.print(this.sb);
        System.out.println("\n");
    }



    /**
     * <code>buildAppointmentBookString</code> turns the contents of an
     * AppointmentBook into a string that can be written to an output.
     * @param book      An appointment book to be converted into a string.
     */
    public String buildAppointmentBookString(AbstractAppointmentBook book)
    {
        Iterator iterator = book.getAppointments().iterator();
        Appointment element;

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



    /**
     * Searches for appointments with dates between specific times and builds a pretty formatted string
     * @param book The appointment book to be searched
     * @param beginDate The beginning time to search between
     * @param endDate The ending time to search between
     * @return The string of appointments between specific times nicely formatted
     */
    public String getAppointmentsBetweenBeginTimeAndEndTime(AbstractAppointmentBook book, Date beginDate, Date endDate)
    {
        Iterator iterator = book.getAppointments().iterator();
        Appointment element;
        int count = 0;

        while(iterator.hasNext())
        {
            element = (Appointment) iterator.next();
            if(element.getBeginTime().compareTo(beginDate) >= 0)
            {
                if(element.getEndTime().compareTo(endDate) <= 0)
                {
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
            }
        }

        return sb.toString();
    }
}