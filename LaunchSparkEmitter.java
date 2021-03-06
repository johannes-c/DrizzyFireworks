import java.awt.Color;
/**
 * Emitter for launch sparks.
 * @author Alan McLeod
 * @version 1.1
 */
public class LaunchSparkEmitter extends Emitter {

	/**
	 * The LaunchSparkEmitter constructor.
	 * @param initialXPos The initial X position of the emitter.
	 * @param initialYPos The initial Y position of the emitter.
	 * @param initialXV The initial X velocity component of the emitter.
	 * @param initialYV The initial Y velocity component of the emitter.
	 * @param exitVelocity The launch velocity of the sparks from the emitter.
	 * @param firingAngle The launch angle of the emitter, from the vertical in degrees.
	 * @param variation The random variation range for the launch angle in degrees.
	 * @throws EmitterException If the two angles are not legal.
	 */
	public LaunchSparkEmitter(double initialXPos, double initialYPos, double initialXV, double initialYV,
			double exitVelocity, double firingAngle, double variation) throws EmitterException {
		super(initialXPos, initialYPos, initialXV, initialYV, exitVelocity, firingAngle, variation);
	}
	
	/**
	 * Launches (returns) a single LaunchSpark object at the supplied time.
	 * @param time The absolute launch time in seconds.
	 * @return An instance of a LaunchSpark.
	 */
	public LaunchSpark launch(double time) {
		double angle = getRandomLaunchAngle();
		double[] position = getPosition();
		double[] velocity = getVelocity();
		double vXInitial = velocity[0] + getExitVelocity() * Math.sin(angle);
		double vYInitial = velocity[1] + getExitVelocity() * Math.cos(angle);
		return new LaunchSpark(time, position[0], position[1], vXInitial, vYInitial);
	} // end launch

} // end LaunchSparkEmitter
