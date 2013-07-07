package edu.pdx.cs410J.forgie;

import edu.pdx.cs410J.AbstractAppointment;

import java.text.*;
import java.util.*;



/**
 *  This class represents an <code>Appointment</code> and contains all objects defining an appointment.
 *
 *  @author Shawn Forgie
 *  Date: 7/4/13
 */
public class Appointment extends AbstractAppointment{
    private String Description;
    private String BeginTime;
    private String EndTime;
    private Date StartTime;
    private Date End;
    static Date date;
    static DateFormat format = new SimpleDateFormat("MM/dd/yyyy h:mm a");
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
    public Appointment(String description, String beginTime, String endTime){
        super();
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
    public String getDescription() {
        return Description;
    }

    @Override
    public Date getBeginTime(){
        format.setLenient(false);
        try{
            StartTime = format.parse(BeginTime);
        }catch(ParseException ex){
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        return StartTime;
    }

    @Override
    public Date getEndTime(){
        format.setLenient(false);
        try{
            End = format.parse(EndTime);
        }catch(ParseException ex){
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        return End;
    }
    /**
     *<code>getBeginTimeString</code> returns the date and time an appointment is scheduled for.
     *
     * @return  returns the date and time as a string.
     */
    @Override
    public String getBeginTimeString() {

        //beginTime = DateFormat.getDateInstance(DateFormat.SHORT).format(date);
            //getTimeInstance()
        //StartTime = checkDateTimeFormat(BeginTime);

        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(getBeginTime());
    }



    /**
     * <code>getEndTimeString</code> returns the date and time an appointment ends.
     *
     * @return  returns the date and time as a string.
     */
    @Override
    public String getEndTimeString() {

        //endTime = DateFormat.getDateInstance(DateFormat.SHORT).format(date);

        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(getEndTime());
    }


    /*
    /**
     * Checks that a date and time match the format of mm/dd/yyyy hh:mm (0:00-23:59)
     *
     * @param dateTime  The date/time that needs to be converted into a date.
     * @return          The string received into a properly formatted date.

    public Date checkDateTimeFormat(String dateTime) {
        format.setLenient(false);
        try{
            date = format.parse(dateTime);
        }catch(ParseException ex){
            System.err.println(ex.getMessage());
            System.exit(1);
        }
        return date;
    }
    */

}
