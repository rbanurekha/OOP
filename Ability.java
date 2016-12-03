package Pokemon_FP;

public class Ability
{
	
	private String move1;
	private String move2;
	
	public Ability(String move1, String move2) {
		
		this.move1 = move1;
		this.move2 = move2;
	}
	public String getMove1() {
		return move1;
	}
	public void setMove1(String move1) {
		this.move1 = move1;
	}
	public String getMove2() {
		return move2;
	}
	public void setMove2(String move2) {
		this.move2 = move2;
	}

}
