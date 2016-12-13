package Pokemon_FP;

public class Potion 
{
	private String potionType;
	private int healingPower;
	private int potionCount;
	public static final String POTION_REGULAR = "regular";
	public static final String POTION_SUPER = "super";
	public static final String POTION_HYPER= "hyper";
	public Potion()
	{
		potionType="regular";
		healingPower=20;
		potionCount=1;
	}
	public Potion(String potionType, int healingPower, int potionCount) {
		super();
		this.potionType = potionType;
		this.healingPower = healingPower;
		this.potionCount = potionCount;
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
	public boolean isHyperPotion()
	{
		return POTION_HYPER.equalsIgnoreCase(potionType);
	}
	public boolean isSuperPotion()
	{
		return POTION_SUPER.equalsIgnoreCase(potionType);
	}
	public boolean isRegularPotion()
	{
		return POTION_REGULAR.equalsIgnoreCase(potionType);
	}


}
