import java.awt.Color;

/**
 * This class describes the star from a Roman Candle.
 * @author Alan McLeod
 * @version 1.1
 */
public class Star extends NonNewtonianParticle {
	
	private final double BURN_RATE = 0.003;	// kg/second
	private final double DENSITY_STAR = 1900;	// kg/m*m*m
	private final double STARTING_MASS = 0.008;	// kg
	private int shrinkRenderSize = 10;
	
	/**
	 * The Star constructor.
	 * The lifetime of the star is set to 2.1 seconds,
	 * the initial render size to 10 pixels,
	 * the initial radius to 0.01 metre and the initial mass to 0.008 kg.
	 * @param creationTime The absolute time of creation of the star.
	 * @param initialXPos The initial position of the star in the X direction.
	 * @param initialYPos The initial position of the star in the Y direction
	 * @param initialVX The initial X velocity component of the star.
	 * @param initialVY The initial Y velocity component of the star.
	 * @param starColour The colour of the star.
	 */
	public Star(double creationTime, double initialXPos, double initialYPos, double initialVX, 
			double initialVY, Color starColour) {
		super(creationTime, initialXPos, initialYPos, initialVX, initialVY, 2.1, 10, 0.01, 0.008, starColour);
	} // end constructor
	
	/**
	 * Returns the render size for the star.
	 * The render size for a star is not constant but shrinks in proportion to the remaining mass
	 * of the particle.  Arguably this refinement is not necessary...
	 * @return The render size of the star in pixels.
	 */
	public int getRenderSize() {
		return shrinkRenderSize;
	}
	
	/**
	 * Returns the mass of the star at the given time in seconds.
	 * @param time The time in seconds.
	 * @return The mass of the star in kg.
	 */
	public double getMass(double time) {
		return STARTING_MASS - time * BURN_RATE;
	}
	
	// Returns the radius of the star at the supplied time.
	// Time is in seconds and the returned radius is in metres.
	/**
	 * Returns the radius of the star for the given time in seconds.
	 * @param time The time in seconds.
	 * @return The radius of the star in metres.
	 */
	public double getRadius(double time) {
		double volume = getMass(time) / DENSITY_STAR;
		return Math.pow(3 * volume / (4 * Math.PI), 1.0 / 3.0);
	}
	
	/**
	 * Updates the position of the Star and calculates the new render size.
	 * Refines the updatePosition in the parent class.
	 * @param time The time in seconds.
	 * @param deltaTime The time interval in seconds.
	 * @param env The current environment object.
	 */
	public void updatePosition(double time, double deltaTime, Environment env) {
		super.updatePosition(time, deltaTime, env);
		shrinkRenderSize = (int)(10 * getMass(time) / STARTING_MASS);
	}
	
} // end Star class
