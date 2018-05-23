abstract public class Animal extends Organism
{

	Animal(World world, Point position, int power, int initiative)
	{
		super(world, position, power, initiative);
	}

	protected boolean escape()
	{
		int d[][] = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

		for (int i = 0; i < 4; ++i)
		{
			Point temp = new Point(position.x + d[i][0], position.y + d[i][1]);

			wrapPosition(temp);

			if (world.getOrganism(temp) == null)
			{
				position = temp;
				return true;
			}
		}
		return false;
	}

	@Override
	public void action()
	{
		getOlder();

		Point destination = randomStep();

		if (world.getOrganism(destination) != null)
		{
			world.getOrganism(destination).collision(this);
		}
		else
			position = destination;
	}

	@Override
	public void collision(Organism other)
	{
		if (other.getClass().equals(this.getClass()))
		{
			if (this.getAge() > 18 && other.getAge() > 18)
				if (!reproduce())
					other.reproduce();
		}
		else
		{
			fight(other);
		}
	}
}
