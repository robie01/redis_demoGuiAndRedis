package org.example;

import redis.clients.jedis.Jedis;

import javax.swing.*;
import java.awt.*;

public class RedisClientGui  {

    static PubSub client = new PubSub();
    private static String outputFromInputKey;
    private static JTextField inputKeyTextField = new JTextField();

    public static void main(String[] args) {
        createGui();
        try{
            Jedis client = new Jedis("127.0.0.1", 7000);
            client.connect();
            System.out.println("Connection successful" + client.ping());
        } catch (Exception e){
            System.out.println(e);
        }
    }

    private static void createGui(){
        JFrame frame = new JFrame("my Gui");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.getContentPane().add(firstBorderLayout(), BorderLayout.NORTH);
        frame.getContentPane().add(createOutPutMessage(), BorderLayout.CENTER);
        frame.getContentPane().add(createView(),BorderLayout.SOUTH);
        frame.setVisible(true);

    }

    private static JPanel createOutPutMessage(){
        JPanel panel2 = new JPanel(new BorderLayout());
        JTextArea display = new JTextArea("Output here");
        display.setSize(30,30);
        display.setVisible(true);
        panel2.add(display);
        return panel2;
    }

    private static JPanel createView(){
        JPanel panel3 = new JPanel(new FlowLayout());
        JButton buttonAddUpdate = new JButton("Add/Update");
        buttonAddUpdate.addActionListener(e -> popUpAddView());

        JButton buttonDelete = new JButton("Delete");
        buttonDelete.addActionListener(e ->  popUpDeleteView());

        panel3.add(buttonAddUpdate);
        panel3.add(buttonDelete);
        return panel3;
    }

    private static JPanel createDeleteView(){
        JPanel panel4 = new JPanel(new BorderLayout());
        JButton button = new JButton("Delete");
        button.addActionListener(e -> popUpAddView());
        button.getHorizontalAlignment();
        button.getIcon();
        panel4.add(button);
        return panel4;
    }
    private static void popUpDeleteView() {
        int response = JOptionPane.showConfirmDialog(null, "Are you sure, you want to delete?", "Delete",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    }

    private static void popUpAddView() {
        int response = JOptionPane.showConfirmDialog(null, "This view is use for create and update.", "Add/Update",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
    }

    private static JPanel firstBorderLayout() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBounds(50,50,100,100);
        JLabel inputKeyLabel = new JLabel("input key:");
        inputKeyLabel.getHorizontalAlignment();

        JButton inputReadButton = new JButton("Read");
        inputReadButton.setSize(10,10);
        inputReadButton.getHorizontalAlignment();
        inputReadButton.addActionListener(e -> getData());

        panel.add(inputKeyLabel, BorderLayout.WEST);
        panel.add(inputKeyTextField, BorderLayout.CENTER);
        panel.add(inputReadButton, BorderLayout.EAST);
        return panel;
    }

     static void getData() {
        String inputData = inputKeyTextField.getText();
        if(inputData != null){
            outputFromInputKey = client.read(inputData);
           System.out.println(outputFromInputKey);
        }
    }




}                   
