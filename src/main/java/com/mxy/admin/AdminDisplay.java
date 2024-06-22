package com.mxy.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminDisplay {

    @SuppressWarnings("unused")
    private Controller controller;
    private ArrayList<String> consoleLines;
    private ArrayList<String> feedLines;

    // Text Fields
    private JTextArea consoleTextArea;
    private JTextArea feedTextArea;

    public AdminDisplay(Controller controller) {
        this.controller = controller;

        // Initialize consoleLines and feedLines
        consoleLines = new ArrayList<>();
        consoleLines.add("Admin Console Initiated");
        feedLines = new ArrayList<>();
        feedLines.add("mxy Feed");

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
            }
        });

        // Add empty filler component to push buttons to top
        JPanel emptyPanel = new JPanel();
        emptyPanel.setBackground(Color.LIGHT_GRAY);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        controlPanel.add(emptyPanel, gbc);

        // Open New Frame Buttons
        // Open Analytics Frame
        JButton openAnalyticsButton = new JButton("Open Analytics");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        controlPanel.add(openAnalyticsButton, gbc);

        // Add Event Listener to Open Analytics
        openAnalyticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAnalytics();
            }
        });

        // Open Users List Frame
        JButton listUsersButton = new JButton("List Users");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        controlPanel.add(listUsersButton, gbc);

        // Add Event Listener to List Users
        listUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listUsers();
            }
        });

        // Open Group List Frame
        JButton listGroupsButton = new JButton("List Groups");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        controlPanel.add(listGroupsButton, gbc);

        // Add Event Listener to List Groups
        listGroupsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listGroups();
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

    protected void listGroups() {
        GroupListDisplay groupListDisplay = new GroupListDisplay(controller);
    }

    protected void listUsers() {
        UsersListDisplay usersListDisplay = new UsersListDisplay(controller);
    }

    protected void openAnalytics(String userId) {
        AnalyticsDisplay analyticsDisplay = new AnalyticsDisplay(controller);
    }

    protected void joinGroup(String text) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'joinGroup'");
    }

    protected void createGroup(String text) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createGroup'");
    }

    protected void followUser(String text) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'followUser'");
    }

    protected void createUser(String text) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createUser'");
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateConsoleListFromController'");
    }

    public void updateConsoleDisplayFromList() {
        consoleTextArea.setText(String.join("\n", consoleLines));
    }

    public void updateFeed() {
        updateFeedListFromController();
        updateFeedDisplayFromList();
    }
    
    private void updateFeedListFromController() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateFeedListFromController'");
    }

    public void updateFeedDisplayFromList() {
        feedTextArea.setText(String.join("\n", feedLines));
    }
}
