package edu.pdx.cs410J.forgie;

import edu.pdx.cs410J.AbstractAppointmentBook;

/**
 * The main class for the CS410J appointment book Project
 */
public class Project1 {

  public static void main(String[] args) {
    Class c = AbstractAppointmentBook.class;  // Refer to one of Dave's classes so that we can be sure it is on the classpath

      if(args.length == 0){
        System.err.println("Missing command line arguments");
         System.exit(1);
      }else{
          if(args.length == 1){
              System.err.println("Missing Description");
              System.exit(1);
          }
      }

    for (String arg : args) {
      System.out.println(arg);
    }

  }

}