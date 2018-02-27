import java.io.Serializable;

/**
 * Result class will represent the a web page and it's score.
 *
 */
public class Result implements Serializable, Comparable<Result>{

	//Serial Version UID. DO NOT EDIT
	public static final long serialVersionUID = -938761094876384658L;

	/** URL of this Result */
	public String url;

	/** The score for this Result */
	public int score;

	/** ID for this URL */
	public int urlID;
	
	//public Result(){}

	/**
	 * Constructor for a Result. The default score is set to 1
	 * and changed as more and more occurences happen.
	 * @param url String representation of the Result
	 * @param urlID ID of the Page
	 */
	public Result(String url, int urlID)
	{
		this.url = url;
		this.urlID = urlID;
		this.score = 1;
	}

	/**
	 * Update the score for this reference by a given amount.
	 * @param score score to add to the existing score
	 */
	public void updateScore(int score)
	{
		this.score += score;
	}

	/**
	 * Update the score by just one.
	 */
	public void incrementScore()
	{
		this.score++;
	}

	/**
	 * Get the score of this Result
	 * @return int score of the total score
	 */
	public int getScore()
	{
		return this.score;
	}

	/**
	 * Get the URL of this Result as a String
	 * @return String result
	 */
	public String getURL()
	{
		return this.url;
	}

	/**
	 * Get the URL ID for this Result. These are the same
	 * associations you made in the Page object.
	 * @return int of the URL ID
	 */
	public int getURLID()
	{
		return this.urlID;
	}

	/**
	 * Override for the equals() method so that
	 * contains() will do proper comparison for ArrayList
	 * 
	 * @param obj Result object to be compared
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof Result)
		{
			String res = ((Result) obj).getURL();
			if(url.equals(res))
				return true;

			int id = ((Result) obj).getURLID();
			if(this.getURLID() == id)
				return true;
		}

		return false;
	}

	/**
	 * When we have finished building our Results list, we need to
	 * sort the list by each Results score so that we can return
	 * the ranked list. Our list should be sorted in descending order
	 * (highest scores first).
	 * @param candidate Result being compared to 'this' Result
	 * @return -1 if this Result has a higher score, 0 if the same, 1 if score is lower.
	 */
	@Override
	public int compareTo(Result candidate) {
		if(this.getScore() == candidate.getScore())
			return 0;
		return this.getScore() > candidate.getScore() ? -1 : 1;
	}
}
