import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Date;

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

    // test 1
    @Test
    public void testParseCommandLineNoError() throws InputError {
        WC counter = new WC(new String[]{"java", "WC", "src"+File.separator+"test01.txt", "-D", "#"});
        counter = new WC(new String[]{"java", "WC", "src"+File.separator+"test01.txt", "-D", "$"});
        counter = new WC(new String[]{"java", "WC", "src"+File.separator+"test01.txt", "-D", "^"});
    }
    
    // test 2
    @Test(expected=InputError.class)
    public void testBadArgument() throws InputError {
    	WC counter = new WC(new String[]{"java", "WC", "src"+File.separator+"test01.txt", "-D", "@", "-L", "M"});
    }
    
    // test 3
    @Test(expected=InputError.class)
    public void testParseCommandLineError() throws InputError {
    	WC counter = new WC(new String[]{"java", "WC", "src"+File.separator+"test01.txt", "-A", "@"});
    }
    
    // test 4
    @Test(expected=NullPointerException.class)
    public void testNoInputFile() {
    	try {
    		WC counter = new WC(new String[]{"java", "WC", null});
    	}
    	catch(InputError ie) {
    		//System.out.println(ie.getMessage());
    		assertTrue("incorrect error encountered", ie.getMessage().compareTo("[Input error] ERR04 - No input file provided. Please refer to the user manual for further information.") == 0);
    	}
    }
    
    // test 5
    @Test
    public void testInputDirectory() {
    	try {
    		WC counter = new WC(new String[]{"java", "WC", "src"});
    	}
    	catch(InputError ie) {
    		//System.out.println(ie.getMessage());
    		assertTrue("incorrect error encountered", ie.getMessage().compareTo("[Input error] ERR05 - The file is a directory. Please refer to the user manual for further information.") == 0);
    	}
    }
    
    // test 6
    @Test
    public void testParseEmptyFile() {
    	try {
    		WC counter = new WC(new String[]{"java", "WC", "src"+File.separator+"test02.txt"});
    	}
    	catch(InputError ie) {
    		//System.out.println(ie.getMessage());
    		assertTrue("incorrect error encountered", ie.getMessage().compareTo("[Input error] ERR06 - The file provided was empty. Please refer to the user manual for further information.") == 0);
    	}
    }
    
    // test 7
    @Test
    public void testNoSuchFileExist() {
    	try {
    		WC counter = new WC(new String[]{"java", "WC", "i"});
    	}
    	catch(InputError ie) {
    		//System.out.println(ie.getMessage());
    		assertTrue("incorrect error encountered", ie.getMessage().compareTo("[Input error] ERR07 - No such file exists. Please refer to the user manual for further information.") == 0);
    	}
    }
    
    // test 8
    @Test
    public void testReadingContent() throws InputError {
    	WC counter = new WC(new String[]{"java", "WC", "src"+File.separator+"test01.txt", "-D", "#"});
    }
    
    // test 9
    @Test
    public void testCountNonzero() throws Exception {
    	WC counter = new WC(new String[]{"java", "WC", "src"+File.separator+"test01.txt", "-D", "*"});
    	int result = (int)counter.count();
    	assertTrue("test01.txt expected average of 9, but got " + result, result == 9);
    }
    
    // test 10
    @Test(expected=InputError.class)
    public void testCountZero() throws Exception {
    	WC counter = new WC(new String[]{"java", "WC", "src"+File.separator+"test02.txt", "-D", "*"});
    	int result = (int)counter.count();
    	assertTrue("test02.txt expected average of 0, but got " + result, result == 0);

    	counter = new WC(new String[]{"java", "WC", "src"+File.separator+"test01.txt", "-D", "*"});
        result = (int) counter.count();
        assertTrue("test01.txt expected average of 9, but got " + result, result == 9);
    }

    // test 11
    @Test
    public void testNonEmptyFile() throws InputError {
        WC counter = new WC(new String[]{"java", "WC", "src"+File.separator+"test05.txt"});
        int result = (int) counter.count();
        assertTrue("test05.txt expected average of 1, but got " + result, result == 1);
    }

    // test 12
    @Test
    public void testMultipleDelimiters() throws InputError {
        WC counter = new WC(new String[]{"java", "WC", "src"+File.separator+"test03.txt", "-D", "#$"});
        int result = (int) counter.count();
        assertTrue("test03.txt expected average of 1, but got " + result, result == 1);

        counter = new WC(new String[]{"java", "WC", "src"+File.separator+"test03.txt", "-D", "$"});
        result = (int) counter.count();
        assertTrue("test03.txt expected average of 3, but got " + result, result == 3);

        counter = new WC(new String[]{"java", "WC", "src"+File.separator+"test01.txt", "-D", "#$%"});
        counter = new WC(new String[]{"java", "WC", "src"+File.separator+"test01.txt", "-D", "#$%^"});
    }

    // test 13
    @Test
    public void testCounterSpeed() throws Exception {
        WC counter = new WC(new String[]{"java", "WC", "src"+File.separator+"test01.txt", "-D", "#$"});

        Long startTime = new Long(System.currentTimeMillis());
        counter.count();
        Long endTime = new Long(System.currentTimeMillis());
        double time_taken = (endTime - startTime)/(double)1000;
        assertTrue("Expected to finish the test in less than "+counter.getWords()/5000 +" seconds", time_taken < (double)counter.getWords()/5000);
    }

    // test 14
    @Test
    public void testFileCorruption() throws Exception {

        WC counter = new WC(new String[]{"java", "WC", "src"+File.separator+"test01.txt"});
        long len = new File("src"+File.separator+"test01.txt").length();
        boolean corrupted = false;
        if (counter.count() != 0) {

            File file = new File("src"+File.separator+"test01.txt");
            if (file.length() != len) {
                corrupted = true;
            }
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            if (br.readLine() == null) {
                corrupted = true;
            }

            br.close();
        }
        ;

        assertFalse("The file was corrupted during processing", corrupted);

    }
}
