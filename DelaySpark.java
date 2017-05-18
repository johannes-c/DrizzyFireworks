import java.awt.Color;
/**
 * A spark shot out by the delay charge in the Roman Candle.
 * @author Alan McLeod
 * @version 1.1
 */
public class DelaySpark extends NewtonianParticle {

	/**
	 * The DelaySpark constructor.
	 * The lifetime of the spark is set to 0.6 seconds and the render size to 2 pixels.
	 * @param creationTime The absolute time of creation of the spark.
	 * @param initialXPos The initial position of the spark in the X direction.
	 * @param initialYPos The initial position of the spark in the Y direction
	 * @param initialVX The initial X velocity component of the spark.
	 * @param initialVY The initial Y velocity component of the spark.
	 */
	public DelaySpark(double creationTime, double initialXPos, double initialYPos, double initialVX, 
			double initialVY) {
		super(creationTime, initialXPos, initialYPos, initialVX, initialVY, 0.6, 2, Color.ORANGE);
	} // end constructor

} // end DelaySpark
