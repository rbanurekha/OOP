package Pokemon_FP;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Player implements LeveledObject
{
	private String playerName;
	private int experience;
	private int level=1;
	private ArrayList<Potion> potionsLeft;
	private ArrayList<Pokeball> pokeballsLeft;
	private EvolvablePokemon currenPokemon;
	ArrayList<EvolvablePokemon> pokemonsLeft;
	private int battlesWon;
	private DB db=new DB();
	private static final int maxLevel=3;
	private static final int XpToIncrement=10;
	private int rewards;
 public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public int getRewards() {
		return rewards;
	}
	public void setRewards(int rewards) {
		this.rewards = rewards;
	}
	public Player()
	 {
		    this.playerName = "";
			this.experience = 0;
			this.level = 1;
			this.pokemonsLeft=new ArrayList<EvolvablePokemon>();
			this.pokeballsLeft=new ArrayList<Pokeball>();
		    this.potionsLeft=new ArrayList<Potion>();
			this.battlesWon = 0;
			this.rewards=100;
		 
	 }
	public Player(String userName, int experience, int level,
			ArrayList<Potion> potionsLeft, ArrayList<Pokeball> pokeballsLeft,
			EvolvablePokemon currenPokemon, ArrayList<EvolvablePokemon> pokemonsLeft,
			int battlesWon,int rewards) {
		super();
		this.playerName = userName;
		this.experience = experience;
		this.level = level;
		this.potionsLeft = potionsLeft;
		this.pokeballsLeft = pokeballsLeft;
		this.currenPokemon = currenPokemon;
		this.pokemonsLeft = pokemonsLeft;
		this.battlesWon = battlesWon;
		this.rewards=rewards;
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
		System.out.printf("\n%s Battle's won:%d",this.playerName,this.battlesWon);
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


public Player createPlayer(Player p,String name) throws SQLException
{
	if(!db.isExistingPlayer(name))
	{
		this.setUserName(name);
		this.pokeballsLeft.add(new Pokeball());
		this.potionsLeft.add(new Potion());
	    createPokeGang(p);
     }
	else
	{
		System.out.println("Welcome back "+name+"!!!");
		p=db.getPlayerDetails(name);
		if(p.getPokemonsLeft().size()<1)
			createPokeGang(p);
	}
	return p;
 }
private void createPokeGang(Player p)
{
	EvolvablePokemon poke=new EvolvablePokemon(); 
	Scanner input=new Scanner(System.in);
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
	 db.insertOrUpdatePlayer(p);
	 }
	  catch (SQLException e) {
		e.printStackTrace();
	  //System.out.println("Oops!! Something went wrong!!");
	 }
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
		this.setRewards(this.rewards+10);
		System.out.printf("\nYou gained 10 reward points for reaching the level %d\n Your total reward points:%d ",getLevel(),this.rewards);
		if(this.getLevel()==maxLevel)
			evolveUser(this);
	}
	private void evolveUser(Player p)
	{
		System.out.printf("\n%s has reached the maximum level\nEvolving the pokegang..",this.getUserName());
		for(int i=0;i<this.pokemonsLeft.size();i++)
		{
			if(p.pokemonsLeft.get(i).pokemonAfterEvolution!=null)
			  p.pokemonsLeft.get(i).evolve();
			else
				System.out.println(i);
		}
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
				System.out.printf("%d\t%s\t%d\n",i+1,pball.getBallType(),pball.getPokeballCount());
			  
		    }
			System.out.println("\nPlease choose a ball type");
			choice=input.nextInt();
			Pokeball tempBall=this.pokeballsLeft.get(choice-1);
			System.out.printf("Throwing ballType %s to capture pokemon...\n",tempBall.getBallType());
			int ballProb=(int)(Math.random()*10);			
			if (ballProb<=tempBall.getProbability()*10)
			{
				EvolvablePokemon tempPoke=new EvolvablePokemon();
                tempPoke=db.getPokemonDetails(ballProb+1);
                System.out.println("Congratulations! you have captured the pokemon");
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
		if(this.getPokemonsLeft().size()<1)
			createPokeGang(this);
		int choice;
		Scanner input=new Scanner(System.in);
		System.out.printf("\n%s Please choose your pokemon for the battle:",this.getUserName());
	 	this.listPokemonsLeft();
	 	choice=input.nextInt();
	 	this.setCurrenPokemon(this.getPokemonsLeft().get(choice-1));
	 	this.getCurrenPokemon().viewPokemon();
	}
	public void visitPokeStop() throws SQLException
	{
		int choice;
		Scanner input=new Scanner(System.in);
		System.out.println("Welcome to the Pokestop!!!\nPlease choose from the following reward items\n1.Potions\t2.Pokeballs");
		choice=input.nextInt();
		if(choice==1)
		{
			db.listPotions();
			choice=input.nextInt();
			getPotionReward(choice);
		}
		
		else
		{
			db.listPokeballs();
			choice=input.nextInt();
			getPokeballReward(choice);
		}
	}
	private void getPokeballReward(int choice)throws SQLException
	{
		boolean pokeballFound=false;
		Pokeball tempBall=db.getPokeballDetails(choice);
		int convertedProb=(int)tempBall.getProbability()*10;
		if(convertedProb<this.rewards)
		{		
			this.rewards=this.rewards-convertedProb;
			if(this.getPokeballsLeft().size()>0)
			{
				pokeballFound=false;
				for(int i=0;i<this.getPokeballsLeft().size();i++)
				{
					if(this.getPokeballsLeft().get(i).getBallType().toString().equals(tempBall.getBallType()))
					{
						pokeballFound=true;
						this.getPokeballsLeft().get(i).setPokeballCount(this.getPokeballsLeft().get(i).getPokeballCount()+1);
					}
				}
			}
			if(!pokeballFound)
			{
				this.pokeballsLeft.add(new Pokeball());
				this.pokeballsLeft.set(this.pokeballsLeft.size()>0?this.pokeballsLeft.size()-1:this.pokeballsLeft.size(),tempBall);
			}
			System.out.println("You acquired a new ball!!\nYour new PokeBall pack details below\nType\tcount\n----------------");
			for(int i=0;i<this.pokeballsLeft.size();i++)
			{
				Pokeball pl=this.pokeballsLeft.get(i);
				System.out.printf("%s\t\t%d\n",pl.getBallType(),pl.getPokeballCount());
		    }
		}
		else
			System.out.println("Sorry you do not have enough rewards! Your current reward points :"+this.getRewards());		
	}
	private void getPotionReward(int choice)throws SQLException
	{
		boolean potionFound=false;
		Potion tempPotion=db.getPotionDetails(choice);
		if(tempPotion.getHealingPower()<this.rewards)
		{				
			this.rewards=this.rewards-tempPotion.getHealingPower();
			if(this.getPotionsLeft().size()>0)
			{
				for(int i=0;i<this.getPotionsLeft().size();i++)
				{
					
					if(this.getPotionsLeft().get(i).getPotionType().toString().equals(tempPotion.getPotionType()))
					{
						potionFound=true;
						this.getPotionsLeft().get(i).setPotionCount(this.getPotionsLeft().get(i).getPotionCount()+1);
					}
				}
			}
			if(!potionFound)
			{
				this.potionsLeft.add(new Potion());
				this.potionsLeft.set(this.potionsLeft.size()-1,tempPotion);
			}
			System.out.println("You acquired a new potion!!!\nYour new Potion pack details below\npotionType\tcount\n------------------------");
			for(int i=0;i<this.potionsLeft.size();i++)
			{
				Potion pn=this.potionsLeft.get(i);
				System.out.printf("%s\t\t%d\n",pn.getPotionType(),pn.getPotionCount());
		    }
		}
		else
			System.out.println("Sorry you do not have enough rewards! Your current reward points :"+this.getRewards());		
	}
	
	public void applyPotion()
	{
		Scanner input=new Scanner(System.in);
		int choices=0;
		if(this.potionsLeft.size()>0)
		{
			System.out.println("Option PotionType\tcount");
			for(int i=0;i< this.potionsLeft.size();i++)
			{
				Potion potioncount=this.potionsLeft.get(i);
				System.out.printf("%d\t%s\t%d",i+1,potioncount.getPotionType(),potioncount.getPotionCount());
			
		    }
			System.out.println("\nPlease choose Potion type");
			choices=input.nextInt();
			Potion tempPotion=this.potionsLeft.get(choices-1);
			System.out.printf("Applying PotionType %s to  pokemon",tempPotion.getPotionType());
			this.currenPokemon.setHitPoint(this.currenPokemon.getHitPoint() + tempPotion.getHealingPower()); 
		 	if(tempPotion.getPotionCount()>1)
				this.potionsLeft.get(choices-1).setPotionCount(tempPotion.getPotionCount()-1);
			else
				this.potionsLeft.remove(choices-1);			
		}
	 }
	public void viewPlayer()
	{
		System.out.printf("Name:%s\nRegularball:%d\nGreatball:%d\n",this.playerName,this.pokeballsLeft.get(0).pokeballCount,this.pokeballsLeft.get(1).pokeballCount);
	}
}
