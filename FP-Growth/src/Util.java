import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Map.Entry;


public class Util {
	static Scanner in;
	
	
	public static boolean ifTransactional() {																	//CHECK IF SOFTWARE IF TRANSCTIONAL
		System.out.println("Is Database Transactional? (Y/N)");
		in = new Scanner(System.in);
		String dbType = in.nextLine();
		if(dbType.equalsIgnoreCase("N"))
			return false;
		return true;
	}
	
	public static HashMap<String, Integer> calFreq(String filePath) {											//CALCULATE FREQUECY OF EACH ELEMENT FOR NON-TRANSACTIONAL DATASET
		
		HashMap<String, Integer> freq = new HashMap<String, Integer>();
		
		try {
			
			in = new Scanner(new File(filePath));
			while(in.hasNext()) {
				String[] strAttrs = in.next().split(",");
				for (int i = 0; i < strAttrs.length; i++) {
					if (!strAttrs[i].isEmpty() && !strAttrs[i].equals("~")){
						strAttrs[i] = strAttrs[i].concat("col") + i;           					//MAKE EACH ATTRIBUTE UNIQUE
						if(freq.containsKey(strAttrs[i])) {
							freq.put(strAttrs[i], freq.get(strAttrs[i])+1);
						}else 
							freq.put(strAttrs[i], 1);
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
		return freq;			
	}

	public static ArrayList<Attribute> readCSV(HashMap<String, Integer> freq, String filePath, int min_Sup) {	//READ CSV FILE STORE TUPLES INTO DATABASE
		
		Scanner in;
		ArrayList<Attribute> dataset = new ArrayList<Attribute>();
		HashMap<String, Integer> tuple = new HashMap<String, Integer>();
		List<Entry<String, Integer>> sortedTuple; 
		
		try {
			
			in = new Scanner(new File(filePath));
			
			/*TODO: IF FIRST ROW IS INFORMATION
			String str = in.next();*/
			
			int lenOfDS = 0;
			
			while(in.hasNext()) {
				dataset.add(new Attribute());
				String[] strAttrs = in.next().split(",");
				for (int i = 0; i < strAttrs.length; i++) {
					if (!strAttrs[i].isEmpty() && !strAttrs[i].equals("~")){
						strAttrs[i] = strAttrs[i].concat("col") + i; 
						tuple.put(strAttrs[i], freq.get(strAttrs[i]));
					}
				}
				sortedTuple = orderElement(tuple);
				
				//for(int j = sortedTuple.size()-1; j >= 0; j--)            //DATASET SORTED IN ASCENDING ORDER
				for(int j = 0; j < sortedTuple.size(); j++)
					if(checkMinSup(min_Sup, sortedTuple.get(j).getValue())) {
						dataset.get(lenOfDS).nodes.add(sortedTuple.get(j).getKey());
					}	
				lenOfDS++;
				tuple.clear();
			}
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
		
		return dataset;
	}
	
	public static boolean checkMinSup(int minSup, int node) {													//CHECK MINIMUN SUPPORT
		return node > minSup ? true : false;
	}
	
	public static List<Entry<String, Integer>> orderElement(HashMap<String, Integer> tuple) {					//SORT HASHMAP BASED ON VALUE
		List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(tuple.entrySet());
		Collections.sort(list, new Comparator<Entry<String, Integer>>(){
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {				
				return o1.getValue().compareTo(o2.getValue());
			}			
		});
		return list;
	}

	public static HashMap<String, Integer> minSup(int min_Sup, HashMap<String, Integer> freq) {					//REMOVE ELEMENTS WHICH DO NOT HAVE MINIMUM SUPPORT
		Iterator<Integer> it = freq.values().iterator();
			//freq.valuesSet().iterator();
			while (it.hasNext()){
			      Integer item = it.next();
			      if(item < min_Sup)
			    	  it.remove();
			}
			return freq;
		}

	public static void display(ArrayList<Attribute> list) {														//DISPLAY DATABASE
		System.out.println("Frequency Based Sorted Database");
		System.out.println();
		for(int i = 0; i < list.size(); i++) {
			for(int j = 0; j < list.get(i).nodes.size(); j++) {
				String str = list.get(i).nodes.get(j);
				//.substring(0, str.length() - 4) 
				System.out.print(str+ "  ");
			}
			System.out.println();
		}
	}
	
	public static void displayfreq(HashMap<String, Integer> freq) {												//DISPLAY FREQUENCY OF EACH NODE
		for (String name: freq.keySet()){
            String key =name.toString();
            Integer value = freq.get(name);  
            System.out.println(key + " " + value);
		} 
	}
	
	public static void displayPatt(ArrayList<Result> result) {													//DISPLAY PATTERN
		
		for(Result node : result) {
			System.out.println("Pattern for node: " + node.name);
			for(String str : node.pattern) {
				System.out.print(str + "-->");
			}
			System.out.println();
			//System.out.println();
		}
	}
	
	public static void displayTree(Node root) {																	//DISPLAY TREE
		Queue<Node> parents = new LinkedList<Node>();
		parents.add(root);
		while (!parents.isEmpty()) {
			Queue<Node> children = new LinkedList<Node>();
			for (Node node : parents) {
				System.out.print(node.name + ":" + node.count);
				for (Node childNode : node.childern) {
					children.add(childNode);
					System.out.print("\t");
				}
				if (node.childern.isEmpty())
					System.out.print("\t");
			}
			System.out.println();
			parents = children;
		}
	}
	
	
}
