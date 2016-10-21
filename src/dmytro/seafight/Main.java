package dmytro.seafight;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;


public class Main
{

	public static void main(final String[] args) throws IOException
	{
		final Battlefield userBattlefield = new Battlefield();
		userBattlefield.print();
		addUserShips(userBattlefield);

		final Battlefield computerBattlefield = new Battlefield();
		addComputerShips(computerBattlefield);

		final Battlefield computerBattlefieldVisible = new Battlefield();

		while (userBattlefield.hasShipsRemained() && computerBattlefield.hasShipsRemained())
		{
			playerTurn(computerBattlefieldVisible, computerBattlefield);
			computerTurn(userBattlefield);
		}

		if (!userBattlefield.hasShipsRemained() && !computerBattlefield.hasShipsRemained())
		{
			System.out.println("No winners!!! You was as effective as the computer.");
		}
		else if (!userBattlefield.hasShipsRemained())
		{
			System.out.println("You loose");
			System.out.println("Let's see where computer has placed his ships.");
			computerBattlefield.print();
		}
		else if (!computerBattlefield.hasShipsRemained())
		{
			System.out.println("You win!");
		}


	}

	private static void computerTurn(final Battlefield userBattlefield)
	{
		final Random random = new Random();

		boolean hit = true;
		while (hit)
		{
			final int horisontal = random.nextInt(userBattlefield.width());
			final int vertical = random.nextInt(userBattlefield.height());

			try
			{
				hit = userBattlefield.markHit(horisontal, vertical);
				System.out.println("Computer attacked your field:");
				userBattlefield.print();
			}
			catch (final RuntimeException e)
			{
				continue;
			}
		}


	}

	private static void playerTurn(final Battlefield computerBattlefieldVisible, final Battlefield computerBattlefield)
	{

		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		boolean hit = true;
		while (hit)
		{
			try
			{
				System.out.println("Enter coordinates of the point where you want to hit. ");
				System.out.print("Horisontal: ");
				final int horisontal = Integer.parseInt(reader.readLine());
				System.out.print("Vertical: ");
				final int vertical = Integer.parseInt(reader.readLine());
				try
				{
					hit = computerBattlefield.markHit(horisontal, vertical);
					System.out.println("The result of your hit:");
					computerBattlefieldVisible.copyVisible(computerBattlefield);
					computerBattlefieldVisible.print();
				}
				catch (final RuntimeException e)
				{
					System.out.println(e.getMessage());
					continue;
				}
			}
			catch (final Exception e)
			{
				System.out.println(e.getMessage());
				continue;
			}

		}

	}

	private static void addUserShips(final Battlefield userBattlefield) throws NumberFormatException, IOException
	{
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (userBattlefield.canAddSuperBigShip())
		{
			System.out.println("Enter coordinates for the a super big ship(size 4): ");

			System.out.println("Start point:");
			System.out.print("Horisontal: ");
			final int h1 = Integer.parseInt(reader.readLine());
			System.out.print("Vertical: ");
			final int v1 = Integer.parseInt(reader.readLine());

			System.out.println("End point:");
			System.out.print("Horisontal: ");
			final int h2 = Integer.parseInt(reader.readLine());
			System.out.print("Vertical: ");
			final int v2 = Integer.parseInt(reader.readLine());

			try
			{
				userBattlefield.addSuperBigShip(new Point(h1, v1), new Point(h2, v2));
			}
			catch (final RuntimeException e)
			{
				System.out.println(e.getMessage());
				continue;
			}
			userBattlefield.print();
		}

		while (userBattlefield.canAddBigShip())
		{
			System.out.println("Enter coordinates for the a big ship(size 3): ");

			System.out.println("Start point:");
			System.out.print("Horisontal: ");
			final int h1 = Integer.parseInt(reader.readLine());
			System.out.print("Vertical: ");
			final int v1 = Integer.parseInt(reader.readLine());

			System.out.println("End point:");
			System.out.print("Horisontal: ");
			final int h2 = Integer.parseInt(reader.readLine());
			System.out.print("Vertical: ");
			final int v2 = Integer.parseInt(reader.readLine());

			try
			{
				userBattlefield.addBigShip(new Point(h1, v1), new Point(h2, v2));
			}
			catch (final RuntimeException e)
			{
				System.out.println(e.getMessage());
				continue;
			}
			userBattlefield.print();
		}

		while (userBattlefield.canAddMediumShip())
		{
			System.out.println("Enter coordinates for the a medium ship(size 2): ");

			System.out.println("Start point:");
			System.out.print("Horisontal: ");
			final int h1 = Integer.parseInt(reader.readLine());
			System.out.print("Vertical: ");
			final int v1 = Integer.parseInt(reader.readLine());

			System.out.println("End point:");
			System.out.print("Horisontal: ");
			final int h2 = Integer.parseInt(reader.readLine());
			System.out.print("Vertical: ");
			final int v2 = Integer.parseInt(reader.readLine());

			try
			{
				userBattlefield.addMediumShip(new Point(h1, v1), new Point(h2, v2));
			}
			catch (final RuntimeException e)
			{
				System.out.println(e.getMessage());
				continue;
			}
			userBattlefield.print();
		}

		while (userBattlefield.canAddSmallShip())
		{
			System.out.println("Enter coordinates for the a small ship: ");
			System.out.print("Horisontal: ");
			final int h = Integer.parseInt(reader.readLine());
			System.out.print("Vertical: ");
			final int v = Integer.parseInt(reader.readLine());

			try
			{
				userBattlefield.addSmallShip(new Point(h, v));
			}
			catch (final RuntimeException e)
			{
				System.out.println(e.getMessage());
				continue;
			}
			userBattlefield.print();
		}
	}

	private static void addComputerShips(final Battlefield battlefield)
	{
		final Random random = new Random();

		while (battlefield.canAddSuperBigShip())
		{
			final int h1 = random.nextInt(battlefield.width());
			final int v1 = random.nextInt(battlefield.height());

			final int h2 = random.nextInt(battlefield.width());
			final int v2 = random.nextInt(battlefield.height());

			try
			{
				battlefield.addSuperBigShip(new Point(h1, v1), new Point(h2, v2));
			}
			catch (final RuntimeException e)
			{
				continue;
			}
		}

		while (battlefield.canAddBigShip())
		{
			final int h1 = random.nextInt(battlefield.width());
			final int v1 = random.nextInt(battlefield.height());

			final int h2 = random.nextInt(battlefield.width());
			final int v2 = random.nextInt(battlefield.height());

			try
			{
				battlefield.addBigShip(new Point(h1, v1), new Point(h2, v2));
			}
			catch (final RuntimeException e)
			{
				continue;
			}
		}

		while (battlefield.canAddMediumShip())
		{
			final int h1 = random.nextInt(battlefield.width());
			final int v1 = random.nextInt(battlefield.height());

			final int h2 = random.nextInt(battlefield.width());
			final int v2 = random.nextInt(battlefield.height());

			try
			{
				battlefield.addMediumShip(new Point(h1, v1), new Point(h2, v2));
			}
			catch (final RuntimeException e)
			{
				continue;
			}
		}

		while (battlefield.canAddSmallShip())
		{
			final int h1 = random.nextInt(battlefield.width());
			final int v1 = random.nextInt(battlefield.height());

			try
			{
				battlefield.addSmallShip(new Point(h1, v1));
			}
			catch (final RuntimeException e)
			{
				continue;
			}
		}
	}
}
