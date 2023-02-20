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
    private JLabel sharedDataLabel = new JLabel();
    private JLabel messageDataLabel = new JLabel();
    private JFrame frame;
    private JTextField inputKeyTextField = new JTextField();

    private String messageDataString = "Default Message Data - Client #" + CLIENT_NUMBER;

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
        crudClient.connect("172.29.42.26", 6990);

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
        inputReadButton.getHorizontalAlignment();
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
    }

    private void popUpAddView() {
        String uncheckedString = JOptionPane.showInputDialog(frame, "Input message:", "Client #" + CLIENT_NUMBER);
        if (uncheckedString != null) {
            messageDataString = uncheckedString;
            messageDataLabel.setText(messageDataString);
            sendMessageData();
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
