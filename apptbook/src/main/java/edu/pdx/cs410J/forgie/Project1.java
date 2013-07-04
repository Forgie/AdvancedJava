package edu.pdx.cs410J.forgie;

import edu.pdx.cs410J.AbstractAppointmentBook;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <code>Project1</code> is the main class for the CS410J Project 1
 *
 * @author Shawn Forgie
 */
public class Project1 {

    private static String owner;
    private static String description;
    private static String beginTime;
    private static String endTime;
    private static Date date;
    private static SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy H:m");


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


    private static final String README = "\tProject1 parses 6-8 command line strings, two options are -print and -README. -print prints the\n" +
            "description of the appointment that has been added. -README prints this description and exits. The other\n" +
            "arguments (in this order: Owner, Description, BeginTime, EndTime) are used to create an appointment that\n" +
            "will be stored in an appointment book of an 'Owner'. An appointment book consists of the owners name and\n" +
            "a collection of appointments. An appointment consists of; a 'description' describing the appointment, a\n" +
            "'beginTime' indicating the start date and time of the appointment, and an 'endTime' indicating the date\n" +
            "and time the appointment will end.\n";






    /**
     * Main method that parses command line arguments.
     * Creates appointments and appointment books for
     * owners.
     *
     * @param args  [options] -print, -README [arguments] Owner, Description, Begin Date, Begin Time, End Date, End Time
     */
    public static void main(String[] args) {
    //Class c = AbstractAppointmentBook.class;  // Refer to one of Dave's classes so that we can be sure it is on the classpath

      boolean print = false;
      int start = 0;

      //Date date = null;
      //SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy H:m");
      format.setLenient(false);

      if(args.length == 0){
          clearScreen();
          System.err.println("Missing command line arguments\n" + USAGE);
          System.exit(1);
      }else{
          if(args.length == 1){
              clearScreen();
              System.err.println("Missing description");
              printArgsEntered(args);
              System.err.println(USAGE);
              System.exit(1);
          }else{
              if(args.length == 2){
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


        endTime = args[start++] + " " + args[start++];


        //Check that end date is in correct format
        checkDateTimeFormat("End", endTime);







        clearScreen();


        printArgsEntered(args);


        System.exit(0);
  }


    /**
     * Checks that a date and time match the format of mm/dd/yyyy hh:mm (0-24hr)
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
                                      + "\nShould be in the form: mm/dd/yyyy hh:mm (0-24hr)");
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
        System.out.println(README + "\n" + USAGE);
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