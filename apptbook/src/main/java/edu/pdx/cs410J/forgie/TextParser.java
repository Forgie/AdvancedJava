package edu.pdx.cs410J.forgie;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 *
 * @author Shawn Forgie
 * Date: 7/5/13
 */
public class TextParser implements AppointmentBookParser{
   TreeMap<String, AbstractAppointment> map;

    public TextParser(String fileName){

        try{
            InputStream file = new FileInputStream(fileName);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);
            try{
                map = (TreeMap<String, AbstractAppointment>) input.readObject();

            }catch(ClassNotFoundException ex){
                System.err.println("Text file is malformatted");
                System.exit(1);
            }finally{
                input.close();
            }
        }catch(FileNotFoundException ex){

        }catch(IOException e){
        }


        System.out.println("HI");
    }



    public AbstractAppointmentBook parse() throws ParserException {
        /*
        String text = "Beggars in Spain*Nancy Kress*1992";
        StringTokenizer tokenizer = new StringTokenizer(text,"*");
        String title = tokenizer.nextToken();
        String author = tokenizer.nextToken();
        String year = tokenizer.nextToken();
        */
        String Description;
        String BeginTime;
        String EndTime;
        String Owner = "hi";
        String [] appt = new String[10];

        StringTokenizer tokenizer = new StringTokenizer(Owner, "[");
        Owner = tokenizer.nextToken();
        int i = 0;
        while(tokenizer.hasMoreTokens()){
            appt[i] = tokenizer.nextToken();
        }




        return null;
    }
}



        /*
                //Restore providerDirectory Object
        System.out.println("Restoring Provider Directory");
        try{
            //use buffering
            InputStream file = new FileInputStream( "ProviderDirectory.ser" );
            InputStream buffer = new BufferedInputStream( file );
            ObjectInput input = new ObjectInputStream( buffer );
            try{
                map = (TreeMap<String,Provider>) input.readObject();
            }
            finally{
                input.close();
            }
        }
        catch(ClassNotFoundException ex){
            System.out.println("Cannot perform input. Class not found.");
        }
        catch(IOException ex){
            System.out.println("Cannot perform input.");
            ex.printStackTrace();
        }
         */