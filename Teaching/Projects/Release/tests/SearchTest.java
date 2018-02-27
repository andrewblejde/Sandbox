import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertTrue;

/**
 * Created by Aravind on 3/13/17.
 */
public class SearchTest {
    Search sr;
    private static final String testWordTable = "searchTestWordTable.txt";
    private static final String testPageTable = "searchTestPageTable.txt";
    private boolean correctEvaluation;
    private FileInputStream fis;
    private ObjectInputStream ois;
    private ArrayList<Result> res1;
    private ArrayList<Result> res2;
    private ArrayList<Result> res3;
    private ArrayList<Result> res4;


    @Before
    public void setUp() throws Exception {
        correctEvaluation = false;

        //Initialize Search object
        sr = new Search(testWordTable, testPageTable);

        //Populate result lists
        try
        {
            fis = new FileInputStream("searchTestResult1.txt");
            ois = new ObjectInputStream(fis);

            res1 = (ArrayList<Result>) ois.readObject();
            Collections.sort(res1);
            fis.close();
            ois.close();

            fis = new FileInputStream("searchTestResult2.txt");
            ois = new ObjectInputStream(fis);

            res2 = (ArrayList<Result>) ois.readObject();
            Collections.sort(res2);
            fis.close();
            ois.close();

            fis = new FileInputStream("searchTestResult3.txt");
            ois = new ObjectInputStream(fis);

            res3 = (ArrayList<Result>) ois.readObject();
            Collections.sort(res3);
            fis.close();
            ois.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        res4 = new ArrayList<>();
    }

    @Test(timeout = 1000)
    public void testSingleTermQuery() {
        ArrayList<Result> testList;

        //Single term query
        testList = sr.executeQuery("Purdue");
        boolean comparisonResult = resListCompareSorted(testList, res1);
        assertTrue("Failed to correctly execute query \"Purdue\"", correctEvaluation);
        assertTrue("Failed to correctly sort results of query \"Purdue\"", comparisonResult);
    }

    @Test(timeout = 1000)
    public void testMultiTermQuery() {
        ArrayList<Result> testList;

        //Multiple term query
        testList = sr.executeQuery("West Lafayette");
        boolean comparisonResult = resListCompareSorted(testList, res2);
        assertTrue("Failed to correctly execute query \"West Lafayette\"", correctEvaluation);
        assertTrue("Failed to correctly sort results of query \"West Lafayette\"", comparisonResult);
    }

    @Test(timeout = 1000)
    public void testNumericalTermQuery() {
        ArrayList<Result> testList;

        //Numerical term query
        testList = sr.executeQuery("365");
        boolean comparisonResult = resListCompareSorted(testList, res3);
        assertTrue("Failed to correctly execute query \"365\"", correctEvaluation);
        assertTrue("Failed to correctly sort results of query \"365\"", comparisonResult);
    }

    @Test(timeout = 1000)
    public void testNonExistentTermQuery() {
        ArrayList<Result> testList;

        //Non-existent term query
        testList = sr.executeQuery("FakeWord");
        boolean comparisonResult = resListCompareSorted(testList, res4);
        assertTrue("Failed to correctly execute query \"FakeWord\"", correctEvaluation);
        assertTrue("Failed to correctly sort results of query \"FakeWord\"", comparisonResult);
    }

    private boolean resListCompareSorted(ArrayList<Result> resList1, ArrayList<Result> resList2)
    {
        //null checking
        if(resList1==null && resList2==null) {
            correctEvaluation = true;
            return true;
        }
        if((resList1 == null && resList2 != null) || (resList1 != null && resList2 == null)) {
            correctEvaluation = false;
            return false;
        }

        if(resList1.size()!=resList2.size()) {
            correctEvaluation = false;
            return false;
        }

        for(Result itemList1: resList1)
        {
            if(!resList2.contains(itemList1)) {
                correctEvaluation = false;
                return false;
            }
        }

        correctEvaluation = true;

        for (int i = 0; i < resList1.size(); i++) {
            if (!resList1.get(i).equals(resList2.get(i)))
                return false;
        }

        return true;
    }
}