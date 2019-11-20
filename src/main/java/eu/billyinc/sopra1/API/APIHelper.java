package eu.billyinc.sopra1.API;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class APIHelper {
	private String url;
	private String name;
	private String password;
	
	final private Client client = ClientBuilder.newClient();
	
	public APIHelper() {
		String rootPath = "/Users/quentin/Documents/eclipse-workspace/sopra1/";
		String appConfigPath = rootPath + "src/main/ressources/configuration.properties";
		
		Properties appProps = new Properties();
		
		try {
			appProps.load(new FileInputStream(appConfigPath));
			
			this.url = appProps.getProperty("rest.base.url");
			this.name = appProps.getProperty("team.name");
			this.password = appProps.getProperty("team.password");
		} catch (IOException e) {
			System.out.println("Impossible de récupérer le fichier de configuration !");
		}
	}
	
	public String call(String param) {
		return client.target(url + param).request().get(String.class);
	}
	
	public String ping() {
		return call("ping");
	}
	
	public String user() {
		return call("player/getIdEquipe/" + name + "/" + password);
	}
	
	public String versus() {
		return call("versus/next/" + user());
	}
	
	public String practice() {
		return call("practice/new/{numberBot}/{idEquipe}");
	}
	
}
