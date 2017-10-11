
public class Variables {

	public String name;
	public int value;

	public void clear() {
		value = 0;
	}
	
	public void incr() {
		value++;
	}
	
	public void decr() {
		value--;
	}
	
	Variables(String inputName, int inputValue) {
		name = inputName;
		value = inputValue;
	}
	
}
