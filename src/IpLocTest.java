import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IpLocTest {
		
	public static void main(String[] args) throws Exception {
		P.t("ip");
		Ip2Location ilc = new Ip2Location();
		P.t("ip","load map");
		int n = 1000000;
		if (args.length>0)
			n = Integer.parseInt(args[0]);
		test(ilc,n,2);test(ilc,n*2,2);test(ilc,n*4,2);test(ilc,n*8,2);test(ilc,n*16,2);
	}
	
	private static void test(Ip2Location ilc,int n,int repeat){
		List<String> ipList = ilc.prepareTestIpList(n);
		List<String[]> results = new ArrayList<String[]>(n);
		System.out.println();
		P.t("ip","prepare ip data, ip[" + ipList.size() + "]=" + ipList.get(ipList.size()-1));
		
		for (int i=0; i<repeat; i++) {
			int count = 0;			
			for (String ip:ipList) {
				String[] loc = ilc.getIpLoc(ip);
				if (loc!=null){
					results.add(loc);
					count++;
				}
			}
			P.t("ip",(i+1) +  " query " + ipList.size() + " ips, found " + count + " last result " + Arrays.toString(results.get(results.size()-1)));
		}
	}
	
}
