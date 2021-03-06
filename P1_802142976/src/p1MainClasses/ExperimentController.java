package p1MainClasses;

import java.io.FileNotFoundException;
import java.util.AbstractMap;
import java.util.ArrayList;

public class ExperimentController {

	
	

	private int n = 10; 	             //n = number of companies
	private int m = 10;                  //m = number of crime events
	private int initialSize = 5;         //initialSize = the initial size of experimentations
	private int finalSize = 5;           //finalSize = final size of experimentations
	private int incrementalSizeStep = 2; //incrementalSizeStep = increment of sizes
	private int repetitionsPerSize = 2;  //rep - number of repetitions for a each size
	
	private ArrayList<StrategiesTimeCollection<Integer>> resultsPerStrategy; 
	
	// The i-th position will contain a particular strategy being tested. 
	// At the end, the i-th position will also contain a list of 
	// pairs (n, t), where t is the estimated time for size n for
	// the strategy at that position. 
	
	ExperimentController(int is, int rps, int iss, int fs){
		initialSize = is;
		repetitionsPerSize = rps;
		incrementalSizeStep = iss;
		finalSize = fs;
		resultsPerStrategy = new ArrayList<>();
	}
	
	public void addStrategy(StrategiesTimeCollection<Integer> strategy) { 
		resultsPerStrategy.add(strategy); 
	}
	
	public void run() {
		if(resultsPerStrategy.isEmpty()) {
			throw new IllegalStateException("No strategy has been added.");
		}
		ArrayList<Integer> dataSet;
		
		//for #1
		for (int size=initialSize; size<=finalSize; size+=incrementalSizeStep) { 
		// For each strategy, reset the corresponding internal variable that will be used to store the sum 
		// of times that the particular strategy exhibits for the current size size.
			
		//for #2
		for (StrategiesTimeCollection<Integer> strategy : resultsPerStrategy) 
			strategy.resetSum();    // set to 0 an internal instance variable used to accumulate sum of times
		 // Run all trials for the current size. 
		
		//for #3
		for (int r = 0; r<repetitionsPerSize; r++) {
			  // The following will be the common dataset to be used in the current trial by all the strategies being
			  // tested. Here, that data set is generated by a method that gets as input (parameter values): n, m, size. 
			  // Where n and m are the number of companies and number of crime events, respective. The generated
			  // must satisfy: size  =  i=1nj=1m(dataset[i][j].length)
			  Integer[][][] dataset =  generateFiles(n, m, size);  // data set for this trial of given size

			  // Apply each one of the strategies being tested using the previous dataset (of size size) as input; 
			  // and, for each, estimate the time that the execution takes.
			  
			  //for#4
			  for (StrategiesTimeCollection<Integer> strategy : resultsPerStrategy) {	
				  long startTime = System.nanoTime();  // Measure system�s clock time before.
				  strategy.runTrial(dataset);          // Run the strategy using the data in dataset.        
				  long endTime = System.nanoTime();    // Measure system�s clock time after.

                   int estimatedTime = (int) (endTime-startTime);   // The estimated time.
                   // Accumulate the estimated time (add it) to sum of times that the current strategy has 
                   // so far accumulated  on the previous trials for datasets of the current size. 
                   strategy.incSum(estimatedTime);    				
			  }//end of for #4
		  }//end of for #3
		  // For each strategy, compute the average time for the current size.
		
		  for (StrategiesTimeCollection<Integer> strategy : resultsPerStrategy)
			  strategy.add( new AbstractMap.SimpleEntry<Integer, Float> (size, (strategy.getSum()/((float) repetitionsPerSize))));
		
			System.out.println(n); 
	}//end of for #1
	}//end of run()
	
	public void saveResults() throws FileNotFoundException { 
		
		for (StrategiesTimeCollection<Integer> trc : resultsPerStrategy) {
			String fileName = "results"+trc.getStrategyName()+".txt"; 
			PrintStream out = new PrintStream(new File("experimentalResults", fileName)); 

			for (Map.Entry<Integer, Float> e : trc)
				out.println(e.getKey()+ "\t" + e.getValue());
			
			out.close(); 
		}

	}//end of saveResults()
}
