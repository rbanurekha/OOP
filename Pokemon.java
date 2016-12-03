package Pokemon_FP;


public class Pokemon 
{
	private String name;
	private int combatPower;
	private int hitPoint;
	private String type;
	private Ability ability;
	private int strength;
	
	public Pokemon()
	{
		
	}
	public Pokemon(String name, int combatPower, int hitPoint, String type,
			Ability ability) {
		
		this.name = name;
		this.type = type;
		this.ability = ability;
		this.createUserProfile(combatPower, hitPoint);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCombatPower() {
		return combatPower;
	}
	public void setCombatPower(int combatPower) {
		this.combatPower = combatPower;
	}
	public int getHitPoint() {
		return hitPoint;
	}
	public int getStrength() {
		return strength;
	}
	public void setStrength(int strength) {
		this.strength = strength;
	}
	public void setHitPoint(int hitPoint) {
		this.hitPoint = hitPoint;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Ability getAbility() {
		return ability;
	}
	public void setAbility(Ability ability) {
		this.ability = ability;
	}

	public void createUserProfile(int maxCP,int maxHP)
	{
		this.setCombatPower((int)(Math.random()*maxCP));
		this.setHitPoint(20+(int)(Math.random()*maxHP));
	    this.strength=10+maxCP/maxHP;	
	}
	public void viewPokemon()
	{
		System.out.printf("A wild %s with CP %d HP %d has appeared!.....",this.getName(),this.getCombatPower(),this.getHitPoint());
		System.out.printf("\n%s has the ability %s with strength %d",this.getName(),this.getType(),this.getStrength());
	}
	
}
