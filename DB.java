package Pokemon_FP;
import java.sql.*;
import java.util.ArrayList;
public class DB
{
	private Connection conn = null;
	private static final String DB_URL = "jdbc:mysql://localhost/banu?autoReconnect=true&useSSL=false";
	private static final String USER = "root";
	private static final String PASS = "Java2016";
	Statement stmt; 

	public DB()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertPlayerPokemon(EvolvablePokemon poke,String playerName) throws SQLException
	{
		
		 String insert = "insert into PlayerOwnedPokemon(pokeID,playerName,combatPower,HP) values(?,?,?,?)";  
		    PreparedStatement ps = conn.prepareStatement(insert);  
		    ps.setInt(1,getPokemonID(poke.getName()));
		   ps.setString(2, playerName);
		   ps.setInt(3, poke.getCombatPower());
		   ps.setInt(4,poke.getHitPoint());
		   ps.addBatch();  
		    ps.executeBatch();
		    ps.close();
	}
 
	public void listPotions() throws SQLException
	{  
		String sql = "SELECT * FROM Potion";
	      ResultSet rs = stmt.executeQuery(sql);      
	      int j=1;
	      while (rs.next() && j++<4) {
	              System.out.print(j-1+"."+rs.getString(2) + " potion");
	          System.out.print("\t");
	      }
	      rs.close();
    }
	public Potion getPotionDetails(int PotionID) throws SQLException
	{
		Potion pn=null;
		int count=1;
		String sql = "SELECT * FROM Potion where ID="+PotionID;
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
		{
		String type = rs.getString("POTIONTYPE");
		int healingPower=Integer.parseInt(rs.getString("HEALINGPOWER"));
		pn=new Potion(type,healingPower,count);
		}
		rs.close();
		return pn;
	}
	
	public void listPokeballs() throws SQLException
	{  
		String sql = "SELECT * FROM Pokeball";
	      ResultSet rs = stmt.executeQuery(sql);      
	      int j=1;
	      while (rs.next() && j++<4) {
	              System.out.print(j-1+"."+rs.getString(2) + " ball");
	          System.out.print("\t");
	      }
	      rs.close();
    }
	public Pokeball getPokeballDetails(int PokeballID) throws SQLException
	{
		Pokeball pl=null;
		int count=1;
		String sql = "SELECT * FROM Pokeball where ID="+PokeballID;
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
		{
		String type = rs.getString("BALLTYPE");
		double probability=Integer.parseInt(rs.getString("PROBABILITY"));
		pl=new Pokeball(type,probability,count);
		}
		return pl;
	}
	
	public int getPokemonID(String PokemonName) throws SQLException
	{
		int pokeID=1;
		String sql = "SELECT * FROM Pokemon where pokename=\""+PokemonName+"\"";
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
		pokeID = rs.getInt("PokeID");	
		return pokeID;
	}
	public void listPokemons() throws SQLException
	{  
		String sql = "SELECT * FROM Pokemon";
	      ResultSet rs = stmt.executeQuery(sql);      
	      int j=1;
	      while (rs.next() && j++<11) {
	          for(int i = 2; i < 4; i++)//print 2nd and 3rd column
	              System.out.print(rs.getString(i) + ".");
	          System.out.print("\t");
	      }
	      rs.close();
    } 

