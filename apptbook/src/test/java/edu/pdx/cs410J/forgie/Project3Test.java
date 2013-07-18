package edu.pdx.cs410J.forgie;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import edu.pdx.cs410J.InvokeMainTestCase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static junit.framework.Assert.assertEquals;

/**
 * Tests the functionality in the {@link Project3} main class.
 */
public class Project3Test extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project3} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project3.class, args );
    }


    /**
     * Tests that two appointments are equal, when compareTo is called with equal appointments it should return 0
     */

    public void compareToOverriddenAndReturns0WhenTwoAppointmentsAreEqual()
    {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        Appointment appt1 = new Appointment("description", date, date);

        Appointment appt2 = new Appointment("description", date, date);

        int compare = appt1.compareTo(appt2);

        assertEquals(compare, 0);

    }




}