import java.awt.Color;
/**
 * A spark shot out by the star as it rises from the Roman Candle.
 * @author Alan McLeod
 * @version 1.1
 */
public class StarSpark extends NonNewtonianParticle {
	
	/**
	 * The StarSpark constructor.
	 * The lifetime of the spark is set to 0.25 seconds, the render size to 3 pixels,
	 * the radius to 0.001 metres and the mass to 2e-6 kg.
	 * @param creationTime The absolute time of creation of the spark.
	 * @param initialXPos The initial position of the spark in the X direction.
	 * @param initialYPos The initial position of the spark in the Y direction
	 * @param initialVX The initial X velocity component of the spark.
	 * @param initialVY The initial Y velocity component of the spark.
	 * @param starColour The colour of the spark.
	 */
	public StarSpark(double creationTime, double initialXPos, double initialYPos, double initialVX, 
			double initialVY, Color starColour) {
		super(creationTime, initialXPos, initialYPos, initialVX, initialVY, 0.25, 3, 0.001, 2E-6, starColour);
	} // end constructor

} // end StarSpark
