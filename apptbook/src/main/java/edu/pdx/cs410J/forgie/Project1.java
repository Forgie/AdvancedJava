package edu.pdx.cs410J.forgie;

import edu.pdx.cs410J.AbstractAppointmentBook;

/**
 * The main class for the CS410J appointment book Project
 */
public class Project1 {

    static final String USAGE = "args are (in this order):\n" +
            "\towner\n" +
            "\tdescription\n" +
            "\tbeginDate\n" +
            "\tbeginTime\n" +
            "\tendDate\n" +
            "\tendTime\n" +
            "options are (in any order):\n" +
            "\t-print\n" +
            "\t-README";

    public static void main(String[] args) {
    //Class c = AbstractAppointmentBook.class;  // Refer to one of Dave's classes so that we can be sure it is on the classpath

      if(args.length == 0){
        System.err.println("Missing command line arguments");
        System.err.println(USAGE);
         System.exit(1);
      }else{
          if(args.length == 1){
              System.err.println("Missing description");
              System.err.println(USAGE);
              System.exit(1);
          }else{
              if(args.length == 2){
                  System.err.println("Missing begin date");
                  System.err.println(USAGE);
                  System.exit(1);
              }
          }
      }

    for (String arg : args) {
      System.out.println(arg);
    }
    System.exit(0);
  }

}