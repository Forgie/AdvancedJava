package edu.pdx.cs410J.forgie;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <code>Project1</code> is the main class for the CS410J Project 1 and
 * utilizes {@link Appointment} and {@link AppointmentBook}
 *
 * @author Shawn Forgie
 */
public class Project2 {

    static String owner;
    static String description;
    static String beginTime;
    static String endTime;
    static String textFile = "";
    static Date date;
    static SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy H:m");
    static AbstractAppointment appointment;
    static AbstractAppointmentBook appointmentBook;



    static final String USAGE = "\nargs are (in this order):\n" +
            "  owner\n" +
            "  description\n" +
            "  beginDate\n" +
            "  beginTime\n" +
            "  endDate\n" +
            "  endTime\n" +
            "options are (at the beginning in any order):\n" +
            "  -print\n" +
            "  -README\n"  +
            "  -textFile <filename>\n";


    private static final String README = "usage: java edu.pdx.cs410J.<login-id>.Project1 [options] <args>\n" +
            USAGE +
            "\n"+
            "    Project1 parses 6-8 command line arguments that are used to create an appointment. The appointment\n" +
            "will be stored in an appointment book of an 'Owner'. An appointment book consists of the owners name and\n" +
            "a collection of appointments. An appointment consists of; a 'description' describing the appointment, a\n" +
            "'beginTime' indicating the start date and time of the appointment, and an 'endTime' indicating the date\n" +
            "and time the appointment will end.";






    /**
     * Main method that parses command line arguments.
     * Creates appointments and appointment books for
     * owners.
     *
     * @param args  [options] -print, -README [arguments] Owner, Description, Begin Date, Begin Time, End Date, End Time
     */
    public static void main(String[] args) {

      boolean print = false;
      String missingArgs = "";
      int i, j = 0;
      int start = 0;
      int count = args.length;
      format.setLenient(false);


        if(args.length > 0){
            if(args.length >= 3){
                j = 3;
            }else{
                if(args.length == 2){
                    j = 2;
                }else{
                    if(args.length == 1)
                    j = 1;
                }
            }
            //check for options at start
            for(i = 0; i < j; i++){
                if(args[i].equals("-print")){
                    print = true;
                    ++start;
                }
                if(args[i].equals("-README")){
                    readMe();
                }
                if(args[i].equals("-textFile")){
                    if(args.length > (i+1)){
                        textFile = args[++i];
                        start += 2;
                    }
                }
            }
            if(start > 0)
                count = args.length - start;
        }

     if(args.length > 3){
         if(args[3].equals("-README")){
             readMe();
         }
     }
     if(count > 6){
         clearScreen();
         System.err.println("Too many command line arguments.");
         printArgsEntered(args);
         System.err.println(USAGE);
         System.exit(1);
     }

     switch(count){
         case 0:
                missingArgs = " owners name,";
         case 1:
                missingArgs += " description,";
         case 2:
                missingArgs += " begin date,";
         case 3:
                missingArgs += " begin time,";
         case 4:
                missingArgs += " end date,";
         case 5:
             clearScreen();
             System.err.println("Missing command line arguments: " + missingArgs + " end time");
             printArgsEntered(args);
             System.err.println(USAGE);
             System.exit(1);
     }


        owner = args[start++];
        description = args[start++];
        beginTime = args[start++] + " " + args[start++];


        //Check that begin date is in correct format
        checkDateTimeFormat("Begin", beginTime);


        endTime = args[start++] + " " + args[start];


        //Check that end date is in correct format
        checkDateTimeFormat("End", endTime);


        TextParser textParser = new TextParser(textFile + ".txt");
       /*
        try{
            appointmentBook = textParser.parse();
        }catch(ParserException ex){
            System.err.println("Text file is malformatted");
            System.exit(1);
        }
        */

        appointment = new Appointment(description, beginTime, endTime);
        appointmentBook = new AppointmentBook(owner);
        appointmentBook.addAppointment(appointment);


        TextDumper textDumper = new TextDumper(textFile + ".txt");

        try{
            textDumper.dump(appointmentBook);
        }catch(IOException ex){
            System.err.println("Appointment could not be written to " + textFile);
            System.exit(0);
        }



        if(print){
            clearScreen();
            System.out.println(appointment.toString());
        }



        System.exit(0);
  }


    /**
     * Checks that a date and time match the format of mm/dd/yyyy hh:mm (0:00-23:59)
     *
     * @param string    Identify if this is the start date/time or end date/time
     * @param dateTime  The date/time that needs to be checked
     */
    static void checkDateTimeFormat(String string, String dateTime) {
        try{
            date = format.parse(dateTime);
        }catch(ParseException ex){
            clearScreen();
            System.err.println(string + " date/time format is incorrect: " + dateTime
                    + "\nShould be in the form: mm/dd/yyyy hh:mm (0:00-23:59)");
            System.exit(1);
        }
    }

    /**
     * Displays all command line arguments the user entered.
     * @param args      Command line arguments entered by user.
     */
    private static void printArgsEntered(String[] args) {
        System.out.println("You entered:");
        for (String arg : args) {
            System.out.println("\t" + arg);
        }
    }


    /**
     * Prints a brief, concise, and nicely-formatted textual description of what project1 does.
     */
    static void readMe() {
        clearScreen();
        System.out.printf("%60s" , "Project1-README\n");
        System.out.printf("%100s", "Shawn Forgie\n\n");
        System.out.println(README);
        System.exit(0);
    }


    /**
     * Clears the command line screen, makes it more readable.
     */
    static void clearScreen() {
        for(int i = 0; i <= 80; i++){
            System.out.println();
        }
    }
}