public class Sheep extends Animal
{
	Sheep(World world, Point position)
	{
		super(world, position, 4, 4);
		species = "Sheep";
	}

	@Override
	protected void createNew(int i, Point position)
	{
		world.organisms[i] = new Sheep(world, position);
	}

	@Override
	public void draw()
	{
		world.drawSingle(position, "animals/sheep.png", "sheep");
	}
}
