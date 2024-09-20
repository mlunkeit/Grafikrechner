import javax.swing.*;
import java.util.*;
import java.util.function.*;
import java.awt.event.*;

public class Eingabe extends JDialog
{
    private GroupLayout layout = new GroupLayout(getContentPane());

    private JLabel infoLabel = new JLabel("Information zur Eingabe");

    private JLabel textField1Label = new JLabel("Label 1");
    private JLabel textField2Label = new JLabel("Label 2");
    private JLabel textField3Label = new JLabel("Label 3");

    private JTextField textField1 = new JTextField();
    private JTextField textField2 = new JTextField();
    private JTextField textField3 = new JTextField();

    private JButton submitButton = new JButton("Weiter");
    private JButton cancelButton = new JButton("Abbrechen");

    private List<Consumer<String[]>> submitCallbackFunctions = new ArrayList<>();
    private List<Runnable> cancelCallbackFunctions = new ArrayList<>();

    public Eingabe(JFrame parent)
    {
        super(parent, true);

        submitButton.addActionListener(ev -> {
            String[] inputs = new String[] { textField1.getText(), textField2.getText(), textField3.getText() };

            dispose();
            submitCallbackFunctions.forEach(consumer -> consumer.accept(inputs));
        });

        cancelButton.addActionListener(ev -> {
            cancelCallbackFunctions.forEach(Runnable::run);
            dispose();
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent)
            {
                cancelCallbackFunctions.forEach(Runnable::run);                
                dispose();
            }        
        });  

        setSize(500, 230);
        setLocationRelativeTo(parent);
        setResizable(false);

        setTitle("Eingabe");

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addComponent(infoLabel)
                .addGap(30)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(textField1Label)
                    .addComponent(textField1)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(textField2Label)
                    .addComponent(textField2)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(textField3Label)
                    .addComponent(textField3)
                )
                .addGap(30)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(submitButton)
                    .addComponent(cancelButton)
                )
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(infoLabel)
                .addGap(30)
                .addGroup(layout.createParallelGroup()
                    .addComponent(textField1Label)
                    .addComponent(textField1)
                )
                .addGroup(layout.createParallelGroup()
                    .addComponent(textField2Label)
                    .addComponent(textField2)
                )
                .addGroup(layout.createParallelGroup()
                    .addComponent(textField3Label)
                    .addComponent(textField3)
                )
                .addGap(30)
                .addGroup(layout.createParallelGroup()
                    .addComponent(submitButton)
                    .addComponent(cancelButton)
                )
        );

        layout.linkSize(SwingConstants.VERTICAL, textField1, textField1Label);
        layout.linkSize(SwingConstants.VERTICAL, textField2, textField2Label);
        layout.linkSize(SwingConstants.VERTICAL, textField3, textField3Label);

        layout.linkSize(SwingConstants.HORIZONTAL, textField1Label, textField2Label, textField3Label);

        getContentPane().setLayout(layout);
    }

    public void display()
    {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    public void setInfo(String value)
    {
        infoLabel.setText(value);
    }

    public void setLabel1(String value)
    {
        textField1Label.setText(value);
    }

    public void setLabel2(String value)
    {
        textField2Label.setText(value);
    }

    public void setLabel3(String value)
    {
        textField3Label.setText(value);
    }

    public void onSubmit(Consumer<String[]> callbackFunction)
    {
        submitCallbackFunctions.add(callbackFunction);
    }

    public void onCancel(Runnable callbackFunction)
    {
        cancelCallbackFunctions.add(callbackFunction);
    }
}
