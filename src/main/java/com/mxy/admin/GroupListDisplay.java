package com.mxy.admin;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GroupListDisplay {

    private Controller controller;
    private JFrame frame;
    private JTextArea textArea;

    public GroupListDisplay(Controller controller) {
        this.controller = controller;

        // Initialize JFrame
        frame = new JFrame("Group List");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 300));

        // Create JTextArea for displaying groups
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
        displayGroups(controller.getGroupsList()); // Assuming getGroups() returns a list of groups
    }

    // Method to display groups in the text area
    public void displayGroups(List<String> groups) {
        StringBuilder sb = new StringBuilder();
        for (String group : groups) {
            sb.append(group).append("\n");
        }
        textArea.setText(sb.toString());
    }
}
