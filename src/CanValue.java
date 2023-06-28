import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import net.miginfocom.swing.*;
/*
 * Created by JFormDesigner on Tue Jun 27 21:19:22 CDT 2023
 */



/**
 * @author josh
 */
public class CanValue extends JPanel {
    public CanValue() {
        initComponents();
    }

    public void calculateCurrentValue(CanMsg canMsg) {
        int bitStart = Integer.parseInt(bitStartField.getText());
        int bitLength = Integer.parseInt(bitLengthField.getText());
        double scale = Double.parseDouble(scaleField.getText());
        double offset = Double.parseDouble(offsetField.getText());

        long rawData = (canMsg.data >> bitStart) & ((1L << bitLength) - 1);
        if((rawData & (1<<(bitLength)))>1){ //type cast correction for negative numbers. This assumes that all incoming data is signed
            rawData = rawData | -1L << bitLength;
        }
        double scaledData = rawData * scale + offset;

        valueLabel.setText(String.valueOf(scaledData));
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner non-commercial license
        textField7 = new JTextField();
        valueLabel = new JLabel();
        label1 = new JLabel();
        bitStartField = new JTextField();
        graphPanel = new JPanel();
        label2 = new JLabel();
        bitLengthField = new JTextField();
        label3 = new JLabel();
        scaleField = new JTextField();
        label4 = new JLabel();
        offsetField = new JTextField();

        //======== this ========
        setMinimumSize(new Dimension(200, 125));
        setMaximumSize(new Dimension(5000, 5000));
        setPreferredSize(new Dimension(350, 150));
        setBorder(LineBorder.createBlackLineBorder());
        setLayout(new MigLayout(
            "insets 0,hidemode 3,gap 5 5",
            // columns
            "[52:55,shrink 0,right]" +
            "[45:45,shrink 0,fill]" +
            "[90,fill]" +
            "[grow,fill]",
            // rows
            "[center]0" +
            "[25,shrink 0,center]0" +
            "[25,shrink 0,center]0" +
            "[25,shrink 0,center]0" +
            "[25,shrink 0]0" +
            "[grow]"));

        //---- textField7 ----
        textField7.setText("Name");
        add(textField7, "cell 0 0 2 1,dock center");

        //---- valueLabel ----
        valueLabel.setText("Value");
        add(valueLabel, "cell 2 0");

        //---- label1 ----
        label1.setText("Bit Start");
        add(label1, "cell 0 1");

        //---- bitStartField ----
        bitStartField.setText("0");
        bitStartField.setMinimumSize(new Dimension(20, 22));
        add(bitStartField, "cell 1 1");

        //======== graphPanel ========
        {

            GroupLayout graphPanelLayout = new GroupLayout(graphPanel);
            graphPanel.setLayout(graphPanelLayout);
            graphPanelLayout.setHorizontalGroup(
                graphPanelLayout.createParallelGroup()
                    .addGap(0, 88, Short.MAX_VALUE)
            );
            graphPanelLayout.setVerticalGroup(
                graphPanelLayout.createParallelGroup()
                    .addGap(0, 101, Short.MAX_VALUE)
            );
        }
        add(graphPanel, "cell 2 1 2 5,dock center");

        //---- label2 ----
        label2.setText("Bit Length");
        add(label2, "cell 0 2");

        //---- bitLengthField ----
        bitLengthField.setText("16");
        bitLengthField.setMinimumSize(new Dimension(20, 22));
        add(bitLengthField, "cell 1 2");

        //---- label3 ----
        label3.setText("Scale");
        add(label3, "cell 0 3");

        //---- scaleField ----
        scaleField.setText("1");
        scaleField.setMinimumSize(new Dimension(20, 22));
        add(scaleField, "cell 1 3");

        //---- label4 ----
        label4.setText("Offset");
        add(label4, "cell 0 4");

        //---- offsetField ----
        offsetField.setText("0");
        offsetField.setMinimumSize(new Dimension(20, 22));
        add(offsetField, "cell 1 4");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner non-commercial license
    private JTextField textField7;
    public JLabel valueLabel;
    private JLabel label1;
    public JTextField bitStartField;
    private JPanel graphPanel;
    private JLabel label2;
    public JTextField bitLengthField;
    private JLabel label3;
    public JTextField scaleField;
    private JLabel label4;
    public JTextField offsetField;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
