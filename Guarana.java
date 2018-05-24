import java.io.*;

public class Guarana extends Plant
{
	Guarana(World world, Point position)
	{
		super(world, position, 0);
		species = "Guarana";
	}

	@Override
	public void fight(Organism other)
	{
	try
	{
		world.addToLog(other.species + " has eaten a guarana and gained +3 to it's power!");
	}
	catch (IOException ex)
	{
		System.err.println("Caught IOException: " + ex.getMessage());
	}
		other.setPower(other.getPower() + 3);
		die();
	}

	@Override
	protected void createNew(int i, Point position)
	{
		world.organisms[i] = new Guarana(world, position);
	}

	@Override
	public void draw()
	{
		world.drawSingle(position, "plants/guarana.png", "guarana");
	}
}
