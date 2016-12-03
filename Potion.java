package Pokemon_FP;

public class Potion 
{
	private String potionType;
	private int healingPower;
	private int potionCount;
	
	public Potion()
	{
		potionType="basic";
		healingPower=20;
		potionCount=1;
	}
	public String getPotionType() {
		return potionType;
	}
	public int getPotionCount() {
		return potionCount;
	}
	public void setPotionCount(int potionCount) {
		this.potionCount = potionCount;
	}
	public void setPotionType(String potionType) {
		this.potionType = potionType;
	}
	public int getHealingPower() {
		return healingPower;
	}
	public void setHealingPower(int healingPower) {
		this.healingPower = healingPower;
	}

}
