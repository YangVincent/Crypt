import java.io.*;

import java.util.*;


public class Crypt
{
	//FIELDS
	//private static final int numLetterChange = 5; //the number of letters to shift in the alphabet, make as input
    private String s_lineSeparator = System.getProperty("line.separator");

	//CONSTRUCTORS
	public Crypt()
	{

	}


	private static HashMap<String, String> initAlpha(String key)
	{

		HashSet keyAlpha = new HashSet();
		HashMap<String, String> cipher = new HashMap<String, String>(26*2);
		ArrayList<Character> alphabet = new ArrayList<Character>(26*2);
		for (int i = 65; i <= 90; i++) //upper case
		{
			alphabet.add(new Character((char)i));
		}
		/*
		for (int i = 97; i <= 122; i++) //lower case
		{
			alphabet.add(new Character((char)i));
		}
        */
		Character[] allAlphabet = alphabet.toArray(new Character[0]);
		int alphaCounter = 0;
		String currentChar = null;
		for(int i = 0; i < key.length(); i++)
		{
			if (keyAlpha.add(key.charAt(i)))
			{
				currentChar = key.charAt(i)+"";
				cipher.put(allAlphabet[alphaCounter]+"",currentChar.toUpperCase() );
				//System.out.println("Map " +allAlphabet[alphaCounter] + ", " + currentChar.toUpperCase());
				// add corresponding lower case
				cipher.put((allAlphabet[alphaCounter++]+"").toLowerCase(),currentChar.toLowerCase() );
				alphabet.remove(new Character(Character.toUpperCase(key.charAt(i))));
			}
			else
			{
				//System.out.println("Skipping " + key.charAt(i));
			}
		}
		//put in the rest of the alphabet

		while (!alphabet.isEmpty())
		{
			//System.out.println("size of alphabet is " + alphabet.size());
			currentChar = alphabet.remove(alphabet.size()-1)+"";
			cipher.put(allAlphabet[alphaCounter]+"",currentChar.toUpperCase());
			//System.out.println(alphaCounter+" Map " + allAlphabet[alphaCounter] + ", " + currentChar.toUpperCase());
			cipher.put((allAlphabet[alphaCounter++]+"").toLowerCase(),currentChar.toLowerCase() );
		}
		return cipher;

	}
	public void encrypt(String inputFileName, String outputFileName, String key)
	{
		int keyword = key.length();
		FileReader reader = null;
		BufferedReader bReader = null;
		FileWriter writer = null;
		BufferedWriter bWriter = null;

		try
		{
			//reader = new FileReader (inputFilename);
			//bReader = new BufferedReader (reader);
			//writer = new FileWriter(outputFilename);
			//bWriter = new BufferedWriter(writer);
			bReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName)));
			bWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFileName)));

			Scanner in = new Scanner(bReader);
			HashMap<String, String> alphabet = initAlpha(key);
			while (in.hasNextLine())
			{
				//StringBuffer input = new StringBuffer(in.nextLine() + System.getProperty("line.separator"));
				String input =in.nextLine() + s_lineSeparator;
				StringBuilder encryptedText = new StringBuilder();
				String currentChar = null;
				//ENCRYPT DATA HERE
				 for (int i = 0; i < input.length(); i++)
				 {
				 	currentChar = input.substring(i, i+1);
				 	if (alphabet.containsKey(currentChar))
				 	  encryptedText.append(alphabet.get(currentChar));
				 	else
				 	  encryptedText.append(currentChar);
				 }

				//END ENCRYPTION HERE
				bWriter.write(encryptedText.toString(), 0, encryptedText.length());
			}
			bWriter.flush();
		}
		catch (IOException ex)
		{
			System.out.println("File cannot be read or written.");
		}
		finally
		{
			try
			{
				if (bReader != null)
					bReader.close();
				if (bWriter != null)
					bWriter.close();
			}
			catch (IOException ex)
			{
				System.out.println("FIle cannot be closed.");
			}
		}
	}
	private static HashMap<String, String> initDecryptAlpha(String key)
	{
		//System.out.println("decrypt");
		HashSet keyAlpha = new HashSet();
		HashMap<String, String> cipher = new HashMap<String, String>(26*2);
		ArrayList<Character> alphabet = new ArrayList<Character>(26*2);
		for (int i = 65; i <= 90; i++) //upper case
		{
			alphabet.add(new Character((char)i));
		}
		/*
		for (int i = 97; i <= 122; i++) //lower case
		{
			alphabet.add(new Character((char)i));
		}*/

		Character[] allAlphabet = alphabet.toArray(new Character[0]);
		int alphaCounter = 0;
		String currentChar = null;
		for(int i = 0; i < key.length(); i++)
		{
			if (keyAlpha.add(key.charAt(i)))
			{
				currentChar = key.charAt(i)+"";
				cipher.put(currentChar.toUpperCase(), allAlphabet[alphaCounter]+"");
				//System.out.println("Map " + currentChar.toUpperCase() + ", " + allAlphabet[alphaCounter]);
				cipher.put(currentChar.toLowerCase(), (allAlphabet[alphaCounter++]+"").toLowerCase());
				alphabet.remove(new Character(Character.toUpperCase(key.charAt(i))));
			}
			else
			{
				//System.out.println("Skipping " + key.charAt(i));
			}
		}
		//put in the rest of the alphabet

		while (!alphabet.isEmpty())
		{
			currentChar = alphabet.remove(alphabet.size()-1)+"";
			cipher.put(currentChar, allAlphabet[alphaCounter]+"");
			//System.out.println("Map " + currentChar + ", " + allAlphabet[alphaCounter-1]);
			cipher.put(currentChar.toLowerCase(), (allAlphabet[alphaCounter++]+"").toLowerCase());
		}
		return cipher;

	}
	public void decrypt(String inputFileName, String outputFileName, String key)
	{
		int keyword = key.length();
		FileReader reader = null;
		BufferedReader bReader = null;
		FileWriter writer = null;
		BufferedWriter bWriter = null;

		try
		{
			//reader = new FileReader (inputFilename);
			//bReader = new BufferedReader (reader);
			//writer = new FileWriter(outputFilename);
			//bWriter = new BufferedWriter(writer);
			bReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName)));
			bWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFileName)));
			HashMap<String, String> alphabet = initDecryptAlpha(key);
			Scanner in = new Scanner(bReader);
			while (in.hasNextLine())
			{
				String input = in.nextLine() + s_lineSeparator;
				StringBuilder decryptedText = new StringBuilder();
				//StringBuffer input = new StringBuffer(in.nextLine());
				//DECRYPT DATA HERE
				String currentChar = null;
				for (int i = 0; i < input.length(); i++)
				 {
				 	currentChar = input.substring(i, i+1);
				 	if (alphabet.containsKey(currentChar))
				 	  decryptedText.append(alphabet.get(currentChar));
				 	else
				 	  decryptedText.append(currentChar);
				 }

				//END ENCRYPTION HERE

				bWriter.write(decryptedText.toString(), 0, decryptedText.length());
			}
			bWriter.flush();
		}
		catch (IOException ex)
		{
			System.out.println("File cannot be read or written.");
		}
		finally
		{
			try
			{
				if (bReader != null)
					bReader.close();
				if (bWriter != null)
					bWriter.close();
			}
			catch (IOException ex)
			{
				System.out.println("FIle cannot be closed.");
			}
		}
	}


	//testing

}