package eu.billyinc.sopra1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import eu.billyinc.sopra1.API.APIHelper;

public class BattleMain {

	public static void main(String[] args) {
		/*Options options = new Options();
		options.addOption("p", false, "print pong");
		options.addOption("config", false, "print config");
		
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine cmd = parser.parse(options, args);
			
			if (cmd.hasOption("p")) {
				System.out.println("pong");
			} else if (cmd.hasOption("config")) {
				printConfig();
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		APIHelper apiHelper = new APIHelper();
		System.out.println(apiHelper.ping());
		System.out.println(apiHelper.user());
		System.out.println(apiHelper.versus());
	}

	
	static public void printConfig() {
		String rootPath = "/Users/quentin/Documents/eclipse-workspace/sopra1/";
		String appConfigPath = rootPath + "src/main/ressources/configuration.properties";
		
		Properties appProps = new Properties();
		
		try {
			appProps.load(new FileInputStream(appConfigPath));
			System.out.println(appProps.getProperty("rest.base.url"));
			System.out.println(appProps.getProperty("team.name"));
			System.out.println(appProps.getProperty("team.password"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
