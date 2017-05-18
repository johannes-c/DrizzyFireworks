import java.awt.Color;

/**
 * The base class for Emitter objects.
 * @author Alan McLeod
 * @version 1.0
 */
public class Emitter extends PositionVelocity {

	private double launchAngle = 0;				// radians
	private double launchAngleVariation = 0;	// radians
	private double exitVelocity;	// m/sec	
	
	/**
	 * The constructor for an Emitter object.
	 * @param initialXPos The initial X position of the emitter.
	 * @param initialYPos The initial Y position of the emitter.
	 * @param initialXV The initial X velocity component of the emitter.
	 * @param initialYV The initial Y velocity component of the emitter.
	 * @param exitVelocity The launch velocity of the sparks from the emitter.
	 * @param firingAngle The launch angle of the emitter, from the vertical in degrees.
	 * @param variation The random variation range for the launch angle in degrees.
	 * @throws EmitterException If the two angles are not legal. The firing angle must lie
	 * between -15 and 15 degrees, and the variation angle between 0 and 180 degrees.
	 */
	public Emitter (double initialXPos, double initialYPos, double initialXV, double initialYV,
			double exitVelocity, double firingAngle, double variation) throws EmitterException {
		super(initialXPos, initialYPos, initialXV, initialYV);
		this.exitVelocity = exitVelocity;
		if (firingAngle < -15 || firingAngle > 15)
			throw new EmitterException("Firing angle out of range: " + firingAngle);
		else
			launchAngle = firingAngle * Math.PI / 180.0;
		if (variation < 0 || variation > 180)
			throw new EmitterException("Firing angle variation out of range: " + variation);
		else
			launchAngleVariation = variation * Math.PI / 180.0;
	}
	
	/**
	 * An accessor that calculates and returns an angle randomly generated between (firing angle - variation)
	 * and (firing angle + variation) in radians.
	 * @return The launch angle in radians.
	 */
	public double getRandomLaunchAngle() {
		return launchAngle + launchAngleVariation * 2 * (Math.random() - 0.5);
	}
	
	/**
	 * An accessor for the exit (or launch) velocity of the emitter.
	 * @return The exit velocity in m/sec.
	 */
	public double getExitVelocity() { return exitVelocity; }
	
	/**
	 * A mutator for the exit (or launch) velocity.
	 * @param exitVelocity The desired exit velocity in m/sec.
	 */
	public void setExitVelocity(double exitVelocity) {
		this.exitVelocity = exitVelocity;
	}
	
	/**
	 * An accessor for the launch angle.
	 * @return The launch angle in degrees.
	 */
	public double getLaunchAngle() { 
		return launchAngle * 180.0 / Math.PI; 
	}
	
} // end Emitter class
