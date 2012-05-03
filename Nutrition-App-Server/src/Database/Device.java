package Database;

public class Device {

	public int times_correct;
	public int times_used;
	public int id;

	public Device(int id, int times_used, int times_correct) {
		this.id = id;
		this.times_used = times_used;
		this.times_correct = times_correct;
	}
}
