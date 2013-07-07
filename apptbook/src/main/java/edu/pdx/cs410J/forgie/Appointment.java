package edu.pdx.cs410J.forgie;

import edu.pdx.cs410J.AbstractAppointment;

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



    /**
     *<code>getBeginTimeString</code> returns the date and time an appointment is scheduled for.
     *
     * @return  returns the date and time as a string.
     */

    //DateFormat.SHORT.
    @Override
    public String getBeginTimeString() {
        return BeginTime;
    }



    /**
     * <code>getEndTimeString</code> returns the date and time an appointment ends.
     *
     * @return  returns the date and time as a string.
     */
    @Override
    public String getEndTimeString() {
        return EndTime;
    }
}
