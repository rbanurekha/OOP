package Pokemon_FP;

public class EvolvablePokemon extends Pokemon
{
	EvolvablePokemon pokemonAfterEvolution;
	public EvolvablePokemon()
	{
		
	}
	public EvolvablePokemon(String name, int combatPower, int hitPoint,
			String type, Ability ability,EvolvablePokemon pokemonAfterEvolution)
	{
		super(name, combatPower, hitPoint, type, ability);
		this.pokemonAfterEvolution = pokemonAfterEvolution;
	}

	public void evolve()
	{
    	this.setName(this.pokemonAfterEvolution.getName());
		this.setCombatPower(this.pokemonAfterEvolution.getCombatPower());
		this.setType(this.getType());
		this.setAbility(this.getAbility());
	}
}