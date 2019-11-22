package eu.billyinc.sopra1.board;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(value = { "playerMoves" })
public class Board {
	@JsonProperty("playerBoards")
	private List<EpicHeroesLeague> playerBoards;
	@JsonProperty("nbrTurnsLeft")
	private int nbrTurnsLeft;

	public List<EpicHeroesLeague> getPlayerBoards() {
		return playerBoards;
	}

	public void setPlayerBoards(List<EpicHeroesLeague> playerBoards) {
		this.playerBoards = playerBoards;
	}

	public int getNbrTurnsLeft() {
		return nbrTurnsLeft;
	}

	public void setNbrTurnsLeft(int nbrTurnsLeft) {
		this.nbrTurnsLeft = nbrTurnsLeft;
	}
}
