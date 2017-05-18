import java.awt.Color;
/**
 * Emitter for Roman candle stars.
 * @author Alan McLeod
 * @version 1.0
 */
public class StarEmitter extends Emitter {

	private Color colour = Color.RED;
	
	/**
	 * The StarEmitter constructor.
	 * @param initialXPos The initial X position of the emitter.
	 * @param initialYPos The initial Y position of the emitter.
	 * @param initialXV The initial X velocity component of the emitter.
	 * @param initialYV The initial Y velocity component of the emitter.
	 * @param exitVelocity The launch velocity of the sparks from the emitter.
	 * @param firingAngle The launch angle of the emitter, from the vertical in degrees.
	 * @param variation The random variation range for the launch angle in degrees.
	 * @throws EmitterException If the two angles are not legal.
	 */
	public StarEmitter(double initialXPos, double initialYPos, double initialXV, double initialYV,
			double exitVelocity, double firingAngle, double variation) throws EmitterException {
		super(initialXPos, initialYPos, initialXV, initialYV, exitVelocity, firingAngle, variation);
	}
	
	/**
	 * A mutator for the colour of the Star to be launched.
	 * @param colour The desired Star colour.
	 */
	public void setColour (Color colour) {
		this.colour = colour;
	}
	
	/**
	 * Creates and "launches" a star at the given time in seconds.
	 * @param time The absolute time in seconds.
	 * @return An instance of a Star object, which will contain its initial position,
	 * initial velocity components and the desired colour.
	 */
	public Star launch(double time) {
		double angle = getRandomLaunchAngle();
		double[] position = getPosition();
		double[] velocity = getVelocity();
		double vXInitial = velocity[0] + getExitVelocity() * Math.sin(angle);
		double vYInitial = velocity[1] + getExitVelocity() * Math.cos(angle);
		return new Star(time, position[0], position[1], vXInitial, vYInitial, colour);
	}
	
} // end StarEmitter
