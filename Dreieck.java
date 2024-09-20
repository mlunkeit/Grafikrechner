import java.lang.Math;

public class Dreieck
{
  private Vector2 punktA;
  private Vector2 punktB;
  private Vector2 punktC;
  
  public Dreieck(Vector2 a, Vector2 b, Vector2 c)
  {
    this.punktA = a;
    this.punktB = b;
    this.punktC = c;
  }
  
  public Dreieck(float alpha, double b, double c)
  {
    this(new Vector2(0, 0), new Vector2(c, 0), new Vector2(
      Math.cos(Math.toRadians(alpha)) * b,
      Math.sin(Math.toRadians(alpha)) * b
    ));
  }
  
  public Dreieck(float alpha, float beta, double c)
  {
    this(
      (float) alpha,
      (float) (c / Math.sin(Math.toRadians(180 - alpha - beta))) * Math.sin(beta),
      c
    );
  }
  
  public Dreieck(double a, double b, double c)
  {
    this((float) Math.toDegrees(Math.acos((-Math.pow(a, 2) + Math.pow(b, 2) + Math.pow(c, 2))/(2*b*c))), b, c);
  }
  
  public Vector2 getPunktA()
  {
    return punktA;
  }
  
  public Vector2 getPunktB()
  {
    return punktB;
  }
  
  public Vector2 getPunktC()
  {
    return punktC;
  }
  
  public double getSeiteA()
  {
    return punktB.abstand(punktC);
  }
  
  public double getSeiteB()
  {
    return punktC.abstand(punktA);
  }
  
  public double getSeiteC()
  {
    return punktA.abstand(punktB);
  }
  
  public float getAlpha()
  {
    double a = getSeiteA();
    double b = getSeiteB();
    double c = getSeiteC();
    
    return (float) Math.toDegrees(Math.acos((-Math.pow(a, 2) + Math.pow(b, 2) + Math.pow(c, 2))/(2*b*c)));
  }
  
  public float getBeta()
  {
    double a = getSeiteA();
    double b = getSeiteB();
    double c = getSeiteC();
    
    return (float) Math.toDegrees(Math.acos((-Math.pow(b, 2) + Math.pow(a, 2) + Math.pow(c, 2))/(2*a*c)));
  }
  
  public float getGamma()
  {
    double a = getSeiteA();
    double b = getSeiteB();
    double c = getSeiteC();
    
    return (float) Math.toDegrees(Math.acos((-Math.pow(c, 2) + Math.pow(a, 2) + Math.pow(b, 2))/(2*a*b)));
  }
  
  public Vector2 getSchwerpunkt()
  {
    return new Vector2(
      (punktA.getX() + punktB.getX() + punktC.getX()) / 3,
      (punktA.getY() + punktB.getY() + punktC.getY()) / 3
    );
  }
  
  /**
  * Dreht das Dreieck um einen gegebenen Winkel Alpha.
  *
  * @param alpha der Winkel
  */
  public void drehen(float alpha)
  {
    Vector2 schwerpunkt = getSchwerpunkt();
    Vector2[] punkte = new Vector2[] {punktA, punktB, punktC};
    
    for(Vector2 punkt : punkte) {
      punkt.drehen(schwerpunkt, alpha);
    }
  }
  
  public void verschieben(Vector2 vector)
  {
    Vector2[] punkte = new Vector2[] {punktA, punktB, punktC};
    
    for(Vector2 punkt : punkte) {
      punkt.verschieben(vector);
    }
  }
  
  public void strecken(double faktor)
  {
    punktA = new Vector2(punktA.getX() * faktor, punktA.getY() * faktor);
    punktB = new Vector2(punktB.getX() * faktor, punktB.getY() * faktor);
    punktC = new Vector2(punktC.getX() * faktor, punktC.getY() * faktor);
  }
}
