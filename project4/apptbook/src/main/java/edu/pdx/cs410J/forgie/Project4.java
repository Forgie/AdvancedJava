package edu.pdx.cs410J.forgie;

import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The main class that parses the command line and communicates with the
 * Appointment Book server using REST.
 */
public class Project4 {
    static String owner;
    static String description;
    static String begintime;
    static String endtime;
    static String hostName = "";
    static int portNum;

    static String portString = "";
    static int start = 0;

    static Date beginDate;
    static Date endDate;

    static boolean host = false;
    static boolean port = false;
    static boolean print = false;
    static boolean search = false;

    public static final String MISSING_ARGS = "Missing command line arguments: ";

    private static final String README = "\n\n"+
            "    Project4 parses 10-15 command line arguments that are used to create an appointment and add it to an\n" +
            "appointment book posted on a server. An appointment will be created from command line arguments unless\n" +
            "the search option has been selected. The appointment will be added to an existing appointment book for\n" +
            "the specified owner or if the owner does not have an existing appointment book one will be created with\n" +
            "the single appointment. If the search option is specified no description is expected and an appointment\n" +
            "book will be searched for appointments between the beginTime an endTime. If the print option is specified\n" +
            "a message describing the appointment that was just added will be shown or if search was enabled it will\n" +
            "display a message describing the search completed. An appointment consists of; a 'description' describing\n" +
            "the appointment, a 'beginTime' indicating the start date and time of the appointment and an 'endTime'\n" +
            "indicating the date and time the appointment will end.";


    /**
     * The main method for Project4, parses command line and calls methods to complete desired actions
     * @param args The command line arguments from the user
     */
    public static void main(String... args)
    {
        int j;
        int count = args.length;

        j = getPossibleNumberOfOptionsInCommandLineArguments(count);

        checkOptionsSelected(args, j);

        if(start > 0) count = args.length - start;

        if(count > 8 || (search && count > 7)) tooManyCommandLineArgumentsReceived(args);

        if(port && !host || !port && host)
        {
            if(port) error("Host name is required with a port number.");
            else error("Port number is required with a host name.");
        }

        printMissingArgumentsMessage(args, count);                          //Notify user which command line arguments are missing

        owner = args[start++];
        if(!search) description = args[start++];
        begintime = args[start++] + " " + args[start++] + " " + args[start++];
        beginDate = checkDateTimeFormat("Begin", begintime);                     //Check that begin date is in correct format
        endtime = args[start++] + " " + args[start++] + " " + args[start];
        endDate = checkDateTimeFormat("End", endtime);                         //Check that end date is in correct format

        if(host)
        {
            getPortNumFromString();
            AppointmentBookRestClient client = new AppointmentBookRestClient(hostName, portNum);

            try {
                if (search) getResponseFromSearchAction(client);     //search appointments
                else getResponseFromAddAppointmentAction(client);    //add appointments

            } catch ( IOException ex ) {
                error("While contacting server: " + ex);
                return;
            }
        }else completePrintOption();                                       //Just do print function if selected

        System.exit(0);
    }


    /**
     * Completes the add post to the server and checks the response code for completion.
     *
     * @param client The RESTful server client that generates the URL
     * @throws IOException The client was not able to generate a URL
     */
    private static void getResponseFromAddAppointmentAction(AppointmentBookRestClient client) throws IOException
    {
        HttpRequestHelper.Response response;

        response = client.addAppointment(owner, description, begintime, endtime);

        checkResponseCode( HttpURLConnection.HTTP_OK, response);

        completePrintOption();
    }


    /**
     * Completes the search request to the server, checks the response and prints it to the command line
     *
     * @param client The RESTful server client that generates the URL
     * @throws IOException The client was not able to generate a URL
     */
    private static void getResponseFromSearchAction(AppointmentBookRestClient client) throws IOException
    {
        HttpRequestHelper.Response response;

        // seach for appointments between beginDate and EndDate
        response = client.getAppointmentsBetweenDates(owner, begintime, endtime);

        checkResponseCode(HttpURLConnection.HTTP_OK, response);

        clearScreen();
        System.out.println(response.getContent() + "\n\n");
    }


    /**
     * Prints the newly added appointment if -print is specified in the command line arguments
     * or if -search is also found then it will print a message with the interval being searched for
     */
    private static void completePrintOption()
    {
        if(search && print)
        {
            System.out.printf("\n\nYou were searching for appointments with dates between %s and %s\n",
                    DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(beginDate),
                    DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(endDate));
            return;
        }

        Appointment appointment = new Appointment(description, beginDate, endDate);
        if(print) System.out.println("\n\n" + appointment.toString() + "\n");
    }


