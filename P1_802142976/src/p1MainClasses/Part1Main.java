package p1MainClasses;

import java.io.FileNotFoundException;

import dataGenerator.DataReader;

/**
 * 
 * @author Barbara P. Gonzalez 802-14-2976
 *
 */


//Leer Data generada por FilesGeneratorMain
//Enviar dicha data a P1andP2, P3 y P4

public class Part1Main {

	  public static void main(String[] args) throws FileNotFoundException {
		  
		DataReader dr = new DataReader();
		
		//Calling the FilesGeneratorMain to read the files
		FilesGeneratorMain fgm = new FilesGeneratorMain();
		
		//tells the FilesGeneratorMain to read the arguments given by user 
		//when calling part1main by cmd and giving the numbers
		//FilesGeneratorMain has already a method to read the arguments or assign a default value.
		fgm.main(args);
		
		dr.readDataFiles();
		
		dr.printSets();
		
		//remember to change the argument
		P1andP2 p1p2result = new P1andP2(null);
		P3 p3result = new P3(null);
		P4 p4result = new P4(null);
		
		  
	  }
}
