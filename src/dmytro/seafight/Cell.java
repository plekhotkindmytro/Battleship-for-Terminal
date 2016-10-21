package dmytro.seafight;

public enum Cell
{
	Empty(""), EmptyChecked("*"), ShipPart("@"), ShipPartDestroyed("X");

	private final String value;

	private Cell(final String value)
	{
		this.value = value;
	}

	@Override
	public String toString()
	{
		return value;
	}

}