    /**
     * Makes sure that the argument following -port is a number
     */
    private static void getPortNumFromString()
    {
        try {
            portNum = Integer.parseInt( portString );

        } catch (NumberFormatException ex) {
            usage("Port \"" + portString + "\" must be an integer");
        }
    }



    /**
     * Makes sure that the give response has the expected HTTP status code
     * @param code The expected status code
     * @param response The response from the server
     */
    private static void checkResponseCode( int code, HttpRequestHelper.Response response )
    {
        if (response.getCode() != code) {
            error(String.format("Expected HTTP code %d, got code %d.\n\n%s", code,
                                response.getCode(), response.getContent()));
        }
    }


    /**
     * Prints an error message and exits the program.
     *
     * @param message The error message
     */
    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);

        System.exit(1);
    }




    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message )
    {
        PrintStream err = System.err;
        err.println(message);
        err.println();
        err.println("usage: java edu.pdx.cs410J.<login-id>.Project4 [options] <args>");
        err.println("args are (in this order):");
        err.println("  owner               The person whose owns the appt book");
        err.println("  description         A description of the appointment");
        err.println("  beginTime           When the appt begins");
        err.println("  endTime             When the appt ends");
        err.println("options are (at the beginning in any order):");
        err.println("  -host <hostname>    Host computer on which the server runs");
        err.println("  -port <port>        Port on which the server is listening");
        err.println("  -search             Appointments should be searched for");
        err.println("  -print              Prints the appointment that has been added or the date range of the search");
        err.println("  -README             Prints a README for this project and exits");
        err.println();
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
            case 4:
                j = 4;
                break;
            case 5:
                j = 5;
                break;
            default:
                j = 6;
        }
        return j;
    }




    /**
     * Determines which options are selected and completes the necessary actions
     *
     * @param args
     *      The command line arguments to search for option flags
     * @param j
     *      The maximum number of arguments to be searched for options
     */
    private static void checkOptionsSelected(String [] args, int j)
    {
        int i;
        //check for options at start
        for(i = 0; i < j; i++)
        {
            if(args[i].equals("-README")) readMe();
            else if(args[i].equals("-print"))
            {
                print = true;
                ++start;
            }else if(args[i].equals("-host"))
            {
                if(args.length > (i + 1))
                {
                    host = true;
                    hostName = args[++i];
                    start += 2;
                }
            }else if(args[i].equals("-port"))
            {
                if(args.length > (i + 1))
                {
                    port = true;
                    portString = args[++i];
                    start += 2;
                }
            }else if(args[i].equals("-search"))
            {
                search = true;
                ++start;
            }
        }
        if(args.length > 7) if(args[6].equals("-README")) readMe();
    }



    /**
     * Prints a brief, concise, and nicely-formatted textual description of what project1 does.
     */
    static void readMe()
    {
        clearScreen();
        System.out.printf("%60s" , "Project4-README\n");
        System.out.printf("%100s", "Shawn Forgie\n\n");
        usage(README);
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
     * Notifies the user if too many command line arguments have been entered and exits after displaying the correct
     * usage expected.
     *
     * @param args
     *      The command line arguments entered by a user.
     */
    private static void tooManyCommandLineArgumentsReceived(String[] args)
    {
        clearScreen();
        usage("Too many command line arguments.");
        printArgsEntered(args);
        System.exit(1);
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
     * Prints missing command line arguments to the console notifying the user which arguments are missing specifically
     * and displays the expected usage of the program.
     *
     * @param args
     *      The command line arguments entered by a user.
     * @param count
     *      The identifier for the switch statement based on the number of arguments from the command line.
     */
    private static void printMissingArgumentsMessage(String[] args, int count)
    {
        StringBuilder missingArgs = new StringBuilder();
        if(search)
        {
            switch(count)
            {
                case 0:
                    missingArgs.append(" owners name,");
                case 1:
                    missingArgs.append(" begin date,");
                case 2:
                    missingArgs.append(" begin time,");
                case 3:
                    missingArgs.append(" am/pm for begin date");
                case 4:
                    missingArgs.append(" end date,");
                case 5:
                    missingArgs.append(" end time,");
                case 6:
                    clearScreen();
                    usage(MISSING_ARGS + missingArgs + " am/pm for end date.");
                    printArgsEntered(args);
                    System.exit(1);
            }
        }else{
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
                    missingArgs.append(" am/pm for begin date,");
                case 5:
                    missingArgs.append(" end date,");
                case 6:
                    missingArgs.append(" end time,");
                case 7:
                    clearScreen();
                    usage(MISSING_ARGS + missingArgs + " am/pm for end date.");
                    printArgsEntered(args);
                    System.exit(1);
            }
        }
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
}
