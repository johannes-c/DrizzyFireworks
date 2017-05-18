/**
 * An exception thrown by the RungeKuttaSolver class' constructor.
 * @author Alan McLeod
 * @version 1.0
 */
public class SolverException extends Exception {

	/**
	 * The constructor accepts a specific message about the problem.
	 * @param message
	 */
	public SolverException(String message) {
		super(message);
	}
	
} // end SolverException
