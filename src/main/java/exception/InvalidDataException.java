package exception;

/**
 * Exception when Invalid data is provided
 */
public class InvalidDataException extends Exception{

    public InvalidDataException(String message) {
        super(message);
    }
}
