package eu.billyinc.sopra1;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import eu.billyinc.sopra1.api.APIHelper;
import eu.billyinc.sopra1.board.Board;
import eu.billyinc.sopra1.board.EpicHero;
import eu.billyinc.sopra1.board.EpicHeroesLeague;
import eu.billyinc.sopra1.board.State;
import eu.billyinc.sopra1.enumeration.Format;

public class BattleMain {
	
	final static APIHelper apiHelper = new APIHelper();

	public static void main(String[] args) {	
		Options options = new Options();
		options.addOption("p", false, "ping");
		options.addOption("e", true, "practice");
		options.addOption("m", false, "practice");
		
		CommandLineParser parser = new DefaultParser();
		
		try {
			String teamID = apiHelper.user();
			
			CommandLine commande = parser.parse(options, args);
			if(commande.hasOption("p")) {
				System.out.println(apiHelper.ping());
			} else if(commande.hasOption("e")) {
				String gameID = apiHelper.practice(commande.getOptionValue("e"), teamID);
				BattleMain.play(gameID, teamID);
			} else if(commande.hasOption("m")) {
				String gameID = apiHelper.versus();
				if("NA".equals(gameID)) {
					System.out.println("Aucune partie de prévue");
				} else {
					BattleMain.play(gameID, teamID);
				}
			}
			
		} catch(ParseException e) {
			System.out.println("Impossible de parser les options !");
			e.printStackTrace();
		}
	}
	
	public static void play(String gameID, String teamID) {
		System.out.println("ID partie : " + gameID + "\n");
		
		String[] heroes = {"GUARD", "ORC", "PRIEST"};
		int numTour = 1;
		while ("CANPLAY".equals(apiHelper.status(gameID, teamID)) || "CANTPLAY".equals(apiHelper.status(gameID, teamID))) {				
			if("CANPLAY".equals(apiHelper.status(gameID, teamID))) {
				System.out.println(numTour);
				if(numTour <= 3) {
					apiHelper.play(gameID, teamID, heroes[numTour - 1]);
				} else {
					String move = move(apiHelper.boardTeam(gameID, teamID, Format.JSON));
					apiHelper.play(gameID, teamID, move);
				}
				System.out.println(summaryToString(apiHelper.boardTeam(gameID, teamID, Format.JSON)));
				numTour++;
			} else {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					System.out.println("Impossible de faire une pause !");
				}
			}
		}
		
		System.out.println(apiHelper.status(gameID, teamID));
		System.out.println(summaryToString(apiHelper.boardTeam(gameID, teamID, Format.JSON)));
	}
	
	public static String move(Board board) {
		EpicHeroesLeague enemiesTeam = board.getPlayerBoards().get(1);
		EpicHeroesLeague heroesTeam = board.getPlayerBoards().get(0);
		
		int enemieID = 0;
		if (enemiesTeam.hasOrc() instanceof EpicHero && !enemiesTeam.hasOrc().isDead()) {
			enemieID = enemiesTeam.hasOrc().getOrderNumberInTeam();
		} else if (enemiesTeam.hasGuard() instanceof EpicHero && !enemiesTeam.hasGuard().isDead()) {
			enemieID = enemiesTeam.hasGuard().getOrderNumberInTeam();
		} else {
			enemieID = enemiesTeam.hasPriest().getOrderNumberInTeam();
		}
		
		String move = "";
		for (EpicHero hero : heroesTeam.getFighters()) {
			move += hero.attack(enemieID) + "$";
		}
		
		return move.substring(0, move.length() - 1);
	}
	
	public static String summaryToString(Board board) {
		String summary = "";
		for (EpicHeroesLeague player : board.getPlayerBoards()) {
			summary += player.getPlayerName() + " :\n";
			
			if (player.getFighters() == null)
				return "Aucun combatant !";
			
			for (EpicHero hero : player.getFighters()) {
				if (!hero.isDead()) {
					summary += hero.getFighterClass() + " : " + hero.getCurrentLife() + "PV " + hero.getCurrentMana() + "PA\n";
			
				}
			}
			summary += "\n";
		}
		
		return summary;
	}
}
