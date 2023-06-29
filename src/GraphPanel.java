import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
/*
 * Created by JFormDesigner on Wed Jun 28 14:01:38 CDT 2023
 */


/**
 * @author josh
 */
public class GraphPanel extends JPanel {
    ArrayList<CanMsg> canMsgs;
    CanValue.VarDetails varInfo;
    double min = 1000000;
    double max = -1000000;
    final long scrollHistory = 60000;

    public GraphPanel() {
        initComponents();
    }

    @Override
    protected void paintComponent(Graphics g) {
        try {
            super.paintComponent(g);
            FontMetrics fontMetrics = g.getFontMetrics();
            Dimension pSize = getSize();
            g.setColor(Color.black);
            //g.drawString(title, 8, 25);

            int drawIndex = canMsgs.size() - 1;
            long currentTime = canMsgs.get(drawIndex).timeStamp;
            double scaleX = (double) pSize.width / (scrollHistory); //60 seconds data window
            double scaleY = (double) pSize.height / (max - min);
            int offY = pSize.height - (int) ((-min) * scaleY);

            long timeOffset = currentTime - scrollHistory;
            int x1;
            int x2;
            int y1;
            int y2;
            g.setColor(Color.GREEN);
            g.drawLine(0, offY, pSize.width, offY); //draw zero line

            //Draw scrolling graph with 2 minute history
            g.setColor(Color.BLACK);
            String currentValue = "Data: " + getTransformedData(drawIndex);
            g.drawString(currentValue, pSize.width - fontMetrics.stringWidth(currentValue) - 5, 25);//draws current value
            //draw history
            while (drawIndex > 0 && currentTime - canMsgs.get(drawIndex).timeStamp < scrollHistory) {
                try {
                    x1 = (int) ((canMsgs.get(drawIndex).timeStamp - timeOffset) * scaleX);
                    x2 = (int) ((canMsgs.get(drawIndex - 1).timeStamp - timeOffset) * scaleX);
                    y1 = offY - (int) (getTransformedData(drawIndex) * scaleY);
                    y2 = offY - (int) (getTransformedData(drawIndex - 1) * scaleY);
                    g.drawLine(x1, y1, x2, y2);
                } catch (Exception ignored) {
                }
                drawIndex--;
            }
        } catch (Exception ignored) {
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner non-commercial license

        //======== this ========

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGap(0, 100, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGap(0, 60, Short.MAX_VALUE)
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    public void setGraph(ArrayList<CanMsg> canMsgs, CanValue.VarDetails varInfo) {
        this.canMsgs = canMsgs;
        this.varInfo = varInfo;
        try {
            double value = getTransformedData(canMsgs.size() - 1);
            min = max = value;

            for (int i = canMsgs.size() - 2; (i > canMsgs.size() - 2000) && i >= 0; i--) {
                value = getTransformedData(i);
                if (value < min) min = value;
                if (value > max) max = value;
            }
            updateGraph();
        }catch (NullPointerException ignored) {}

    }

    public void updateGraph() {
        try {
            double value = getTransformedData(canMsgs.size() - 1);
            if (value < min) min = value;
            if (value > max) max = value;
        } catch (NullPointerException ignored) {}
        repaint();
    }

    public double getTransformedData(int index) {
        long rawData = (canMsgs.get(index).data >> varInfo.bitStart) & ((1L << varInfo.bitLength) - 1);
        if ((rawData & (1L << (varInfo.bitLength))) > 1) { //type cast correction for negative numbers. This assumes that all incoming data is signed
            rawData = rawData | -1L << varInfo.bitLength;
        }
        return rawData * varInfo.scale + varInfo.offset;
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner non-commercial license
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
