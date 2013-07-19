package edu.pdx.cs410J.forgie;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <code>Project3</code> is the main class for the CS410J Project 2 and
 * utilizes {@link Appointment}, {@link AppointmentBook}, {@link TextDumper} and {@link TextParser}
 * to store and read appointments in text files.
 *
 * @author Shawn Forgie
 */
public class Project3
{
    static String owner;
    static String description;
    static String time;

    static String textFile = "";
    static String prettyFileName = "";
    static int start = 0;

    static Date beginDate;
    static Date endDate;

    static boolean print = false;
    static boolean filename = false;
    static boolean pretty = false;
    static boolean prettyFile = false;


    static AbstractAppointment appointment;
    static AbstractAppointmentBook appointmentBook = null;
    static PrettyPrinter prettyPrinter = new PrettyPrinter(null);


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
            "  -textFile <filename>\n" +
            "  -pretty <filename> or -\n";


    private static final String README = "usage: java edu.pdx.cs410J.<login-id>.Project3 [options] <args>\n" +
            USAGE +
            "\n"+
            "    Project3 parses 6-10 command line arguments that are used to create an appointment. The appointment\n" +
            "will be stored in an appointment book of an 'Owner' and then written to a file designated by the user. An\n" +
            "appointment book consists of the owners name and a linked list of appointments. An appointment consists\n" +
            "of; a 'description' describing the appointment, a 'beginTime' indicating the start date and time of the\n" +
            "appointment, and an 'endTime' indicating the date and time the appointment will end. Just a name is needed\n" +
            "for a textfile the .txt will be added regardless.";



    /**
     * Main method that parses command line arguments.
     * Creates appointments and appointment books for
     * owners.
     *
     * @param args  [options] -print, -README [arguments] Owner, Description, Begin Date, Begin Time, End Date, End Time
     */
    public static void main(String[] args)
    {
        StringBuilder missingArgs = new StringBuilder();
        int j;
        int count = args.length;

        j = getPossibleNumberOfOptionsInCommandLineArguments(count);

        checkOptionsSelected(args, j);

        if(start > 0) count = args.length - start;

        if(count > 8) tooManyCommandLineArgumentsReceived(args);

        printMissingArgumentsToStandardOut(args, missingArgs, count);        //Notify user which command line arguments are missing

        owner = args[start++];
        description = args[start++];
        time = args[start++] + " " + args[start++] + " " + args[start++];
        beginDate = checkDateTimeFormat("Begin", time);                     //Check that begin date is in correct format
        time = args[start++] + " " + args[start++] + " " + args[start];
        endDate = checkDateTimeFormat("End", time);                         //Check that end date is in correct format

        populateAppointmentBookWithExistingAppointmentsFromFile();

        appointment = new Appointment(description, beginDate, endDate);

        if(compareExistingOwnerNameAndOwnerNameFromCommandLine()) appointmentBook.addAppointment(appointment);

        completeOptionMethods();

        System.exit(0);
    }


    /**
     * Call the appropriate methods when an option has been found in the command line arguments.
     */
    private static void completeOptionMethods() {
        if(filename) writeAppointmentBookToATextFile();

        if(prettyFile) writeAppointmentBookInPrettyFormatToATextFile();

        if(pretty) prettyPrinter.print(appointmentBook);

        if(print) System.out.println(appointment.toString());
    }


    /**
     * Once an appointmentBook is retrieved from a text file the name of the owner from the file and the owner name of
     * the new appointment are compared. If they are the same return true otherwise Exit program and tell user names do
     * not match.
     *
     * @return
     *      returns true if owner names match.
     */
    private static boolean compareExistingOwnerNameAndOwnerNameFromCommandLine()
    {
        //check if need to initialize a new appointmentBook or have already read in data for one
        if(appointmentBook == null) appointmentBook = new AppointmentBook(owner);

        //Check that owners of appointment books are the same
        if(!owner.equals(appointmentBook.getOwnerName()))
        {
            System.err.println("Owner Name from command line arguments does not match existing Owner Name in " + textFile + ".txt");
            System.exit(1);

        }
        return true;
    }


    /**
     * Notifies the user if too many command line arguments have been entered and exits after displaying the correct
     * usage expected.
     *
     * @param args
     *      The command line arguments entered by a user.
     */
    private static void tooManyCommandLineArgumentsReceived(String[] args)
    {
        clearScreen();
        System.err.println("Too many command line arguments.");
        printArgsEntered(args);
        System.err.println(USAGE);
        System.exit(1);
    }


    /**
     * Prints missing command line arguments to the console notifying the user which arguments are missing specifically
     * and displays the expected usage of the program.
     *
     * @param args
     *      The command line arguments entered by a user.
     * @param missingArgs
     *      A stringbuilder to build the message identifying the missing command line arguments.
     * @param count
     *      The identifier for the switch statement based on the number of arguments from the command line.
     */
    private static void printMissingArgumentsToStandardOut(String[] args, StringBuilder missingArgs, int count)
    {
        switch(count)
        {
            case 0:
                   missingArgs.append(" owners name,");
            case 1:
                   missingArgs.append(" description,");
            case 2:
                   missingArgs.append(" begin date,");
            case 3:
                   missingArgs.append(" begin time,");
            case 4:
                   missingArgs.append(" am/pm,");
            case 5:
                   missingArgs.append(" end date,");
            case 6:
                   missingArgs.append(" end time,");
            case 7:
                clearScreen();
                System.err.println("Missing command line arguments: " + missingArgs + " am/pm");
                printArgsEntered(args);
                System.err.println(USAGE);
                System.exit(1);
        }
    }


