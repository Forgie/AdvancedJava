package edu.pdx.cs410J.forgie;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import edu.pdx.cs410J.InvokeMainTestCase;
import static junit.framework.Assert.assertEquals;

/**
 * Tests the functionality in the {@link Project1} main class.
 */
public class Project1Test extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project1.class, args );
    }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  public void NoCommandLineArgumentsPrintsMissingCommandLineArgsToStandardError() {
    MainMethodResult result = invokeMain();
    assertEquals(new Integer(1), result.getExitCode());
    assertTrue(result.getErr().contains( "Missing command line arguments\n" + Project1.USAGE));
  }

  @Test
  public void onlyOneArgumentPrintsMissingDescriptionToStandardError(){
      MainMethodResult result = invokeMain("Owner");
      assertEquals(new Integer(1), result.getExitCode());
      assertTrue(result.getErr().contains( "Missing description\n" + Project1.USAGE));
  }

    @Test
    public void onlyTwoArgumentsPrintsMissingBeginDateToStandardError(){
        MainMethodResult result = invokeMain("Owner", "Description");
        assertEquals(new Integer(1), result.getExitCode());
        assertTrue(result.getErr().contains("Missing begin date\n" + Project1.USAGE));
    }

    @Test
    public void onlyThreeArgsPrintsMissingBeginTimeToStandardError(){
        MainMethodResult result = invokeMain("Owner", "Description", "01/12/1989");
        assertEquals(new Integer(1), result.getExitCode());
        assertTrue(result.getErr().contains("Missing begin time\n" + Project1.USAGE));
    }


    @Test
    public void onlyFourArgsPrintsMissingEndDateToStandardError(){
        MainMethodResult result = invokeMain("Owner", "Description", "01/12/1989", "1:00");
        assertEquals(new Integer(1), result.getExitCode());
        assertTrue(result.getErr().contains("Missing end date\n" + Project1.USAGE));
    }


    @Test
    public void onlyFiveArgsPrintsMissingEndTimeToStandardError(){
        MainMethodResult result = invokeMain("Owner", "Description", "01/12/1989", "1:00", "01/12/1989");
        assertEquals(new Integer(1), result.getExitCode());
        assertTrue(result.getErr().contains("Missing end time\n" + Project1.USAGE));
    }

}