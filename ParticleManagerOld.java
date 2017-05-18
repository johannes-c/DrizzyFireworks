import java.awt.Color;
import java.util.ArrayList;
/**
 * This class manages the simulation.  It draws the roman candle and launches 8 stars of various colours.
 * The class also manages all the other particle effects: the sparks emitted by the star, the 
 * launch sparks and the delay charge sparks.
 * @author Alan McLeod
 * @version 1.0
 */
public class ParticleManager {

	private double deltaTime;		// seconds
	private double lastTime;		// seconds
	private Environment env;
	private StarEmitter emit;
	private StarSparkEmitter starSparkEmit;
	private LaunchSparkEmitter launchSparkEmit;
	private DelaySparkEmitter delaySparkEmit;
	// This ArrayList will hold all the generated particles.
	private ArrayList<Particle> fireworks = new ArrayList<>();
	private int numStars = 8;
	private int countStars = 0;
	private double starLaunchTime;
	private double maxExitVelocity;
	private boolean launchFlag = false;
	
	private Color[] colours = {Color.BLUE, Color.GREEN, Color.ORANGE, Color.RED, Color.YELLOW, Color.WHITE,
			Color.CYAN, Color.MAGENTA};
	
	/**
	 * The ParticleManager constructor
	 * @param windVelocity The wind velocity in m/sec.
	 * @param launchAngle The launch angle of the roman candle in degress off the vertical.
	 * @throws EnvironmentException If the wind velocity is not between -20 and 20 m/sec.
	 * @throws EmitterException If the launch angle is not between -15 and 15 degrees.
	 */
	public ParticleManager(double windVelocity, double launchAngle) throws EnvironmentException, EmitterException {
		env = new Environment(AssignmentFour9jc54.windSpeed);
		double la = Math.PI * launchAngle / 180.0;	// radians
		// Position the star emitter at the end of the roman candle.  Use a launch velocity of 22 m/sec
		// and add a 2 degree random variation to the launch angle.
		maxExitVelocity = 22;
		emit = new StarEmitter(Math.sin(la), Math.cos(la), 0, 0, maxExitVelocity, AssignmentFour9jc54.launchAngle, 2);
		lastTime = 0;
	}
	
	/**
	 * Launches a single star at the supplied absolute time.
	 * @param time The absolute time in seconds.  The first star will be launched at time=0.
	 */
	public void start(double time) {
		// Add some variation to the star's exit velocity
		emit.setExitVelocity(maxExitVelocity - 2 * Math.random());
		// Set the colour for the star to be launched.
		emit.setColour(colours[countStars]);
		// Launch the star
		Star singleStar = emit.launch(time);
		starLaunchTime = time;
		// Add the star to the particles collection.
		fireworks.add(singleStar);
		// Create the spark emitters using the initial position and velocity of the star.
		double[] position = singleStar.getPosition();
		double[] velocity = singleStar.getVelocity();
		double launchAngle = emit.getLaunchAngle();
		try {
			// Star sparks of the same colour as the star will be launched at 3 m/sec in all directions.
			starSparkEmit = new StarSparkEmitter(position[0], position[1], velocity[0], velocity[1], 3, 0, 180);
			starSparkEmit.setColour(colours[countStars]);
			// Launch sparks will be launched at 20 m/sec within 4 degrees of the star's launch angle.
			launchSparkEmit = new LaunchSparkEmitter(position[0], position[1], 0, 0, 20, launchAngle, 4);
			// Delay charge sparks will be sprayed out at 2.2 m/sec.
			delaySparkEmit = new DelaySparkEmitter(position[0], position[1], 0, 0, 2.2, launchAngle, 90);
		} catch (EmitterException e) {
			// Not likely to get here unless the angles are not legal.
			System.out.println(e.getMessage());
			return;
		}
		// Create 20 launch sparks to "push" the star out.
		for (int sparkCount = 0; sparkCount < 20; sparkCount++)
			fireworks.add(launchSparkEmit.launch(time));		
	} // end start method
	
	/**
	 * This method updates the simulation.
	 * @param time The absolute time in seconds. The simulation was started at time = 0;
	 */
	private void update(double time) {
		deltaTime = time - lastTime;
		lastTime = time;
		int index = 0;
		Particle firework;
		// Clean out dead fireworks
		do {
			firework = fireworks.get(index);
			if (time - firework.getCreationTime() >= firework.getLifetime()) {
				// Get rid of the star spark emitter if the star is gone.
				if (firework instanceof Star)
					starSparkEmit = null;
				fireworks.remove(index);
			} else
				index++;
		} while (fireworks.size() > 0 && index < fireworks.size());
		// Update positions
		for (Particle fire : fireworks) {
			fire.updatePosition(time, deltaTime, env);
			// moving star spark emitter along with the star
			if (fire instanceof Star)
				starSparkEmit.setPosition(fire.getPosition());
		}
		// Keep adding 5 delay charge sparks until 3.5 seconds are up.
		if (time - starLaunchTime < 3.5) {
			for (int i = 0; i < 5; i++)
				fireworks.add(delaySparkEmit.launch(time));
		}
		// Add 20 star sparks
		for (int sparkCount = 0; starSparkEmit != null && sparkCount < 20; sparkCount++)
			// Add some random variation to the creation time, so the sparks are not as clumped together.
			fireworks.add(starSparkEmit.launch(time + (1.0 * (Math.random() - 0.5))));
		// If all the particles associated with the previous star are all gone, then prevent the particle 
		// collection from becoming empty by adding a single delay charge spark, and then start the launch
		// of another star.
		if (fireworks.size() == 0) {
			if (countStars < numStars - 1) {
				for (int sparkCount = 0; sparkCount < 5; sparkCount++)
					fireworks.add(delaySparkEmit.launch(time));
				countStars++;
				start(time);
			} else {
				// Stop the simulation after 8 stars have been launched.  The collection will be empty.
				return;
			}
		}		
	} // end update
	
	public boolean getLaunchFlag(){
		return AssignmentFour9jc54.launchFlag;}
	
	public void resetLaunchFlag(){
		
		if (getLaunchFlag() == true)
			AssignmentFour9jc54.launchFlag = false;
	}
	/**
	 * An accessor for the collection of particles.
	 * For this to work, each firework type would have to have its own clone method.
	 * @param time The absolute time in seconds. The simulation stated at time = 0.
	 * @return The collection of particles.
	 */
	public ArrayList<Particle> getFireworks(double time) {
		update(time);
		ArrayList<Particle> copy = new ArrayList<>(fireworks.size());
		
		for (Particle firework : fireworks)
			copy.add(firework);
		return copy;
		
		/*
		public abstract double getRadius (double t){
			
			return ((Object) fireworks).getRenderSize(t);
			
		}
		*/
		
	}
	
	
} // end ParticleManager class
