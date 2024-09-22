public class Cylinder
{
  public static final byte OUTSIDE = 0x1;
  public static final byte INSIDE = 0x2;
  public static final byte SURFACE = 0x4;
  
  private Vector3 anchorPoint;
  private double height;
  private double radius;

  public Cylinder(Vector3 anchorPoint, double height, double radius)
  {
    this.anchorPoint = anchorPoint;
    this.height = height;
    this.radius = radius;
  }
  
  
}
