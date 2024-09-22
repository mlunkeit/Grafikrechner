import java.util.*;

public class Cylinder
{
  public static final byte OUTSIDE = 0x1;
  public static final byte INSIDE = 0x2;
  public static final byte SURFACE = 0x4;
  
  private Vector3 anchorPoint;
  private double height;
  private double radius;

  /**
  * Konstruiert eine neue Instanz der Zylinder Klasse.
  *
  * @param anchorPoint der Ankerpunkt des Zylinders
  * @param radius der Radius des Zylinders
  * @param height die Höhe des Zylinders
  */
  public Cylinder(Vector3 anchorPoint, double radius, double height)
  {
    this.anchorPoint = anchorPoint;
    
    if(radius <= 0)
      throw new IllegalArgumentException("Radius needs to be greater than 0");
    
    this.radius = radius;
    
    if(height <= 0)
      throw new IllegalArgumentException("Height needs to be greater than 0");
    
    this.height = height;
  }
  
  public Vector3 getAnchorPoint()
  {
    return anchorPoint;
  }
  
  public double getHeight()
  {
    return height;
  }
  
  public double getRadius()
  {
    return radius;
  }
  
  public double getBaseArea()
  {
    return Math.PI * Math.pow(radius, 2);
  }
  
  public double getLateralArea()
  {
    return 2 * Math.PI * radius * height;
  }
  
  public double getSurface()
  {
    return 2 * getBaseArea() + getLateralArea();
  }
  
  public double getVolume()
  {
    return getBaseArea() * height;
  }
  
  public void move(Vector3 vector)
  {
    anchorPoint.move(vector);
  }
  
  public void stretch(double factor)
  {
    height *= factor;
    radius *= factor;
  }
  
  /**
  * Prüft, ob sich ein gegebener Punkt innerhalb des Zylinders befindet.
  *
  * @param vector Der dreidimensionale Zylinder
  */
  public byte checkPoint(Vector3 vector)
  {
    if(vector.getY() < anchorPoint.getY() || vector.getY() > (anchorPoint.getY() + height))
      return OUTSIDE;
      
    Vector2 midpoint = new Vector2(anchorPoint.getX(), anchorPoint.getZ());
    Vector2 point2D = new Vector2(vector.getX(), vector.getZ());
    
    if(midpoint.abstand(point2D) > radius)
      return OUTSIDE;
      
    if(vector.getY() == anchorPoint.getY() || vector.getY() == (anchorPoint.getY() + height))
      return SURFACE;
      
    if(midpoint.abstand(point2D) == radius)
      return SURFACE;
      
    return INSIDE;
  }
}
