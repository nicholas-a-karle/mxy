package com.mxy.admin;

import javax.swing.*;

import java.awt.*;

public class AnalyticsDisplay {

    private Controller controller;
    private JFrame frame;

    public AnalyticsDisplay(Controller controller) {
        this.controller = controller;

        // Create the main frame
        frame = new JFrame("Analytics Display");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(4, 1)); // Grid layout for four metrics

        // Display number of users
        JLabel numUsersLabel = new JLabel("Number of Users: " + controller.getMetricNumUsers());
        numUsersLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(numUsersLabel);

        // Display number of groups
        JLabel numGroupsLabel = new JLabel("Number of Groups: " + controller.getMetricNumGroups());
        numGroupsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(numGroupsLabel);

        // Display user feed size
        JLabel feedSizeLabel = new JLabel("User Feed Size: " + controller.getMetricFeedSize());
        feedSizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(feedSizeLabel);

        // Display user feed positiveness
        JLabel feedPositivenessLabel = new JLabel("User Feed Positiveness: " + controller.getMetricPositiveFeedProportion());
        feedPositivenessLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(feedPositivenessLabel);

        // Make the frame visible
        frame.setVisible(true);
    }

}
