import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Map.Entry;

public class TransactionalUtil {

	static HashMap<String, Integer> freq;
	//HashMap<String, Integer> 
	//CALCULATE FREQUECY OF EACH ELEMENT
	public static HashMap<String, Integer> calFreq(String filePath) {
		Scanner in;
		freq = new HashMap<String, Integer>();
		
		try {
			
			in = new Scanner(new File(filePath));
			//TODO: IF FIRST ROW IS INFORMATION
			//String str = in.next();
		
			while(in.hasNext()) {
				String[] strAttrs = in.next().split(",");
				for (int i = 0; i < strAttrs.length; i++) {
					if (!strAttrs[i].isEmpty()){
						//MAKE EACH ELEMENT UNIQUE
						//strAttrs[i] = strAttrs[i].concat("col") + i;
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
		return freq;//minSup(min_Sup, freq);
				
	}

	//READ CSV FILE STORE TUPLES INTO DATABASE
	public static ArrayList<Attribute> readCSV(HashMap<String, Integer> freq, String filePath, int min_Sup) {
		
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
					if (!strAttrs[i].isEmpty()){
						//strAttrs[i] = strAttrs[i].concat("col") + i; 
						tuple.put(strAttrs[i], freq.get(strAttrs[i]));
					}
				}
				sortedTuple = Util.orderElement(tuple);
				
				for(int j = sortedTuple.size()-1; j >= 0; j--)
					if(Util.checkMinSup(min_Sup, sortedTuple.get(j).getValue())) {
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

}
