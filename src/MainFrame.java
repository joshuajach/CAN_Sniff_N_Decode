import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.swing.*;
/*
 * Created by JFormDesigner on Tue Jun 27 21:14:40 CDT 2023
 */



/**
 * @author josh
 */
public class MainFrame extends JFrame {
    final TreeMap<Integer, ArrayList<CanMsg>> canMsgsMap;
    final ArrayList<MainFrame> mainFrames;
    public MainFrame(TreeMap<Integer, ArrayList<CanMsg>> canMsgsMap, ArrayList<MainFrame> mainFrames) {
        initComponents();
        this.canMsgsMap = canMsgsMap;
        this.mainFrames = mainFrames;
        rebuildCanIdSelectBox();
        startTimerTask();
    }
    //rebuild CanIdSelectBox
    public void rebuildCanIdSelectBox() {
        int currentSelection = canIdPanel.CanIdSelectBox.getSelectedItem()!=null?(int)canIdPanel.CanIdSelectBox.getSelectedItem():0;
        canIdPanel.CanIdSelectBox.removeAllItems();
        for (Integer canId : canMsgsMap.keySet()) //noinspection unchecked
            canIdPanel.CanIdSelectBox.addItem(canId);
        canIdPanel.CanIdSelectBox.setSelectedItem(currentSelection);
    }

    public void updateFrame(int id) {
        //noinspection DataFlowIssue
        if ((int)canIdPanel.CanIdSelectBox.getSelectedItem() == id) {
            canIdPanel.setCurrentValues(canMsgsMap.get(id));
            canIdPanel.updateGraphs();
        }
    }


    private void addFrame(ActionEvent ignoredE) {
        MainFrame mainFrame = new MainFrame(canMsgsMap, mainFrames);
        mainFrames.add(mainFrame);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        java.awt.EventQueue.invokeLater(() -> mainFrame.setVisible(true));
    }

    private void closeFrame(ActionEvent ignoredE) {
        //mainFrame.setVisible(false);
        mainFrames.remove(this);
        if(mainFrames.size()==0){
            System.exit(1);
        }
        dispose();
    }
    private void startTimerTask() {
        ActionListener taskPerformer = e -> {
            //calculates values for each canValue panel
            if(canIdPanel.newIDSelected) {
                canIdPanel.newIDSelected=false;
                if (canIdPanel.CanIdSelectBox.getSelectedItem() != null) {
                    int canId = (int) canIdPanel.CanIdSelectBox.getSelectedItem();
                    canIdPanel.setCurrentValues(canMsgsMap.get(canId));
                }
            }
            canIdPanel.repaint();
            this.repaint();
        };
        new Timer(100, taskPerformer).start();
    }

    private void thisWindowClosed(WindowEvent ignoredE) {
        mainFrames.remove(this);
        if(mainFrames.size()==0){
            System.exit(1);
        }
    }
    @SuppressWarnings("Convert2MethodRef")
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner non-commercial license
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        menuItem1 = new JMenuItem();
        menuItem2 = new JMenuItem();
        canIdPanel = new CanIdPanel();

        //======== this ========
        setMinimumSize(new Dimension(500, 350));
        setPreferredSize(new Dimension(500, 350));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                thisWindowClosed(e);
            }
        });
        var contentPane = getContentPane();
        contentPane.setLayout(new GridLayout());

        //======== menuBar1 ========
        {

            //======== menu1 ========
            {
                menu1.setText("Menu");

                //---- menuItem1 ----
                menuItem1.setText("New Window");
                menuItem1.addActionListener(e -> addFrame(e));
                menu1.add(menuItem1);
                menu1.addSeparator();

                //---- menuItem2 ----
                menuItem2.setText("Exit");
                menuItem2.addActionListener(e -> closeFrame(e));
                menu1.add(menuItem2);
            }
            menuBar1.add(menu1);
        }
        setJMenuBar(menuBar1);
        contentPane.add(canIdPanel);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner non-commercial license
    private JMenuBar menuBar1;
    private JMenu menu1;
    private JMenuItem menuItem1;
    private JMenuItem menuItem2;
    private CanIdPanel canIdPanel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
