import java.awt.Color;
/**
 * A base class for any Particle that must take drag force into account and use the RungeKutta solver.
 * @author Alan McLeod
 * @version 1.1
 */
public class NonNewtonianParticle extends Particle implements ODESystem {

	private final double DRAG_COEFF = 0.5;		// unitless
	private final int SYSTEM_SIZE = 2;
	private double startingRadius;	// metre
	private double startingMass;		// kg
	private RungeKuttaSolver solver;
	
	/**
	 * The constructor for the NonNewtonianParticle class.  The constructor also 
	 * instantiates the solver object.
	 * @param creationTime The absolute time of creation of the particle.
	 * @param initialXPos The initial position of the particle in the X direction.
	 * @param initialYPos The initial position of the particle in the Y direction
	 * @param initialVX The initial X velocity component of the particle.
	 * @param initialVY The initial Y velocity component of the particle.
	 * @param lifetime The lifetime of the particle in seconds.
	 * @param renderSize The diameter of the rendered oval in pixels.
	 * @param startRadius The initial radius of the particle in metres.
	 * @param startMass The initial mass of the particle in kg.
	 * @param colour The colour of the particle.
	 */
	public NonNewtonianParticle(double creationTime, double initialXPos, double initialYPos, double initialVX, 
			double initialVY, double lifetime, int renderSize, double startRadius, double startMass, Color colour) {
		super(creationTime, initialXPos, initialYPos, initialVX, initialVY, lifetime, renderSize, colour);
		startingRadius = startRadius;
		startingMass = startMass;
		try {
			solver = new RungeKuttaSolver(this);
		} catch (SolverException e) {
			System.out.println(e.getMessage());
			System.out.println("Exiting!");
		}
	} // end constructor
		
	/**
	 * Returns the particle radius at a given time.
	 * @param time The time in seconds.
	 * @return The radius in metres.
	 */
	public double getRadius(double time) { return startingRadius; }
	
	/**
	 * Returns the mass at a given time.
	 * @param time The time in seconds.
	 * @return The mass in kg.
	 */
	public double getMass(double time) { return startingMass; }
	
	public int getSystemSize() { return SYSTEM_SIZE; }
	
	// Returns the velocity magnitude in m/sec, given the two
	// velocity components.
	private double getVelocityMag(double vx, double vy) {
		return Math.sqrt(vx * vx + vy * vy);
	}
	
	// Calculates the magnitude of the drag force on the star, given time in
	// seconds and the two velocity components in m/sec.
	private double getDragForce(double time, double vx, double vy) {
		double velocityMag = getVelocityMag(vx, vy);
		double radius = getRadius(time);
		double area = Math.PI * radius * radius;
		return Environment.DENSITY_AIR * velocityMag * velocityMag * area * DRAG_COEFF / 2;
	}
	
	// This method returns the value of the fx function, given the 
	// time in seconds and the two velocity components in m/sec.
	// The meaning of fx is described in the assignment statement.
	private double xDE(double time, double vx, double vy) {
		double velocityMag = getVelocityMag(vx, vy);
		double mass = getMass(time);
		double dragForce = getDragForce(time, vx, vy);
		return -dragForce * vx / (mass * velocityMag);
	}
	
	// This method returns the value of the fy function, given the 
	// time in seconds and the two velocity components in m/sec.
	// The meaning of fy is described in the assignment statement.
	private double yDE(double time, double vx, double vy) {
		double velocityMag = getVelocityMag(vx, vy);
		double mass = getMass(time);
		double dragForce = getDragForce(time, vx, vy);
		return -Environment.G - dragForce * vy / (mass * velocityMag);
	}
	
	public double[] getFunction(double time, double[] values) {
		double[] functionVal = new double[SYSTEM_SIZE];
		double vX = values[0];
		double vY = values[1];
		functionVal[0] = xDE(time, vX, vY);
		functionVal[1] = yDE(time, vX, vY);
		return functionVal;
	}

	/**
	 * A mutator that updates the current position of the star.
	 * @param time The current time in seconds.
	 * @param deltaTime The time interval in seconds.
	 * @param env An instance of the current Environment object is needed to supply the
	 * wind velocity, which is used to calculate the apparent velocity.
	 */
	public void updatePosition(double time, double deltaTime, Environment env) {
		time = time - getCreationTime();
		double[] newValues = solver.getNextPoint(time, deltaTime);
		setVelocity(newValues);
		double xVelocity = newValues[0];
		double yVelocity = newValues[1];
		double wind = env.getWindVelocity();
		double[] positions = getPosition();
		double xPos = positions[0] + (xVelocity + wind) * deltaTime;
		double yPos = positions[1] + yVelocity * deltaTime;
		newValues[0] = xPos;
		newValues[1] = yPos;
		setPosition(newValues);
	} // end updatePosition

} // end NonNewtonianParticle
