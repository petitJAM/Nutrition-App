package Database;

/**
 * A class to represent devices that connect to our system to keep track of
 * interesting statistics and stuff
 * 
 * @author bellrj
 * 
 */
public class Device {

	/**
	 * the number of times we correctly identified the type of food
	 */
	public int times_correct;
	/**
	 * the number of times this device has used our service
	 */
	public int times_used;
	/**
	 * the integer identifier for this device
	 */
	public int id;

	/**
	 * constructor
	 * 
	 * @param id
	 *            the unique identifier for this device
	 * @param times_used
	 *            the number of times this device has used our service
	 * @param times_correct
	 *            the number of times we correctly identified the type of food
	 */
	public Device(int id, int times_used, int times_correct) {
		this.id = id;
		this.times_used = times_used;
		this.times_correct = times_correct;
	}
}
