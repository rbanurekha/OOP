package Pokemon_FP;

public class Pokeball 
{
	private String ballType;
	private double probability;
	int pokeballCount;
	public static final String REG_BALL_NAME = "regular";
	public static final String GREAT_BALL_NAME = "great";
	public static final String ULTRA_BALL_NAME = "ultra";

	
	public Pokeball()
	{
		ballType="regular";
		probability=0.2;
		pokeballCount=2;
	}
	public Pokeball(String ballType, double probability, int pokeballCount) {
		super();
		this.ballType = ballType;
		this.probability = probability;
		this.pokeballCount = pokeballCount;
	}
	public int getPokeballCount() {
		return pokeballCount;
	}
	public void setPokeballCount(int pokeballCount) {
		this.pokeballCount = pokeballCount;
	}
	public String getBallType() {
		return ballType;
	}
	public void setBallType(String ballType) {
		this.ballType = ballType;
	}
	public double getProbability() {
		return probability;
	}
	public void setProbability(double probability) {
		this.probability = probability;
	}
	
	public boolean isRegularBall()
	{
		return REG_BALL_NAME.equalsIgnoreCase(ballType);
	}
	public boolean isUltraBall()
	{
		return ULTRA_BALL_NAME.equalsIgnoreCase(ballType);
	}
	public boolean isGreatBall()
	{
		return GREAT_BALL_NAME.equalsIgnoreCase(ballType);
	}
}
