package dmytro.seafight;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class Battlefield
{

	private static class Settings
	{
		private static final char DEFAULT_SEPARATOR = '|';
		private static final int DEFAULT_CELL_WIDTH = 3;
		private static final int DEFAULT_BATTLEFIELD_WIDTH = 10;
		private static final int DEFAULT_BATTLEFIELD_HEIGHT = DEFAULT_BATTLEFIELD_WIDTH;

		private static final int SMALL_SHIP_LENGTH = 1;
		private static final int MEDIUM_SHIP_LENGTH = 2;
		private static final int BIG_SHIP_LENGTH = 3;
		private static final int SUPER_BIG_SHIP_LENGTH = 4;

		private static final int MAX_SMALL_SHIP_COUNT = 4;
		private static final int MAX_MEDIUM_SHIP_COUNT = 3;
		private static final int MAX_BIG_SHIP_COUNT = 2;
		private static final int MAX_SUPER_BIG_SHIP_COUNT = 1;
	}

	private final Cell[][] battlefieldInternal;
	private final int cellWidth;
	private final char separator;
	private int smallShipsCount = 0;
	private int mediumShipsCount = 0;
	private int bigShipsCount = 0;
	private int superBigShipsCount = 0;





	public Battlefield()
	{
		battlefieldInternal = new Cell[Settings.DEFAULT_BATTLEFIELD_HEIGHT][Settings.DEFAULT_BATTLEFIELD_WIDTH];
		for (final Cell[] row : battlefieldInternal)
		{
			Arrays.fill(row, Cell.Empty);
		}

		cellWidth = Settings.DEFAULT_CELL_WIDTH;
		separator = Settings.DEFAULT_SEPARATOR;
	}

	public int height()
	{
		return battlefieldInternal.length;
	}

	public int width()
	{
		return battlefieldInternal[0].length;
	}


	public void print()
	{
		printHorisontalAxis();
		printSea();
	}

	private void printSea()
	{
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < battlefieldInternal.length; i++)
		{
			String cellValue = String.valueOf(i + 1);
			builder.append(getCell(cellValue, cellWidth));
			builder.append(separator);

			for (int j = 0; j < battlefieldInternal.length; j++)
			{
				cellValue = String.valueOf(battlefieldInternal[i][j]);
				builder.append(getCell(cellValue, cellWidth));
				builder.append(separator);
			}

			builder.append(System.lineSeparator());
		}

		System.out.println(builder.toString());


	}

	private void printHorisontalAxis()
	{
		final StringBuilder builder = new StringBuilder();

		builder.append(getOffset(cellWidth));
		builder.append(separator);

		for (int i = 0; i < battlefieldInternal[0].length; i++)
		{
			final String cellValue = String.valueOf(i + 1);
			builder.append(getCell(cellValue, cellWidth));
			builder.append(separator);

		}

		System.out.println(builder.toString());
	}

	private static String getOffset(final int width)
	{
		return getCell("", width);
	}

	private static String getCell(final String value, final int width)
	{
		if (width < value.length())
		{
			throw new IllegalArgumentException("Cell width = " + width + " is to small for the value '" + value + "'");
		}
		final StringBuilder builder = new StringBuilder();

		final int whitespaceLength = width - value.length();
		for (int i = 0; i < whitespaceLength; i++)
		{
			builder.append(" ");
		}
		builder.append(value);
		return builder.toString();
	}


	public boolean canAddSmallShip()
	{
		return smallShipsCount < Settings.MAX_SMALL_SHIP_COUNT;
	}

	public boolean canAddMediumShip()
	{
		return mediumShipsCount < Settings.MAX_MEDIUM_SHIP_COUNT;
	}

	public boolean canAddBigShip()
	{
		return bigShipsCount < Settings.MAX_BIG_SHIP_COUNT;
	}

	public boolean canAddSuperBigShip()
	{
		return superBigShipsCount < Settings.MAX_SUPER_BIG_SHIP_COUNT;
	}

	public void addSmallShip(final Point start)
	{
		if (smallShipsCount == Settings.MAX_SMALL_SHIP_COUNT)
		{
			throw new IllegalStateException("You have added the maximum number of small ships.");
		}
		addShip(start, start, Settings.SMALL_SHIP_LENGTH);
		smallShipsCount++;
	}


	public void addMediumShip(final Point start, final Point finish)
	{
		if (mediumShipsCount == Settings.MAX_MEDIUM_SHIP_COUNT)
		{
			throw new IllegalStateException("You have added the maximum number of medium ships.");
		}
		addShip(start, finish, Settings.MEDIUM_SHIP_LENGTH);
		mediumShipsCount++;
	}

	public void addBigShip(final Point start, final Point finish)
	{
		if (bigShipsCount == Settings.MAX_BIG_SHIP_COUNT)
		{
			throw new IllegalStateException("You have added the maximum number of big ships.");
		}
		addShip(start, finish, Settings.BIG_SHIP_LENGTH);
		bigShipsCount++;
	}

	public void addSuperBigShip(final Point start, final Point finish)
	{
		if (superBigShipsCount == Settings.MAX_SUPER_BIG_SHIP_COUNT)
		{
			throw new IllegalStateException("You have added the maximum number of super big ships.");
		}
		addShip(start, finish, Settings.SUPER_BIG_SHIP_LENGTH);
		superBigShipsCount++;
	}




	private void addShip(final Point start, final Point finish, final int maxLength)
	{
		if (Math.abs(start.horisontal - finish.horisontal) + 1 != maxLength
				&& Math.abs(start.vertical - finish.vertical) + 1 != maxLength)
		{
			throw new IllegalArgumentException("Please enter a valid coordinates for the ship.");
		}

		if (start.horisontal != finish.horisontal && start.vertical != finish.vertical)
		{
			throw new IllegalArgumentException("Please enter a valid coordinates for the ship.");
		}

		final int[] xPath = getPathLine(start.vertical - 1, finish.vertical - 1);
		final int[] yPath = getPathLine(start.horisontal - 1, finish.horisontal - 1);

		final Set<Point> points = new HashSet<Point>();
		for (final int x : xPath)
		{
			for (final int y : yPath)
			{
				if (!inBorders(x, y))
				{
					throw new IllegalArgumentException("Please enter a valid coordinates");
				}

				if (neighbor(x, y))
				{
					throw new IllegalArgumentException("You are trying to place the ship too close to anothership.");
				}

				points.add(new Point(y, x));
			}
		}

		for (final Point point : points)
		{
			battlefieldInternal[point.vertical][point.horisontal] = Cell.ShipPart;
		}
	}


	private int[] getPathLine(final int a, final int b)
	{
		final int size = Math.abs(a - b) + 1;
		final int[] path = new int[size];

		final int min = a < b ? a : b;
		for (int i = 0; i < size; i++)
		{
			path[i] = min + i;
		}

		return path;

	}


	/*
	 * x - vertical y - horizontal
	 */
	private boolean neighbor(final int x, final int y)
	{
		boolean neighbor = false;
		final int xEdgeStart = x - 1 >= 0 ? x - 1 : 0;
		final int xEdgeEnd = x + 1 < battlefieldInternal.length ? x + 1 : battlefieldInternal.length - 1;

		final int yEdgeStart = y - 1 >= 0 ? y - 1 : 0;
		final int yEdgeEnd = y + 1 < battlefieldInternal[0].length ? y + 1 : battlefieldInternal[0].length - 1;
		for (int i = xEdgeStart; i <= xEdgeEnd; i++)
		{
			for (int j = yEdgeStart; j <= yEdgeEnd; j++)
			{
				if (battlefieldInternal[i][j] != Cell.Empty)
				{
					neighbor = true;

				}
			}

		}
		return neighbor;
	}


	private boolean inBorders(final int x, final int y)
	{
		return (x >= 0 && x < battlefieldInternal.length) && (y >= 0 && y < battlefieldInternal[0].length);
	}


	public boolean markHit(final int horisontal, final int vertical)
	{
		final int x = vertical - 1;
		final int y = horisontal - 1;
		boolean hit = false;
		if (!inBorders(x, y))
		{
			throw new IllegalArgumentException("Please enter a valid coordinates");
		}

		if (battlefieldInternal[x][y] == Cell.ShipPartDestroyed)
		{
			throw new IllegalStateException("You have already hit ship in this point");
		}

		if (battlefieldInternal[x][y] == Cell.EmptyChecked)
		{
			throw new IllegalStateException("You have already hit in this point");
		}

		if (battlefieldInternal[x][y] == Cell.Empty)
		{
			battlefieldInternal[x][y] = Cell.EmptyChecked;
		}
		else if (battlefieldInternal[x][y] == Cell.ShipPart)
		{
			battlefieldInternal[x][y] = Cell.ShipPartDestroyed;
			if (this.hasShipsRemained())
			{
				hit = true;
			}
		}

		return hit;
	}

	public boolean hasShipsRemained()
	{
		boolean shipsRemained = false;
		for (final Cell[] cells : battlefieldInternal)
		{
			for (final Cell cell : cells)
			{
				if (cell == Cell.ShipPart)
				{
					shipsRemained = true;
				}
			}
		}

		return shipsRemained;
	}

	public void copyVisible(final Battlefield computerBattlefield)
	{
		final Cell[][] copied = computerBattlefield.battlefieldInternal;
		for (int i = 0; i < copied.length; i++)
		{
			for (int j = 0; j < copied[i].length; j++)
			{
				if (copied[i][j] != Cell.ShipPart)
				{
					battlefieldInternal[i][j] = copied[i][j];
				}
			}
		}

	}
}
