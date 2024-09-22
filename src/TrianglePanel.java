import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Iterator;

public class TrianglePanel extends JSplitPane
{ 
  private LinkedHashMap<String, String> values = new LinkedHashMap<>(); 

  private final Triangle triangle;
  
  private final Sidebar sidebar;

  public TrianglePanel(Triangle triangle)
  {
    super(HORIZONTAL_SPLIT, new Sidebar((byte) 0x7, 9), new TriangleDisplay(triangle));
    
    this.triangle = triangle;
    
    sidebar = (Sidebar) getTopComponent();
    
    setDividerLocation(200);
    
    updateValues();
    updateTable();
    
    sidebar.onAction(actions -> {
      if((actions.byteValue() & Sidebar.Action.MOVE) != 0)
      {
        String input = JOptionPane.showInputDialog(this, "Bitte geben Sie einen Vektor im Format (x, y) ein.", "Eingabe", JOptionPane.PLAIN_MESSAGE);
        try
        {
          Vector2 vector = Vector2.parseVector2(input);
          triangle.move(vector);
          updateValues();
          updateTable();
          getBottomComponent().repaint();
        }
        catch(Exception e) {}
      }
      
      if((actions.byteValue() & Sidebar.Action.STRETCH) != 0)
      {
        String input = JOptionPane.showInputDialog(this, "Bitte geben Sie einen Streckungsfaktor ein.", "Eingabe", JOptionPane.PLAIN_MESSAGE);
        try
        {
          double faktor = Double.parseDouble(input);
          triangle.stretch(faktor);
          updateValues();
          updateTable();
          getBottomComponent().repaint();
        }
        catch(Exception e) {}
      }
      
      if((actions.byteValue() & Sidebar.Action.ROTATE) != 0)
      {
        String input = JOptionPane.showInputDialog(this, "Bitte geben Sie einen Drehwinkel in Gradmaß ein.", "Eingabe", JOptionPane.PLAIN_MESSAGE);
        try
        {
          float winkel = Float.parseFloat(input);
          triangle.rotate(winkel);
          updateValues();
          updateTable();
          getBottomComponent().repaint();
        }
        catch(Exception e) {}
      }
    });
  }
  
  private void updateValues()
  {
    values.put("Punkt A", triangle.getPointA().toString());
    values.put("Punkt B", triangle.getPointB().toString());
    values.put("Punkt C", triangle.getPointC().toString());
    values.put("Seite A", Math.round(triangle.getSideA()*100)/100.0 + "");
    values.put("Seite B", Math.round(triangle.getSideB()*100)/100.0 + "");
    values.put("Seite C", Math.round(triangle.getSideC()*100)/100.0 + "");
    values.put("Alpha", Math.round(triangle.getAlpha()*100)/100.0 + "°");
    values.put("Beta", Math.round(triangle.getBeta()*100)/100.0 + "°");
    values.put("Gamma", Math.round(triangle.getGamma()*100)/100.0 + "°");
  }
  
  private void updateTable()
  {
    Iterator<String> keyIterator = values.keySet().iterator();
    
    for(int i = 0; i < 9; i++)
    {
      String key = keyIterator.next();
      String value = values.get(key);
      
      sidebar.setRow(new String[] {key, value}, i);
    }
  }
  
  private static class TriangleDisplay extends JComponent
  {
    private final Triangle triangle;
    
    private int scale = 20;
    
    private TriangleDisplay(Triangle triangle)
    {
      this.triangle = triangle;
    }
    
    private Point translateVector(Vector2 vector)
    {
      return new Point(getWidth()/2 + (int) (vector.getX()*scale), getHeight()/2 - (int) (vector.getY()*scale));
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
      g.setColor(Color.WHITE);
      g.fillRect(0, 0, getWidth(), getHeight());
    
      g.setColor(Color.LIGHT_GRAY);
    
      for(int i = 0; i <= (getWidth()/2)/scale; i++)
      {
        g.drawLine(getWidth()/2 + i*scale, 0, getWidth()/2 + i*scale, getHeight());
        g.drawLine(getWidth()/2 - i*scale, 0, getWidth()/2 - i*scale, getHeight());
      }
      
      for(int i = 0; i <= (getHeight()/2)/scale; i++)
      {
        g.drawLine(0, getHeight()/2 + i*scale, getWidth(), getHeight()/2 + i*scale);
        g.drawLine(0, getHeight()/2 - i*scale, getWidth(), getHeight()/2 - i*scale);
      }
      
      g.setColor(Color.BLACK);
      g.drawLine(0, getHeight()/2, getWidth(), getHeight()/2);
      g.drawLine(getWidth()/2, 0, getWidth()/2, getHeight());
      
      g.setColor(new Color(0x339cff));
      
      Point posA = translateVector(triangle.getPointA());
      Point posB = translateVector(triangle.getPointB());
      Point posC = translateVector(triangle.getPointC());
      
      g.fillPolygon(new int[] {(int) posA.getX(), (int) posB.getX(), (int) posC.getX()}, new int[] {(int) posA.getY(), (int) posB.getY(), (int) posC.getY()}, 3);
    }
  }
}
