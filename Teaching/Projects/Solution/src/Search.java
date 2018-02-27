import java.util.*;
/**
 * Search class will prepare the necessary data structures
 * for searching and then execute provided queries.
 *
 */
public class Search {
	
	/** Parsed pages. We will read in the list we made in the
	 * Crawler class and save it to this global static variable. */
	public static List<Page> pageList;
	
	/** List of Word objects. We will read in the list we made in the
	 * Crawler class and save it to this global static variable. */
	public static List<Word> wordList;

	/** List of Results. This will be shared by the threads. This list
	 * must be designed to be resilient to double counting by competing
	 * threads. Take a look at the Synchronizing Lists section of the
	 * handout for information on how to handle this. */
	public static List<Result> resultSet;

	/** Word List File Path */
	private String wordListFile;

	/** Page List File Path */
	private String pageListFile;

	/**
	 * Constructor for Search should load word list and page list from object files specified.
	 * @param wordListFile path to the serialized word list file
	 * @param pageListFile path to the serialized page list file
	 */
	public Search(String wordListFile, String pageListFile)
	{
		this.wordListFile = wordListFile;
		this.pageListFile = pageListFile;
		setup(wordListFile, pageListFile);
	}
	
	/**
	 * Read in word and page table Objects from files.
	 * @param wordListFile path to the serialized word list file
	 * @param pageListFile path to the serialized page list file
	 */
	public void setup(String wordListFile, String pageListFile)
	{
		/* Create FileUtils instance */
		FileUtils f = new FileUtils();
		
		Search.wordList = f.getWordList(wordListFile);
		Search.pageList = f.getPageList(pageListFile);

		Collections.sort(Search.pageList);
	}
	
	/**
	 * Execute a query over the built tables and return an
	 * ArrayList of Result objects.
	 * @param query query to execute
	 * @return ArrayList of results.
	 */
	public List<Result> executeQuery(String query)
	{
		/* Check to make sure tables aren't null */
		nullCheck();

		/* Split query into terms */
		String[] terms = query.toLowerCase().split(" ");

		/* Create ArrayList of Results */
		resultSet = Collections.synchronizedList(new ArrayList<Result>());

		/* Array of Threads */
		Thread[] threads = new Thread[4];
		for(int i = 0; i < threads.length; i++)
		{
			int start = i * (wordList.size() / threads.length);
			int finish = (i + 1) * (wordList.size() / threads.length);

			threads[i] = new Thread(new SearchThread(start, finish, terms));
		}

		for(int i =0 ; i < threads.length; i++)
			threads[i].start();

		try
		{
			for(int i =0 ; i < threads.length; i++)
				threads[i].join();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}

		/* call sort method */
		sort();

		return resultSet;
	}

	/**
	 * Utility method to check if we have not read in our
	 * lists from file. If we haven't, call the setup() method.
	 */
	public void nullCheck()
	{
		if(wordList == null || pageList == null)
			setup(wordListFile, pageListFile);
	}

	/**
	 * Method to sort our ArrayList of Results by
	 * their score. Since the Result List is global,
	 * we don't need to pass it as a parameter or return it -
	 * we can simply access it within the method.
	 */
	public void sort()
	{
		Collections.sort(Search.resultSet);
	}
}
