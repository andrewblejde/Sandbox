import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Class for managing crawling pages, calling parse methods, and
 * storing the results.
 *
 * @author Andrew Blejde
 *
 */

public class Crawler {

    /**
     * Queue of pages that need to be parsed.
     * You will be constantly enqueuing pages that you have not seen that will then be visited later.
     * */
    public MyQueue toParse;

    /**
     * List of parsed pages.
     * When you have finished parsing a Page, you will add it to this List so that you can lookup Page objects from a URL ID when you are searching.
     * */
    public static List<Page> parsed;

    /**
     * (optional) This is a throw away List of visited URLs. You can use the default implementations
     * of ArrayList to check if it contains a String or not.
     */
    public static List<String> visited;

    /**
     * List of Word objects.
     * This is the postings list mentioned in the handout, where each
     * Word will be represented by a Word object that itself maintains its own ArrayList of
     * URL IDs that it has seen.
     * */
    public static List<Word> words;

    /**
     * Parser Object.
     * You will call methods from the Parser class using this object.
     * */
    public static Parser parser;

    /** Current URL ID that is next up to be parsed. */
    public static int currentID;

    /**
     * Total URLs enqueued.
     * The difference here is that they are waiting to be parsed, so currentID is still our bound
     * against limit. */
    public static int totalURLs;

    /**
     * Number of URLs to Parse. When you bump up against this limit, you should stop
     * adding items to your Queue.*/
    public static int limit;

    /** Domain to restrict our crawler to. Example: a domain of cs.purdue.edu
     * will prevent our Crawler from going to ece.purdue.edu, because the
     * cs.purdue.edu substring does not exist in ece.purdue.edu
     * */
    public static String domain;


    /**
     * Constructor for Crawler where a seed URL and
     * page count limit are provided.
     * The pages should be crawled in this constructor
     * and the result should be stored in Crawler.words and Crawler.parsed.
     * @param seed starting URL for the crawler.
     * @param domain root domain that the crawler is restricted to.
     * @param limit total number of URLs to crawl.
     */
    public Crawler(String seed, String domain, int limit)
    {
		/* Initialize Queue as LinkedList */
        if(toParse == null)
            toParse = new MyQueue();

		/* Initialize Map of parsed pages */
        parsed = new ArrayList<Page>();

		/* Initialize List of Word objects */
        words = new ArrayList<Word>();

        /* Initialize visited */
        visited = Collections.synchronizedList(new ArrayList<String>());

		/* Initialize parse object */
        parser = new Parser();

		/* Set member fields */
        this.limit = limit;
        this.domain = domain;
        this.currentID = 0;
        this.totalURLs = 0;

		/* Add the seed URL */
        toParse.add(seed);

    }

    /**
     * Method to initiate and run the crawling process.
     */
    public void crawl()
    {
        /* Continue parsing process while
         * there are URLs enqueued.
         */
        while(!toParse.isEmpty() && currentID < limit)
        {
            System.out.println(( currentID + 1 ) + " / " + limit + ", toParse size: " + toParse.size());

            // Get next Page to parse
            Page page = new Page((String)toParse.remove().getData(), currentID);

            if(page == null || visited.contains(page.getURL())) {
                continue;
            }

            /* Get a url to parse */
            String url = page.getURL();

            System.out.println("ID: " + page.getURLID() + ", Parsing: " + url);

            // Check for null URL
            if(url == null)
                continue;

            Document doc = null;
            try
            {
                /* Get the Document */
                doc = parser.getDocument(url);

                /* Parse it */
                parse(doc, currentID);

                /* Update values */
                currentID++;

                /* Add to lists */
                parsed.add(page);
                visited.add(page.getURL());

            } catch (ParseException e)
            {
                //e.printStackTrace();
			    /* Continue execution if it fails. */
                continue;
            }

        }
    }
    /**
     * Parse driver to manage parsing for links and text.
     * @param doc Document object for URl
     * @param id currentID that is being parsed
     * @return true if parsing is successful, false otherwise
     */
    public boolean parse(Document doc, int id)
    {
        /* Parse links for Document only if we haven't
    	 * enqueued up to our limit. */
        parseLinks(doc);

    	/* Parse text for Document */
        parseText(doc, id);

        return true;

    }
    /**
     * Parse a Document object for available links
     * @param doc page to parse
     */
    public void parseLinks(Document doc)
    {
        // Get links from page
        Elements links = null;
        try {
            links = parser.getLinks(doc);
        } catch (ParseException e) {
            return;
        }

        for(Element link : links)
        {
            String candidate = link.attr("abs:href");

            if(isInDomain(candidate) && isValidURL(candidate))
            {
                //System.out.println("Adding link: " + candidate);
                addToQueue(candidate);
            }
        }
    }
    /**
     * Parse a Document for the body of text
     * @param doc page to parse
     * @param id urlID of the current page that is being parsed
     */
    public void parseText(Document doc, int id)
    {
        String body = null;
        try
        {
            body = parser.getBody(doc);
        }
        catch(Exception e)
        {
            e.printStackTrace();

            return;
        }

        if(body == null)
            return;

        String[] arr = body.split(" ");

        for(String w : arr)
            addWordToList(w.replaceAll(" ", ""), id);
    }
    /**
     * Add a Word to the word postings list or increment if necessary.
     *
     * @param word candidate word to be added
     * @param id UrlID under consideration
     */
    public void addWordToList(String word, int id)
    {
        word = word.toLowerCase();
        if(!Crawler.words.contains(new Word(word, -1)))
        {
            /* New word, add it to the List */
            Word w = new Word(word, id);
            Crawler.words.add(w);

        }
        else
        {
            /* Get previous count and add current url ID */
            Word w = Crawler.words.remove(words.indexOf(new Word(word, -1)));
            w.addURLID(id);

            /* Add updated Word */
            Crawler.words.add(w);

        }
    }

    /**
     * Add a url to be parsed. Should avoid duplicated URLs.
     *
     * @param url String url to be added
     */
    public void addToQueue(String url)
    {
        /* Check for duplicate add attempt */

        if(!Crawler.visited.contains(url))
        {
	    	/* Add to Queue */
            toParse.add(url);
            totalURLs++;
        }
    }

    /**
     * Add a Page to the parsed pages List
     *
     * @param p Page to be added
     */
    public void addPageToList(Page p)
    {
        /* Put Page into visited List */
        System.out.println("Add ID: " + p.getURLID() + " to map");

        Page page = parsed.get(p.getURLID());
        if(page == null)
            Crawler.parsed.add(p);
    }

    /**
     * Check that the candidate URL is in the specified
     * domain.
     * To simplify this, you can just check if the url contains the domain as a substring.
     * @param url candidate URL to check
     * @return true if in domain, false otherwise
     */
    public boolean isInDomain(String url)
    {
        return url.contains(domain);
    }

    /**
     * Check that the URL begins with http:// or https://
     * @param url candidate URL to check
     * @return true if valid, false otherwise
     */
    public boolean isValidURL(String url)
    {
        return url.substring(0,7).equals("http://") ||
                url.substring(0,8).equals("https://");
    }
}
