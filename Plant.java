import java.util.Random;
import java.io.*;

abstract public class Plant extends Organism
{
	Plant(World world, Point position, int power)
	{
		super(world, position, power, 0);
	}

	public void action()
	{
		getOlder();
		Random rand = new Random();
		int chance = rand.nextInt(100);

		if (age >= 10 && chance == 0)
			reproduce();
	}

	@Override
	public void collision(Organism other)
	{
		fight(other);
	}

	@Override
	public void fight(Organism other)
	{
		try
		{
			world.addToLog(other.species + " is trying to eat a " + species + '!');
			if (power > other.getPower())
			{
				world.addToLog(other.species + " has been poisoned and died!");
				other.die();
			}
			else
			{
				world.addToLog(other.species + " has eaten " + species + '!');
				die();
			}
		}
		catch (IOException ex)
		{
			System.err.println("Caught IOException: " + ex.getMessage());
		}
	}
}
