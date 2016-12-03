package Pokemon_FP;
import java.sql.*;
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
	public void insertPlayer(Player p) throws SQLException
	{  
		//insert player in DB
	    //String insert = "insert into Question(subcategory,question_text,question_link,popularity,answer,answered_by,confidence_votes) values(?,?,?,?,?,?,?)";  
	    //PreparedStatement ps = conn.prepareStatement(insert);  
	    //        ps.setString(7,q.get(i).getConfidenceVotes(); 
		              
	      //  ps.addBatch();  
	      
	    //ps.executeBatch(); 
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
		}
		
		return pm;
	}
	
	public Player getPlayerDetails(String name)
	{
		Player p=new Player();
		//refer getPokemonDetails
		return p;
		
	}
	public boolean isExistingPlayer(String name)
	{
		//if the player name exists already in the table return true else return false
	  return false;
	}
	@Override
	protected void finalize() throws Throwable {
		if (conn != null && !conn.isClosed()) {
			conn.close();
			 stmt.close();
		}
	}
 
}
