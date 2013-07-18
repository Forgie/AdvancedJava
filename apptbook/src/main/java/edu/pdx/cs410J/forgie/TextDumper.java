package edu.pdx.cs410J.forgie;

import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.*;
import java.util.*;

/**
 * <code>TextDumper</code> writes an appointment book to an external file designated by a user.
 *
 * @author Shawn Forgie
 * Date: 7/5/13
 */
public class TextDumper implements AppointmentBookDumper
{
    private String File;
    private StringBuilder builder;

    public TextDumper(String fileName)
    {
        this.File = fileName;
        this.builder = new StringBuilder();
    }


    /**
     * Writes an Appointment Book to a specified file.
     *
     * @param book An appointment book to be stored in a specified file.
     * @throws  IOException    Data cannot be written to the text file.
     */
    public void dump(AbstractAppointmentBook book) throws IOException
    {
        OutputStreamWriter output = null;

        buildAppointmentFileString(book);

        try {
            output = new OutputStreamWriter(new FileOutputStream(this.File));
            output.write(this.builder.toString());
        } finally {
            if(output != null) output.close();
        }
    }


    /**
     * <code>buildAppointmentFileString</code> turns the contents of an
     * AppointmentBook into a string that can be written to a file.
     * @param book      An appointment book to be converted into a string.
     */
    private void buildAppointmentFileString(AbstractAppointmentBook book)
    {
        Iterator iterator = book.getAppointments().iterator();
        Appointment element;

        this.builder.append("OWNER NAME: |");
        this.builder.append(book.getOwnerName());
        this.builder.append("|");
        int count = 0;

        while(iterator.hasNext())
        {
            element = (Appointment) iterator.next();

            this.builder.append("\n<");
            this.builder.append(++count);
            this.builder.append(">\nDescription: $");
            this.builder.append(element.getDescription());
            this.builder.append("$\nStarts: $");
            this.builder.append(element.getBeginTimeString());
            this.builder.append("$\nEnds: $");
            this.builder.append(element.getEndTimeString());
            this.builder.append("$");
        }
    }
}
