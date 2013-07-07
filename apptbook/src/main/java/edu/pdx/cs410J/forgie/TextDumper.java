package edu.pdx.cs410J.forgie;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.*;
import java.util.Iterator;

/**
 * <code>TextDumper</code> writes an appointment book to an external file designated by a user.
 *
 * @author Shawn Forgie
 * Date: 7/5/13
 */
public class TextDumper implements AppointmentBookDumper {
    String File;

    public TextDumper(String fileName){
        this.File = fileName;
    }


    /**
     * Writes an Appointment Book to a specified file.
     *
     * @param book An appointment book to be stored in a specified file.
     * @throws  IOException    Data cannot be written to the text file.
     */
    public void dump(AbstractAppointmentBook book) throws IOException{

        Iterator iterator = book.getAppointments().iterator();
        AbstractAppointment element;
        OutputStreamWriter output = null;


        int count = 0;
        try{
            output = new OutputStreamWriter(new FileOutputStream(this.File));
            output.write("OWNER NAME: |" + book.getOwnerName() + "|" +
                         "\n\t******APPOINTMENTS******\n");

            while(iterator.hasNext()){
                element = (AbstractAppointment) iterator.next();
                output.write("\n<" + ++count + ">" + "\nDescription: $" + element.getDescription() + "$" +
                             "\nStarts: $" + element.getBeginTimeString() + "$" +
                             "\nEnds: $" + element.getEndTimeString() + "$");
                }
        }catch(IOException ex){
                throw ex;
        }finally{
            if(output != null)
            output.close();
        }
    }






}
