public class Application
{
	public static void main(String[] args)
	{
		int width = 20;
		int height = 20;

		World world = new World(width, height);

		world.simulate();
	}
}
