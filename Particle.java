import java.awt.Color;
/**
 * A base class for all particles.
 * @author Alan McLeod
 * @version 1.1
 */
public abstract class Particle extends PositionVelocity {

	private double creationTime;				// sec
	private double lifetime;					// sec
	private Color colour;
	private int renderSize;						// pixels
	
	/**
	 * The Particle constructor.
	 * @param creationTime The absolute time of creation of the particle.
	 * @param initialXPos The initial position of the particle in the X direction.
	 * @param initialYPos The initial position of the particle in the Y direction
	 * @param initialVX The initial X velocity component of the particle.
	 * @param initialVY The initial Y velocity component of the particle.
	 * @param lifetime The lifetime of the particle in seconds.
	 * @param renderSize The diameter of the rendered oval in pixels.
	 * @param colour The rendered colour of the particle.
	 */
	public Particle(double creationTime, double initialXPos, double initialYPos, double initialVX, 
			double initialVY, double lifetime, int renderSize, Color colour) {
		super(initialXPos, initialYPos, initialVX, initialVY);
		this.lifetime = lifetime;
		this.renderSize = renderSize;
		this.creationTime = creationTime;
		this.colour = colour;
	}
	
	/**
	 * An accessor for the particle lifetime.
	 * @return The particle lifetime in seconds.
	 */
	public double getLifetime() { return lifetime; }
	
	/**
	 * An accessor for the particle creation time.
	 * @return The particle creation time in seconds.
	 */
	public double getCreationTime() { return creationTime; }
	
	/**
	 * An accessor for the particle colour.
	 * @return The colour of the particle.
	 */
	public Color getColour() { return colour; }
	
	
	/**
	 * An accessor for the render size.
	 * @return The render size in pixels.
	 */
	public int getRenderSize() { return renderSize; }
		
	/**
	 * Updates the position of the particle.
	 * @param time The absolute time in seconds.
	 * @param deltaTime The time difference in seconds from the last time this method was invoked.
	 * @param env An instance of the current environment object. 
	 */
	public abstract void updatePosition(double time, double deltaTime, Environment env);
	
} // end Particle
