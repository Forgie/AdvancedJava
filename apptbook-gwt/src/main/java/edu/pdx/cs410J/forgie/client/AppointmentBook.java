package edu.pdx.cs410J.forgie.client;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.*;
import java.lang.*;

/**
 * This class represents an <code>AppointmentBook</code>. An appointment book has an owner and an appointment.
 *
 * @author Shawn Forgie
 *
 * Date: 7/4/13
 */
public class AppointmentBook extends AbstractAppointmentBook
{
    private String owner;
    private TreeSet<Comparable<AbstractAppointment>> set;


    /**
     * Creates a new <code>AppointmentBook</code>, has a owner and set of appointments.
     *
     * @param name   A unique name identifying the owner of the appointment book.
     *
     */
    public AppointmentBook(String name)
    {
        this.owner = name;
        this.set = new TreeSet<Comparable<AbstractAppointment>>();
    }

    public AppointmentBook()
    {
    }


    /**
     *  <code>addAppointment</code> adds an appointment to the set of appointments for an appointment book.
     *
     * @param appointment   An appointment with all associated information (description, beginTime, endTime).
     */
    @Override
    public void addAppointment(AbstractAppointment appointment)
    {
        this.set.add((Comparable<AbstractAppointment>) appointment);
    }


    /**
     * <code>getOwnerName</code> gets the owners name and returns it as a string.
     *
     * @return   returns the name of whose appointment book is being accessed.
     */
    @Override
    public String getOwnerName()
    {
        return this.owner;
    }



    /**
     * Gets the collection of appointments for an owner.
     *
     * @return  returns the collection of appointments.
     */
    @Override
    public Collection getAppointments()
    {
        return this.set;
    }



}