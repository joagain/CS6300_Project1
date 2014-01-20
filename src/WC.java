import java.util.ArrayList;
import Errors.InputError;
import Errors.Error;


/**
 * WordCount class. Parses the command-line input, counts the average word count per sentence.
 * @author Team #5
 *
 */
public class WC {

	private static final int KO_EXIT = -1;
	private static final int OK_EXIT = 0;
	private String filename;
	private int length;
	private ArrayList<Character> delimiters;	

	/**
	 * Constructor. Sets default values for all options.
	 * @param args
	 * @throws InputError 
	 */
	public WC(String[] args) throws InputError {
		filename = null;
		length = 3;
		delimiters = new ArrayList<Character>() {{
			add(',');
			add('.');
			add(';');
			add(':');
			add('?');
			add('!');
		}};
		parseCommandLine(args);
	}
	
	/**
	 * Parses the command-line arguments entered by the user.
	 * @param args
	 * @throws InputError 
	 */
	private void parseCommandLine(String args[]) throws InputError {
		for (int i = 0; i < args.length; i++) {
			switch (args[i].charAt(0)) {
			case '-':
				if (args[i].length() != 2) { // no "--D" or "-DD" option
					throw new InputError("Wrong argument specifier ('" + args[i].charAt(1) + "'). Please refer to the user manual for further information.");
				}
				else {
					switch (args[i].charAt(1)) { // only "-D" and "-L" options are accepted
					case 'D':
						delimiters.clear(); // the delimiters specified by the user override the default ones
						for (int j = 0; j < args[i+1].length(); j++) { // parsing the delimiters one by one, removing potential duplicates
							char currDelimiter = args[i+1].charAt(j);
							if (!delimiters.contains(currDelimiter))
								delimiters.add(currDelimiter); 
						}
						break;
					case 'L':
						try {
							length = Integer.parseInt(args[i+1]); // the value must be a number
						} catch (NumberFormatException e) {
							throw new InputError("Wrong argument format ('" + args[i].charAt(1) + "'). The value provided is not a number.");
						}
						break;
					default: // e.g. "-O" option, which does not exist
						throw new InputError("Wrong argument specifier ('" + args[i].charAt(1) + "'). Please refer to the user manual for further information.");
					}
					i++;
				}
				break;
			default:
				filename = new String(args[i]); // only raw option (no preceding '-')
				break;
			}
		}
		if (filename == null) // a filename MUST be specified by the user
			throw new InputError("No input file provided. Please refer to the user manual for further information.");
		// TODO: remove when OK
//		System.out.println("Filename = " + filename);
//		System.out.println("Length = " + length);
//		System.out.print("Delimiters = ");
//		for (char c : delimiters)
//		System.out.print(c);
	}
	
	// TODO
	public int count() {
		return 0;
	}

	/**
	 * 
	 * @param command line arguments
	 */
	public static void main(String[] args) {
		try {
			WC wc = new WC(args);
			System.exit(OK_EXIT);
		} catch (Error e) {
			e.printMsg();
			System.exit(KO_EXIT);
		}
	}

}
