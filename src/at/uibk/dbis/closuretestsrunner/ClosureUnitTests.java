package at.uibk.dbis.closuretestsrunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.thoughtworks.selenium.SeleneseTestBase;

/**
 * Parametized TestCase which runs closure unit tests with selenium
 * UnitTests are taken from JsTestParser 
 * 
 * @author Christian Esswein
 */
@RunWith(Parameterized.class)
public class ClosureUnitTests extends SeleneseTestBase {
	public static String URL_PREFIX;
	
	private String testCasePath;

	public ClosureUnitTests(String testCasePath) {
		this.testCasePath = testCasePath;
	}

	@Override
	@Before
	public void setUp() throws Exception {
		final String url = URL_PREFIX;
		// This initializes the protected "selenium" field with the base URL for the
		// tests and the browser to use when running the tests.
		setUp(url, "*firefox");
	}

	@Test
	public void testGoogString() throws Exception {
		assertTrue("URL_PREFIX to testserver is not set", URL_PREFIX != null);
		
		selenium.open(URL_PREFIX + testCasePath);

		// Because the test runs automatically when the HTML file is loaded,
		// poll for up to 5 seconds to see whether the test is complete.
		selenium.waitForCondition(
				"window.G_testRunner && window.G_testRunner.isFinished()",
				"5000");

		// Invoke this snippet of JavaScript in the browser to query whether the
		// test succeeded or failed.
		String success = selenium.getEval("window.G_testRunner.isSuccess()");
		boolean isSuccess = "true".equals(success);
		if (!isSuccess) {
			// If the test failed, report the reason why.
			String report = selenium.getEval("window.G_testRunner.getReport()");
			fail(report);
		}
	}

	@Parameters
	public static Collection<Object[]> data() throws IOException {
		List<Object[]> params = new ArrayList<Object[]>();
		
		List<String> fileNames;
		fileNames = JsTestParser.getInstance().getTestFiles();
		
		for(String fileName : fileNames){
			Object[] arr = new Object[] { fileName };
			params.add(arr);
		}

		return params;
	}

}
