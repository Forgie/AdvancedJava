package edu.pdx.cs410J.forgie;

import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <code>TextParser</code> receives a file name for a text file and tries to parse it into an Appointment book.
 *
 * @author Shawn Forgie
 *
 * Date: 7/5/13
 */
public class TextParser implements AppointmentBookParser
{
    private String stringToParse;
    private String fileName;



    /**
     * Constructor for TextParser initializes the file name to access.
     *
     * @param file      The file name of a text file.
     */
    public TextParser(String file)
    {
        this.fileName = file;
        this.stringToParse = null;


    }



    /**
     * <code>parse</code> Reads input from a text file and creates Linked List of appointments.
     *
     * @return AbstractAppointmentBook An appointment book is generated and returned.
     * @throws ParserException         If the text file is not formatted correctly and cannot be parsed an exception is thrown.
     */
    public AbstractAppointmentBook parse() throws ParserException
    {
        AbstractAppointmentBook appointmentBook;

        if (getStringToParseFromFile()) return null;

        clearScreen();

        appointmentBook = parseAppointmentBookStoredInAFile();

        return appointmentBook;
    }

    private void clearScreen() {
        for(int i = 0; i < 80; ++i) System.out.println();
    }

    /**
     * Parses the string created from <code>getStringToParseFromFile</code> and creates an <code>AppointmentBook</code>.
     *
     * @return
     *      An AppointmentBook if the file is properly formatted and an <code>AppointmentBook</code> has been created.
     * @throws ParserException
     *      If the file is malformatted an exception is thrown.
     */
    private AbstractAppointmentBook parseAppointmentBookStoredInAFile() throws ParserException
    {
        String Owner;
        AbstractAppointmentBook appointmentBook;
        String Description;
        String BeginDate;
        String EndDate;
        Appointment appointment;

        String delimiter = "[|]";
        String [] tokens = stringToParse.split(delimiter);
        Owner = tokens[1];
        appointmentBook = new AppointmentBook(Owner);

        String apptDelimiter = "[>]";

        String [] appts = stringToParse.split(apptDelimiter);


        delimiter = "[$]";
        for(int i = 1; i < appts.length; i++)
        {
             tokens = appts[i].split(delimiter);
             try {
                 Description = tokens[1];
                 BeginDate = tokens[3];
                 EndDate = tokens[5];

                 try {
                     appointment = new Appointment(Description, checkDateTimeFormat(BeginDate) , checkDateTimeFormat(EndDate));
                 } catch (ParseException e) {
                     throw new ParserException("File has a malformatted date.");
                 }
                     appointmentBook.addAppointment(appointment);

             } catch (ArrayIndexOutOfBoundsException ex) {
                 throw new ParserException("Incomplete appointment.");
             }
         }
        return appointmentBook;
    }


    /**
     * Reads a text file and stores the text as a string using <code>StringBuilder</code>.
     *
     * @return
     *      returns a boolean true if the file does not exist and false if a string is created.
     */
    private boolean getStringToParseFromFile()
    {
        String line;
        try {
            BufferedReader input = new BufferedReader(new FileReader(this.fileName));
            try {
                    while((line = input.readLine()) != null)
                    {
                            this.stringToParse += line;
                    }

                    if(this.stringToParse == null)
                        return true;

            } catch (IOException ex) {
                System.err.println("Could not read file.");
                System.exit(1);
            }
        } catch (FileNotFoundException ex) {
            return true;
        }
        return false;
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
}
