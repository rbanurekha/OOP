package Pokemon_FP;
import java.sql.SQLException;
import java.util.Scanner;
public class PokemonGo
{
	public static void main(String[] args) throws SQLException, InterruptedException 
	{
        String playAgain="Y";
		Scanner input=new Scanner(System.in);
		Player[] player = new Player[2];
		int option=0;
		String username;
		while("Y".equalsIgnoreCase(playAgain))
		{
			for(int i=0;i<2;i++)
			{
				System.out.printf("\nPlease enter Player %d:",i+1);
				username=input.next();
				player[i]=new Player();
				player[i].createPokeGang(player[i],username);
				if(i==0)
				{
					System.out.printf("Please choose an option 1.Battle\t2.Catch Pokemon\t3.Visit PokeStop");
					option=input.nextInt();
					if(option!=1)
						 break;
				}
				
		     	if(i==1)
		     		PokemonFight.battle(player);
			  }   
			 if(option==2)
				player[0].capture();
			 else
				 player[0].visitPokeStop();
			System.out.println("\nAre you ready to roll again?? Press Y:");
			playAgain=input.next();
	   }
		System.out.println("Hmm!! looks like you are tired of fighting.. Come back soon!!");       
	}
}


