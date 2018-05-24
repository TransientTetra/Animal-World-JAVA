public class Human extends Animal
{
	private boolean superpower;
	char doing;

	Human(World world, Point position)
	{
		super(world, position, 5, 4);
		superpower = false;
		doing = 'n';
		species = "Human";
	}

	public boolean getSuperpower()
	{
		return superpower;
	}

	public void setSuperpower(boolean p)
	{
		superpower = p;
	}

	public void toDo(char com)
	{
		doing = com;
	}

	@Override
	public void die()
	{
		if (superpower)
		{
			escape();
		}
		else
			super.die();
	}

	@Override
	public void action()
	{
		getOlder();
		int dx = 0;
		int dy = 0;
		switch (doing) 
		{
			case 'l':
				--dx;
				break;
			case 'r':
				++dx;
				break;
			case 'u':
				--dy;
				break;
			case 'd':
				++dy;
				break;
			case 'p':
				superpower = true;
				break;
		}
		if (dx != 0 || dy != 0)
		{
			Point destination = new Point(position.x, position.y);
			destination.x += dx;
			destination.y += dy;

			wrapPosition(destination);
			if (world.getOrganism(destination) != null)
			{
				world.getOrganism(destination).collision(this);
			}
			else
			{
				position = destination;
			}
		}
	}

	@Override
	protected void createNew(int i, Point position)
	{
		world.organisms[i] = new Human(world, position);
	}

	@Override
	public void draw()
	{
		world.drawSingle(position, "animals/human.png", "human");
	}
}
