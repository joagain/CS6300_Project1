package Errors;

/**
 * Error class.
 * @author Team #5
 *
 */
public class Error extends Exception {

	private static final long serialVersionUID = 1L;
	protected String message;

	public Error() {
	}
	
	public Error(String message) {
		this.message = message;
	}
	
	public void printMsg() {
		System.err.println(message);
	}
	
}
