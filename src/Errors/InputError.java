package Errors;

/**
 * InputError class. Should be instantiated whenever a command-line related error occurs.
 * @author Team #5
 *
 */
public class InputError extends Error {

	private static final long serialVersionUID = 1L;

	public InputError(String message) {
		this.message = "[Input error] " + message;
	}
	
	public String getMessage() {
		return this.message;
	}
}
