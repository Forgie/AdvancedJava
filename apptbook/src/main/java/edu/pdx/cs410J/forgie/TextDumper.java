package edu.pdx.cs410J.forgie;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.*;

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
     * Dumps an Appointment Book to a specified file
     *
     * @param book An appointment book to be stored in a specified file
     * @throws  IOException
     */
    //public void dump(AbstractAppointmentBook book) throws IOException {
       // OutputStream file = new FileOutputStream(textFile);
       // OutputStreamWriter buffer = new OutputStreamWriter(file);
       // Writer writer = new BufferedWriter(buffer);

       // writer.writeln(book.getOwnerName() + "~" + book.getAppointments());

    /*
    Writer writer = null;

try {
    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("filename.txt"), "utf-8"));
    writer.writeln("Something");
    } catch (IOException ex){
        // report
   } finally{
        try {
        writer.close();
        } catch (Exception ex) {}
   }
     */

   // }

    public void dump(AbstractAppointmentBook book) throws IOException{

        try{
            System.out.println("Got here");
            OutputStream file = new FileOutputStream(File);
            OutputStreamWriter buffer = new OutputStreamWriter(file);
            Writer writer = new BufferedWriter(buffer);
            try{
                writer.write(book.toString() + "~" + book.getAppointments());
            }finally{
                writer.close();
            }
        }catch(IOException ex){
            throw ex;
        }
    }
}
