import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Errors.InputError;

public class WCTest {

    @Test
    public void testCountNonzero() throws Exception {
    	WC counter = new WC(new String[]{"java", "WC", "src\\test01.txt", "-D", "*"});
    	int result = (int)counter.count();
    	assertTrue("test01.txt expected average of 9, but got " + result, result == 9);
    }
    
    @Test(expected=InputError.class)
    public void testCountZero() throws Exception {
    	WC counter = new WC(new String[]{"java", "WC", "src\\test02.txt", "-D", "*"});
    	int result = (int)counter.count();
    	assertTrue("test02.txt expected average of 0, but got " + result, result == 0);
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
    	WC counter = new WC(new String[]{"java", "WC", "src\\test03.txt", "-D", "#$"});
    	int result = (int)counter.count();
    	assertTrue("test03.txt expected average of 1, but got " + result, result == 1);
    	
    	counter = new WC(new String[]{"java", "WC", "src\\test03.txt", "-D", "$"});
    	result = (int)counter.count();
    	assertTrue("test03.txt expected average of 3, but got " + result, result == 3);
    	
    	counter = new WC(new String[]{"java", "WC", "src\\test01.txt", "-D", "#$%"});
    	counter = new WC(new String[]{"java", "WC", "src\\test01.txt", "-D", "#$%^"});
    }
}
