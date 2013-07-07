package edu.pdx.cs410J.forgie;

import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Shawn Forgie
 * Date: 7/5/13
 */
public class TextParser implements AppointmentBookParser{
    private String stringToParse;
    private String fileName;
    private Date date;
    private SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy H:m");

    public TextParser(String file){
        this.fileName = file;
        this.stringToParse = null;


    }

    public boolean checkDateTimeFormat(String dateTime) {
        format.setLenient(false);
        try{
            date = format.parse(dateTime);
        }catch(ParseException ex){
            return false;
        }
        return true;
    }

    public AbstractAppointmentBook parse() throws ParserException {

        String line;
        String Description;
        String BeginDate;
        String EndDate;
        String Owner;
        Appointment appointment;
        AbstractAppointmentBook appointmentBook;

        try{
            BufferedReader input = new BufferedReader(new FileReader(this.fileName));
            try{

                    while((line = input.readLine()) != null){
                            this.stringToParse += line;
                    }

                    if(this.stringToParse == null)
                        return null;

            }catch(IOException ex){
                System.err.println("Could not read file.");
                System.exit(1);
            }
        }catch(FileNotFoundException ex){
            return null;
        }


        for(int i = 0; i < 80; ++i){
            System.out.println();
        }



        String delimiter = "[|]";
        String [] tokens = stringToParse.split(delimiter);
        Owner = tokens[1];
        appointmentBook = new AppointmentBook(Owner);





       String apptDelimiter = "[>]";

       String [] appts = stringToParse.split(apptDelimiter);


        delimiter = "[$]";
        for(int i = 1; i < appts.length; i++){

            tokens = appts[i].split(delimiter);

            Description = tokens[1];
            BeginDate = tokens[3];
            EndDate = tokens[5];

            if(checkDateTimeFormat(BeginDate) && checkDateTimeFormat(EndDate)){
                appointment = new Appointment(Description, BeginDate , EndDate);
                appointmentBook.addAppointment(appointment);
            }else{
                throw new ParserException("File has a malformatted date.");
            }
        }

        return appointmentBook;
    }
}
