public class Grass extends Plant
{
	Grass(World world, Point position)
	{
		super(world, position, 0);
		species = "Grass";
	}

	@Override
	protected void createNew(int i, Point position)
	{
		world.organisms[i] = new Grass(world, position);
	}

	@Override
	public void draw()
	{
		world.drawSingle(position, "plants/grass.png", "grass");
	}
}
