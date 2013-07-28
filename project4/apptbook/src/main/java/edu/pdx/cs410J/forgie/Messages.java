package edu.pdx.cs410J.forgie;


/**
 * Class for formatting messages on the server side.  This is mainly to enable
 * test methods that validate that the server returned expected strings.
 */
public class Messages
{
    public static String getMappingCount( int count )
    {
        return String.format("Server contains %d Appointment Books", count);
    }

    public static String formatAppointmentBook(String value )
    {
        return String.format("%s",value);
    }

    public static String missingRequiredParameter( String key )
    {
        return String.format("The required parameter \"%s\" is missing", key);
    }

    public static String malformattedAppointment()
    {
        return "Malformatted appointment found.";
    }

    public static String malformattedDate(String string, String dateTime)
    {
        return String.format("%s date/time format is incorrect: %s should be in the form: mm/dd/yyyy h:mm am/pm", string, dateTime);

    }

    public static String searchMessage(String search)
    {
        return String.format("%s", search);
    }

    public static String ownerAndInfo(String name, String begin, String end, String search)
    {
        return String.format("\t%s has the following appointments between %s and %s\n\n%s", name, begin, end, search);
    }

    public static String noAppointmentBookForOwner(String name)
    {
        return String.format("An appointment book for %s does not exist.", name);
    }

    public static String noAppointmentsBetweenTimes(String name, String s, String e)
    {
        return String.format("%s has no appointments between %s and %s", name, s, e);
    }
}
