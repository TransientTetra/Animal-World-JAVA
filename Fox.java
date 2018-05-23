public class Fox extends Animal
{
	Fox(World world, Point position)
	{
		super(world, position, 3, 7);
		species = "Fox";
	}

	@Override
	public void action()
	{
		getOlder();
		Point destination = randomStep();
		if (world.getOrganism(destination) != null)
		{
			if (getClass().equals(world.getOrganism(destination).getClass()) || world.getOrganism(destination).getPower() < power)
				world.getOrganism(destination).collision(this);
		}
		else
			position = destination;
	}

	@Override
	protected void createNew(int i, Point position)
	{
		world.organisms[i] = new Fox(world, position);
	}

	@Override
	public void draw()
	{
		world.drawSingle(position, "animals/fox.png", "fox");
	}
}
