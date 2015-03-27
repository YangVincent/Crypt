
import java.util.*;
import java.io.*;


public class CryptTester {

	public static final String testFile = "Shakespeare"; //Assumes .txt extension
	//public static final String testFile = "dumb";
	public static final int algorithm = 1;
	// Set to:
	// 1 for 15.2 (random monoalphabet)
	// 2 for 15.4 (Vigenere)
	// 3 for 15.5 (Playfair)

	public static final String keyword = "crypt";



    public static String readFile (String filename) {
    	String fileData = "";

    	FileReader reader = null;
    	try {

	    	try {
    			reader = new FileReader(filename);
				Scanner in = new Scanner(reader);
				while (in.hasNextLine()) {
					String input = in.nextLine();
					fileData += input + System.getProperty("line.separator");                     //(char)13 + (char)10;
				}
	    	} catch (IOException ex) {
	    		System.out.println("File cannot be read.");
	    		return null;
			} finally {
				if (reader != null) reader.close();
			}

    	} catch (Exception ex) {
    		System.out.println("PANIC.");
    	}
		return fileData;

    }



	public static void main(String[] args) {


		// TESTING: How long does it take to encrypt and decrypt?
		long startTime = System.nanoTime();

		Crypt worker1 = new Crypt();
		worker1.encrypt(testFile+".txt", testFile+"Enc.txt", keyword);

		long endTime = System.nanoTime();
		long time = endTime - startTime;

		String data = readFile("numberData.txt");

		startTime = System.nanoTime();
		Crypt worker2 = new Crypt();
		worker2.decrypt(testFile+"Enc.txt", testFile+"2.txt", keyword);

		endTime = System.nanoTime();
		time += endTime - startTime;


		// TESTING: Is your unencrypted file exactly equal to the original?
		/*String data1 = readFile(testFile+".txt");
		String data2 = readFile(testFile+"2.txt");

		boolean equal1 = true;
		if (algorithm < 3)
			equal1 = data1.equals(data2);
		else {
			for (int i = 0; i < data1.length(); i++) {
				char one = data1.charAt(i);
				char two = data2.charAt(i);
				if (one != 'j' && one != 'J' && one != two) {
					equal1 = false;
					break;
				}
			}
		}


		// TESTING: Is your encrypted file exactly equal to the algorithm sample?
    	String data3 = readFile(testFile+"Enc.txt");
		String data4 = readFile(testFile+"Enc"+algorithm+".txt");

		boolean equal2 = data3.equals(data4);


    	// OUTPUT!
    	System.out.println("Is the file the same after an encrypt, then a decrypt? " + equal1);
    	System.out.println("Does the encrypted file match the sample? " + equal2);*/
    	System.out.println("The run time (nanoseconds): " + time);


	}


}
















