import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Iterator;

public class DreieckPanel extends JSplitPane
{
  private JButton drehenButton = new JButton("Drehen");
  private JButton verschiebenButton = new JButton("Verschieben");
  private JButton streckenButton = new JButton("Strecken");
  
  private LinkedHashMap<String, String> values = new LinkedHashMap<>(); 
  
  private JPanel leftPanel;
  private JTable table = new JTable(9, 2);

  private final Dreieck dreieck;

  public DreieckPanel(Dreieck dreieck)
  {
    super(HORIZONTAL_SPLIT, new JPanel(), new DreieckDisplay(dreieck));
    
    this.dreieck = dreieck;    
   
    setDividerLocation(200);
    
    leftPanel = (JPanel) getTopComponent();
    
    getBottomComponent().setBackground(Color.PINK);
    table.setEnabled(false);
    
    GroupLayout layout = new GroupLayout(leftPanel);
    
    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);
    
    layout.setHorizontalGroup(
      layout.createParallelGroup()
        .addComponent(drehenButton, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(verschiebenButton, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(streckenButton, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(table, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    
    layout.setVerticalGroup(
      layout.createSequentialGroup()
        .addComponent(drehenButton)
        .addComponent(verschiebenButton)
        .addComponent(streckenButton)
        .addComponent(table)
    );
    
    leftPanel.setLayout(layout);
    
    updateValues();
    updateTable();
    
    verschiebenButton.addActionListener(ev -> {
      String input = JOptionPane.showInputDialog(this, "Bitte geben Sie einen Vektor im Format (x, y) ein.", "Eingabe", JOptionPane.PLAIN_MESSAGE);
      try
      {
        Vector2 vector = Vector2.parseVector2(input);
        dreieck.verschieben(vector);
        updateValues();
        updateTable();
        getBottomComponent().repaint();
      }
      catch(Exception e) {}
    });
    
    streckenButton.addActionListener(ev -> {
      String input = JOptionPane.showInputDialog(this, "Bitte geben Sie einen Streckungsfaktor ein.", "Eingabe", JOptionPane.PLAIN_MESSAGE);
      try
      {
        double faktor = Double.parseDouble(input);
        dreieck.strecken(faktor);
        updateValues();
        updateTable();
        getBottomComponent().repaint();
      }
      catch(Exception e) {}
    });
    
    drehenButton.addActionListener(ev -> {
      String input = JOptionPane.showInputDialog(this, "Bitte geben Sie einen Drehwinkel in Gradmaß ein.", "Eingabe", JOptionPane.PLAIN_MESSAGE);
      try
      {
        float winkel = Float.parseFloat(input);
        dreieck.drehen(winkel);
        updateValues();
        updateTable();
        getBottomComponent().repaint();
      }
      catch(Exception e) {}
    });
  }
  
  private void updateValues()
  {
    values.put("Punkt A", dreieck.getPunktA().toString());
    values.put("Punkt B", dreieck.getPunktB().toString());
    values.put("Punkt C", dreieck.getPunktC().toString());
    values.put("Seite A", Math.round(dreieck.getSeiteA()*100)/100.0 + "");
    values.put("Seite B", Math.round(dreieck.getSeiteB()*100)/100.0 + "");
    values.put("Seite C", Math.round(dreieck.getSeiteC()*100)/100.0 + "");
    values.put("Alpha", Math.round(dreieck.getAlpha()*100)/100.0 + "°");
    values.put("Beta", Math.round(dreieck.getBeta()*100)/100.0 + "°");
    values.put("Gamma", Math.round(dreieck.getGamma()*100)/100.0 + "°");
  }
  
  private void updateTable()
  {
    Iterator<String> keyIterator = values.keySet().iterator();
    
    for(int i = 0; i < 9; i++)
    {
      String key = keyIterator.next();
      String value = values.get(key);
      
      table.setValueAt(key, i, 0);
      table.setValueAt(value, i, 1);
    }
  }
  
  private static class DreieckDisplay extends JComponent
  {
    private final Dreieck dreieck;
    
    private int scale = 20;
    
    private DreieckDisplay(Dreieck dreieck)
    {
      this.dreieck = dreieck;
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
      
      Point posA = translateVector(dreieck.getPunktA());
      Point posB = translateVector(dreieck.getPunktB());
      Point posC = translateVector(dreieck.getPunktC());
      
      g.fillPolygon(new int[] {(int) posA.getX(), (int) posB.getX(), (int) posC.getX()}, new int[] {(int) posA.getY(), (int) posB.getY(), (int) posC.getY()}, 3);
    }
  }
}
