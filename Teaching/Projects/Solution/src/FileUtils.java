import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
/**
 * FileUtils will contain the functionality needed to write
 * our table objects to files as well as read them back later
 * when we need them.
 *
 */
public class FileUtils {
	
	/**
	 * Write the wordTable to a file as an ObjectOutputStream
	 * @param wordTable word table that is being written
	 * @param filePath output file path
	 * @return true if successful, false if an exception occurs
	 */
	public boolean saveWordTable(List<Word> wordTable, String filePath)
	{
		if (wordTable == null || filePath == null)
			return false;
		try
		{
			FileOutputStream fos = new FileOutputStream(filePath);
		
			ObjectOutputStream oos = new ObjectOutputStream(fos);
		
			oos.writeObject(wordTable);
			
			oos.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	
	/**
	 * Write the page table to a file.
	 * @param pageTable table of UrlIDs mapped to Page objects
	 * @param filePath output file path
	 * @return true if successful, false if an exception occurs
	 */
	public boolean savePageTable(List<Page> pageTable, String filePath)
	{
		if (pageTable == null || filePath == null)
			return false;
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try
		{
			fos = new FileOutputStream(filePath);

			oos = new ObjectOutputStream(fos);

			oos.writeObject(pageTable);

		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			try
			{
				fos.close();
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return true;
	}
	
	/**
	 * Read back the word table from the hard coded filename.
	 * @param filePath serialized word list file
	 * @return the word list, if the filePath is valid; null, otherwise.
	 */
	public List<Word> getWordList(String filePath)
	{
		if (filePath == null)
			return null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try
		{
			fis = new FileInputStream(filePath);
			ois = new ObjectInputStream(fis);
			
			@SuppressWarnings("unchecked")
			List<Word> words = (ArrayList<Word>) ois.readObject();
			return words;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				ois.close();
				fis.close();


			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * Read back the page table from the hard coded file name.
	 * @param filePath serialized page list file.
	 * @return the page list, if the filePath is valid; null, otherwise.
	 */
	public List<Page> getPageList(String filePath)
	{
		if (filePath == null)
			return null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try
		{
			fis = new FileInputStream(filePath);
			ois = new ObjectInputStream(fis);
			
			List<Page> pages = (ArrayList<Page>) ois.readObject();

			return pages;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				ois.close();
				fis.close();


			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
