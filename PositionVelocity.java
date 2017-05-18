/**
 * A base class for all Emitter and Particle objects.  This class holds position and
 * velocity data.
 * @author Alan McLeod
 * @version 1.0
 */
public class PositionVelocity {

	private double[] position = new double[2];
	private double[] velocity = new double[2];
	
	/**
	 * The PositionVelocity constructor.
	 * @param xPos The x position in metres.
	 * @param yPos The y position in metres.
	 * @param xVel The x velocity component in m/sec.
	 * @param yVel The y velocity component in m/sec.
	 */
	public PositionVelocity(double xPos, double yPos, double xVel, double yVel) {
		position[0] = xPos;
		position[1] = yPos;
		velocity[0] = xVel;
		velocity[1] = yVel;
	}

	/**
	 * An accessor for position data.
	 * @return An array for the (x, y) position in metres.
	 */
	public double[] getPosition() { return position.clone(); }
	
	/**
	 * An accessor for the velocity data.
	 * @return An array for the (vx, vy) velocity data in m/sec.
	 */
	public double[] getVelocity() { return velocity.clone(); }
	
	/**
	 * A mutator for the position data.
	 * @param position An array containing the (x, y) position data in metres.
	 */
	public void setPosition(double[] position) {
		this.position = position.clone();
	}
	
	/**
	 * A mutator for the velocity data.
	 * @param velocity An array containing the (vx, vy) velocity data in m/sec.
	 */
	public void setVelocity(double[] velocity) {
		this.velocity = velocity.clone();
	}
	
} // end PositionVelocity
