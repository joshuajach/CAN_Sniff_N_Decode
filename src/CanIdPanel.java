import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import net.miginfocom.swing.*;
/*
 * Created by JFormDesigner on Tue Jun 27 21:16:00 CDT 2023
 */



/**
 * @author josh
 */
public class CanIdPanel extends JPanel {
    public boolean newIDSelected = false;
    ArrayList<CanMsg> canMsgs;
    public CanIdPanel() {
        initComponents();
        canValue1.bitStartField.setText("0");
        canValue2.bitStartField.setText("16");
        canValue3.bitStartField.setText("32");
        canValue4.bitStartField.setText("48");
    }

    private void CanIdSelectBox(ActionEvent e) {
        // TODO add your code here
        newIDSelected = true;
    }
    public void setCurrentValues(ArrayList<CanMsg> canMsgs) {
        this.canMsgs = canMsgs;
        canValue1.setGraph(canMsgs);
        canValue2.setGraph(canMsgs);
        canValue3.setGraph(canMsgs);
        canValue4.setGraph(canMsgs);
        calculateCurrentValues();
    }

    public void calculateCurrentValues(){

        canValue1.calculateCurrentValue();
        canValue2.calculateCurrentValue();
        canValue3.calculateCurrentValue();
        canValue4.calculateCurrentValue();

        String label ="";
        int index = canMsgs.size()-1;
        long data = canMsgs.get(index).data;
        for(int i=0;i<canMsgs.get(index).length;i++) {
            label = String.format(" x%02X",data & 0xFF)+label;
            data = data >> 8;
        }
        dataPackLabel.setText(label);
    }
    public void setGraphs(ArrayList<CanMsg> canMsgs) {
        canValue1.setGraph(canMsgs);
        canValue2.setGraph(canMsgs);
        canValue3.setGraph(canMsgs);
        canValue4.setGraph(canMsgs);
    }
    public void updateGraphs() {
        canValue1.updateGraph();
        canValue2.updateGraph();
        canValue3.updateGraph();
        canValue4.updateGraph();
    }
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner non-commercial license
        label1 = new JLabel();
        CanIdSelectBox = new JComboBox();
        dataPackLabel = new JLabel();
        canValue1 = new CanValue();
        canValue2 = new CanValue();
        canValue3 = new CanValue();
        canValue4 = new CanValue();

        //======== this ========
        setLayout(new MigLayout(
            "insets 0,hidemode 3",
            // columns
            "[350,grow,fill]0" +
            "[grow,fill]",
            // rows
            "2[]2" +
            "[grow,fill]0" +
            "[grow,fill]0"));

        //---- label1 ----
        label1.setText("CanID");
        add(label1, "cell 0 0");

        //---- CanIdSelectBox ----
        CanIdSelectBox.addActionListener(e -> CanIdSelectBox(e));
        add(CanIdSelectBox, "cell 0 0");

        //---- dataPackLabel ----
        dataPackLabel.setText("text");
        add(dataPackLabel, "cell 1 0,alignx center,growx 0");
        add(canValue1, "cell 0 1");
        add(canValue2, "cell 1 1");
        add(canValue3, "cell 0 2");
        add(canValue4, "cell 1 2");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner non-commercial license
    private JLabel label1;
    public JComboBox CanIdSelectBox;
    private JLabel dataPackLabel;
    private CanValue canValue1;
    private CanValue canValue2;
    private CanValue canValue3;
    private CanValue canValue4;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
