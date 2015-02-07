import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import javax.swing.*;

/*
 * 
 * Code for Question 1 in this class
 *  NOTE: For some reason my File tab in the menu bar doesn't appear sometimes when the program is run
 * You may need to run it 4 or 5 times before it displays(maybe this is just a glitch on my version of java)
 * 
 * 
 */
public class TokenCategorizer extends JFrame {	//class extends the JFrame class

	//declare variables
	private static final long serialVersionUID = 1L;
	protected ArrayList<Long> listOfLongs = new ArrayList<Long>();
	protected ArrayList<Double> listOfDoubles = new ArrayList<Double>();
	protected ArrayList<String> listOfStrings = new ArrayList<String>();
	protected static Locale locale;
	
	//main method
	public static void main(String args[]) throws FileNotFoundException {
		//arguments that been set in run configurations
		String filename = args[0];
		String language = args[1];
		String country = args[2];

		//create locale using arguments above
		locale = new Locale(language, country);
		TokenCategorizer2 token2=new TokenCategorizer2(filename, locale);//create object for TokenCategorizer 2 Class and pass arguments
	}
	
	public void FileInput(String filename,Locale locale) throws FileNotFoundException{
		Scanner s = null;	//create scanner object

		StringBuilder out = new StringBuilder();	//create stringbuilder object

		try {
			s = new Scanner(new BufferedReader(new FileReader(filename)));
			s.useLocale(locale);	//set the locale t

			while (s.hasNext()) { // while any more tokens in stream

				if (s.hasNextLong()) {	//if token is a long
					listOfLongs.add(s.nextLong());	//add to list of longs
				}

				else if (s.hasNextDouble()) {	//if next token is a double
					listOfDoubles.add(s.nextDouble());	//add it to the list of doubles

				}

				else {
					listOfStrings.add(s.next());	//otherwise add to the list of longs
				}

			}
		} finally {
			if (s != null)	//if null
				s.close();	//close the scanner
		}

		//print statements
		System.out.println("Longs: " + listOfLongs);
		System.out.println("Doubles: " + listOfDoubles);
		System.out.println("Tokens: " + listOfStrings);
		
	}//end method
	
}//end class