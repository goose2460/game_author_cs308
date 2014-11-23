package engine.physics;

/**
 * friction force, subclass of force, is a vector
 * 
 * @author Ben
 *
 */
public class Friction extends Force {
	private static final double GRAVITY_ACCELERATION = 9.8;

	public Friction(double x, double y, double mass, double friction) {
		super();
		myValues.put("mass", mass);
		myValues.put("friction", friction);
		constructionHelper(x * myForceValue, y * myForceValue);
	}

	/**
	 * calculates value of friction force
	 */
	protected void calculateForce() {
		myForceValue = myValues.get("friction") * myValues.get("mass")
				* GRAVITY_ACCELERATION;
	}
}
