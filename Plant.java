import java.util.Random;

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
		if (power > other.getPower())
		{
			other.die();
		}
		else
		{
			die();
		}
	}
}
