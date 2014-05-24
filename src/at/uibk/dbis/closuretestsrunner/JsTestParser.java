package at.uibk.dbis.closuretestsrunner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * extracts paths to UnitTest-files from array of strings 
 * see alltests.js from closure library
 * 
 * @author Christian Esswein
 */
public class JsTestParser {

	private String fileName;

	public void setTestFileName(String fileName) {
		this.fileName = fileName;
	}

	private static class SingletonHolder {
		static JsTestParser INSTANCE = new JsTestParser();
	}

	public static JsTestParser getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public List<String> getTestFiles() throws IOException {
		List<String> files = new LinkedList<String>();
		Pattern pattern = Pattern.compile("\"(.*?)\"");
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(this.fileName));

			String currentLine = "";
			while ((currentLine = reader.readLine()) != null) {

				Matcher matcher = pattern.matcher(currentLine);
				if (matcher.find()) {
					files.add(matcher.group(1));
				}

			}
		} finally {
			if(reader != null)
				reader.close();
		}

		return files;
	}
}
