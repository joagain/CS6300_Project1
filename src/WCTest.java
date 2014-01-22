import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Errors.InputError;

public class WCTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCount() throws Exception {
    	WC counter = new WC(new String[]{"java", "WC", "test01.txt", "-D", "*"});
    	double result1 = counter.count();
    	assertTrue("test01.txt expected average of 5, but got " + result1, result1 == 5);
    	
    	counter.setFile(new File("test02.txt"));
    	double result2 = counter.count();
    	assertTrue("test01.txt expected average of 0, but got " + result2, result2 == 0);
    }

    @Test
    public void testFormat_count() throws Exception {

    }

    @Test
    public void testMain() throws Exception {

    }

    @Test(expected=InputError.class)
    public void testParseCommandLineError() throws InputError {
    	WC counter = new WC(new String[]{"java", "WC", "test01.txt", "-A", "@"});
    }
    
    @Test
    public void testParseCommandLineNoError() throws InputError {
    	WC counter = new WC(new String[]{"java", "WC", "test01.txt", "-D", "#"});
    }
}
