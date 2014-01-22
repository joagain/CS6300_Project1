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
    	WC counter = new WC(new String[]{"java", "WC", "src\\test01.txt", "-D", "*"});
    	int result1 = (int)counter.count();
    	assertTrue("test01.txt expected average of 9, but got " + result1, result1 == 9);
    	
    	counter.setFilename("src\\test02.txt");
    	int result2 = (int)counter.count();
    	assertTrue("test02.txt expected average of 0, but got " + result2, result2 == 0);
    }

    @Test
    public void testFormat_count() throws Exception {

    }

    @Test
    public void testMain() throws Exception {

    }

    @Test(expected=InputError.class)
    public void testParseCommandLineError() throws InputError {
    	WC counter = new WC(new String[]{"java", "WC", "src\\test01.txt", "-A", "@"});
    	counter = new WC(new String[]{"java", "WC", "src\\test01.txt", "-B", "&"});
    	counter = new WC(new String[]{"java", "WC", "src\\test01.txt", "-C", "%"});
    }
    
    @Test
    public void testParseCommandLineNoError() throws InputError {
    	WC counter = new WC(new String[]{"java", "WC", "src\\test01.txt", "-D", "#"});
    	counter = new WC(new String[]{"java", "WC", "src\\test01.txt", "-D", "$"});
    	counter = new WC(new String[]{"java", "WC", "src\\test01.txt", "-D", "^"});
    }
    
    @Test
    public void testNonEmptyFile() throws InputError {
    	WC counter = new WC(new String[]{"java", "WC", "src\\test01.txt", "-D", "#"});
    }
    
    @Test(expected=InputError.class)
    public void testParseEmptyFile() throws InputError {
    	WC counter = new WC(new String[]{"java", "WC", "src\\test02.txt", "-D", "#"});
    }
    
    @Test
    public void testMultipleDelimiters() throws InputError {
    	WC counter = new WC(new String[]{"java", "WC", "src\\test01.txt", "-D", "#$%"});
    }
}
