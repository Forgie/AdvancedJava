package edu.pdx.cs410J.forgie;

import edu.pdx.cs410J.AbstractAppointment;

import java.text.*;
import java.util.*;



/**
 *  This class represents an <code>Appointment</code> and contains all objects defining an appointment.
 *
 *  @author Shawn Forgie
 *
 *  Date: 7/4/13
 */
public class Appointment extends AbstractAppointment implements Comparable<AbstractAppointment>
{
    private String Description;
    private Date BeginTime;
    private Date EndTime;


    /**
     * Creates a new <code>Appointment</code>.
     * This is the Appointment class that contains all the information
     * regarding an appointment. {@link AppointmentBook}
     * then adds the appointment to a list of appointments.
     *
     * @param description The description of an appointment.
     * @param beginTime   The date and time the appointment begins.
     * @param endTime     The date and time the appointment ends.
     */
    public Appointment(String description, Date beginTime, Date endTime)
    {
        this.Description = description;
        this.BeginTime = beginTime;
        this.EndTime = endTime;
    }



    /**
     *<code>getDescription</code> returns the description of the appointment.
     *
     * @return  returns the description as a string.
     */
    @Override
    public String getDescription()
    {
        return Description;
    }



    /**
     * <code>getBeginTime</code> returns the date object storing the beginning time of an Appointment.
     *
     * @return <code>BeginTime</code>     The time an appointment begins is returned.
     */
    @Override
    public Date getBeginTime()
    {
        return BeginTime;
    }


    /**
     * <code>getEndTime</code> returns the date object storing the end time of an Appointment.
     *
     * @return <code>EndTime</code>     The time an appointment ends is returned.
     */
    @Override
    public Date getEndTime()
    {
        return EndTime;
    }



    /**
     *<code>getBeginTimeString</code> returns the date and time an appointment is scheduled for.
     *
     * @return  returns the date and time as a string.
     */
    @Override
    public String getBeginTimeString()
    {
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(getBeginTime());
    }



    /**
     * <code>getEndTimeString</code> returns the date and time an appointment ends.
     *
     * @return  returns the date and time as a string.
     */
    @Override
    public String getEndTimeString()
    {
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(getEndTime());
    }



    /**
     * <code>getDuration</code> calculates the difference between begin and end times of an
     * appointment and returns the number as a representation of the minutes.
     *
     * @return duration     The time difference between the start and end time in minutes.
     */
    public long getDuration()
    {
        long dur = getEndTime().getTime() - getBeginTime().getTime();

        return dur/(1000 * 60);
    }

    /**
     * Compares two appointment books for sorting into a <code>TreeSet</code>.
     *
     * @param appointment
     *      The appointment to be compared against.
     * @return
     *      An integer -1 if the appointment book should be before the one it is compared against, 0 if they are equal,
     *      and 1 if the appointment book should be after the one it is compared against.
     */
    @Override
    public int compareTo(AbstractAppointment appointment)
    {
        int b, e, d;

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy h:mm a");
        format.setLenient(false);

        Date begin = new Date();
        Date end = new Date();
        try{
            begin = format.parse(getBeginTimeString());
            end = format.parse(getEndTimeString());
        }catch(ParseException ex){
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        b = begin.compareTo(appointment.getBeginTime());
        e = end.compareTo(appointment.getEndTime());
        d = getDescription().compareTo(appointment.getDescription());

        if(b < 0)
        {
            return -1;
        }else
        {
            if(b > 0)
            {
                return 1;
            } else
            {
                if(e < 0)
                {
                    return -1;
                }else{
                    if(e > 0)
                    {
                        return 1;
                    }else
                    {
                        if(d < 0)
                        {
                            return -1;
                        }
                        else
                        {
                            if( d > 0)
                            {
                                return 1;
                            }
                            else return 0;
                        }
                    }
                }
            }
        }


    }

}
