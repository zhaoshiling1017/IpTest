import java.util.Arrays;

public class Scope {
	private short start;
	private short end;
	private String[] loc;
	
	public Scope(short start, short end, String[] loc) {
		this.start = start;
		this.end = end;
		this.loc = loc;
	}
	
	public Scope(String sc,String[] loc){
		String[] s = sc.split("/");
		start = Short.parseShort(s[0]);
		end = Short.parseShort(s[1]);
		if (start>end){
			short temp = start;
			start = end;
			end = temp;
		}
		this.loc = loc;
	}
	
	public boolean in(short value){
		return value>=start&&value<=end;
	}
	
	public boolean in(int value){
		return value>=start&&value<=end;
	}
	
	// 重叠或首尾相接且loc相等可以合并
	public boolean merge(Scope sc){
		if ((in(sc.start)||in(sc.end)||in(sc.start-1)||in(sc.end+1))&&Arrays.equals(sc.loc, loc)){
			start = (short)Math.min(start,sc.start);
			end = (short)Math.max(end,sc.end);
			return true;
		}
		return false;
	}
	
	public String[] getLoc(short value){
		return (value>=start&&value<=end)?loc:null;
	}
}
