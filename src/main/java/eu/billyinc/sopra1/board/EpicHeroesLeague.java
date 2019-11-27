package eu.billyinc.sopra1.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	
	public List<EpicHero> getFightersAlive() {
		List<EpicHero> fightersAlive = new ArrayList<EpicHero>();
		
		for (EpicHero fighter : this.getFighters()) {
			if (fighter.getCurrentLife() > 0) {
				fightersAlive.add(fighter);
			}
		}
		
		return fightersAlive;
	}

	public void setFighters(List<EpicHero> fighters) {
		this.fighters = fighters;
	}
	
	public EpicHero hasOrc() {
		for (EpicHero fighter : fighters) {
			if ("ORC".equals(fighter.getFighterClass()) && !fighter.isDead()) {
				return fighter;
			}
		}
		
		return null;
	}
	
	public EpicHero hasGuard() {
		for (EpicHero fighter : fighters) {
			if ("GUARD".equals(fighter.getFighterClass()) && !fighter.isDead()) {
				return fighter;
			}
		}
		
		return null;
	}
	
	public EpicHero hasPriest() {
		for (EpicHero fighter : fighters) {
			if ("PRIEST".equals(fighter.getFighterClass()) && !fighter.isDead()) {
				return fighter;
			}
		}
		
		return null;
	}
	
	public static EpicHero hasOrc(List<EpicHero> fighters) {
		for (EpicHero fighter : fighters) {
			if ("ORC".equals(fighter.getFighterClass()) && !fighter.isDead()) {
				return fighter;
			}
		}
		
		return null;
	}
	
	public static EpicHero hasGuard(List<EpicHero> fighters) {
		for (EpicHero fighter : fighters) {
			if ("GUARD".equals(fighter.getFighterClass()) && !fighter.isDead()) {
				return fighter;
			}
		}
		
		return null;
	}
	
	public static EpicHero hasPriest(List<EpicHero> fighters) {
		for (EpicHero fighter : fighters) {
			if ("PRIEST".equals(fighter.getFighterClass()) && !fighter.isDead()) {
				return fighter;
			}
		}
		
		return null;
	}
	
	public static EpicHero hasChaman(List<EpicHero> fighters) {
		for (EpicHero fighter : fighters) {
			if ("CHAMAN".equals(fighter.getFighterClass()) && !fighter.isDead()) {
				return fighter;
			}
		}
		
		return null;
	}
	
	public static EpicHero hasPaladin(List<EpicHero> fighters) {
		for (EpicHero fighter : fighters) {
			if ("PALADIN".equals(fighter.getFighterClass()) && !fighter.isDead()) {
				return fighter;
			}
		}
		
		return null;
	}
	
	public EpicHero hasPaladin() {
		for (EpicHero fighter : fighters) {
			if ("PALADIN".equals(fighter.getFighterClass()) && !fighter.isDead()) {
				return fighter;
			}
		}
		
		return null;
	}
	
	public static EpicHero hasArcher(List<EpicHero> fighters) {
		for (EpicHero fighter : fighters) {
			if ("ARCHER".equals(fighter.getFighterClass()) && !fighter.isDead()) {
				return fighter;
			}
		}
		
		return null;
	}
	
	public EpicHero hasArcher() {
		for (EpicHero fighter : fighters) {
			if ("ARCHER".equals(fighter.getFighterClass()) && !fighter.isDead()) {
				return fighter;
			}
		}
		
		return null;
	}

	
	public List<EpicHero> heroesScared() {
		List<EpicHero> scaredFighters = new ArrayList<EpicHero>();
		
		for (EpicHero fighter : fighters) {
			if (fighter.getStates() != null) {
				for (State state : fighter.getStates()) {
					if ("SCARED".equals(state.getType())) {
						scaredFighters.add(fighter);
					}
				}
			}
		}
		
		return scaredFighters;
	}
	
	public List<EpicHero> heroesBurning() {
		List<EpicHero> scaredFighters = new ArrayList<EpicHero>();
		
		for (EpicHero fighter : fighters) {
			if (fighter.getStates() != null) {
				for (State state : fighter.getStates()) {
					if ("BURNING".equals(state.getType())) {
						scaredFighters.add(fighter);
					}
				}
			}
		}
		
		return scaredFighters;
	}
	
	public List<EpicHero> heroesStunned() {
		List<EpicHero> scaredFighters = new ArrayList<EpicHero>();
		
		for (EpicHero fighter : fighters) {
			if (fighter.getStates() != null) {
				for (State state : fighter.getStates()) {
					if ("STUNNED".equals(state.getType())) {
						scaredFighters.add(fighter);
					}
				}
			}
		}
		
		return scaredFighters;
	}
	
	public List<EpicHero> heroesNotStunned() {
		List<EpicHero> notStunnedFighters = new ArrayList<EpicHero>();
		
		for (EpicHero fighter : fighters) {
			if (!fighter.isStunned()) {
				notStunnedFighters.add(fighter);
			}
		}
		
		return notStunnedFighters;
	}
	
	public List<EpicHero> heroesNeedHeal() {
		List<EpicHero> needHealFighters = new ArrayList<EpicHero>();
		
		for (EpicHero fighter : fighters) {
			if (fighter.getCurrentLife() < (fighter.getMaxAvailableLife() - 7) && !fighter.isDead()) {
				needHealFighters.add(fighter);
			}
		}

		Collections.sort(needHealFighters, new Comparator<EpicHero>() {
			@Override
			public int compare(EpicHero o1, EpicHero o2) {
				return o1.getCurrentLife() - o2.getCurrentLife();
			}
			
		});
		
		return needHealFighters;
	}
	
	public List<EpicHero> fightersWillBurn() {
		List<EpicHero> fightersWillBurn = new ArrayList<EpicHero>();
		
		for (EpicHero fighter : fighters) {
			if (!fighter.isBurning()) {
				fightersWillBurn.add(fighter);
			}
		}
		
		return fightersWillBurn;
	}
	
	public boolean isFighterDead(int i) {
		for (EpicHero fighter : fighters) {
			if (fighter.getOrderNumberInTeam() == i) {
				return fighter.isDead();
			}
		}
		
		return true;
	}
	
}
