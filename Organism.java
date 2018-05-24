import java.util.Random;
import java.io.*;

public abstract class Organism
{
	public String species;
	protected int power;
	protected int age;
	protected int initiative;
	protected Point position;
	protected World world;

	Organism(World world, Point position, int power, int initiative)
	{
		this.world = world;
		this.position = position;
		this.power = power;
		this.initiative = initiative;
	}

	abstract void createNew(int i, Point position);
	abstract void action();
	abstract void draw();
	abstract void collision(Organism other);

	public void wrapPosition(Point position)
	{
		if (position.x > world.getHeight() - 1)
			position.x -= world.getHeight();
		if (position.y > world.getWidth() - 1)
			position.y -= world.getWidth();
		if (position.x < 0)
			position.x += world.getHeight();
		if (position.y < 0)
			position.y += world.getWidth();
	}

	public void setPower(int n)
	{
		power = n;
	}

	public void setAge(int n)
	{
		age = n;
	}

	public void setPosition(Point dest)
	{
		position = dest;
	}

	public void getOlder()
	{
		++age;
	}

	public Point getPosition()
	{
		return position;
	}

	public int getInitiative()
	{
		return initiative;
	}

	public int getAge()
	{
		return age;
	}

	public int getPower()
	{
		return power;
	}

	public String getName()
	{
		return species;
	}

	public void fight(Organism other)
	{
		try
		{
			world.addToLog(species + " and " + other.species + " are fighting!");
			if (power > other.getPower())
			{
				world.addToLog(species + " has won!");
				other.die();
			}
			else
			{
				world.addToLog(other.species + " has won!");
				die();
			}
		}
		catch (IOException ex)
		{
			System.err.println("Caught IOException: " + ex.getMessage());
		}
	}
	
	public Point randomStep()
	{
		Random rand = new Random();
		int randomDirection = rand.nextInt(4);
		int dx = 0;
		int dy = 0;

		switch (randomDirection)
		{
			case 0:
				dx = 1;
				break;
			case 1:
				dx = -1;
				break;
			case 2:
				dy = 1;
				break;
			case 3:
				dy = -1;
				break;
		}
		Point destination = new Point(position.x + dx, position.y + dy);
		wrapPosition(destination);

		return destination;
	}


	public boolean reproduce()
	{
		int d[][] = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
		for (int i = 0; i < 4; ++i)
		{
			Point temp = new Point(position.x + d[i][0], position.y + d[i][1]);
			wrapPosition(temp);

			if (world.getOrganism(temp) == null)
			{
				createNew(world.getFree(), temp);
				try
				{
					world.addToLog(species + " has reproduced!");
				}
				catch (IOException ex)
				{
					System.err.println("Caught IOException: " + ex.getMessage());
				}
				return true;
			}
		}
		return false;
	}

	public void die()
	{
		world.removeOrganism(position);
	}
}
