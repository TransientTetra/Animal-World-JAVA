import java.util.Random;

public class Turtle extends Animal
{
	Turtle(World world, Point position)
	{
		super(world, position, 2, 1);
		species = "Turtle";
	}

	@Override
	public void action()
	{
		Random rand = new Random();
		int chance = rand.nextInt(4);
		if (chance == 0)
			super.action();
		else
			getOlder();
	}

	@Override
	public void collision(Organism other)
	{
		if (other.getPower() >= 5 || getClass().equals(other.getClass()))
			super.collision(other);
		else
		{
			Point temp = new Point(2 * other.getPosition().x - position.x, 2 * other.getPosition().y - position.y);

			wrapPosition(temp);

			other.setPosition(temp);
		}
	}

	@Override
	protected void createNew(int i, Point position)
	{
		world.organisms[i] = new Turtle(world, position);
	}

	@Override
	public void draw()
	{
		world.drawSingle(position, "animals/turtle.png", "turtle");
	}
}
