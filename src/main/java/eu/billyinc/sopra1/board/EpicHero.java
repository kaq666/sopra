package eu.billyinc.sopra1.board;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.billyinc.sopra1.enumeration.Attack;

@JsonIgnoreProperties(value = {"action", "receivedAttacks", "diffMana", "diffLife"})
public class EpicHero {
	@JsonProperty("fighterClass")
	private String fighterClass;
	@JsonProperty("orderNumberInTeam")
	private int orderNumberInTeam;
	@JsonProperty("isDead")
	private boolean isDead;
	@JsonProperty("maxAvailableMana")
	private int maxAvailableMana;
	@JsonProperty("maxAvailableLife")
	private int maxAvailableLife;
	@JsonProperty("currentMana")
	private int currentMana;
	@JsonProperty("currentLife")
	private int currentLife;
	@JsonProperty("states")
	private List<State> states = new ArrayList<State>();
	@JsonProperty("fighterID")
	private String fighterID;

	public String getFighterClass() {
		return fighterClass;
	}

	public void setFighterClass(String fighterClass) {
		this.fighterClass = fighterClass;
	}

	public int getOrderNumberInTeam() {
		return orderNumberInTeam;
	}

	public void setOrderNumberInTeam(int orderNumberInTeam) {
		this.orderNumberInTeam = orderNumberInTeam;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	public int getMaxAvailableMana() {
		return maxAvailableMana;
	}

	public void setMaxAvailableMana(int maxAvailableMana) {
		this.maxAvailableMana = maxAvailableMana;
	}

	public int getMaxAvailableLife() {
		return maxAvailableLife;
	}

	public void setMaxAvailableLife(int maxAvailableLife) {
		this.maxAvailableLife = maxAvailableLife;
	}

	public int getCurrentMana() {
		return currentMana;
	}

	public void setCurrentMana(int currentMana) {
		this.currentMana = currentMana;
	}

	public int getCurrentLife() {
		return currentLife;
	}

	public void setCurrentLife(int currentLife) {
		this.currentLife = currentLife;
	}

	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}

	public String getFighterID() {
		return fighterID;
	}

	public void setFighterID(String fighterID) {
		this.fighterID = fighterID;
	}
	
	public String specialAttack(int enemieID) {		
		if(this.currentMana > 1 && !this.isDead) {
			if ("PRIEST".contentEquals(this.fighterClass) || "CHAMAN".contentEquals(this.fighterClass) || "GUARD".contentEquals(this.fighterClass)) {
				return "A" + this.orderNumberInTeam + "," + Attack.valueOf(this.fighterClass).getSpecialAttack() + ",A" + enemieID + "$";
			} else {
				return "A" + this.orderNumberInTeam + "," + Attack.valueOf(this.fighterClass).getSpecialAttack() + ",E" + enemieID + "$";
			}
		} else {
			return rest();
		}
	}
	
	public String attack(int enemieID) {
		if(this.currentMana > 0 && !this.isDead) {
			return "A" + this.orderNumberInTeam + ",ATTACK,E" + enemieID + "$";
		} else {
			return rest();
		}
	}
	
	public String rest() {
		if (!this.isDead) {
			return "A" + this.orderNumberInTeam + ",REST,A" + this.orderNumberInTeam + "$";
		} else {
			return "";
		}
	}
	
	public String defend() {
		if(this.currentMana > 0 && !this.isDead) {
			return "A" + this.orderNumberInTeam + ",DEFEND,A" + this.orderNumberInTeam + "$";
		} else {
			return rest();
		}
	}
	
	public boolean isBurning() {
		if (this.getStates() != null) {
			for (State state : this.getStates()) {
				if ("BURNING".equals(state.getType())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isStunned() {
		if (this.getStates() != null) {
			for (State state : this.getStates()) {
				if ("STUNNED".equals(state.getType())) {
					return true;
				}
			}
		}
		return false;
	}
	
}
