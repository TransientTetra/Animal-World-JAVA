import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Scanner;
import java.util.Random;

public class World
{
	private JFrame window;
	private JFrame ui;
	private int width;
	private int height;
	public Organism organisms[];

	World(int width, int height)
	{
		this.width = width;
		this.height = height;

		allocOrganisms();
		randSpawn();

		window = new JFrame("Mikołaj Sperkowski 171725");

		window.setDefaultCloseOperation(3);
		window.setSize(650, 670);
		window.setLayout(null);
		window.setVisible(true);

		ui = new JFrame("Animal World");
		
		JButton exit = new JButton("EXIT");
		JButton save = new JButton("SAVE TO FILE");
		JButton load = new JButton("LOAD FROM FILE");
		JButton power = new JButton("SUPERPOWER");
		JButton up = new JButton("UP");
		JButton next = new JButton("NEXT");
		JButton left = new JButton("LEFT");
		JButton down = new JButton("DOWN");
		JButton right = new JButton("RIGHT");

		exit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});

		ui.add(exit);
		ui.add(save);
		ui.add(load);
		ui.add(power);
		ui.add(up);
		ui.add(next);
		ui.add(left);
		ui.add(down);
		ui.add(right);

		ui.setDefaultCloseOperation(3);
		ui.setSize(500, 300);
		ui.setLayout(new GridLayout(0, 3, 2, 2));
		ui.setVisible(true);
	}

	private void allocOrganisms()
	{
		organisms = new Organism[width * height];
	}

	private void sortOrganisms()
	{
		for (int i = 0; i < width * height - 1; ++i)
		{
			if (organisms[i] == null && organisms[i + 1] != null)
			{
				organisms[i] = organisms[i + 1];
				organisms[i + 1] = null;
				i = 0;
			}
		}
		boolean chckSort = true;
		while (chckSort)
		{
			chckSort = false;
			for (int i = 0; i < width * height - 1; ++i)
			{
				if (organisms[i] != null && organisms[i + 1] != null)
				{
					boolean swap = false;
					if (organisms[i].getInitiative() < organisms[i + 1].getInitiative())
						swap = true;
					else if (organisms[i].getInitiative() == organisms[i + 1].getInitiative())
						if (organisms[i].getAge() < organisms[i + 1].getAge())
							swap = true;
					if (swap)
					{
						chckSort = true;

						Organism temp = organisms[i];
						organisms[i] = organisms[i + 1];
						organisms[i + 1] = temp;
					}
				}
			}
		}
	}

	private void randSpawn()
	{
		int index = 0;
		boolean isHuman = false;
		for (int i = 0; i < height; ++i)
		{
			for (int j = 0; j < width; ++j)
			{
				Random rand = new Random();
				int chance = rand.nextInt(100);
				if (chance < 15)
				{
					Random r = new Random();
					int uni = r.nextInt(10);
					switch (uni)
					{
						case 0:
							organisms[index] = new Grass(this, new Point(i, j));
							break;
						case 1:
							organisms[index] = new Dandelion(this, new Point(i, j));
							break;
						case 2:
							organisms[index] = new Guarana(this, new Point(i, j));
							break;
						case 3:
							organisms[index] = new Belladonna(this, new Point(i, j));
							break;
						case 4:
							organisms[index] = new Hogweed(this, new Point(i, j));
							break;
						case 5:
							organisms[index] = new Wolf(this, new Point(i, j));
							break;
						case 6:
							organisms[index] = new Sheep(this, new Point(i, j));
							break;
						case 7:
							organisms[index] = new Fox(this, new Point(i, j));
							break;
						case 8:
							organisms[index] = new Turtle(this, new Point(i, j));
							break;
						case 9:
							organisms[index] = new Antelope(this, new Point(i, j));
							break;
					}
					++index;
				}
				else if (!isHuman)
				{
					organisms[index] = new Human(this, new Point(i, j));
					isHuman = true;
					++index;
				}
			}
		}
	}

	private void drawWorld(int turnNumber)
	{
		window.getContentPane().removeAll();
		for (int i = 0; i < width * height; ++i)
		{
			if (organisms[i] != null)
			{
				organisms[i].draw();
			}
		}
		for (int i = 0; i < width; ++i)
		{
			for (int j = 0; j < height; ++j)
			{
				drawSingle(new Point(i, j), "./base.png", "blank");
			}
		}
		window.revalidate();
		window.repaint();
	}

	private char getInput()
	{
		return 'i';
	}

	public void drawSingle(Point position, String path, String description)
	{
		java.net.URL imgURL = getClass().getResource(path);
		ImageIcon icon = new ImageIcon(imgURL, description);
		JLabel label = new JLabel(icon);
		label.setBounds(position.x * 32, position.y * 32, 32, 32);
		window.add(label);
	}

	public void simulate()
	{
		int turnNumber = 0;
		int powerCounter = 0;
		drawWorld(turnNumber);
		//try
		//{
			//loadFile();
		//}
		//catch (IOException e)
		//{
			//System.err.println("Caught IOException: " + e.getMessage());
		//}


		while (true)
		{
			char input = getInput();
			if (input == 'i')
				continue;
			if (input == 'q')
				return;
			if (input == 's')
			{
				try
				{
					saveToFile();
				}
				catch (IOException e)
				{
					System.err.println("Caught IOException: " + e.getMessage());
				}
				continue;
			}
			if (input == 'f')
			{
				try
				{
					loadFile();
				}
				catch (IOException e)
				{
					System.err.println("Caught IOException: " + e.getMessage());
				}
				turnNumber = 0;
				powerCounter = 0;
				drawWorld(turnNumber);
				continue;
			}
			if (input == 'p' && powerCounter > 0)
				input = 'n';
			if (input == 'p' && powerCounter == 0)
			{
				powerCounter = 11;
			}

			sortOrganisms();
			for (int i = 0; i < width * height; ++i)
			{
				if (organisms[i] != null)
				{
					if (organisms[i] instanceof Human)
					{
						Human h = (Human)organisms[i];
						h.toDo(input);
						if (powerCounter == 5)
							h.setSuperpower(false);
					}
					organisms[i].action();
				}
			}
			if (powerCounter > 0)
				--powerCounter;
			++turnNumber;


			//try        
			//{
			    //Thread.sleep(100);
			//} 
			//catch(InterruptedException ex) 
			//{
			    //Thread.currentThread().interrupt();
			//};
			drawWorld(turnNumber);
		}
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public Organism getOrganism(Point position)
	{
		for (int i = 0; i < width * height; ++i)
		{
			if (organisms[i] != null)
				if (organisms[i].getPosition().x == position.x && organisms[i].getPosition().y == position.y)
					return organisms[i];
		}
		return null;
	}

	public int getFree()
	{
		for (int i = 0; i < width * height; ++i)
		{
			if (organisms[i] == null)
				return i;
		}
		return -1;
	}

	public void removeOrganism(Point position)
	{
		for (int i = 0; i < width * height; ++i)
			if (organisms[i] != null)
				if (organisms[i].position == position)
					organisms[i] = null;
		sortOrganisms();
	}

	public void saveToFile() throws IOException
	{
		FileWriter out = null;
		out = new FileWriter("savefile.wsf");

		for (int i = 0; i < width * height; ++i)
		{
			if (organisms[i] != null)
			{
				String temp = new String(organisms[i].species + ' ');
				temp += organisms[i].getPower();
				temp += ' ';
				temp += organisms[i].getAge();
				temp += ' ';
				temp += organisms[i].getPosition().x;
				temp += ' ';
				temp += organisms[i].getPosition().y;
				temp += ' ';
				out.write(temp);
			}
		}
		out.write("END");

		if (out != null)
			out.close();
	}

	public void loadFile() throws IOException
	{
		organisms = null;
		allocOrganisms();
		Scanner scanner = new Scanner(new File("savefile.wsf"));
		int index = 0;
		while (true)
		{
			String type = scanner.next();
			if (type.equals("END")) break;

			int power = Integer.parseInt(scanner.next());
			int age = Integer.parseInt(scanner.next());
			int x = Integer.parseInt(scanner.next());
			int y = Integer.parseInt(scanner.next());
			Point position = new Point(x, y);

			if (type.equals("Grass"))
				organisms[index] = new Grass(this, position);
			else if (type.equals("Dandelion"))
				organisms[index] = new Dandelion(this, position);
			else if (type.equals("Guarana"))
				organisms[index] = new Guarana(this, position);
			else if (type.equals("Belladonna"))
				organisms[index] = new Belladonna(this, position);
			else if (type.equals("Hogweed"))
				organisms[index] = new Hogweed(this, position);
			else if (type.equals("Wolf"))
				organisms[index] = new Wolf(this, position);
			else if (type.equals("Sheep"))
				organisms[index] = new Sheep(this, position);
			else if (type.equals("Fox"))
				organisms[index] = new Fox(this, position);
			else if (type.equals("Turtle"))
				organisms[index] = new Turtle(this, position);
			else if (type.equals("Antelope"))
				organisms[index] = new Antelope(this, position);
			else if (type.equals("Human"))
				organisms[index] = new Human(this, position);
				
			if (organisms[index] == null)
			{
				System.err.println(type);
				System.err.println(type.length());
				break;
			}
			organisms[index].setPower(power);
			organisms[index].setAge(age);
			++index;
		}

		if (scanner != null)
			scanner.close();
	}
}
