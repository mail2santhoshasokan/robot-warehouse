package exception;

/**
 * Exception when robot moves out of the grid
 */
public class MovingOutOfGridException extends Exception{

    public MovingOutOfGridException(String message) {
        super(message);
    }
}
