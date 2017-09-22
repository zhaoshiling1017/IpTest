import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Ip2Location {
	private Map<String,List<Scope>> map = new HashMap<String,List<Scope>>();
	private int segCount = 0;
	private int mergeCount = 0;
	
	public Ip2Location() {
		try {
			prepareMap();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void prepareMap() throws Exception {
		InputStream in = Ip2Location.class.getResourceAsStream("GeoLite2-City-Blocks-IPv4.csv");
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String data = br.readLine();
		while ((data = br.readLine()) != null) {
			addIp(data);
		}
		br.close();
		in.close();
		System.out.println("ip3 " + map.size() + ", seg " + segCount + ", merge " + mergeCount);
	}
	
	private void addIp(String str){
		String[] s = str.split(",");
		int idx = s[0].lastIndexOf(".");
		if (idx>=0) {
			String ip3 = s[0].substring(0, idx);
			String sc = s[0].substring(idx+1);
			String[] ns = new String[s.length-1];
			for (int i=0;i<ns.length;i++) {
				ns[i] = s[i+1];
			}
			Scope sp = new Scope(sc,ns);
			List<Scope> list = map.get(ip3);
			if (null == list) {
				list = new ArrayList<Scope>();
				map.put(ip3, list);
				list.add(sp);
				segCount++;
			} else {
				Scope sp1 = list.get(list.size()-1);
				if (!sp1.merge(sp)) {
					list.add(sp);
					segCount++;
				} else {
					mergeCount++;
				}
			}			
		}
	}
	// 查询主入口
	public String[] getIpLoc(String ip) {
		int idx = ip.lastIndexOf(".");
		if (idx >= 0) {
			String ip3 = ip.substring(0, idx);
			List<Scope> list = map.get(ip3);
			if (list != null) {
				short value = Short.parseShort(ip.substring(idx+1));
				for (Scope sc : list) {
					String[] v = sc.getLoc(value);
					if (v!=null) {
						return v;
					}
				}
			}
		}
		return null;
	}
	
	public List<String> prepareTestIpList(int n) {
		List<String> ipList = new ArrayList<String>(n);
		Random r = new Random();
		int count = 0;
		int repeat = n/map.size() + 1;
		for (int i=0; i<repeat; i++) {
			for (String key : map.keySet()) {
				StringBuffer sb = new StringBuffer();
				sb.append(key).append(".").append(r.nextInt(256));
				ipList.add(sb.toString());
				count++;
				if (count >= n) {
					return ipList;
				}
			}
		}
		return ipList;
	}
}
