package com.mxy.admin;

import org.bson.types.ObjectId;

import com.mxy.objects.Manager;

/**
 * This class is meant to do the grunt work of control over the system
 * It will create the objects out of the database documents and use them across the program
 * This is the primary controller
 * In theory it would run on the server for admin use
 * For the client side version of this look at the Model class
 * 
 * It will 'recieve' communications from the Model
 * In this hypothetical this controller exists on the same machine as the database and objects folder
 * 
 * In the admin panel, you can select a user and view their profile and feed from the panel
 * There is no login required for this
 */

/** Early Requirements
 * 
 * 1. Create Users
 * 2. Create Usergroups
 * 3. Restrict Users to 1 Usergroup Each
 * 4. User Follow User
 * 5. Post short posts
 * 6. Output total number of Users
 * 7. Output total number of Usergroups
 * 8. Output total number of posts in each user's feed
 * 9. Output the total number of 'positive' messages
 * 
 */

public class Controller {

    private Database database;
    private Manager manager;

    public Controller(Database database, Manager manager) {
        this.database = database;
        this.manager = manager;
    }

    public static String createHashword(String input) {
        return "";
    }

    /**
     * Creates a user from admin-side
     * @param username
     * @param password
     * @throws Exception 
     */
    public void createUser(String username, String password) {
        try {
            database.registerUser(username, createHashword(username + password), java.time.Instant.now().toEpochMilli());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param groupname
     */
    public void createGroup(String groupname) {
        try {
            database.createUserGroup(groupname);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param userId
     * @param groupId
     */
    public void addUserToGroup(ObjectId userId, ObjectId groupId) {
        try {
            database.addUserToGroup(userId, groupId);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void addUserToGroup(String userId, String groupId) {
        addUserToGroup(new ObjectId(userId), new ObjectId(groupId));
    }

    /**
     * 
     * @param followerUserId
     * @param followedUserId
     */
    public void addFollow(ObjectId followerUserId, ObjectId followedUserId) {
        try {
            database.followUser(followerUserId, followedUserId);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param userId
     * @param text
     */
    public void addPost(ObjectId userId, String text) {
        try {
            database.createPost(userId, text, 0);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public int getMetricNumUsers() {
        return -1;
    }

    public int getMetricNumGroups() {
        return -1;
    }

    public int getMetricFeedSize(ObjectId userId) {
        return -1;
    }

    public double getMetricPositiveFeedProportion(ObjectId userId) {
        return -1;
    }

    public String getUsername(String userId) {
        return manager.getUser(new ObjectId(userId)).getUsername();
    }

    public void addGrouptoGroup(String usergroupId1, String usergroupId2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addGrouptoGroup'");
    }

    public void openAnalytics() {
        // Will handle itself
        @SuppressWarnings("unused")
        AnalyticsDisplay analyticsDisplay = new AnalyticsDisplay(this);
    }

    public String getFollowersListString(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFollowers'");
    }

    public String getFollowingListString(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFollowingListString'");
    }

}
