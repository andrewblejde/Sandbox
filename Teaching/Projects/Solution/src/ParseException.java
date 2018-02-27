/**
 * The exception class for Parser.
 */

class ParseException extends Exception
{
    /**
     * Constructor for our ParseException to pass the
     * message to the super constructor (Exception)
     * @param msg Message to be displayed.
     */
    public ParseException(String msg)
    {
        super(msg);
    }
}