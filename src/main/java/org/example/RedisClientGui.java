package org.example;

import org.example.storage.CrudHandler;
import org.example.storage.SubListener;
import org.example.storage.impl.RedisClient;

import javax.swing.*;
import java.awt.*;

public class RedisClientGui implements SubListener {
    private static final int CLIENT_NUMBER = 1;
    private final JLabel clientAliveLabel = new JLabel("", SwingConstants.CENTER);
    private final JButton connectionButton = new JButton("Disconnect Client");
    private boolean isDisconnected;
    private JFrame frame;
    private JTextField inputKeyTextField = new JTextField();

    private String outputValue = null;
    JTextArea outputHolder;

    private final CrudHandler crudClient;

    public RedisClientGui(boolean isDisconnected, CrudHandler crudClient) {
        this.isDisconnected = isDisconnected;
        this.crudClient = crudClient;
    }


    public static void main(String[] args) {
        new RedisClientGui();
    }

    public RedisClientGui() {
        // Create the implementation of the CRUD-interface.
        // Shall be interchangeable in the future.
        crudClient = new RedisClient();
        // TODO: remember to set your own virtual IP Address
        // virtual IP at work 172.29.42.26
        crudClient.connect("127.0.0.1", 6990);

        createTestingWindow();
    }

    @SuppressWarnings("squid:S1192")
    private void createTestingWindow() {
        frame = new JFrame("Client #" + CLIENT_NUMBER);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBounds(new Rectangle(200, 500, 300, 300));
        //  frame.getContentPane().add(createDisconnectButton(), BorderLayout.NORTH);
        frame.getContentPane().add(createInputKeyView(), BorderLayout.NORTH);
        frame.getContentPane().add(createOutPutMessage(), BorderLayout.CENTER);
        frame.getContentPane().add(createView(), BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private JPanel createInputKeyView() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBounds(50,50,100,100);
        JLabel inputKeyLabel = new JLabel("input key:");
        inputKeyLabel.getHorizontalAlignment();

        JButton inputReadButton = new JButton("Read");
        inputReadButton.setSize(10,10);
        inputReadButton.addActionListener(e -> getDataFromRead());

        panel.add(inputKeyLabel, BorderLayout.WEST);
        panel.add(inputKeyTextField, BorderLayout.CENTER);
        panel.add(inputReadButton, BorderLayout.EAST);
        return panel;
    }

    private  JPanel createOutPutMessage(){
        JPanel panel2 = new JPanel(new BorderLayout());
        outputHolder = new JTextArea();
        outputHolder.setSize(30,30);
        outputHolder.setVisible(true);
        panel2.add(outputHolder);
        return panel2;
    }

    private void getDataFromRead() {
        String textFieldData = inputKeyTextField.getText();
        if(textFieldData != null){
            outputValue = crudClient.read(textFieldData);
            outputHolder.setText(outputValue);
            System.out.println(outputValue);
        }

    }

    private  JPanel createView(){
        JPanel panel3 = new JPanel(new FlowLayout());
        JButton buttonAddUpdate = new JButton("Add/Update");
        buttonAddUpdate.addActionListener(e -> popUpAddView());

        JButton buttonDelete = new JButton("Delete");
        buttonDelete.addActionListener(e ->  popUpDeleteView());

        panel3.add(buttonAddUpdate);
        panel3.add(buttonDelete);
        return panel3;
    }

    private void popUpDeleteView() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        JLabel label = new JLabel("Enter user id:");
        JTextField deleteKey = new JTextField(2);
        panel.add(label);
        panel.add(deleteKey);

        int showDialog = JOptionPane.showConfirmDialog(frame, panel, "Delete user", JOptionPane.YES_NO_OPTION);
        if(showDialog == JOptionPane.YES_OPTION){
            if(deleteKey.getText() != null){
                 String userId = crudClient.delete(deleteKey.getText());
                JOptionPane.showMessageDialog(frame, "You have successfully deleted user: " + userId,"", JOptionPane.INFORMATION_MESSAGE);
            }

        }else if (showDialog == JOptionPane.NO_OPTION) {
               JOptionPane.showMessageDialog(frame, "You pressed NO button");
        }
    }

    private void popUpAddView() {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        JLabel labelKey = new JLabel("ID:");
        JTextField textFieldId = new JTextField(12);
        JLabel labelUsername = new JLabel("Username:");
        JTextField textFieldUsername = new JTextField(12);

        panel.add(labelKey);
        panel.add(labelUsername);
        panel.add(textFieldId);
        panel.add(textFieldUsername);
        int showDialog = JOptionPane.showConfirmDialog(frame, panel,"Create user", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (showDialog == JOptionPane.OK_OPTION){
            if(textFieldId.getText() != null && textFieldUsername.getText() != null){
                crudClient.create(textFieldId.getText(), textFieldUsername.getText());
            } else {
                JOptionPane.showMessageDialog(frame, "Something went wrong, please try again","Error", JOptionPane.WARNING_MESSAGE);
            }
        } else if (showDialog == JOptionPane.CANCEL_OPTION) {
                JOptionPane.showMessageDialog(frame, "You pressed cancel button");
        }

    }

    private JButton createDisconnectButton() {
        connectionButton.addActionListener(e -> {/** Do something **/});
        connectionButton.setBackground(Color.RED);
        return connectionButton;
    }

    private void sendMessageData() {
        //** do something **/
    }

    @Override
    public void onMessageReceived(String channel, String message) {

    }
}
