package edu.pdx.cs410J.forgie;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <code>Project1</code> is the main class for the CS410J Project 1 and
 * utilizes {@link Appointment} and {@link AppointmentBook}
 *
 * @author Shawn Forgie
 */
public class Project1 {

    static String owner;
    static String description;
    static String beginTime;
    static String endTime;
    static Date date;
    static SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy H:m");
    static AbstractAppointment appointment;
    static AbstractAppointmentBook appointmentBook;

    static final String USAGE = "args are (in this order):\n" +
            "  owner\n" +
            "  description\n" +
            "  beginDate\n" +
            "  beginTime\n" +
            "  endDate\n" +
            "  endTime\n" +
            "options are (at the beginning in any order):\n" +
            "  -print\n" +
            "  -README";


    private static final String README = "usage: java edu.pdx.cs410J.<login-id>.Project1 [options] <args>\n" +
            USAGE +
            "\n\n"+
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
      int start = 0;
      format.setLenient(false);

      if(args.length == 0){
          clearScreen();
          System.err.println("Missing command line arguments\n" + USAGE);
          System.exit(1);
      }else{
          if(args.length == 1){
              if(args[0].equals("-README")){
                  readMe();
              }

              clearScreen();
              System.err.println("Missing description");
              printArgsEntered(args);
              System.err.println(USAGE);
              System.exit(1);
          }else{
              if(args.length == 2){
                  if(args[0].equals("-REAMDE") || args[1].equals("-README")){
                      readMe();
                  }

                  clearScreen();
                  System.err.println("Missing begin date");
                  printArgsEntered(args);
                  System.err.println(USAGE);
                  System.exit(1);
              }else{
                  if(args.length == 3){
                      clearScreen();
                      System.err.println("Missing begin time");
                      printArgsEntered(args);
                      System.err.println(USAGE);
                      System.exit(1);
                  }else{
                      if(args.length == 4){
                          clearScreen();
                          System.err.println("Missing end date");
                          printArgsEntered(args);
                          System.err.println(USAGE);
                          System.exit(1);
                      }else{
                          if(args.length == 5){
                              clearScreen();
                              System.err.println("Missing end time");
                              printArgsEntered(args);
                              System.err.println(USAGE);
                              System.exit(1);
                          }else{
                              if(args.length == 7){
                                  if(args[0].equals("-print")){
                                      print = true;
                                      start = 1;
                                  }else{
                                      if(args[0].equals("-README")){
                                          readMe();
                                      }else{
                                          clearScreen();
                                          System.err.println(args[0] + " is an invalid option\n" + USAGE);
                                          System.exit(1);
                                      }
                                  }
                              }else{
                                  if(args.length == 8){
                                      if((args[0].equals("-print") && args[1].equals("-README")) || (args[0].equals("-README") && args[1].equals("-print"))){
                                          readMe();
                                      }else{
                                          clearScreen();
                                          System.err.println("An invalid option has been entered\n"
                                                  + args[0] + "\t"
                                                  + args[1] + "\n"
                                                  + USAGE);
                                          System.exit(1);
                                      }
                                  }else{
                                      if(args.length > 8){
                                          System.err.println("Too many command line arguments" );
                                          printArgsEntered(args);
                                      }
                                  }
                              }
                          }
                      }
                  }
              }
          }
      }


        owner = args[start++];
        description = args[start++];
        beginTime = args[start++] + " " + args[start++];


        //Check that begin date is in correct format
        checkDateTimeFormat("Begin", beginTime);


        endTime = args[start++] + " " + args[start];


        //Check that end date is in correct format
        checkDateTimeFormat("End", endTime);


        appointment = new Appointment(description, beginTime, endTime);
        appointmentBook = new AppointmentBook(owner);
        appointmentBook.addAppointment(appointment);

        clearScreen();
        if(print){
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