package com.mxy.admin;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UsersListDisplay {

    @SuppressWarnings("unused")
    private Controller controller;
    private JFrame frame;
    private JTextArea textArea;

    public UsersListDisplay(Controller controller) {
        this.controller = controller;

        // Initialize JFrame
        frame = new JFrame("Users List");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 300));

        // Create JTextArea for displaying users
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        // Create JScrollPane and add textArea to it
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Add scrollPane to JFrame
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Pack and display the frame
        frame.pack();
        frame.setVisible(true);

        // Example: Displaying some dummy data
        displayUsers(controller.getUsersList()); // Assuming getUsers() returns a list of users
    }

    // Method to display users in the text area
    public void displayUsers(List<String> users) {
        StringBuilder sb = new StringBuilder();
        System.out.println("\n\n"+users.size()+"\n\n");
        for (String user : users) {
            System.out.println(user);
            sb.append(user).append("\n");
        }
        textArea.setText(sb.toString());
    }
}
