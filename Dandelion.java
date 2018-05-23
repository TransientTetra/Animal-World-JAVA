public class Dandelion extends Plant
{
	Dandelion(World world, Point position)
	{
		super(world, position, 0);
		species = "Dandelion";
	}

	@Override
	public void action()
	{
		super.action();
		super.action();
		--age;
		super.action();
		--age;
	}

	@Override
	protected void createNew(int i, Point position)
	{
		world.organisms[i] = new Dandelion(world, position);
	}

	@Override
	public void draw()
	{
		world.drawSingle(position, "plants/dandelion.png", "dandelion");
	}
}
