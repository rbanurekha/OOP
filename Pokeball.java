package Pokemon_FP;

public class Pokeball 
{
	private String ballType;
	private double probability;
	int pokeballCount;
	
	public Pokeball()
	{
		ballType="basic";
		probability=0.2;
		pokeballCount=2;
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
	

}
