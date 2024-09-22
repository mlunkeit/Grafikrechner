import javax.swing.*;
import java.util.*;

public class CylinderPanel extends JSplitPane
{
  private final Cylinder cylinder;

  private final LinkedHashMap<String, String> values = new LinkedHashMap<>();
  
  private final Sidebar sidebar;

  public CylinderPanel(Cylinder cylinder)
  {
    super(HORIZONTAL_SPLIT, new Sidebar((byte) 0xE, 7), new JPanel());
    
    this.cylinder = cylinder;
    
    sidebar = (Sidebar) getTopComponent();
    setDividerLocation(200);
    
    updateValues();
    updateTable();
    
    sidebar.onAction(actions -> {
      if((actions.byteValue() & Sidebar.Action.MOVE) != 0)
      {
        String input = JOptionPane.showInputDialog(this, "Bitte geben Sie einen Vektor im Format (x, y, z) ein.", "Eingabe", JOptionPane.PLAIN_MESSAGE);
        try
        {
          Vector3 vector = Vector3.parseVector3(input);
          cylinder.move(vector);
          updateValues();
          updateTable();
          getBottomComponent().repaint();
        }
        catch(Exception e) {}
      }
      else if((actions.byteValue() & Sidebar.Action.STRETCH) != 0)
      {
        String input = JOptionPane.showInputDialog(this, "Bitte geben Sie einen Streckungsfaktor ein.", "Eingabe", JOptionPane.PLAIN_MESSAGE);
        try
        {
          double factor = Double.parseDouble(input);
          cylinder.stretch(factor);
          updateValues();
          updateTable();
          getBottomComponent().repaint();
        }
        catch(Exception e) {}
      }
      else if((actions.byteValue() & Sidebar.Action.CHECK) != 0)
      {
        String input = JOptionPane.showInputDialog(this, "Bitte geben Sie einen Vektor im Format (x, y, z) ein.", "Eingabe", JOptionPane.PLAIN_MESSAGE);
        try
        {
          Vector3 vector = Vector3.parseVector3(input);
          byte state = cylinder.checkPoint(vector);
          switch(state)
          {
            case Cylinder.OUTSIDE:
              JOptionPane.showMessageDialog(this, "Der Punkt befindet sich außerhalb des Zylinders.", "Information", JOptionPane.INFORMATION_MESSAGE);
              break;
              
            case Cylinder.INSIDE:
              JOptionPane.showMessageDialog(this, "Der Punkt befindet sich innerhalb des Zylinders.", "Information", JOptionPane.INFORMATION_MESSAGE);
              break;
              
            case Cylinder.SURFACE:
              JOptionPane.showMessageDialog(this, "Der Punkt befindet sich auf der Oberfläche des Zylinders.", "Information", JOptionPane.INFORMATION_MESSAGE);
              break;
          }
        }
        catch(Exception e) {}
      }
    });
  }
  
  private void updateValues()
  {
    values.put("Ankerpunkt", cylinder.getAnchorPoint().toString());
    values.put("Radius", cylinder.getRadius() + "");
    values.put("Höhe", cylinder.getHeight() + "");
    values.put("Grundfläche", Math.round(cylinder.getBaseArea()*100)/100.0 + "");
    values.put("Mantelfläche", Math.round(cylinder.getLateralArea()*100)/100.0 + "");
    values.put("Oberfläche", Math.round(cylinder.getSurface()*100)/100.0 + "");
    values.put("Volumen", Math.round(cylinder.getVolume()*100)/100.0 + "");
  }
  
  private void updateTable()
  {
    Iterator<String> keyIterator = values.keySet().iterator();
    
    for(int i = 0; i < 7; i++)
    {
      String key = keyIterator.next();
      String value = values.get(key);
      
      sidebar.setRow(new String[] {key, value}, i);
    }
  }
}
