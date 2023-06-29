import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;


public class CanSniff {
    //ArrayList<CanMsg> canMsgs = new ArrayList<>();
    final TreeMap<Integer, ArrayList<CanMsg>> canMsgsMap = new TreeMap<>();
    final ArrayList<MainFrame> mainFrames = new ArrayList<>();
    FileWriter csvWriter;
    final File csvFile = new File(Paths.get("").toAbsolutePath() + "\\CanData_"+System.currentTimeMillis()+".csv");

    public CanSniff() {


        MainFrame mainFrame = new MainFrame(canMsgsMap, mainFrames);
        mainFrames.add(mainFrame);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        java.awt.EventQueue.invokeLater(() -> mainFrame.setVisible(true));

        SerialPort[] comPort = SerialPort.getCommPorts();
        if (comPort.length > 0) {
            setupSerialCanParser(comPort);
        } else {
            parseLoggedData();
        }
    }

    private void parseLoggedData() {
        System.out.println("No Com Ports Found");
        {
            //todo replace with csv reader
            String dummyData = """
                    529 8 255 255 128 0 64 0 0 195\s
                    533 8 2 17 2 17 2 30 0 0\s
                    1200 8 39 16 39 16 39 16 39 16\s
                    538 8 0 0 0 0 0 0 0 0\s
                    513 8 22 115 103 249 39 16 0 255\s
                    533 8 2 17 2 17 2 30 0 0\s
                    1260 8 0 0 0 0 27 103 22 115\s
                    1200 8 39 16 39 16 39 16 39 16\s
                    513 8 22 115 103 249 39 16 0 255\s
                    533 8 2 17 2 17 2 30 0 0\s
                    922 8 8 0 0 0 0 0 0 0\s
                    1200 8 39 16 39 16 39 16 39 16\s
                    513 8 21 237 104 19 39 16 0 255\s
                    533 8 2 18 2 18 2 30 0 0\s
                    1200 8 39 16 39 16 39 16 39 16\s
                    1200 8 39 16 39 16 39 16 39 16\s
                    1260 8 0 0 0 0 27 103 21 237\s
                    529 8 255 255 128 0 64 0 0 199\s
                    538 8 0 0 0 0 0 0 0 0\s
                    529 8 255 255 128 0 64 0 0 200\s
                    533 8 2 19 2 19 2 29 0 0\s
                    1200 8 39 16 39 16 39 16 39 16\s
                    1200 8 39 16 39 16 39 16 39 16\s
                    512 8 39 5 38 255 39 14 0 0\s
                    533 8 2 19 2 19 2 29 0 0\s
                    561 8 255 0 255 255 0 0 0 0\s
                    1260 8 0 0 0 0 27 103 20 222\s
                    529 8 255 255 128 0 64 0 0 202\s
                    533 8 2 19 2 19 2 29 0 0\s
                    529 8 255 255 128 0 64 0 0 201\s
                    1200 8 39 16 39 16 39 16 39 16\s
                    512 8 39 7 39 3 39 16 0 0\s
                    533 8 2 20 2 20 2 29 0 0\s
                    529 8 255 255 128 0 64 0 0 203\s
                    538 8 0 0 0 0 0 0 0 0\s
                    533 8 2 20 2 20 2 29 0 0\s
                    1279 8 49 76 69 86 73 78 67 0\s
                    1200 8 39 16 39 16 39 16 39 16\s
                    1200 8 39 16 39 16 39 16 39 16\s
                    513 8 19 211 104 41 39 16 0 255\s
                    533 8 2 21 2 21 2 29 0 0\s
                    529 8 255 255 128 0 64 0 0 205\s
                    538 8 0 0 0 0 0 0 0 0\s
                    533 8 2 21 2 21 2 29 0 0\s
                    1072 8 201 33 0 0 0 0 0 0\s
                    1260 8 0 0 0 0 27 103 83 211\s
                    1200 8 39 16 39 16 39 16 39 16\s
                    533 8 2 21 2 21 2 29 0 0\s
                    922 8 8 0 0 0 0 0 0 0\s
                    1200 8 39 16 39 16 39 16 39 16\s
                    538 8 0 0 0 0 0 0 0 0\s
                    513 8 19 114 106 222 39 16 0 255\s
                    533 8 2 23 2 23 2 28 0 0\s
                    1200 8 39 16 39 16 39 16 39 16\s
                    1200 8 39 16 39 16 39 16 39 16\s
                    533 8 2 23 2 23 2 28 0 0\s
                    1260 8 0 0 0 0 27 103 83 114\s
                    529 8 255 255 128 0 64 0 0 209\s
                    513 8 19 114 106 222 39 16 0 64\s
                    538 8 0 0 0 0 0 0 0 0\s
                    533 8 2 24 2 24 2 28 0 0\s
                    1200 8 39 16 39 16 39 16 39 16\s
                    1200 8 39 16 39 16 39 16 39 16\s
                    513 8 19 114 106 222 39 16 0 32\s
                    529 8 255 255 128 0 64 0 0 211\s
                    533 8 2 24 2 24 2 28 0 0\s
                    561 8 255 0 255 255 0 0 0 0\s
                    1260 8 0 0 0 0 27 103 82 252\s
                    513 8 19 114 106 222 39 16 0 10\s
                    1200 8 39 16 39 16 39 16 39 16\s
                    533 8 2 24 2 24 2 28 0 0\s
                    512 8 39 12 39 10 39 21 0 0\s
                    1200 8 39 16 39 16 39 16 39 16\s
                    """;
            StringBuilder stringMsg1 = new StringBuilder();
            int pos = 0;
            int[] msg = new int[13];

            byte[] newData = dummyData.getBytes();
            long timeStamp=0;

            //System.out.println("Received data of size: " + newData.length);
            for (byte newDatum : newData) {
                //System.out.print((char) (newData[i]));
                if (newDatum == '\n') {
                    //save canmsg and start new one
                    int id = msg[0];
                    int length = msg[1];
                    long data = 0x0;
                    for (int j = 0; j < 8; j++) {
                        data = (data << 8) | msg[j + 2];
                    }
                    CanMsg temp = new CanMsg(id, length, data, timeStamp += 100);
                    saveParsedData(temp);
                    pos = 0;
                    stringMsg1 = new StringBuilder();
                } else if (newDatum == ' ') {
                    msg[pos] = Integer.parseInt(stringMsg1.toString());
                    //System.out.print("T"+msg[pos]+" ");
                    pos++;
                    stringMsg1 = new StringBuilder();
                } else {
                    stringMsg1.append((char) newDatum);
                }
            }
        }
    }

