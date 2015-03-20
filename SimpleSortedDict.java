import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

public class SimpleSortedDict {

	private final static String separator = "="; // the String which separate
						     // <key> and the <value>
	
	private final static String pattern = "%s %s %s";   // the %s are in order:
							    // "key","separator","value" for each line

	private String pathIn;
	private String pathOut;

	public SimpleDictSort(String pathIn, String pathOut) {
		this.pathIn = pathIn;
		this.pathOut = pathOut;
	}

	/**
	 * Sort the key-value pairs in the pathIn File into the sorted key-value
	 * pairs file pathOut
	 * 
	 * @param overwrite
	 * @return
	 */
	public boolean execute(boolean overwrite) {
		return writeOut(treeMapFromFile(pathIn), pathOut, overwrite);
	}
	
	/**
	 * Sort the key-value pairs in the pathIn File into the sorted key-value
	 * pairs file pathOut
	 * 
	 * @return
	 */
	public boolean execute() {
		return writeOut(treeMapFromFile(pathIn), pathOut, false);
	}

	/**
	 * Write out the TreeMap to a file
	 * 
	 * @param map
	 * @param pathOut
	 *            path of the outPut file
	 */
	private static boolean writeOut(TreeMap<String, String> map, String path, boolean overwrite) {
		try {
			PrintWriter out = new PrintWriter(new FileWriter(path, overwrite));
			for (Map.Entry<String, String> entry : map.entrySet()) {
				out.println(String.format(pattern, entry.getKey(), separator, entry.getValue()));
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * returns a TreeMap<String, String> representing the sorted map of the
	 * dictionary file
	 * 
	 * @param pathIn
	 * @return
	 */
	private static TreeMap<String, String> treeMapFromFile(String path) {
		TreeMap<String, String> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line = br.readLine();
			while (line != null) {
				String[] keyValuePair = line.split(separator);
				map.put(keyValuePair[0].trim(), keyValuePair[1].trim());
				line = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

}
