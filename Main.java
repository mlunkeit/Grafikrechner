import javax.swing.*;
import java.util.*;

public class Main
{
  public static void main(String[] args)
  {
    /*Vector2 vector = new Vector2(1, 1);
    
    vector.drehen(new Vector2(1, 2), 90);
    
    System.out.println(vector);*/
  
    try
    {
      Arrays.stream(UIManager.getInstalledLookAndFeels()).forEach(laf -> System.out.println(laf));
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      //UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
  
    SwingUtilities.invokeLater(() -> new GeometrieFenster());
  }
}
