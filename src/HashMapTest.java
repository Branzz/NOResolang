import java.util.HashMap;

public class HashMapTest {

	public static void main(String[] args) {

		HashMap<String, String> x = new HashMap<String, String>();
		x.put("a", "123");
		System.out.println(x.get("a"));
		x.put("a", "6");
		System.out.println(x.get("a"));

	}

}
