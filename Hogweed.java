public class Hogweed extends Plant
{
	Hogweed(World world, Point position)
	{
		super(world, position, 10);
		species = "Hogweed";
	}

	@Override
	public void action()
	{
		super.action();

		int d[][] = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
		for (int i = 0; i < 4; ++i)
		{
			Point temp = new Point(position.x + d[i][0], position.y + d[i][1]);
			wrapPosition(temp);
			if (world.getOrganism(temp) != null)
			{
				if (world.getOrganism(temp) instanceof Animal)
				{
					world.getOrganism(temp).die();
				}
			}
		}
	}

	@Override
	public void fight(Organism other)
	{
		if (other.getPower() >= power)
			die();
		other.die();
	}

	@Override
	protected void createNew(int i, Point position)
	{
		world.organisms[i] = new Hogweed(world, position);
	}

	@Override
	public void draw()
	{
		world.drawSingle(position, "plants/hogweed.png", "hogweed");
	}
}
