package eu.billyinc.sopra1.board;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EpicHeroesLeague {
	@JsonProperty("playerId")
	private String playerID;
	@JsonProperty("playerName")
	private String playerName;
	@JsonProperty("fighters")
	private List<EpicHero> fighters;
	
	public String getPlayerID() {
		return playerID;
	}

	public void setPlayerID(String playerID) {
		this.playerID = playerID;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public List<EpicHero> getFighters() {
		return fighters;
	}

	public void setFighters(List<EpicHero> fighters) {
		this.fighters = fighters;
	}
	
	public EpicHero hasOrc() {
		for (EpicHero fighter : fighters) {
			if ("ORC".equals(fighter.getFighterClass())) {
				return fighter;
			}
		}
		
		return null;
	}
	
	public EpicHero hasGuard() {
		for (EpicHero fighter : fighters) {
			if ("GUARD".equals(fighter.getFighterClass())) {
				return fighter;
			}
		}
		
		return null;
	}
	
	public EpicHero hasPriest() {
		for (EpicHero fighter : fighters) {
			if ("PRIEST".equals(fighter.getFighterClass())) {
				return fighter;
			}
		}
		
		return null;
	}
	
}
