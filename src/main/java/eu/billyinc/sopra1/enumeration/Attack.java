package eu.billyinc.sopra1.enumeration;

public enum Attack {
	GUARD("PROTECT"), ORC("YELL"), PRIEST("HEAL"), PALADIN("CHARGE"), ARCHER("FIREBOLT"), CHAMAN("CLEANSE");
	
	private String specialAttack;

	private Attack(String specialAttack) {
		this.specialAttack = specialAttack;
	}

	public String getSpecialAttack() {
		return specialAttack;
	}

	public void setSpecialAttack(String specialAttack) {
		this.specialAttack = specialAttack;
	}
}
