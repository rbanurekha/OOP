package Pokemon_FP;
import java.sql.SQLException;
import java.util.Scanner;
public class PokemonGo
{
	public static void main(String[] args) throws SQLException, InterruptedException 
	{
		Scanner input=new Scanner(System.in);
		Player[] player = new Player[2];
		int option=1;
		String username;
		System.out.printf("\nWelcome to PokemonGo\nPlease enter your name:");
		username=input.next();
		player[0]=new Player();
		player[0]=player[0].createPlayer(player[0],username);
		while(option<4 && option>0)
		{	
			System.out.printf("\nMain menu\nPlease choose an option:  1.Battle\t2.Catch Pokemon \t3.Visit PokeStop\t 4.Save and Exit");
			option=input.nextInt();				
			if(option==1)
			{
				System.out.printf("\nPlease enter the second Player");
				username=input.next();
				if(player[0].getPlayerName().equalsIgnoreCase(username))
				{
					System.out.println("Duh! you cannot fight with yourself.Please choose a different option");
					continue;
				}
				player[1]=new Player();
				player[1]=player[1].createPlayer(player[1],username);
				PokemonFight.battle(player);
			}
			else if(option==2)
				player[0].capture();
			else if(option==3)
				player[0].visitPokeStop();
			else
			{
				
				System.out.println("Hmm!! looks like you are tired of playing.. Come back soon!!");       						
			}
		}
		DB db=new DB();
		db.insertOrUpdatePlayer(player[0]);
	}
}


