import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GeometrieFenster extends JFrame
{
  private final JMenuBar menuBar = new JMenuBar();
  private final JMenu neuMenu = new JMenu("Neu");
  private final JMenu dreieckMenu = new JMenu("Dreieck");
  private final JMenuItem sssItem = new JMenuItem("von SSS");
  private final JMenuItem sswItem = new JMenuItem("von SSW");
  private final JMenuItem swwItem = new JMenuItem("von SWW");
  private final JMenuItem punkteItem = new JMenuItem("von den Eckpunkten");
  private final JMenu zylinderMenu = new JMenu("Zylinder");
  
  private final JTabbedPane tabbedPane = new JTabbedPane();
  
  private int dreiecke = 0;

  public GeometrieFenster()
  {
    super("Geometrie Rechner");
  
    setSize(800, 500);
    setLocationRelativeTo(null);
    
    dreieckMenu.add(sssItem);
    sssItem.setActionCommand("SSS");
    sssItem.addActionListener(this::neuesDreieck);
    dreieckMenu.add(sswItem);
    sswItem.setActionCommand("SSW");
    sswItem.addActionListener(this::neuesDreieck);
    dreieckMenu.add(swwItem);
    swwItem.setActionCommand("SWW");
    swwItem.addActionListener(this::neuesDreieck);
    dreieckMenu.add(punkteItem);
    punkteItem.setActionCommand("Punkte");
    punkteItem.addActionListener(this::neuesDreieck);
    neuMenu.add(dreieckMenu);
    neuMenu.add(zylinderMenu);
    menuBar.add(neuMenu);
    setJMenuBar(menuBar);
    
    add(tabbedPane);
    
    setVisible(true);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  private void neuesDreieck(ActionEvent ev)
  {
    //JOptionPane.showMessageDialog(this, "Hier kommt noch ein Eingabefeld für " + ev.getActionCommand());
    Eingabe eingabe = new Eingabe(this);

    if(ev.getActionCommand().equals("Punkte"))
    {
        eingabe.setInfo("Geben Sie die Eckpunkte des Dreiecks im Format (x, y) ein.");

        eingabe.setLabel1("Punkt A");
        eingabe.setLabel2("Punkt B");
        eingabe.setLabel3("Punkt C");

        eingabe.onSubmit(eingaben -> {
            try
            {
                Vector2 punktA = Vector2.parseVector2(eingaben[0]);
                Vector2 punktB = Vector2.parseVector2(eingaben[1]);
                Vector2 punktC = Vector2.parseVector2(eingaben[2]);

                Dreieck dreieck = new Dreieck(punktA, punktB, punktC);
                dreiecke++;
                tabbedPane.add("Dreieck " + dreiecke, new DreieckPanel(dreieck));
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(this, "Fehler: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    else if(ev.getActionCommand().equals("SSS"))
    {
        eingabe.setInfo("Geben Sie die Seitenlängen des Dreiecks ein.");

        eingabe.setLabel1("Seite A");
        eingabe.setLabel2("Seite B");
        eingabe.setLabel3("Seite C");

        eingabe.onSubmit(eingaben -> {
            try
            {
                double seiteA = Double.parseDouble(eingaben[0]);
                double seiteB = Double.parseDouble(eingaben[1]);
                double seiteC = Double.parseDouble(eingaben[2]);

                Dreieck dreieck = new Dreieck(seiteA, seiteB, seiteC);
                dreiecke++;
                tabbedPane.add("Dreieck " + dreiecke, new DreieckPanel(dreieck));
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(this, "Fehler: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    eingabe.display();
  }
}
