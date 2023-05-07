package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class SimulationFrame extends JFrame implements ActionListener {
    private JFrame frame;
    private final CountDownLatch latch = new CountDownLatch(1);
    public JTextField text1;
    public JTextField text2;
    public JTextField text3;
    public JTextField text4;
    public JTextField text5;
    public JTextField text6;
    public JTextField text7;
    private JTextField timp;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JLabel label7;
    private JButton start;
    ArrayList<Integer> list;
    public SimulationFrame() {
        list=new ArrayList<>();
        frame = new JFrame("Simulare");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 300, 340);
        frame.getContentPane().setLayout(null);
        text1 = new JTextField();
        text2 = new JTextField();
        text3 = new JTextField();
        text4 = new JTextField();
        text5 = new JTextField();
        text6 = new JTextField();
        text7 = new JTextField();
        timp = new JTextField();
        label1 = new JLabel("Number of clients");
        label2 = new JLabel("Number of threads");
        label3 = new JLabel("Minimum arrival time");
        label4 = new JLabel("Maximum arrival time");
        label5 = new JLabel("Minimum service time");
        label6 = new JLabel("Maximum service time");
        label7 = new JLabel("Time limit");
        start = new JButton("Start");
        initializare();
        frame.setVisible(true);
    }

    public void initializare() {
        label1.setBounds(20, 20, 140, 20);
        frame.getContentPane().add(label1);
        label2.setBounds(20, 40, 140, 20);
        frame.getContentPane().add(label2);
        label3.setBounds(20, 60, 140, 20);
        frame.getContentPane().add(label3);
        label4.setBounds(20, 80, 140, 20);
        frame.getContentPane().add(label4);
        label5.setBounds(20, 100, 140, 20);
        frame.getContentPane().add(label5);
        label6.setBounds(20, 120, 140, 20);
        frame.getContentPane().add(label6);
        label7.setBounds(20, 140, 140, 20);
        frame.getContentPane().add(label7);
        text1.setBounds(160, 20, 100, 20);
        frame.getContentPane().add(text1);
        text2.setBounds(160, 40, 100, 20);
        frame.getContentPane().add(text2);
        text3.setBounds(160, 60, 100, 20);
        frame.getContentPane().add(text3);
        text4.setBounds(160, 80, 100, 20);
        frame.getContentPane().add(text4);
        text5.setBounds(160, 100, 100, 20);
        frame.getContentPane().add(text5);
        text6.setBounds(160, 120, 100, 20);
        frame.getContentPane().add(text6);
        text7.setBounds(160, 140, 100, 20);
        frame.getContentPane().add(text7);
        timp.setBounds(20, 180, 100, 20);
        start.setBounds(20, 160, 100, 20);
        start.addActionListener(this);
        frame.getContentPane().add(start);
    }

    public List<Integer> getInitValues (){
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Integer noOfClients = Integer.parseInt(this.text1.getText());
        Integer noOfThreads = Integer.parseInt(this.text2.getText());
        Integer timeLimit = Integer.parseInt(this.text7.getText());
        Integer minArrivalTime = Integer.parseInt(this.text3.getText());
        Integer maxArrivalTime = Integer.parseInt(this.text4.getText());
        Integer minServiceTime = Integer.parseInt(this.text5.getText());
        Integer maxServiceTime = Integer.parseInt(this.text6.getText());
        if (actionEvent.getSource() == start) {
            this.list.add(noOfClients);
            this.list.add(noOfThreads);
            this.list.add(timeLimit);
            this.list.add(minArrivalTime);
            this.list.add(maxArrivalTime);
            this.list.add(minServiceTime);
            this.list.add(maxServiceTime);
            latch.countDown();
        }
    }
}
