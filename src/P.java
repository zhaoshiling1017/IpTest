
import java.util.HashMap;
import java.util.Map;

public class P {
	private static Map<String,Long> map = new HashMap<String,Long>();
	
	public static void t(String s){
		t(s,null);
	}
	
	public static void t(String s,String des) {
		long t = System.currentTimeMillis();
		Long l = map.get(s);
		if (null != l) {
			System.out.println((des==null?s:des) + " in " + (t-l) + "ms");
		}
		map.put(s,t);
	}
}
