package com.mxy.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AdminDisplay {

    private Controller controller;
    private List<String> consoleLines;
    private List<String> feedLines;

    // Text Fields
    private JTextArea consoleTextArea;
    private JTextArea feedTextArea;

    public AdminDisplay(Controller controller) {
        this.controller = controller;

        // Initialize consoleLines and feedLines
        consoleLines = new ArrayList<>();
        feedLines = new ArrayList<>();

        // Create JFrame
        JFrame frame = new JFrame("Admin Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 600));
        
        // Create main panel with GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());

        // Define this
        GridBagConstraints gbc;


        // Console (not really the console just admin notifications)
        //#region

        // Console on left side
        JPanel consolePanel = new JPanel(new GridBagLayout());
        consolePanel.setBackground(Color.DARK_GRAY);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;  // First column
        gbc.gridy = 0;  // First row
        gbc.gridwidth = 1;  // Take up 1 column width
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        mainPanel.add(consolePanel, gbc);

        // Console display JTextArea
        consoleTextArea = new JTextArea(15, 30);
        consoleTextArea.setLineWrap(true);
        consoleTextArea.setWrapStyleWord(true);
        consoleTextArea.setEditable(false);
        consoleTextArea.setBackground(Color.DARK_GRAY);
        consoleTextArea.setForeground(Color.YELLOW);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        consolePanel.add(new JScrollPane(consoleTextArea), gbc);

        // End Console
        //#endregion

        // Feed
        //#region
        // Feed in middle
        JPanel feedPanel = new JPanel(new GridBagLayout());
        feedPanel.setBackground(Color.WHITE);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;  // Second column
        gbc.gridy = 0;  // First row
        gbc.gridwidth = 1;  // Take up 1 column width
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        mainPanel.add(feedPanel, gbc);

        // Feed display JTextArea
        feedTextArea = new JTextArea(15, 30);
        feedTextArea.setLineWrap(true);
        feedTextArea.setWrapStyleWord(true);
        feedTextArea.setEditable(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        feedPanel.add(new JScrollPane(feedTextArea), gbc);

        // End Feed
        //#endregion

        // Control
        //#region

        // Control Panel on right side
        JPanel controlPanel = new JPanel(new GridBagLayout());
        controlPanel.setBackground(Color.LIGHT_GRAY);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;  // Third column
        gbc.gridy = 0;  // First row
        gbc.gridwidth = 1;  // Take up 1 column width
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        mainPanel.add(controlPanel, gbc);

        // User Creation
        JLabel createUserLabel = new JLabel("Create User");
        JTextField createUserTextField = new JTextField(25);
        JButton createUserSubmitButton = new JButton("Submit");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        controlPanel.add(createUserLabel, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        controlPanel.add(createUserTextField, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        controlPanel.add(createUserSubmitButton, gbc);

        // Create User Event Listener
        createUserSubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createUser(createUserTextField.getText());
                updateConsole();
                updateFeed();
            }
        });

        // Follow
        JLabel followLabel = new JLabel("Follow User");
        JTextField followTextField = new JTextField(25);
        JButton followSubmitButton = new JButton("Submit");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        controlPanel.add(followLabel, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        controlPanel.add(followTextField, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        controlPanel.add(followSubmitButton, gbc);

        // Follow Event Listener
        followSubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                followUser(followTextField.getText());
                updateConsole();
                updateFeed();
            }
        });

        // Group Creation
        JLabel createGroupLabel = new JLabel("Create Group");
        JTextField createGroupTextField = new JTextField(25);
        JButton createGroupSubmitButton = new JButton("Submit");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        controlPanel.add(createGroupLabel, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        controlPanel.add(createGroupTextField, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        controlPanel.add(createGroupSubmitButton, gbc);

        // Create Group Event Listener
        createGroupSubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createGroup(createGroupTextField.getText());
                updateConsole();
                updateFeed();
            }
        });

        // Join Group
        JLabel joinGroupLabel = new JLabel("Join Group");
        JTextField joinGroupTextField = new JTextField(25);
        JButton joinGroupSubmitButton = new JButton("Submit");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        controlPanel.add(joinGroupLabel, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        controlPanel.add(joinGroupTextField, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        controlPanel.add(joinGroupSubmitButton, gbc);

        // Join Group Event Listener
        joinGroupSubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                joinGroup(joinGroupTextField.getText());
                updateConsole();
                updateFeed();
            }
        });

        // Post Creation
        JLabel postLabel = new JLabel("Post");
        JTextField postTextField = new JTextField(25);
        JButton postSubmitButton = new JButton("Submit");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        controlPanel.add(postLabel, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        controlPanel.add(postTextField, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        controlPanel.add(postSubmitButton, gbc);

        // Post Event Listener
        postSubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                post(postTextField.getText());
                updateConsole();
                updateFeed();
            }
        });

                // Switch User
                JLabel switchLabel = new JLabel("Change User");
                JTextField switchTextField = new JTextField(25);
                JButton switchSubmitButton = new JButton("Submit");
                gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 10;
                gbc.gridwidth = 1;
                gbc.insets = new Insets(5, 5, 5, 5);
                gbc.anchor = GridBagConstraints.NORTHWEST;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                controlPanel.add(switchLabel, gbc);
                gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 11;
                gbc.gridwidth = 1;
                gbc.insets = new Insets(5, 5, 5, 5);
                gbc.anchor = GridBagConstraints.NORTHWEST;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                controlPanel.add(switchTextField, gbc);
                gbc = new GridBagConstraints();
                gbc.gridx = 1;
                gbc.gridy = 11;
                gbc.gridwidth = 1;
                gbc.insets = new Insets(5, 5, 5, 5);
                gbc.anchor = GridBagConstraints.NORTHWEST;
                controlPanel.add(switchSubmitButton, gbc);
        
                // Switch Event Listener
                switchSubmitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        switchUser(switchTextField.getText());
                        updateConsole();
                        updateFeed();
                    }
                });

        // Add empty filler component to push buttons to top
        JPanel emptyPanel = new JPanel();
        emptyPanel.setBackground(Color.LIGHT_GRAY);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        controlPanel.add(emptyPanel, gbc);

        // Open New Frame Buttons
        // Open Analytics Frame
        JButton openAnalyticsButton = new JButton("Open Analytics");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        controlPanel.add(openAnalyticsButton, gbc);

        // Add Event Listener to Open Analytics
        openAnalyticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAnalytics();
                updateConsole();
                updateFeed();
            }
        });

        // Open Users List Frame
        JButton listUsersButton = new JButton("List Users");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 14;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        controlPanel.add(listUsersButton, gbc);

        // Add Event Listener to List Users
        listUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listUsers();
                updateConsole();
                updateFeed();
            }
        });

        // Open Group List Frame
        JButton listGroupsButton = new JButton("List Groups");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 15;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        controlPanel.add(listGroupsButton, gbc);

        // Add Event Listener to List Groups
        listGroupsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listGroups();
                updateConsole();
                updateFeed();
            }
        });

        // End Control
        //#endregion

        // Add mainPanel to frame
        frame.getContentPane().add(mainPanel);
        
        // Pack and display the frame
        frame.pack();
        frame.setVisible(true);

        fullUpdate();
    }

    protected void switchUser(String text) {
        System.out.println("Attempting to Switch User to " + text);
        controller.setUser(text);
    }

    protected void post(String text) {
        System.out.println("Attempting to post " + text);
        controller.addPost(text);
    }

    protected void listGroups() {
        @SuppressWarnings("unused")
        GroupListDisplay groupListDisplay = new GroupListDisplay(controller);
    }

    protected void listUsers() {
        @SuppressWarnings("unused")
        UsersListDisplay usersListDisplay = new UsersListDisplay(controller);
    }

    protected void openAnalytics() {
        @SuppressWarnings("unused")
        AnalyticsDisplay analyticsDisplay = new AnalyticsDisplay(controller);
    }

    protected void joinGroup(String groupId) {
        controller.addUserToGroup(groupId);
    }

    protected void createGroup(String groupName) {
        controller.createGroup(groupName);
    }

    protected void followUser(String userId) {
        controller.addFollow(userId);
    }

    protected void createUser(String username) {
        controller.createUser(username, "");
    }

    public void fullUpdate() {
        updateConsole();
        updateFeed();
    }

    public void updateConsole() {
        updateConsoleListFromController();
        updateConsoleDisplayFromList();
    }

    private void updateConsoleListFromController() {
        // TODO
        consoleLines = controller.getConsole();
    }

    public void updateConsoleDisplayFromList() {
        consoleTextArea.setText(String.join("\n", consoleLines));
    }

    public void updateFeed() {
        updateFeedListFromController();
        updateFeedDisplayFromList();
    }
    
    private void updateFeedListFromController() {
        feedLines = controller.getFeed();
    }

    public void updateFeedDisplayFromList() {
        feedTextArea.setText(String.join("\n", feedLines));
    }
}
