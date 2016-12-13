package Pokemon_FP;

public interface LeveledObject {
	//int[] XPNeededForNextLevel={0,100,200,300,400,500,600,700,800,900,Integer.MAX_VALUE};
	void gainExperience(int amount);
	void levelUP();
	boolean enoughXPForNextLevel();
}
