package dmytro.seafight;

public class Point
{
	public final int horisontal;
	public final int vertical;

	public Point(final int horisontal, final int vertical)
	{
		this.horisontal = horisontal;
		this.vertical = vertical;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + horisontal;
		result = prime * result + vertical;
		return result;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final Point other = (Point) obj;
		if (horisontal != other.horisontal)
		{
			return false;
		}
		if (vertical != other.vertical)
		{
			return false;
		}
		return true;
	}


}
