package Pokemon_FP;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Player implements LeveledObject
{
	private String playerName;
	private int experience;
	private int level;
	private ArrayList<Potion> potionsLeft;
	private ArrayList<Pokeball> pokeballsLeft;
	private EvolvablePokemon currenPokemon;
	ArrayList<EvolvablePokemon> pokemonsLeft;
	private int battlesWon;
	private DB db=new DB();
	private static final int maxLevel=3;
	private static final int XpToIncrement=10;
 public Player()
 {
	 this.playerName = "";
		this.experience = 0;
		this.level = 1;
		this.pokemonsLeft=new ArrayList<EvolvablePokemon>();
		this.pokeballsLeft=new ArrayList<Pokeball>();
	this.potionsLeft=new ArrayList<Potion>();
		this.battlesWon = 0;
	 
 }
	public Player(String userName, int experience, int level,
			ArrayList<Potion> potionsLeft, ArrayList<Pokeball> pokeballsLeft,
			EvolvablePokemon currenPokemon, ArrayList<EvolvablePokemon> pokemonsLeft,
			int battlesWon) {
		super();
		this.playerName = userName;
		this.experience = experience;
		this.level = level;
		this.potionsLeft = potionsLeft;
		this.pokeballsLeft = pokeballsLeft;
		this.currenPokemon = currenPokemon;
		this.pokemonsLeft = pokemonsLeft;
		this.battlesWon = battlesWon;
	}
	public String getUserName() {
		return playerName;
	}
	public void setUserName(String userName) {
		this.playerName = userName;
	}
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public ArrayList<Potion> getPotionsLeft() {
		return potionsLeft;
	}
	public void setPotionsLeft(ArrayList<Potion> potionsLeft) {
		this.potionsLeft = potionsLeft;
	}
	public ArrayList<Pokeball> getPokeballsLeft() {
		return pokeballsLeft;
	}
	public void setPokeballsLeft(ArrayList<Pokeball> pokeballsLeft) {
		this.pokeballsLeft = pokeballsLeft;
	}
	public EvolvablePokemon getCurrenPokemon() {
		return currenPokemon;
	}
	public void setCurrenPokemon(EvolvablePokemon currenPokemon) {
		this.currenPokemon = currenPokemon;
	}
	public ArrayList<EvolvablePokemon> getPokemonsLeft() {
		return pokemonsLeft;
	}
	public void setPokemonsLeft(ArrayList<EvolvablePokemon> pokemonsLeft) {
		this.pokemonsLeft = pokemonsLeft;
	}
	public int getBattlesWon() {
		return battlesWon;
	}
	public void setBattlesWon(int battlesWon) {
		this.battlesWon = battlesWon;
	}
   public void listPokemonsLeft()
   {
	   int i=1;
	for(EvolvablePokemon p : this.pokemonsLeft)
	{
		System.out.printf("%d.%s\t",i,p.getName());
	    i++;
    }	
   }


public void createPokeGang(Player p,String name)
{
	Scanner input=new Scanner(System.in);
	EvolvablePokemon poke=new EvolvablePokemon(); 
	if(!db.isExistingPlayer(name))
	{
		this.setUserName(name);
		this.pokeballsLeft.add(new Pokeball());
	    try
	    {
		 for (int j=1;j<=3;j++)
		 {
			 System.out.printf("Please choose pokemon %d:\n",j);
			 db.listPokemons();
			 int choice=input.nextInt();
		 	 poke=db.getPokemonDetails(choice);
			 p.pokemonsLeft.add(new EvolvablePokemon());
			 p.pokemonsLeft.set(j-1, poke);	
		 }
		  }
	      catch (SQLException e) {
			//e.printStackTrace();
		  System.out.println("Oops!! Something went wrong!!");
	     }
     }
	else
	 p=db.getPlayerDetails(name);

 }

  public void gainExperience(int amount)
  {
		this.experience+=amount;
		System.out.printf("\n%s's current experience:%d",this.getUserName(),this.getExperience());
		if(enoughXPForNextLevel()) 
			levelUP();
 }
	
	public void levelUP(){
		this.level++;
		System.out.printf("\n%s just leveled up to %d",this.getUserName(),getLevel());
		getLevelUPreward(this.level);
	}
	
	public boolean enoughXPForNextLevel()
	{
		return this.experience>=(level*XpToIncrement) ;
	}
	
	private void getLevelUPreward(int level)
	{
		// gain a pokeball or a potion as reward
		System.out.println("\nYou gained new items as reward for Level "+getLevel());
		if(this.getLevel()==maxLevel)
			evolveUser(this);
	}
	private void evolveUser(Player p)
	{
		System.out.printf("\n%s has reached the maximum level\nEvolving the pokegang..",this.getUserName());
		for(int i=0;i<this.pokemonsLeft.size();i++)
			if(p.pokemonsLeft.get(i).pokemonAfterEvolution!=null)
			  p.pokemonsLeft.get(i).evolve();
		System.out.println("Here comes the new Pokegang");
			p.listPokemonsLeft();	
	}
	
	public void capture() throws SQLException
	{	
		Scanner input=new Scanner(System.in);
		int choice=0;
		if(this.pokeballsLeft.size()>0)
		{
			System.out.println("Option BallType\tcount");
			for(int i=0;i< this.pokeballsLeft.size();i++)
			{
				Pokeball pball=this.pokeballsLeft.get(i);
				System.out.printf("%d\t%s\t%d",i+1,pball.getBallType(),pball.getPokeballCount());
			    i++;
		    }
			System.out.println("\nPlease choose a ball type");
			choice=input.nextInt();
			Pokeball tempBall=this.pokeballsLeft.get(choice-1);
			System.out.printf("Throwing ballType %s to capture pokemon",tempBall.getBallType());
			int ballProb=(int)(Math.random()*10);
			System.out.println(ballProb);
			if (ballProb<=tempBall.getProbability()*10)
			{
				System.out.println("Congratulations! you have captured the pokemon");
				EvolvablePokemon tempPoke=new EvolvablePokemon();
                tempPoke=db.getPokemonDetails(ballProb+1);
                if(this.pokemonsLeft.size()<7)
                {
                	this.pokemonsLeft.add(new EvolvablePokemon());
                	this.pokemonsLeft.set(this.pokemonsLeft.size()-1, tempPoke);
                	this.pokemonsLeft.get(this.pokemonsLeft.size()-1).viewPokemon();
			
                }
                else
                {
                	this.pokemonsLeft.set(this.pokemonsLeft.size()-1, tempPoke);
                	this.pokemonsLeft.get(this.pokemonsLeft.size()-1).viewPokemon();
                }
                	
			}
			else
				System.out.println("Sorry! the ball you threw did not hit the pokemon");
			if(tempBall.getPokeballCount()>1)
				this.pokeballsLeft.get(choice-1).setPokeballCount(tempBall.getPokeballCount()-1);
			else
				this.pokeballsLeft.remove(choice-1);		
		}
	
		else
			System.out.println("Sorry! you have no pokeballs left..");
	}
	public void removePokemon()
	{
		for(int i=0;i< this.pokemonsLeft.size();i++)
	    {
	    	if(this.pokemonsLeft.get(i).getHitPoint()<=0)
	    	{
	    		this.pokemonsLeft.remove(i);
	    		System.out.printf("\nThe defeated pokemon %s has been removed from %s 's PokeGang",this.getCurrenPokemon().getName(),this.getUserName());
	    		this.currenPokemon=null;
	    	}
	    }
	}
	public void chooseFighterPokemon()
	{
		int choice;
		Scanner input=new Scanner(System.in);
		System.out.printf("\nChoose your pokemon for the player %s:",this.getUserName());
	 	this.listPokemonsLeft();
	 	choice=input.nextInt();
	 	this.setCurrenPokemon(this.getPokemonsLeft().get(choice-1));
	 	this.getCurrenPokemon().viewPokemon();
	}
	public void visitPokeStop()
	{
		
	}
	public void applyPotion()
	{
		//Determine which potion to use
		//increase hp
		//decrease potion count
		
	}
}
