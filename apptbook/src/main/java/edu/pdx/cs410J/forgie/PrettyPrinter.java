package edu.pdx.cs410J.forgie;

import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookDumper;

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
public class PrettyPrinter implements AppointmentBookDumper {
    private String File;
    private StringBuilder builder;


    public PrettyPrinter(String fileName){
        this.File = fileName;
        this.builder = new StringBuilder();
    }


    /**
     * Writes an Appointment Book to a specified file.
     *
     * @param book An appointment book to be stored in a specified file.
     * @throws  IOException    Data cannot be written to the text file.
     */
    public void dump(AbstractAppointmentBook book) throws IOException {

        OutputStreamWriter output = null;

        buildAppointmentFileString(book);

        try{
            output = new OutputStreamWriter(new FileOutputStream(this.File));
            output.write(this.builder.toString());
        } finally{
            if(output != null)
                output.close();
        }
    }


    /**
     * <code>print</code> prints out an AppointmentBook in a pretty format.
     *
     * @param book  The AppointmentBook to be displayed.
     */
    public void print(AbstractAppointmentBook book){
        buildAppointmentFileString(book);

        System.out.print(this.builder);
    }



    /**
     * <code>buildAppointmentFileString</code> turns the contents of an
     * AppointmentBook into a string that can be written to a file.
     * @param book      An appointment book to be converted into a string.
     */
    private void buildAppointmentFileString(AbstractAppointmentBook book) {
        Iterator iterator = book.getAppointments().iterator();
        Appointment element;

        this.builder.append("OWNER NAME: ");
        this.builder.append(book.getOwnerName());
        this.builder.append("\n\t******APPOINTMENTS******\n");
        int count = 0;

        while(iterator.hasNext()){
            element = (Appointment) iterator.next();

            this.builder.append("\n<");
            this.builder.append(++count);
            this.builder.append(">\nDescription:\n\t");
            this.builder.append(element.getDescription());
            this.builder.append("\n\nStarts:    ");
            this.builder.append(element.getBeginTimeString());
            this.builder.append("\nEnds:      ");
            this.builder.append(element.getEndTimeString());
            this.builder.append("\nDuration:  ");
            this.builder.append(element.getDuration());
            this.builder.append(" minutes\n");
        }
    }

}
