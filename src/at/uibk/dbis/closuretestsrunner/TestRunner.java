package at.uibk.dbis.closuretestsrunner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.commons.cli.*;
import org.junit.runner.JUnitCore;

import barrypitman.junitXmlFormatter.AntXmlRunListener;

/**
 * 
 * @author Christian Esswein
 */
public class TestRunner {
	public static void main(String[] args) throws Exception {
		Options options = new Options();

		options.addOption("testsfile", true, "path to alltests.js");
		options.addOption("outputfile", true, "path where outputfile should be saved");
		options.addOption("testserver", true, "url to testserver"); // = "http://localhost:9080/www/"

		CommandLine cmd = new PosixParser().parse(options, args);
		
		if(cmd.hasOption("testsfile") && cmd.hasOption("outputfile") && cmd.hasOption("testserver")){
			startTests(cmd.getOptionValue("testsfile"), cmd.getOptionValue("outputfile"), cmd.getOptionValue("testserver"));
		}else{
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("ClosureTester", options );
		}
	}
	
	/**
	 * starts tests found in testsFilePath and writes result into file
	 * 
	 * @param testsFilePath path to file containing array of testfiles
	 * @param resultFileName filename for resultfile
	 * @throws FileNotFoundException
	 */
	private static void startTests(String testsFilePath, String resultFileName, String serverUrl) throws FileNotFoundException{
		JsTestParser.getInstance().setTestFileName(testsFilePath);

		// xml formatter from https://github.com/barrypitman/JUnitXmlFormatter
		AntXmlRunListener runListener = new AntXmlRunListener();
		runListener.setOutputStream(new FileOutputStream(new File(resultFileName)));

		JUnitCore jCore = new JUnitCore();
		jCore.addListener(runListener);
		ClosureUnitTests.URL_PREFIX = serverUrl;
		jCore.run(ClosureUnitTests.class);
	}

}
