import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Fetch web page from URL, extract and return content.
 *
 */

public class Parser {

    /**
     * Get the web page, create a Document object and return it
     * You should use JSoup to do that and customize all the exceptions by throwing ParseException
     * @param url - webpage to capture as a Document object
     * @return the web page as a Document object
     * @throws ParseException - Simple custom exception with a message.
     */
    public Document getDocument(String url) throws ParseException
    {
        /* Make sure String url is not null */
        if(url == null)
            throw new ParseException("getDocument() failed. String url is null.");

        if(url.equals(""))
            throw new ParseException("getDocument() failed. String url is empty.");
        /* Prepare a Jsoup Document object to hold the page */
        Document d = null;

        /* Attempt a connection, throws IOException so be
         * prepared to handle that.
         */
        try
        {
            /* Connect to URL and get the web page */
            d = Jsoup.connect(url).timeout(3000).get();

        }
        catch(Exception e)
        {
        	/* Print brief message and continue */
            throw new ParseException("getDocument() failed. Connection failed.");
        }

        if(d == null)
            throw new ParseException("getDocument() failed: Document is null.");

        /* Return the Document */
        return d;
    }

    /**
     * Method to extract and enumerate all links on a given web page
     * @param doc - Document object to parse
     * @return an Elements object which contains all links
     * @throws ParseException - thrown when document is null
     */
    public Elements getLinks(Document doc) throws ParseException
    {
        /* Null check */
        if(doc == null)
        {
            throw new ParseException("getLinks() failed. Document parameter is null.");
        }

        /* Select and store all links from the Document */
        Elements links = doc.select("a[href]");

        /* Return the links */
        return links;
    }


    /**
     * Method to get and return the contents of a web pages body. The
     * returned String should be in lowercase.
     * @param doc - Document object to parse
     * @return the body of the given web page as a String object
     * @throws ParseException - thrown when document is null
     */
    public String getBody(Document doc) throws ParseException {

        /* Null check */
        if (doc == null) {
            throw new ParseException("getBody() failed. Document parameter is null.");
        }

        /* Get the body of the Document as an Element */
        Element body = doc.body();

        /* Get and return the body content of the web page */
        if (body == null)
            return null;
        return body.text();

    }
}