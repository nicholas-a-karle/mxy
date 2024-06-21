package com.mxy.admin;

import javax.swing.*;

import com.mxy.objects.Manager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminDisplay {

    // Variable to store the user ID
    private String userId;
    // ArrayList to store the feed
    private ArrayList<String> feed;
    // Central text area to display feed contents
    private JTextArea textArea;

    //The Controller, used in buttons
    @SuppressWarnings("unused")
    private Controller controller;

    // Constructor to set up the UI
    public AdminDisplay(Controller controller) {
        // The admin controller
        this.controller = controller;

        // Initialize the feed ArrayList
        feed = new ArrayList<>();

        // Create the main frame
        JFrame frame = new JFrame("Admin Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Create the side panels
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        JPanel topPanel = new JPanel();
        JPanel centerPanel = new JPanel(new BorderLayout());

        // Set a preferred size for the side and bottom panels
        leftPanel.setPreferredSize(new Dimension(150, 0));
        rightPanel.setPreferredSize(new Dimension(150, 0));
        bottomPanel.setPreferredSize(new Dimension(0, 25));
        topPanel.setPreferredSize(new Dimension(0, 25));

        // Set a background color for visual distinction (optional)
        leftPanel.setBackground(Color.LIGHT_GRAY);
        rightPanel.setBackground(Color.LIGHT_GRAY);
        bottomPanel.setBackground(Color.DARK_GRAY);
        topPanel.setBackground(Color.DARK_GRAY);

        // Create components for the left panel
        JLabel userAccountLabel = new JLabel("User Account");
        JTextField userIdNumField = new JTextField("User ID Num");
        JLabel followingLabel = new JLabel("Following:");
        JLabel followersLabel = new JLabel("Followers:");
        JLabel userIdLabelLabel = new JLabel("Change User");
        JTextArea followingScrollingTextArea = new JTextArea(10, 10);
        JScrollPane followingScrollPaneLeft = new JScrollPane(followingScrollingTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JTextArea followersScrollingTextArea = new JTextArea(10, 10);
        JScrollPane followersScrollPaneLeft = new JScrollPane(followersScrollingTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JTextField userIdEntryField = new JTextField(); // Specify the number of columns
        JButton userIdSubmitButton = new JButton("Change User");

        // Set preferred size of text fields
        userIdNumField.setPreferredSize(new Dimension(100, 20));
        userIdEntryField.setPreferredSize(new Dimension(100, 20));

        // Set text fields to be non-editable (optional)
        userIdNumField.setEditable(false);
        followersScrollingTextArea.setEditable(false);
        followingScrollingTextArea.setEditable(false);

        // Add action listener to the user ID submit button
        userIdSubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Store the value of userIdEntryField into userId
                userId = userIdEntryField.getText();

                // Change the value of userIdEntryField to an empty string
                userIdEntryField.setText("");

                // Change the value of userIdNumField to the value stored in userId
                userIdNumField.setText(controller.getUsername(userId));
                followersScrollingTextArea.setText(controller.getFollowersListString(userId));
                followingScrollingTextArea.setText(controller.getFollowingListString(userId));
            }
        });

        // Bind Enter key to userIdSubmitButton's action
        userIdEntryField.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("ENTER"), "submitUserId");
        userIdEntryField.getActionMap().put("submitUserId", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userIdSubmitButton.doClick();
            }
        });

        // Set layout for the left panel and add components
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(userAccountLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add space between components
        leftPanel.add(userIdNumField);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add space between components
        leftPanel.add(followingLabel);
        leftPanel.add(followingScrollPaneLeft);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add space between components
        leftPanel.add(followersLabel);
        leftPanel.add(followersScrollPaneLeft);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add space between components
        leftPanel.add(userIdLabelLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add space between components
        leftPanel.add(userIdEntryField);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add space between components
        leftPanel.add(userIdSubmitButton);

        // Create the central text display area
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPaneCenter = new JScrollPane(textArea);

        // Create text entry area and submit button for the bottom panel
        JTextField textFieldBottom = new JTextField();
        JButton postSubmitButton = new JButton("Post");

        // Add action listener to the post submit button
        postSubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the text from textFieldBottom
                String postText = textFieldBottom.getText();

                // If userId == null do not add to feed
                if (userId != null) {
                    // Add the text to the feed
                    feed.add(controller.getUsername(userId) + ": " + postText);

                    // Update the central text area
                    updateTextArea();
                }

                // Clear the textFieldBottom
                textFieldBottom.setText("");
            }
        });

        // Bind Enter key to postSubmitButton's action
        textFieldBottom.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("ENTER"), "submitPost");
        textFieldBottom.getActionMap().put("submitPost", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                postSubmitButton.doClick();
            }
        });

        // Add text field and button to the bottom panel
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(textFieldBottom, BorderLayout.CENTER);
        bottomPanel.add(postSubmitButton, BorderLayout.EAST);

        // Add components to the center panel
        centerPanel.add(scrollPaneCenter, BorderLayout.CENTER);
        centerPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Create components for the right panel
        JLabel createUserLabel = new JLabel("User Creation");
        JTextField createUserTextField = new JTextField();
        JButton createUserButton = new JButton("Create User");

        JLabel createUsergroupLabel = new JLabel("Usergroup Creation");
        JTextField createUsergroupTextField = new JTextField();
        JButton createUsergroupButton = new JButton("Create Usergroup");

        JLabel addUsertoGroupLabel = new JLabel("Add user to Usergroups");
        JTextField addUserToUsergroupUserIdTextField = new JTextField();
        JTextField addUserToUsergroupUsergroupIdTextField = new JTextField();
        JButton addUserToUsergroupButton = new JButton("Add User to Usergroup");

        JLabel addGroupToGroupLabel = new JLabel("Add Group under Group");
        JTextField addUsergroupToUsergroupUsergroup1IdTextField = new JTextField();
        JTextField addUsergroupToUsergroupUsergroup2IdTextField = new JTextField();
        JButton addUsergroupToUsergroupButton = new JButton("Add Usergroup to Usergroup");
        
        JButton openAnalyticsButton = new JButton("Open Analytics");

        // Add action listeners for user creation, usergroup creation, user addition to usergroup, usergroup addition to usergroup, and open analytics
        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the username from createUserTextField
                String username = createUserTextField.getText();

                // Call controller method to create user
                controller.createUser(username, "");

                // Clear createUserTextField
                createUserTextField.setText("");
            }
        });

        createUsergroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the usergroup name from createUsergroupTextField
                String usergroupName = createUsergroupTextField.getText();

                // Call controller method to create usergroup
                controller.createGroup(usergroupName);

                // Clear createUsergroupTextField
                createUsergroupTextField.setText("");
            }
        });

        addUserToUsergroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get userId and usergroupId from text fields
                String userId = addUserToUsergroupUserIdTextField.getText();
                String usergroupId = addUserToUsergroupUsergroupIdTextField.getText();

                // Call controller method to add user to usergroup
                controller.addUserToGroup(userId, usergroupId);

                // Clear text fields
                addUserToUsergroupUserIdTextField.setText("");
                addUserToUsergroupUsergroupIdTextField.setText("");
            }
        });

        addUsergroupToUsergroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get usergroupIds from text fields
                String usergroupId1 = addUsergroupToUsergroupUsergroup1IdTextField.getText();
                String usergroupId2 = addUsergroupToUsergroupUsergroup2IdTextField.getText();

                // Call controller method to add usergroup to usergroup
                controller.addGrouptoGroup(usergroupId1, usergroupId2);

                // Clear text fields
                addUsergroupToUsergroupUsergroup1IdTextField.setText("");
                addUsergroupToUsergroupUsergroup2IdTextField.setText("");
            }
        });

        openAnalyticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call controller method to open analytics
                controller.openAnalytics();
            }
        });

        // Set preferred size of text fields
        createUserTextField.setPreferredSize(new Dimension(120, 20));
        createUsergroupTextField.setPreferredSize(new Dimension(120, 20));
        addUserToUsergroupUserIdTextField.setPreferredSize(new Dimension(120, 20));
        addUserToUsergroupUsergroupIdTextField.setPreferredSize(new Dimension(120, 20));
        addUsergroupToUsergroupUsergroup1IdTextField.setPreferredSize(new Dimension(120, 20));
        addUsergroupToUsergroupUsergroup2IdTextField.setPreferredSize(new Dimension(120, 20));

        // Set layout for the right panel and add components
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(createUserLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add space between components
        rightPanel.add(createUserTextField);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add space between components
        rightPanel.add(createUserButton);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add space between components
        rightPanel.add(createUsergroupLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add space between components
        rightPanel.add(createUsergroupTextField);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add space between components
        rightPanel.add(createUsergroupButton);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add space between components
        rightPanel.add(addUserToUsergroupButton);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add space between components
        rightPanel.add(addUsertoGroupLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add space between components
        rightPanel.add(addUserToUsergroupUsergroupIdTextField);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add space between components
        rightPanel.add(addUserToUsergroupButton);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add space between components
        rightPanel.add(addGroupToGroupLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add space between components
        rightPanel.add(addUsergroupToUsergroupUsergroup1IdTextField);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add space between components
        rightPanel.add(addUsergroupToUsergroupUsergroup2IdTextField);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add space between components
        rightPanel.add(addUsergroupToUsergroupButton);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add space between components
        rightPanel.add(openAnalyticsButton);

        // Add components to the frame
        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(rightPanel, BorderLayout.EAST);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(topPanel, BorderLayout.NORTH);

        // Make the frame visible
        frame.setVisible(true);
    }

    // Method to update the central text area with the contents of the feed
    private void updateTextArea() {
        StringBuilder feedContent = new StringBuilder();
        for (String post : feed) {
            feedContent.append(post).append("\n");
        }
        textArea.setText(feedContent.toString());
    }

    // Main method to create an instance of AdminDisplay
    public static void main(String[] args) {
        // Run the UI creation on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Database dtbs = new Database();
                Manager mngr = new Manager(dtbs);
                Controller ctrl = new Controller(dtbs, mngr);
                new AdminDisplay(ctrl);
            }
        });
    }
}
