import java.io.Serializable;

/**
 * Page object to represent a web page. 
 * @author Andrew Blejde
 *
 */
public class Page implements Serializable, Comparable<Page>
{
	//Serial Version ID. DO NOT EDIT
	public static final long serialVersionUID = -1827677255104766839L;
	/** URL as a String for this Page */
	public String url;

	/** ID for this specific Page */
	private int urlID;
	
	/**
	 * Construct a new page object.
	 * @param url URL of the Page
	 * @param urlID assigned ID for this page
	 */
	public Page(String url, int urlID)
	{
		this.url = url;
		this.urlID = urlID;
	}
	
	
	/**
	 * Get the urlID of this URL
	 * 
	 * @return int of url
	 */
	public int getURLID()
	{
		return this.urlID;
	}
	
	/**
	 * Get the url String for this URL object
	 * 
	 * @return String of url
	 */
	public String getURL()
	{
		return this.url;
	}
	/**
	 * Override for the equals() method so that
	 * contains() will do proper comparison for ArrayList
	 * 
	 * @param obj Page object to be compared
	 * @return True, if the two URLs are equal or the two URLIDs are the same; false, otherwise.
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof Page)
		{
			String page = ((Page) obj).url;

			//System.out.println("\n\nIn contains: " + page + " vs " + url + "\n\n");
			if(url.equals(page))
				return true;

			int id = ((Page) obj).getURLID();
			if(id == this.urlID)
				return true;
		}

		return false;
	}

	/**
	 * Since we implement the Comparable interface, we must provide
	 * a method that describes how two Page objects should be compared.
	 * We will be sorting our Page ArrayList by URLID, so we need to
	 * sort our List in ascending order (lowest ID's first).
	 * @param candidate Page that 'this' Page is being compared to
	 * @return -1 if this page has a lower ID, 0 if the same, 1 if this ID is higher.
	 */
	public int compareTo(Page candidate) {
		if(this.getURLID() == candidate.getURLID())
			return 0;
		return this.getURLID() < candidate.getURLID() ? -1 : 1;
	}
}