	public EvolvablePokemon getPokemonDetails(int PokemonID) throws SQLException
	{
		EvolvablePokemon pm=null;
		EvolvablePokemon epm=null;
		int ePokeid=0;
		String sql = "SELECT * FROM Pokemon where PokeID="+PokemonID;
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
		{
		String name = rs.getString("POKENAME");
		int maxcp=Integer.parseInt(rs.getString("Max_CP"));
		int maxhp=Integer.parseInt(rs.getString("Max_HP"));
		String type=rs.getString("PokeType");
		String move1=rs.getString("move1");
		String move2=rs.getString("move2");
		Ability ab=new Ability(move1,move2);
		ePokeid=Integer.parseInt(rs.getString("Evolve_To"));
		pm=new EvolvablePokemon(name, maxcp, maxhp,type, ab, epm);
		}
		String sql1 = "SELECT * FROM Pokemon where PokeID="+ePokeid;
		ResultSet rs1 = stmt.executeQuery(sql1);
		while (rs1.next())
		{
		String name = rs1.getString("POKENAME");
		int maxcp=Integer.parseInt(rs1.getString("Max_CP"));
		int maxhp=Integer.parseInt(rs1.getString("Max_HP"));
		String type=rs1.getString("PokeType");
		String move1=rs1.getString("move1");
		String move2=rs1.getString("move2");
		Ability ab=new Ability(move1,move2);
		epm=new EvolvablePokemon(name, maxcp, maxhp,type, ab, null);
		pm.pokemonAfterEvolution=epm;
		}		
		return pm;
	}
	
