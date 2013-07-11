package edu.pdx.cs410J.forgie;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Collection;


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
    private Collection<AbstractAppointment> list;



    /**
     * Creates a new <code>AppointmentBook</code>, has a owner and list of appointments.
     *
     * @param name   A unique name identifying the owner of the appointment book.
     *
     */
    public AppointmentBook(String name)
    {
        super();
        this.owner = name;
        this.list = new LinkedList<AbstractAppointment> ();
    }


    /**
     *  <code>addAppointment</code> adds an appointment to the list of appointments for an appointment book.
     *
     * @param appointment   An appointment with all associated information (description, beginTime, endTime).
     */
    @Override
    public void addAppointment(AbstractAppointment appointment)
    {
        Iterator iterator = this.list.iterator();
        Appointment element, temp;
        if(iterator.hasNext())
        {
            while(iterator.hasNext())
            {
                element = (Appointment) iterator.next();

                if(iterator.hasNext())
                {
                    temp = (Appointment) iterator.next();

                    if(element.compareTo(temp) != 0) this.list.add(appointment);

                }else  this.list.add(appointment);
            }
        }else this.list.add(appointment);

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
        return this.list;
    }

}
