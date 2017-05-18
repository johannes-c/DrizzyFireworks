import java.awt.Color;
/**
 * A base class for Newtonian particles - particles that do not need to be modeled with drag forces.
 * @author Alan McLeod
 * @version 1.1
 */
public class NewtonianParticle extends Particle {

	private double initialXPos;
	private double initialYPos;
	
	/**
	 * The constructor for a NewtonianParticle.
	 * @param creationTime The absolute time of creation of the particle.
	 * @param initialXPos The initial position of the particle in the X direction.
	 * @param initialYPos The initial position of the particle in the Y direction
	 * @param initialVX The initial X velocity component of the particle.
	 * @param initialVY The initial Y velocity component of the particle.
	 * @param lifetime The particle lifetime in seconds.
	 * @param renderSize The diameter of the rendered oval in pixels.
	 * @param colour The colour of the particle.
	 */
	public NewtonianParticle(double creationTime, double initialXPos, double initialYPos, double initialVX, 
			double initialVY, double lifetime, int renderSize, Color colour) {
		super(creationTime, initialXPos, initialYPos, initialVX, initialVY, lifetime, renderSize, colour);
		this.initialXPos = initialXPos;
		this.initialYPos = initialYPos;
	} // end constructor
	
	/**
	 * Updates the position of a Newtonian particle using normal, Newtonian physics - ignoring drag
	 * forces.
	 * @param time The absolute time in seconds.
	 * @param deltaTime The time difference.  (Not needed for a Newtonian particle.)
	 * @param env An instance of the current environment object. 
	 */
	public void updatePosition(double time, double deltaTime, Environment env) {
		double[] velocities = getVelocity();
		double xVelocity = velocities[0];
		double yVelocity = velocities[1];
		double wind = env.getWindVelocity();
		double[] positions = new double[2];
		time = time - getCreationTime();
		positions[0] = initialXPos + (xVelocity + wind) * time;
		positions[1] = initialYPos + yVelocity * time - 0.5 * Environment.G * time * time;
		setPosition(positions);
	} // end updatePosition

} // end NewtonianParticle
