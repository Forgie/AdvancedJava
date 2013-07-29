package edu.pdx.cs410J.forgie;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link Project4} class by invoking its main method with various arguments 
 */
public class Project4Test extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = String.valueOf(8080);
    private static final String pr = "-print";
    private static final String s = "-search";
    private static final String h = "-host";
    private static final String p = "-port";
    private static final String OWNER = "Owner";
    private static final String BEGINDATE = "01/12/2013";
    private static final String BEGINTIME = "11:00";
    private static final String BEGINAP = "am";
    private static final String ENDDATE = "01/12/2013";
    private static final String ENDTIME = "11:30";
    private static final String ENDAP = "am";
    private static final String DESCRIPTION = "description";

    @Ignore
    @Test
    public void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain( Project4.class );
        assertEquals(new Integer(1), result.getExitCode());
        assertTrue(result.getErr().contains( Project4.MISSING_ARGS ));
    }

    @Ignore
    @Test
    public void testHostCommandLineArg()
    {
        MainMethodResult result = invokeMain(Project4.class,
                                            h, HOSTNAME,
                                            p, PORT,
                                            s,
                                            OWNER,
                                            BEGINDATE,
                                            BEGINTIME,
                                            BEGINAP,
                                            ENDDATE,
                                            ENDTIME
                                            );
        assertEquals(new Integer(0), result.getExitCode());
    }

    /*
    @Ignore
    @Test
    public void testEmptyServer() {
        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT );
        assertEquals(result.getErr(), new Integer(0), result.getExitCode());
        String out = result.getOut();
        assertTrue( out, out.contains( Messages.getMappingCount(0)));
    }
    @Ignore
    @Test
    public void testNoValues() {
        String key = "KEY";
        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT, key );
        assertEquals(result.getErr(), new Integer(0), result.getExitCode());
        String out = result.getOut();
        assertTrue( out, out.contains( Messages.getMappingCount(0)));
        assertTrue( out, out.contains( Messages.formatKeyValuePair( key, null )));
    }

    @Ignore
    @Test
    public void testAddValue() {
        String key = "KEY";
        String value = "VALUE";

        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT, key, value );
        assertEquals(result.getErr(), new Integer(0), result.getExitCode());
        String out = result.getOut();
        assertTrue( out, out.contains( Messages.mappedKeyValue( key, value )));

        result = invokeMain( Project4.class, HOSTNAME, PORT, key );
        out = result.getOut();
        assertTrue( out, out.contains( Messages.getMappingCount(1)));
        assertTrue( out, out.contains( Messages.formatKeyValuePair( key, value )));

        result = invokeMain( Project4.class, HOSTNAME, PORT );
        out = result.getOut();
        assertTrue( out, out.contains( Messages.getMappingCount(1)));
        assertTrue( out, out.contains( Messages.formatKeyValuePair( key, value )));
    }
    */
}