    private void setupSerialCanParser(SerialPort[] comPort) {
        comPort[0].openPort();
        comPort[0].setBaudRate(115200);
        comPort[0].addDataListener(new SerialPortDataListener() {
            String stringMsg = "";
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
            }
            @Override
            public void serialEvent(SerialPortEvent event) {
                byte[] newData = event.getReceivedData();
                for (byte newDatum : newData) {
                    if (newDatum == '\n') {
                        System.out.println(stringMsg);
                        Scanner sc = new Scanner(stringMsg);

                        int id = sc.hasNextInt() ? sc.nextInt() : 0;
                        int length = sc.hasNextInt() ? sc.nextInt() : 0;
                        long data = 0;
                        short[] bytes = new short[8];
                        for (int j = 0; j < 8; j++) {
                            bytes[j] = sc.hasNextShort() ? sc.nextShort() : 0;
                            data = (data << 8) | bytes[j];
                        }
                        CanMsg temp = new CanMsg(id, length, data, System.currentTimeMillis());
                        saveParsedData(temp);
                        try {
                            csvWriter = new FileWriter(csvFile, true);
                            StringBuilder line = new StringBuilder(temp.timeStamp + "," + temp.id + "," + temp.length);
                            for (int j = 0; j < 8; j++) {
                                line.append(",").append(String.valueOf(bytes[j]));
                            }
                            line.append("\r\n");
                            csvWriter.write(line.toString());
                            csvWriter.close();

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        stringMsg = "";
                    } else {
                        stringMsg += (char) newDatum;
                    }
                }
            }
        });
    }

    private void saveParsedData(CanMsg temp) {
        int id = temp.id;
        if (canMsgsMap.containsKey(id)) {
            canMsgsMap.get(id).add(temp);
        } else {
            ArrayList<CanMsg> tempList = new ArrayList<>();
            tempList.add(temp);
            canMsgsMap.put(id, tempList);
            //rebuild all mainFrames canIdSelectBox
            for (MainFrame mainFrame : mainFrames) {
                mainFrame.rebuildCanIdSelectBox();
            }
        }
        for (MainFrame mainFrame : mainFrames) {
            mainFrame.updateFrame(temp.id);
        }
    }

    public static void main(String[] args) {
        FlatLightLaf.setup();
        new CanSniff();
    }
}

class CanMsg {
    public final int id;
    public final int length;
    public final long data;
    public final long timeStamp;

    public CanMsg(int id, int length, long data, long timeStamp) {
        this.id = id;
        this.length = length;
        this.data = data;
        this.timeStamp = timeStamp;
    }
}
