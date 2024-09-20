import java.lang.Math;

public class Vector2
{
	private double x;
	private double y;
	
	public Vector2(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public void verschieben(Vector2 vector)
	{
		x += vector.getX();
		y += vector.getY();
	}
	
	public double abstand(Vector2 vector)
	{
		return Math.sqrt(Math.pow(vector.getX() - x, 2) + Math.pow(vector.getY() - y, 2));
	}
	
	/**
	* Dreht den Vektor um einen Drehpunkt um den gegebenen Winkel.
  *
  * @param drehpunkt der Drehpunkt
  * @param winkel der Winkel in Gradmaß
  */
	public void drehen(Vector2 drehpunkt, float winkel)
	{
	  double a = abstand(drehpunkt);
	  double alpha = Math.atan2(y - drehpunkt.getY(), x - drehpunkt.getX());
	  
		x = a * Math.cos(alpha - Math.toRadians(winkel)) + drehpunkt.getX();
		y = a * Math.sin(alpha - Math.toRadians(winkel)) + drehpunkt.getY();
	}
	
	@Override
	public String toString()
	{
	  return String.format("(%s, %s)", Math.round(x*100)/100.0, Math.round(y*100)/100.0);
	  //return String.format("Vector2(%s,%s)", Math.round(x*10)/10, Math.round(y*10)/10);
	}

    public static Vector2 parseVector2(String value)
    {
        String[] values = value.replaceAll("[\\(\\) ]", "").split(",");
        if(values.length != 2)
            throw new IllegalArgumentException("Couldn't parse text to Vector2");

        return new Vector2(Double.parseDouble(values[0]), Double.parseDouble(values[1]));
    }
}
