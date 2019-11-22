package eu.billyinc.sopra1.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import eu.billyinc.sopra1.board.Board;
import eu.billyinc.sopra1.enumeration.Format;

public class APIHelper {
	private String url;
	private String name;
	private String password;
	
	public String teamID;
	
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
	
	private String call(String param) {
		return client.target(url + param).request().get(String.class);
	}
	
	private Board callBoard(String param) {
		return client.target(url + param).request().get(Board.class);
	}
	
	public String ping() {
		return call("ping");
	}
	
	public String user() {
		this.teamID = call("player/getIdEquipe/" + name + "/" + password);
		return this.teamID;
	}
	
	public String versus() {
		return call("versus/next/" + user());
	}
	
	public String practice(String numberBot, String teamID) {
		return call("practice/new/" + numberBot + "/" + teamID);
	}
	
	public String status(String gameID, String teamID) {
		return call("game/status/" + gameID + "/" + teamID);
	}
	
	public String boardString(String gameID, Format format) {
		return call("game/board/" + gameID + "?format=" + format);
	}
	
	public Board board(String gameID, Format format) {
		return callBoard("game/board/" + gameID + "?format=" + format);
	}
	
	public String boardTeamString(String gameID, String teamID, Format format) {
		return call("game/board/" + gameID + "/" + teamID + "?format=" + format);
	}
	
	public Board boardTeam(String gameID, String teamID, Format format) {
		return callBoard("game/board/" + gameID + "/" + teamID + "?format=" + format);
	}
	
	public String lastMove(String gameID, String teamID) {
		return call("game/getlastmove/" + gameID + "/" + teamID);
	}
	
	public String play(String gameID, String teamID, String move) {
		return call("game/play/" + gameID +"/" + teamID + "/" + move);
	}
	
	public String opponent(String gameID, String teamID) {
		return call("/epic-ws/epic/game/opponent/" + gameID + "/" + teamID);
	}
}
