import java.util.Random;

public class Antelope extends Animal
{
	Antelope(World world, Point position)
	{
		super(world, position, 4, 4);
		species = "Antelope";
	}

	@Override
	public void action()
	{
		getOlder();

		Point destination = randomStep();
		destination.x += destination.x - position.x;
		destination.y += destination.y - position.y;
		wrapPosition(destination);
		if (world.getOrganism(destination) != null)
			world.getOrganism(destination).collision(this);
		else
			position = destination;
	}

	@Override
	public void fight(Organism other)
	{
		Random rand = new Random();
		int chance = rand.nextInt(2);
		if (chance == 0 && escape());
		else
			fight(other);
	}

	@Override
	protected void createNew(int i, Point position)
	{
		world.organisms[i] = new Antelope(world, position);
	}

	@Override
	public void draw()
	{
		world.drawSingle(position, "animals/antelope.png", "antelope");
	}
}
