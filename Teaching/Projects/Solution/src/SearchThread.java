import java.util.List;

class SearchThread implements Runnable
{
    /** Starting index for this thread */
    public int start;

    /** Finishing index for this thread */
    public int finish;

    /** Terms that this thread needs to evaluate */
    public String[] terms;

    /**
     * Constructor for this Thread. We will need to set the starting and finishing
     * bounds as well as store the array of terms.
     * @param start starting index for this thread
     * @param finish finishing index for this thread
     * @param terms Array of terms to evaluate
     */
    public SearchThread(int start, int finish, String[] terms)
    {
        this.start = start;
        this.finish = finish;
        this.terms = terms;
    }

    public void run()
    {
        for(String term : terms) {
			/* Find Word object */
            Word w = findTerm(term);
            if (w == null)
                continue;

            List<Integer> postings = w.getList();

            for (Integer id : postings) {

                if (Search.resultSet.contains(new Result("", id.intValue()))) {
                    Search.resultSet.get(Search.resultSet.indexOf(new Result("", id.intValue()))).updateScore(1);
                } else {
                    Search.resultSet.add(new Result(Search.pageList.get(id.intValue()).getURL(), id));

                }

            }
        }

    }

    /**
     * Find and return the associated Word object for a given term
     * @param term String term to look up in the word table
     * @return Word - the Word object for a given term
     */
    public Word findTerm(String term)
    {
		/* Check only this threads allocated words */
        for(int i = start; i < finish; i++)
        {
			/* Find index of the appropriate Word object */
            if(Search.wordList.get(i).getWord().toLowerCase().equals(term))
            {
                return Search.wordList.get(i);
            }
        }

        return null;
    }
}