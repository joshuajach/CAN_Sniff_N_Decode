import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import java.awt.*;
import java.util.ArrayList;
/*
 * Created by JFormDesigner on Tue Jun 27 21:19:22 CDT 2023
 */



/**
 * @author josh
 */
public class CanValue extends JPanel {
    final VarDetails varInfo = new VarDetails(0, 16, 1.0, 0.0);
    ArrayList<CanMsg> canMsgs;
    public CanValue() {
        initComponents();
    }


    public void calculateCurrentValue() {
        try {
            varInfo.bitStart = Integer.parseInt(!bitStartField.getText().isEmpty() ? bitStartField.getText() : "0");
            varInfo.bitLength = Integer.parseInt(!bitLengthField.getText().isEmpty() ? bitLengthField.getText() : "16");
            varInfo.scale = Double.parseDouble(!scaleField.getText().isEmpty() ? scaleField.getText() : "1.0");
            varInfo.offset = Double.parseDouble(!offsetField.getText().isEmpty() ? offsetField.getText() : "0.0");

            long rawData = (canMsgs.get(canMsgs.size()-1).data >> varInfo.bitStart) & ((1L << varInfo.bitLength) - 1);
            if ((rawData & (1L << (varInfo.bitLength))) > 1) { //type cast correction for negative numbers. This assumes that all incoming data is signed
                rawData = rawData | -1L << varInfo.bitLength;
            }
            double scaledData = rawData * varInfo.scale + varInfo.offset;

            valueLabel.setText(String.valueOf(scaledData));
        }catch (NumberFormatException ignored){
        }
    }
    public void updateGraph() {
        graphPanel.updateGraph();
    }

    public void setGraph(ArrayList<CanMsg> canMsgs) {
        this.canMsgs = canMsgs;
        graphPanel.setGraph(canMsgs,varInfo);
    }

    private void bitStartFieldCaretUpdate(CaretEvent ignoredE) {
        calculateCurrentValue();
        setGraph(canMsgs);
    }

    private void bitLengthFieldCaretUpdate(CaretEvent ignoredE) {
        calculateCurrentValue();
        setGraph(canMsgs);
    }

    private void scaleFieldCaretUpdate(CaretEvent ignoredE) {
        calculateCurrentValue();
        setGraph(canMsgs);
    }

    private void offsetFieldCaretUpdate(CaretEvent ignoredE) {
        calculateCurrentValue();
        setGraph(canMsgs);
    }

    public static class VarDetails{
        public int bitStart;
        public int bitLength;
        public double scale;
        public double offset;
        public VarDetails(int bitStart, int bitLength, double scale, double offset) {
            this.bitStart = bitStart;
            this.bitLength = bitLength;
            this.scale = scale;
            this.offset = offset;
        }
    }
    @SuppressWarnings("Convert2MethodRef")
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner non-commercial license
        textField7 = new JTextField();
        valueLabel = new JLabel();
        label1 = new JLabel();
        bitStartField = new JTextField();
        graphPanel = new GraphPanel();
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
        bitStartField.addCaretListener(e -> bitStartFieldCaretUpdate(e));
        add(bitStartField, "cell 1 1");
        add(graphPanel, "cell 2 1 2 5,dock center");

        //---- label2 ----
        label2.setText("Bit Length");
        add(label2, "cell 0 2");

        //---- bitLengthField ----
        bitLengthField.setText("16");
        bitLengthField.setMinimumSize(new Dimension(20, 22));
        bitLengthField.addCaretListener(e -> bitLengthFieldCaretUpdate(e));
        add(bitLengthField, "cell 1 2");

        //---- label3 ----
        label3.setText("Scale");
        add(label3, "cell 0 3");

        //---- scaleField ----
        scaleField.setText("1");
        scaleField.setMinimumSize(new Dimension(20, 22));
        scaleField.addCaretListener(e -> scaleFieldCaretUpdate(e));
        add(scaleField, "cell 1 3");

        //---- label4 ----
        label4.setText("Offset");
        add(label4, "cell 0 4");

        //---- offsetField ----
        offsetField.setText("0");
        offsetField.setMinimumSize(new Dimension(20, 22));
        offsetField.addCaretListener(e -> offsetFieldCaretUpdate(e));
        add(offsetField, "cell 1 4");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner non-commercial license
    private JTextField textField7;
    public JLabel valueLabel;
    private JLabel label1;
    public JTextField bitStartField;
    private GraphPanel graphPanel;
    private JLabel label2;
    public JTextField bitLengthField;
    private JLabel label3;
    public JTextField scaleField;
    private JLabel label4;
    public JTextField offsetField;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
