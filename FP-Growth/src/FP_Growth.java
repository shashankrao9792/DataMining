import java.util.*;

public class FP_Growth {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();

		final String filePath = "D:\\SEM-II\\Learning Systems-ESE589-Doboli\\Project 2\\zoo.csv";
		//Adult, restraunt, wine, zoo, self
		//tur, wiki, der
		final int minSup = 15;
		final int minSupPatt = 15;
		ArrayList<Result> pattern;
		Model model;
		HashMap<String, Integer> freq ;
		ArrayList<Attribute> dataset; 
		
		
		if(!Util.ifTransactional()) {
			freq = Util.calFreq(filePath);												//COUNT FREQUENCY OF EACH ELEMENT
			dataset = Util.readCSV(freq, filePath, minSup);								// REARRANGE TUPLE IN SORTED ORDER
		}else {
			freq = TransactionalUtil.calFreq(filePath);
			dataset = TransactionalUtil.readCSV(freq, filePath, minSup);
		}
		
		FP_Tree constructFPTree = new FP_Tree(dataset);									//CONSTRUCT FP_TREE
		model = constructFPTree.parseTree();											//CONTAINS COMPLETE FP_TREE AND HEADER TABLE
		
		
		Algorithm fpGrowth = new Algorithm(model.root, model.header, freq);				//FP GROWTH ALGORITHM
		pattern = fpGrowth.fpGrowth(minSupPatt);
		
		//Util.displayfreq(freq);														//PRINT FREQUENCY OF EACH NODE 
		//Util.display(dataset);														//PRINT REARRANGED DATASET
		//Util.displayTree(model.root);													//PRINT FP-TREE 
		//Util.displayPatt(pattern);													//PRINT PATTERN
		
		int totalPatterns = 0;
		int patternCount = 0;
		for (Result result : pattern) {
			totalPatterns += result.pattern.size();
			patternCount++;
		}
		
		if(patternCount != 0) {
			System.out.println("tatal patterns:\t" + totalPatterns);
			System.out.println("avg patterns:\t" + totalPatterns/patternCount);
		}
		
		long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
		long endTime = System.currentTimeMillis();
		System.out.println("It took " + (afterUsedMem - beforeUsedMem) + "Bytes");		//PRINT MEMORY CONSUMPTION
		System.out.println("It took " + (endTime - startTime) + " milliseconds");		//PRINT TIME CONSUMPTION
	}
}