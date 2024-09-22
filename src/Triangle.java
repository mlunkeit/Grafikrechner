import java.lang.Math;

public class Triangle
{
  private Vector2 pointA;
  private Vector2 pointB;
  private Vector2 pointC;
  
  public Triangle(Vector2 a, Vector2 b, Vector2 c)
  {
    this.pointA = a;
    this.pointB = b;
    this.pointC = c;
  }
  
  public Triangle(float alpha, double b, double c)
  {
    this(new Vector2(0, 0), new Vector2(c, 0), new Vector2(
      Math.cos(Math.toRadians(alpha)) * b,
      Math.sin(Math.toRadians(alpha)) * b
    ));
  }
  
  public Triangle(float alpha, float beta, double c)
  {
    this(
      (float) alpha,
      (float) (c / Math.sin(Math.toRadians(180 - alpha - beta))) * Math.sin(beta),
      c
    );
  }
  
  public Triangle(double a, double b, double c)
  {
    this((float) Math.toDegrees(Math.acos((-Math.pow(a, 2) + Math.pow(b, 2) + Math.pow(c, 2))/(2*b*c))), b, c);
  }
  
  public Vector2 getPointA()
  {
    return pointA;
  }
  
  public Vector2 getPointB()
  {
    return pointB;
  }
  
  public Vector2 getPointC()
  {
    return pointC;
  }
  
  public double getSideA()
  {
    return pointB.distance(pointC);
  }
  
  public double getSideB()
  {
    return pointC.distance(pointA);
  }
  
  public double getSideC()
  {
    return pointA.distance(pointB);
  }
  
  public float getAlpha()
  {
    double a = getSideA();
    double b = getSideB();
    double c = getSideC();
    
    return (float) Math.toDegrees(Math.acos((-Math.pow(a, 2) + Math.pow(b, 2) + Math.pow(c, 2))/(2*b*c)));
  }
  
  public float getBeta()
  {
    double a = getSideA();
    double b = getSideB();
    double c = getSideC();
    
    return (float) Math.toDegrees(Math.acos((-Math.pow(b, 2) + Math.pow(a, 2) + Math.pow(c, 2))/(2*a*c)));
  }
  
  public float getGamma()
  {
    double a = getSideA();
    double b = getSideB();
    double c = getSideC();
    
    return (float) Math.toDegrees(Math.acos((-Math.pow(c, 2) + Math.pow(a, 2) + Math.pow(b, 2))/(2*a*b)));
  }
  
  public Vector2 getFocus()
  {
    return new Vector2(
      (pointA.getX() + pointB.getX() + pointC.getX()) / 3,
      (pointA.getY() + pointB.getY() + pointC.getY()) / 3
    );
  }
  
  /**
  * Dreht das Dreieck um einen gegebenen Winkel Alpha.
  *
  * @param alpha der Winkel
  */
  public void rotate(float alpha)
  {
    Vector2 focus = getFocus();
    Vector2[] points = new Vector2[] {pointA, pointB, pointC};
    
    for(Vector2 point : points) {
      point.rotate(focus, alpha);
    }
  }
  
  public void move(Vector2 vector)
  {
    Vector2[] points = new Vector2[] {pointA, pointB, pointC};
    
    for(Vector2 point : points) {
      point.move(vector);
    }
  }
  
  public void stretch(double factor)
  {
    pointA = new Vector2(pointA.getX() * factor, pointA.getY() * factor);
    pointB = new Vector2(pointB.getX() * factor, pointB.getY() * factor);
    pointC = new Vector2(pointC.getX() * factor, pointC.getY() * factor);
  }
}
