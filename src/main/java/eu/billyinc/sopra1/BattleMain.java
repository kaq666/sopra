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
		System.out.println("ID équipe : " + teamID);
		System.out.println("ID partie : " + gameID + "\n");
		
		String[] heroes = {"ARCHER", "CHAMAN", "PALADIN"};
		int numTour = 1;
		boolean again = true;
		while (again && numTour < 55) {		
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				System.out.println("Impossible de faire une pause !");
			}
			if("CANPLAY".equals(apiHelper.status(gameID, teamID))) {
				System.out.println("Tour n°" + numTour);
				if(numTour <= 3) {
					apiHelper.play(gameID, teamID, heroes[numTour - 1]);
				} else {
					String move = move(apiHelper.boardTeam(gameID, teamID, Format.JSON), numTour);
					apiHelper.play(gameID, teamID, move);
				}
				System.out.println("Eux " + apiHelper.lastMove(gameID, teamID));
				System.out.println(summaryToString(apiHelper.boardTeam(gameID, teamID, Format.JSON)));
				numTour++;
			} else if ("CANTPLAY".equals(apiHelper.status(gameID, teamID))) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					System.out.println("Impossible de faire une pause !");
				}
			} else {
				again = false;
				System.out.println(apiHelper.status(gameID, teamID));
				System.out.println(summaryToString(apiHelper.boardTeam(gameID, teamID, Format.JSON)));
			}
		}
	}
	
	public static String move(Board board, int numTour) {
		String move = "";
		List<EpicHero> heroesUsed = new ArrayList<EpicHero>();
		EpicHeroesLeague heroesTeam = board.getPlayerBoards().get(0);
		EpicHeroesLeague enemiesTeam = board.getPlayerBoards().get(1);
		
		
		
		for (EpicHero hero : heroesTeam.getFighters()) {
			double r;
			if (!heroesUsed.contains(hero)) {
				if (hero.getCurrentLife() < 4) {
					if (hero.getCurrentLife() > 1 && !hero.isBurning()) {
						move += hero.defend();
					} else {
						r = 0;
						do {
							r = (Math.random() * ((3 - 1) + 1)) + 1;
						} while (enemiesTeam.isFighterDead((int) r));
						
						move += hero.attack((int) r);
					}
				} else {
					switch (hero.getFighterClass()) {
					case "PRIEST":
						if (hero.getCurrentMana() < 2) {
							move += hero.rest();
						} else if (heroesTeam.heroesNeedHeal().size() > 0) {
							// On soigne celui avec le moins de pv
							move += hero.specialAttack(heroesTeam.heroesNeedHeal().get(0).getOrderNumberInTeam());
						} else {
							if (hero.getCurrentMana() < 3) {
								move += hero.rest();
							} else {
								r = 0;
								do {
									r = (Math.random() * ((3 - 1) + 1)) + 1;
								} while (enemiesTeam.isFighterDead((int) r));
								
								move += hero.attack((int) r);
							}
						}
						break;
					case "CHAMAN":
						if (hero.getCurrentMana() < 2 && heroesTeam.getFightersAlive().size() > 1) {
							move += hero.rest();
						} else {
							if (hero.isStunned() || hero.isBurning()) {
								move += hero.specialAttack(hero.getOrderNumberInTeam());
							} else {
								if (heroesTeam.heroesStunned().size() > 0) {
									move += hero.specialAttack(heroesTeam.heroesStunned().get(0).getOrderNumberInTeam());
								} else if (heroesTeam.heroesBurning().size() > 0) {
									move += hero.specialAttack(heroesTeam.heroesBurning().get(0).getOrderNumberInTeam());
								} else if (heroesTeam.hasArcher() instanceof EpicHero) {
									move += hero.specialAttack(heroesTeam.hasArcher().getOrderNumberInTeam());
								} else if (heroesTeam.hasPaladin() instanceof EpicHero) {
									move += hero.specialAttack(heroesTeam.hasPaladin().getOrderNumberInTeam());
								} else {
									r = 0;
									do {
										r = (Math.random() * ((3 - 1) + 1)) + 1;
									} while (enemiesTeam.isFighterDead((int) r));
									move += hero.attack((int) r);
									break;
								}
							}
						}
						break;
					case "ARCHER":
						if (hero.getCurrentMana() < 2 && heroesTeam.getFightersAlive().size() > 1) {
							move += hero.rest();
						} else {
							List<EpicHero> enemiesWillBurn = enemiesTeam.fightersWillBurn();
							
							if (EpicHeroesLeague.hasArcher(enemiesWillBurn) instanceof EpicHero) {
								move += hero.specialAttack(EpicHeroesLeague.hasArcher(enemiesWillBurn).getOrderNumberInTeam());
							} else if (EpicHeroesLeague.hasPaladin(enemiesWillBurn) instanceof EpicHero) {
								move += hero.specialAttack(EpicHeroesLeague.hasPaladin(enemiesWillBurn).getOrderNumberInTeam());
							} else if (EpicHeroesLeague.hasOrc(enemiesWillBurn) instanceof EpicHero) {
								move += hero.specialAttack(EpicHeroesLeague.hasOrc(enemiesWillBurn).getOrderNumberInTeam());
							} else if (EpicHeroesLeague.hasPriest(enemiesWillBurn) instanceof EpicHero) {
								move += hero.specialAttack(EpicHeroesLeague.hasPriest(enemiesWillBurn).getOrderNumberInTeam());
							} else if (EpicHeroesLeague.hasChaman(enemiesWillBurn) instanceof EpicHero) {
								move += hero.specialAttack(EpicHeroesLeague.hasChaman(enemiesWillBurn).getOrderNumberInTeam());
							} else if (EpicHeroesLeague.hasGuard(enemiesWillBurn) instanceof EpicHero) {
								move += hero.specialAttack(EpicHeroesLeague.hasGuard(enemiesWillBurn).getOrderNumberInTeam());
							} else {
								r = 0;
								do {
									r = (Math.random() * ((3 - 1) + 1)) + 1;
								} while (enemiesTeam.isFighterDead((int) r));
								move += hero.attack((int) r);
							}
						}
						break;
					case "PALADIN":
						if (hero.getCurrentMana() < 2) {
							move += hero.rest();
						} else {
							List<EpicHero> enemiesNotStunned = enemiesTeam.heroesNotStunned();
							System.out.println("NOT Stunt " + enemiesTeam.heroesNotStunned().size());
							
							if (EpicHeroesLeague.hasPaladin(enemiesNotStunned) instanceof EpicHero) {
								move += hero.specialAttack(EpicHeroesLeague.hasPaladin(enemiesNotStunned).getOrderNumberInTeam());
							} else if (EpicHeroesLeague.hasArcher(enemiesNotStunned) instanceof EpicHero) {
								move += hero.specialAttack(EpicHeroesLeague.hasArcher(enemiesNotStunned).getOrderNumberInTeam());
							} else if (EpicHeroesLeague.hasOrc(enemiesNotStunned) instanceof EpicHero) {
								move += hero.specialAttack(EpicHeroesLeague.hasOrc(enemiesNotStunned).getOrderNumberInTeam());
							} else if (EpicHeroesLeague.hasPriest(enemiesNotStunned) instanceof EpicHero) {
								move += hero.specialAttack(EpicHeroesLeague.hasPriest(enemiesNotStunned).getOrderNumberInTeam());
							} else if (EpicHeroesLeague.hasChaman(enemiesNotStunned) instanceof EpicHero) {
								move += hero.specialAttack(EpicHeroesLeague.hasChaman(enemiesNotStunned).getOrderNumberInTeam());
							} else if (EpicHeroesLeague.hasGuard(enemiesNotStunned) instanceof EpicHero) {
								move += hero.specialAttack(EpicHeroesLeague.hasGuard(enemiesNotStunned).getOrderNumberInTeam());
							} else {
								r = 0;
								do {
									r = (Math.random() * ((3 - 1) + 1)) + 1;
								} while (enemiesTeam.isFighterDead((int) r));
								move += hero.attack((int) r);
								break;
							}
						}
						break;
					default:
						r = 0;
						do {
							r = (Math.random() * ((3 - 1) + 1)) + 1;
						} while (enemiesTeam.isFighterDead((int) r));
						move += hero.attack((int) r);
						break;
					}
				}
				heroesUsed.add(hero);
			} else {
				
			}
		}
		
		
		if (move.length() > 1) {
			System.out.println("Nous " +move.substring(0, move.length() - 1));
			return move.substring(0, move.length() - 1);
		} else {
			System.out.println("Nous " + move);
			return move;
		}
	}
	
	public void attackPriest() {
	}
	
	public void attackOrc() {
	}
	
	public void attackGuard() {
	}
	
	public static String summaryToString(Board board) {
		String summary = "";
		for (EpicHeroesLeague player : board.getPlayerBoards()) {
			summary += player.getPlayerName() + " :\n";
			
			if (player.getFighters() == null)
				return "Aucun combattant !";
			
			for (EpicHero hero : player.getFighters()) {
				if (!hero.isDead()) {
					summary += hero.getFighterClass() + " (" + hero.getOrderNumberInTeam() +"): " + hero.getCurrentLife() + "PV " + hero.getCurrentMana() + "PA ";
					if (hero.getStates() != null) {
						summary += "(";
						for (State state : hero.getStates()) {
							summary += state.getType() + ",";
						}
						summary = summary.substring(0, summary.length() - 1);
						summary += ")";
					}
				}
				summary += "\n";
			}
			summary += "\n";
		}
		
		return summary;
	}
}
