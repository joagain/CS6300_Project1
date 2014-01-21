import java.io.*;
import java.text.*;
import Errors.InputError;
import Errors.Error;


/**
 * WordCount class. Parses the command-line input, counts the average word count per sentence.
 *
 * @author Team #5
 */
public class WC {

    private static final int KO_EXIT = -1;
    private static final int OK_EXIT = 0;
    private final int DEFAULT_LENGTH = 3;
    private final String DEFAULT_DELIMITERS = "[,.;:?!]";
    private String filename;
    private int length;
    private String delimiters_string;

    /**
     * Constructor. Sets default values for all options.
     *
     * @param args
     * @throws InputError
     */
    public WC(String[] args) throws InputError {
        filename = null;
        length = DEFAULT_LENGTH;

        delimiters_string = DEFAULT_DELIMITERS;
        parseCommandLine(args);
    }

    /**
     * Parses the command-line arguments entered by the user.
     *
     * @param args
     * @throws InputError
     */
    private void parseCommandLine(String args[]) throws InputError {

        for (int i = 0; i < args.length; i++) {

            switch (args[i].charAt(0)) {
                case '-':
                    if (args[i].length() != 2) { // no "--D" or "-DD" option
                        throw new InputError("ERR01 - Wrong argument specifier ('" + args[i].charAt(1) + "'). Please refer to the user manual for further information.");
                    } else {
                        switch (args[i].charAt(1)) { // only "-D" and "-L" options are accepted
                            case 'D':
                                delimiters_string = "[" + args[i + 1] + "]";
                                break;
                            case 'L':
                                try {
                                    length = Integer.parseInt(args[i + 1]); // the value must be a number
                                } catch (NumberFormatException e) {
                                    throw new InputError("ERR02 - Wrong argument format ('" + args[i].charAt(1) + "'). The value provided is not a number.");
                                }
                                break;
                            default: // e.g. "-O" option, which does not exist
                                throw new InputError("ERR03 - Wrong argument specifier ('" + args[i].charAt(1) + "'). Please refer to the user manual for further information.");
                        }
                        i++;
                    }
                    break;
                default:
                    filename = new String(args[i]); // only raw option (no preceding '-')
                    break;
            }
        }
        if (filename == null) { // a filename MUST be specified by the user
            throw new InputError("ERR04 - No input file provided. Please refer to the user manual for further information.");
        }

        File file = new File(filename);
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new InputError("ERR05 - The file is a directory. Please refer to the user manual for further information.");
            }
            if (file.length() == 0) { // provided file must be non-empty
                throw new InputError("ERR06 - The file provided was empty. Please refer to the user manual for further information.");
            }
        } else {
            throw new InputError("ERR07 - No such file exists. Please refer to the user manual for further information.");
        }
    }


    public double count() throws InputError {

        File file = new File(filename);
        BufferedReader br = null;

        try {
            FileInputStream fis = new FileInputStream(file);
            br = new BufferedReader(new InputStreamReader(fis));
        } catch (FileNotFoundException e) {
            throw new InputError("ERR07 - No such file exists. Please refer to the user manual for further information.");
        }
        int word_count = 0, sentence_count = 0;
        double avg_wc = -1;

        try {
            String CurrentLine;

            while ((CurrentLine = br.readLine()) != null) {
                String sentences[] = CurrentLine.split(delimiters_string);
                sentence_count += sentences.length;

                for (String line : sentences) {

                    String words[] = line.split("\\W");

                    for (String word : words) {
                        if (word.length() >= length) {
                            word_count++;
                        }
                    }
                }
            }

            avg_wc = ((double) word_count) / sentence_count;

            if (br != null) {
                br.close();
            }
        } catch (IOException e) {
            throw new InputError("ERR08 - Error while reading some file contents.");
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                throw new InputError("ERR09 - Error while closing file.");
            }
        }

        return avg_wc;
    }
    
    public String format_count(double avg_count) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(avg_count);
    }

    /**
     * @param args line arguments
     */
    public static void main(String[] args) {
        try {
            WC wc = new WC(args);
            double avg_count = wc.count();
            System.out.println("The average word count in file is "+wc.format_count(avg_count));
            System.exit(OK_EXIT);
        } catch (Error e) {
            e.printMsg();
            System.exit(KO_EXIT);
        }
    }

}
