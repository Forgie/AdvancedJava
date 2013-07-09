package edu.pdx.cs410J.forgie;

import edu.pdx.cs410J.AbstractAppointment;
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


    public PrettyPrinter(String fileName){
        this.File = fileName;
    }

    public void dump(AbstractAppointmentBook book) throws IOException {
        Iterator iterator = book.getAppointments().iterator();
        Appointment element;
        OutputStreamWriter output = null;


        int count = 0;
        try{
            output = new OutputStreamWriter(new FileOutputStream(this.File));
            output.write("OWNER NAME: " + book.getOwnerName() +
                    "\n\t******APPOINTMENTS******\n");

            while(iterator.hasNext()){
                element = (Appointment) iterator.next();
                output.write("\n<" + ++count + ">" + "\nDescription:\n\t" + element.getDescription() +
                      "\n\nStarts:    " + element.getBeginTimeString() +
                        "\nEnds:      " + element.getEndTimeString() +
                        "\nDuration:  " + element.getDuration() + " minutes\n");
            }
        }catch(IOException ex){
            throw ex;
        }finally{
            if(output != null)
                output.close();
        }
    }

}
