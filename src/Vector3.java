public class Vector3
{
  private double x;
  private double y;
  private double z;

  public Vector3(double x, double y, double z)
  {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  
  public void move(Vector3 anotherVector)
  {
    x += anotherVector.getX();
    y += anotherVector.getY();
    z += anotherVector.getZ();
  }
  
  public double getX()
  {
    return x;
  }
  
  public double getY()
  {
    return y;
  }
  
  public double getZ()
  {
    return z;
  }
  
  @Override
  public String toString()
  {
    return String.format("(%s, %s, %s)", Math.round(x*100)/100.0, Math.round(y*100)/100.0, Math.round(z*100)/100.0);
  }
  
  public static final Vector3 parseVector3(String value)
  {
    String[] values = value.replaceAll("[\\(\\) ]", "").split(",");
    if(values.length != 3)
      throw new IllegalArgumentException("Couldn't parse text to Vector3");

    return new Vector3(Double.parseDouble(values[0]), Double.parseDouble(values[1]), Double.parseDouble(values[2]));
  }
}
