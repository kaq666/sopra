package eu.billyinc.sopra1.enumeration;

public enum Attack {
	GUARD("PROTECT"), ORC("YELL"), PRIEST("HEAL");
	
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
