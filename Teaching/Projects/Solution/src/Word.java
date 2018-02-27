import java.io.Serializable;
/**
 * Project 5 - Word
 * Word object to represent a Word.
 *
 * @author Andrew Blejde
 *
 **/
import java.util.ArrayList;
import java.util.List;
/**
 * Word class will represent a word as well as
 * the ID's of URL's that it is associated with.
 *
 */
public class Word implements Serializable
{
	//Serial Version UID. DO NOT EDIT
	public static final long serialVersionUID = -3696191086353573895L;

	/** Fields for the String representation of this Word.*/
	private String word;
	
	/** List for URLIDs associated with this Word. */
	private List<Integer> postings;

	// ToDo: see if we need this
	///** Total occurences of this word. */
	//private int occurences;
	
	/**
	 * Construct a word object. 
	 * @param word String representation of word
	 * @param urlID urlID where the word was first seen.
	 */
	public Word(String word, int urlID)
	{
		/* Initialize word */
		this.word = word;
		
		/* Initialize postings list */
		this.postings = new ArrayList<Integer>();
		
		/* Add initial urlID */
		addURLID(urlID);
	}
	/**
	 * Add urlID to this List.
	 *
	 * @param urlID URLID of url the word appears
	 */
	public synchronized void addURLID(int urlID)
	{
		/* Add URLID to List */

		postings.add(urlID);
		
		///* Increment occurences */
		//occurences++;
		
	}
	
	/**
	 * Get the String representation for this Word object
	 * 
	 * @return String of this Word
	 */
	public String getWord()
	{
		return this.word;
	}
	
//	/**
//	 * Get the global word count
//	 * @return count
//	 */
//	public int getCount()
//	{
//		return this.occurences;
//	}
	
	/**
	 * Get the ArrayList of associated urls
	 * 
	 * @return ArrayList of urls
	 */
	public List<Integer> getList()
	{
		return this.postings;
	}

	/**
	 * Compare two Word objects by checking their string representations.
	 * Override for the equals() method so that
	 * contains() will do proper comparison for ArrayList
	 *
	 * @param obj Word object to be compared
	 * @return True, if the two word strings are equal; false, otherwise.
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof Word)
		{
			String w = ((Word) obj).getWord();
			if(this.word.equals(w))
				return true;
		}

		if(obj instanceof String)
		{
			if(this.word.equals(obj))
				return true;
		}

		return false;
	}
}