	public Player getPlayerDetails(String name) throws SQLException
	{
		Statement stmt = conn.createStatement();
		Player p=new Player();
		Potion pn=new Potion();
		Pokeball pl=new Pokeball();
		ArrayList<Potion> potionlist=new ArrayList<Potion>();
		ArrayList<Pokeball> pokeballlist=new ArrayList<Pokeball>();
		String sql = "SELECT * FROM Player where  playername=\""+name+"\"";
		ResultSet rs1 = stmt.executeQuery(sql);
		while (rs1.next())
		{
		  p=new Player(name,rs1.getInt("experience"),rs1.getInt("level"),null,null,null,null,rs1.getInt("battleswon"),rs1.getInt("rewardpoints"));
			if(rs1.getInt("regularPotion")>0)
			{
				pn=getPotionDetails(1);
				pn.setPotionCount(rs1.getInt("regularPotion"));
				potionlist.add(new Potion());
				potionlist.set(potionlist.size()-1, pn);
			}
			if(rs1.getInt("superPotion")>0)
			{
				pn=getPotionDetails(2);
				pn.setPotionCount(rs1.getInt("superPotion"));
				potionlist.add(new Potion());
				potionlist.set(potionlist.size()-1, pn);
			}
			if(rs1.getInt("hyperPotion")>0)
			{
				pn=getPotionDetails(3);
				pn.setPotionCount(rs1.getInt("hyperPotion"));
				potionlist.add(new Potion());
				potionlist.set(potionlist.size()-1, pn);
			}
			if(rs1.getInt("regularball")>0)
			{
				pl=getPokeballDetails(1);
				pl.setPokeballCount(rs1.getInt("regularball"));
				pokeballlist.add(new Pokeball());
				pokeballlist.set(pokeballlist.size()-1, pl);
			}
			if(rs1.getInt("greatball")>0)
			{
				pl=getPokeballDetails(2);
				pl.setPokeballCount(rs1.getInt("greatball"));
				pokeballlist.add(new Pokeball());
				pokeballlist.set(pokeballlist.size()-1, pl);
			}
			if(rs1.getInt("ultraball")>0)
			{
				pl=getPokeballDetails(3);
				pl.setPokeballCount(rs1.getInt("ultraball"));
				pokeballlist.add(new Pokeball());
				pokeballlist.set(pokeballlist.size()-1, pl);
			}
			p.setPotionsLeft(potionlist);
			p.setPokeballsLeft(pokeballlist);
			p.setPokemonsLeft(getPlayerPokemonDetails(p.getPlayerName()));
		}
		rs1.close();
		return p;
	}
	public ArrayList<EvolvablePokemon> getPlayerPokemonDetails(String playerName) throws SQLException
	{
		EvolvablePokemon ep=new EvolvablePokemon();
		ArrayList<EvolvablePokemon> pokemonlist=new ArrayList<EvolvablePokemon>();
		Statement stmt = conn.createStatement();
		String sql = "SELECT * FROM PlayerownedPokemon where  playername=\""+playerName+"\"";
		ResultSet rs1 = stmt.executeQuery(sql);
		while (rs1.next())
		{
		 ep=getPokemonDetails(rs1.getInt("pokeID"));
		 ep.setCombatPower(rs1.getInt("combatpower"));
		 ep.setHitPoint(rs1.getInt("hp"));
		 pokemonlist.add(new EvolvablePokemon());
		 pokemonlist.set(pokemonlist.size()-1, ep);
		}
		rs1.close();
		return pokemonlist;
	}
	public boolean isExistingPlayer(String name) throws SQLException
	{
		int count=0;
		String sql = "SELECT count(*) FROM Player where playername=\""+name+"\"";
	      ResultSet rs = stmt.executeQuery(sql);      
	    while(rs.next())
		 count = rs.getInt("count(*)");
	    if(count>0)
	    	return true;
	    else
	    	return false;
	}

	
	public void insertOrUpdatePlayer(Player p) throws SQLException {

		int regBallCount = 0;
		int superBallCount = 0;
		int ultraBallCount = 0;
		int regPotionCount = 0;
		int superPotionCount = 0;
		int hyperPotionCount = 0;

		ArrayList<Pokeball> ballsLeft = p.getPokeballsLeft();
		ArrayList<Potion> potionsLeft = p.getPotionsLeft();

		for (int i = 0; i < ballsLeft.size(); i++) {

			Pokeball ball = ballsLeft.get(i);

			if (ball.isUltraBall()) {
				ultraBallCount=ball.pokeballCount;
			} else if (ball.isGreatBall()) {
				superBallCount=ball.pokeballCount;
			} else {
				regBallCount=ball.pokeballCount;
			}
		}

		for (int j = 0; j < potionsLeft.size(); j++) {

			Potion potion = potionsLeft.get(j);

			
			if (potion.isHyperPotion()) {
				hyperPotionCount=potion.getPotionCount();
			} else if (potion.isSuperPotion()) {
				superPotionCount=potion.getPotionCount();
			} else {
				regPotionCount=potion.getPotionCount();
			}
		}
		
				
		String sql;

		if(isExistingPlayer(p.getPlayerName())) {
			sql  = "update player set level=?,experience=?,battleswon=?,regularball=?,greatball=?,ultraball=?,regularpotion=?,superpotion=?,hyperpotion=?,rewardpoints=? where  playername=\""+p.getPlayerName()+"\"";
		}
		else {
			sql = "insert into player(Level,experience,battleswon,regularball,greatball,ultraball,regularpotion,superpotion,Hyperpotion,Rewardpoints,playerName) values(?,?,?,?,?,?,?,?,?,?,?)";			
		}
		// insert player in DB
		
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, p.getLevel());
		ps.setInt(2, p.getExperience());
		ps.setInt(3, p.getBattlesWon());
		ps.setInt(4, regBallCount);
		ps.setInt(5, superBallCount);
		ps.setInt(6, ultraBallCount);
		ps.setInt(7, regPotionCount);
		ps.setInt(8, superPotionCount);
		ps.setInt(9, hyperPotionCount);
		ps.setInt(10, p.getRewards());
		
		if(!isExistingPlayer(p.getPlayerName())) {
			ps.setString(11, p.getPlayerName());
		}
		
		ps.addBatch();
		ps.executeBatch();

		String sql1 = "DELETE FROM PlayerOwnedPokemon where playerName=\""+p.getPlayerName()+"\"";
		PreparedStatement ps1 = conn.prepareStatement(sql1);
		ps1.execute();
		ps1.close();
		for (EvolvablePokemon pm : p.getPokemonsLeft()) {
					insertPlayerPokemon(pm, p.getPlayerName());				
		}
		
	}
	


	
	@Override
	protected void finalize() throws Throwable {
		if (conn != null && !conn.isClosed()) {
			conn.close();
			 stmt.close();
		}
	}
 
}
