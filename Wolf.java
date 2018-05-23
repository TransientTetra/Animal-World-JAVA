public class Wolf extends Animal
{
	Wolf(World world, Point position)
	{
		super(world, position, 9, 5);
		species = "Wolf";
	}

	@Override
	protected void createNew(int i, Point position)
	{
		world.organisms[i] = new Wolf(world, position);
	}

	@Override
	public void draw()
	{
		world.drawSingle(position, "animals/wolf.png", "wolf");
	}
}
