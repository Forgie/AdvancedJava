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
        System.err.println("Missing command line arguments\n" + USAGE);
         System.exit(1);
      }else{
          if(args.length == 1){
              System.err.println("Missing description\n" + USAGE);
              System.exit(1);
          }else{
              if(args.length == 2){
                  System.err.println("Missing begin date\n" + USAGE);
                  System.exit(1);
              }else{
                  if(args.length == 3){
                      System.err.println("Missing begin time\n" + USAGE);
                      System.exit(1);
                  }else{
                      if(args.length == 4){
                          System.err.println("Missing end date\n" + USAGE);
                          System.exit(1);
                      }else{
                          if(args.length == 5){
                              System.err.println("Missing end time\n" + USAGE);
                              System.exit(1);
                          }
                      }
                  }
              }
          }
      }

        clearScreen();


        for (String arg : args) {
      System.out.println(arg);
    }


    System.exit(0);
  }

    static void clearScreen() {
        for(int i = 0; i <= 80; i++){
            System.out.println();
        }
    }

}