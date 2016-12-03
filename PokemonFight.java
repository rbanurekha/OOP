package Pokemon_FP;
import java.util.Scanner;

public class PokemonFight
{
	private static final int XPwon=20;
	private static final int XPlost=1;
	
	static void hitOpponent(Pokemon striker, Pokemon opponent)
	{
		opponent.setHitPoint(opponent.getHitPoint()-striker.getStrength());
		System.out.printf("\nThe %s used %s against %s....%s's HP decreased to %d",striker.getName(),striker.getAbility().getMove1(),opponent.getName(),opponent.getName(),opponent.getHitPoint());
	}

	private static void announceWinner(Player winner,Player loser) 
	{
		System.out.print("\n\nAnd the WINNER is...");
		System.out.printf("\n%s defeated %s;remaining HP: %d",winner.getCurrenPokemon().getName().toUpperCase(),loser.getCurrenPokemon().getName(),winner.getCurrenPokemon().getHitPoint());
		winner.setBattlesWon(winner.getBattlesWon()+1);
		winner.gainExperience(XPwon);
		loser.gainExperience(XPlost);
		loser.removePokemon();
	}
	
	public static void battle(Player[] player) throws InterruptedException 
	{
		Scanner reader;
		String n;
		Player u1=player[0];
		Player u2=player[1];
		String fightAgain="Y";
		do
		{
			for(int i=0;i<2;i++)
				player[i].chooseFighterPokemon();
			System.out.println("\n\nThe Combat begins in few seconds... Please wait!!");
			EvolvablePokemon p1=u1.getCurrenPokemon();
			EvolvablePokemon p2=u2.getCurrenPokemon();
			try 
			{
				//sleep 3 seconds
				Thread.sleep(3000);			
		    }
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}	
			//CalculateStrength(p1);CalculateStrength(p2);
	        boolean retreat=false; 
			do
			{
				System.out.printf("\nChoose move for %s:1.attack\t2.retreat\t3.Use potion",p1.getName());
				reader = new Scanner(System.in);
				n = reader.next();
				if(n.equals("1"))
				{
					//choose move1 or move2
					hitOpponent(p1,p2);
				
				}
				else if(n.equals("3"))
					u1.applyPotion();
				else
				{
					retreat=true;
					announceWinner(u2,u1);
					break;
				}
				if(p2.getHitPoint()<=0)
					break;
				System.out.printf("\nChoose move for %s:1.attack\t2.retreat\t3.Use potion",p2.getName());
				reader = new Scanner(System.in);
				n = reader.next();
				if(n.equals("1"))
				{
					//choose move1 or move2
					hitOpponent(p2,p1);
				
				}
				else if(n.equals("3"))
					u2.applyPotion();
				else
				{
					retreat=true;
					announceWinner(u2,u1);
					break;
				}
	
				Thread.sleep(500);
		
			}while(p1.getHitPoint()>=0 && p2.getHitPoint()>=0);
			if(retreat==false)
			{
				if(p1.getHitPoint()>p2.getHitPoint())
					announceWinner(u1,u2);		    
				else
					announceWinner(u2,u1);
			}
			System.out.println("\nWould you like to fight again?? Press Y:");
			fightAgain=reader.next();
		}while("Y".equalsIgnoreCase(fightAgain));
	}
	
}
