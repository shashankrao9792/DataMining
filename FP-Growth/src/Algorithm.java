import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Algorithm {
	HashMap<String, Links> header;
	Node root;
	HashMap<String, Integer> freq;
	List<Entry<String, Integer>> sorted;
	
	Algorithm(Node root, HashMap<String, Links> header, HashMap<String, Integer> freq){
		this.header = header;
		this.root = root;
		this.freq = freq;
		//this.sorted = Util.orderElement(freq);
	}
	
	public ArrayList<Result> fpGrowth(int min_sup) {		
		sorted = Util.orderElement(freq);
		HashMap<String, List<AllPattern>> result = new HashMap<>();
		
//		for(String str : header.keySet()) {							//UNSORTED HEADER
//		for(int j = 0; j < sorted.size(); j++) {					//ASCENDING ORDER
		for(int j = sorted.size()-1; j >= 0; j--) {					//DESCENDING ORDER
			String str = sorted.get(j).getKey();
			//sorted.get(j).getKey()
			
			if(header.get(str) != null) {
				List<Node> link1 = header.get(str).link;

				AllPattern ap = new AllPattern();
			
				List<AllPattern> sdf = new ArrayList<AllPattern>();
				for(int i = 0; i < link1.size(); i++)
					{	
						Node current = link1.get(i);
						ap.cnt = current.count;
				
						AllPattern asd = new AllPattern();
						asd.cnt = current.count;
						while(current.parent != null) {
								if(current.parent == root) {
									break;
								}else {
										asd.pattern.add(current.parent.name);
										current = current.parent;
								}
						}
						sdf.add(asd);
					}
			result.put(str, sdf);
			}
		}
		return minSupPattern(result, min_sup);
	}
	
	public ArrayList<Result> minSupPattern(HashMap<String, List<AllPattern>> result, int min_sup) {
		ArrayList<Result> result1 = new ArrayList<Result>();
		
		for(String node : result.keySet()) {
			HashMap<String, Integer> freq = new HashMap<String, Integer>();
			for(int i = 0; i < result.get(node).size(); i++) {
				for(String str : result.get(node).get(i).pattern) {
					if(!freq.containsKey(str)) {
						freq.put(str, 1);
					}else 
						freq.put(str, freq.get(str)+1);
				}
			}
			Result patt = new Result();
			//.substring(0, node.length() - 4);
			patt.name = node;
			
			for(int i = 0; i < result.get(node).size(); i++) {
				for(String str : result.get(node).get(i).pattern) {
					if(freq.get(str) >= min_sup) {
						//.substring(0, str.length() - 4)
						patt.pattern.add(str);
					}
				}
			}
			result1.add(patt);
		}
		return result1;
	}
}