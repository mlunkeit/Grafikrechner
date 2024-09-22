import javax.swing.*;
import java.util.*;

public class Main
{
  public static void main(String[] args)
  {
    try
    {
      //Arrays.stream(UIManager.getInstalledLookAndFeels()).forEach(laf -> System.out.println(laf));
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
