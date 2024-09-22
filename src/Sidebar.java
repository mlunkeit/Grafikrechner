import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.util.function.*;

public class Sidebar extends JPanel implements ActionListener
{
  private final GroupLayout layout = new GroupLayout(this);

  private final List<JButton> buttons = new ArrayList<>();

  private final JTable table;
  
  private final List<Consumer<Byte>> listeners = new ArrayList<>();

  /**
  * Constructs an ActionComponent instance.
  *
  * @param actions
  */
  public Sidebar(byte actions, int tableRows)
  {
    if((actions & Action.ROTATE) != 0)
      buttons.add(createButton("Drehen", Action.ROTATE + ""));
      
    if((actions & Action.MOVE) != 0)
      buttons.add(createButton("Verschieben", Action.MOVE + ""));
      
    if((actions & Action.STRETCH) != 0)
      buttons.add(createButton("Strecken", Action.STRETCH + ""));
      
    if((actions & Action.CHECK) != 0)
      buttons.add(createButton("Prüfen", Action.CHECK + ""));
      
    table = new JTable(tableRows, 2);
      
    GroupLayout.ParallelGroup parallelButtonGroup = layout.createParallelGroup();
    buttons.forEach(button -> parallelButtonGroup.addComponent(button, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
    
    GroupLayout.SequentialGroup sequentialButtonGroup = layout.createSequentialGroup();
    buttons.forEach(sequentialButtonGroup::addComponent);
    
    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);
    
    layout.setHorizontalGroup(layout.createParallelGroup()
      .addGroup(parallelButtonGroup)
      .addComponent(table)
    );
    
    layout.setVerticalGroup(layout.createSequentialGroup()
      .addGroup(sequentialButtonGroup)
      .addComponent(table)
    );
    
    setLayout(layout);
  }
  
  private JButton createButton(String buttonLabel, String actionCommand)
  {
    JButton button = new JButton(buttonLabel);
    button.setActionCommand(actionCommand);
    button.addActionListener(this);
    return button;
  }
  
  @Override
  public void actionPerformed(ActionEvent evt)
  {
    listeners.forEach(callback -> callback.accept(Byte.valueOf(evt.getActionCommand(), 10)));
  }
  
  public void setRow(String[] values, int row)
  {
    if(values.length < 2)
      throw new IllegalArgumentException("The value array needs to have at least 2 elements");
      
    for(int i = 0; i < 2; i++)
      table.setValueAt(values[i], row, i);
  }
  
  public void onAction(Consumer<Byte> callback)
  {
    listeners.add(callback);
  }
  
  public static class Action
  {
    /**
    * Repräsentiert die Drehen Aktion. 
    *
    * {@code 0000 0001}
    */
    public static final byte ROTATE = 0x1;
    
    /**
    * Repräsentiert die Verschieben Aktion.
    *
    * {@code 0000 0010}
    */
    public static final byte MOVE = 0x2;
    
    /**
    * Repräsentiert die Strecken Aktion.
    *
    * {@code 0000 0100}
    */
    public static final byte STRETCH = 0x4;
    
    /**
    * Repräsentiert die Prüfen Aktion.
    *
    * {@code 0000 1000}
    */
    public static final byte CHECK = 0x8;
  }
}