    /**
     * Implements <code>TextParser</code> to read a text file and create an <code>AppointmentBook</code>.
     */
    private static void populateAppointmentBookWithExistingAppointmentsFromFile()
    {
        TextParser textParser = new TextParser(textFile + ".txt");

        try {
            appointmentBook = textParser.parse();
        } catch (ParserException ex) {
            System.err.println(ex.getMessage() + " Could not retrieve data.");
            System.exit(1);
        }
    }


    /**
     * Writes an <code>AppointmentBook</code> to a text file in nice format by implementing <code>PrettyPrinter</code>.
     */
    private static void writeAppointmentBookInPrettyFormatToATextFile()
    {
        clearScreen();
        prettyPrinter = new PrettyPrinter(prettyFileName + ".txt");
        try {
            prettyPrinter.dump(appointmentBook);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }


    /**
     * Writes an <code>AppointmentBook</code> to a text file in a format that can be parsed by implementing <code>TextDumper</code>.
     */
    private static void writeAppointmentBookToATextFile()
    {
        TextDumper textDumper = new TextDumper(textFile + ".txt");

        try {
            textDumper.dump(appointmentBook);
        } catch(IOException ex) {
            System.err.println("Appointment could not be written to " + textFile + ex.getMessage());
            System.exit(0);
        }
    }


    /**
     * Finds the maximum number of options in the command line arguments and returns an integer.
     *
     *
     * @param num
     *      The number of command line arguments.
     * @return
     *      returns an integer identifying the maximum number of possible Options in the command line arguments.
     */
    private static int getPossibleNumberOfOptionsInCommandLineArguments(int num)
    {
        int j = 0;

        switch(num)
        {
            case 0:
              break;
            case 1:
                j = 1;
                  break;
            case 2:
                j = 2;
                  break;
            case 3:
                j = 3;
                  break;
            default:
                  j = 4;
        }
        return j;
    }



    /**
     * Checks that a date and time match the format of mm/dd/yyyy hh:mm am/pm
     *
     * @param string    Identify if this is the start date/time or end date/time.
     * @param dateTime  The date/time that needs to be checked.
     */
    static Date checkDateTimeFormat(String string, String dateTime)
    {
       Date date = new Date();
       DateFormat format = new SimpleDateFormat("MM/dd/yyyy h:mm a");
       format.setLenient(false);

       try {
            date = format.parse(dateTime);
       } catch(ParseException ex) {
            clearScreen();
            System.err.println(string + " date/time format is incorrect: " + dateTime
                    + "\nShould be in the form: mm/dd/yyyy h:mm am/pm)");
            System.exit(1);
       }
        return date;
    }



    /**
     * Displays all command line arguments the user entered.
     * @param args      Command line arguments entered by user.
     */
    private static void printArgsEntered(String[] args)
    {
        System.out.println("You entered:");
        for (String arg : args)
        {
            System.out.println("\t" + arg);
        }
    }



    /**
     * Prints a brief, concise, and nicely-formatted textual description of what project1 does.
     */
    static void readMe()
    {
        clearScreen();
        System.out.printf("%60s" , "Project3-README\n");
        System.out.printf("%100s", "Shawn Forgie\n\n");
        System.out.println(README);
        System.exit(0);
    }



    /**
     * Clears the command line screen, makes it more readable.
     */
    static void clearScreen()
    {
        for(int i = 0; i <= 80; i++) System.out.println();

    }


    /**
     * Determines which options are selected and completes the necessary actions
     *
     * @param args
     *      The command line arguments to search for option flags
     * @param j
     *      The maximum number of arguments to be seached for options
     */
    private static void checkOptionsSelected(String [] args, int j)
    {
        int i;
        //check for options at start
        for(i = 0; i < j; i++)
        {
            if(args[i].equals("-README")) readMe();
            else
            {
                if(args[i].equals("-print"))
                {
                    print = true;
                    ++start;
                }else
                {
                    if(args[i].equals("-textFile"))
                    {
                        if(args.length > (i + 1))
                        {
                            textFile = args[++i];
                            filename = true;
                            start += 2;
                        }
                    }else
                    {
                        if(args[i].equals("-pretty"))
                        {
                            if(args.length > (i + 1))
                            {
                                if(args[++i].equals("-"))
                                {
                                    pretty = true;
                                    start += 2;
                                }else
                                {
                                    prettyFile = true;
                                    prettyFileName = args[i];
                                    start += 2;
                                }
                            }
                        }
                    }
                }
            }
        }

        if(args.length > 6) if(args[5].equals("-README")) readMe();
    }
}