package com.mxy.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;

import com.mxy.objects.Group;
import com.mxy.objects.Manager;
import com.mxy.objects.Post;
import com.mxy.objects.User;

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

/** Early-1 Requirements
 * 
 * 1. Create Users [x]
 * 2. Create Usergroups [x]
 * 3. Restrict Users to 1 Usergroup Each [x]
 * 4. User Follow User [x]
 * 5. Post short posts [x]
 * 6. Output total number of Users [x]
 * 7. Output total number of Usergroups [x]
 * 8. Output total number of posts in each user's feed [x]
 * 9. Output the total number of 'positive' messages [x]
 * 
 */
/** Early-2 Requirements
 * 
 * 1. Validate UserIds in Groups (fix?)
 * 2. Validate GroupIds in Users (fix?)
 * 3. Timestamp User Creations [x]
 *      a. Add to display [x] (in list)
 * 4. Timestamp Group Creations [x]
 *      a. Add to display [x] (in list)
 * 5. Timestamp User Updates (currently only posts and usergroup additions and follows) [x]
 *      a. Add to display [x] (in list)
 * 6. Find most recently updated User [x]
 *      a. Add to display [x] (in list)
 * 
 */
/** Other
 * 1. Make one of the following True:
 *      a. Manager handles ALL Database work
 *      b. This handles entry, Manager handles retrieval/in-memory data
 * 
 */

public class Controller {

    private Database database;
    private Manager manager;
    private ObjectId currentUserId;
    private List<String> consoleLines;

    public Controller(Database database, Manager manager) {
        this.database = database;
        this.manager = manager;
        this.consoleLines = new ArrayList<>();
        setUser(database.getAllUsers().get(0));
    }

    public void dislog(String line) {
        this.consoleLines.add(line);
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
            database.registerUser(username, createHashword(username + password), System.currentTimeMillis());
        } catch (Exception e) {
            // Auto-generate
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param groupname
     */
    public void createGroup(String groupname) {
        try {
            database.createUserGroup(groupname, System.currentTimeMillis());
        } catch (Exception e) {
            // Auto-generate
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
            manager.setUserUpdate(userId, System.currentTimeMillis());
        } catch (Exception e) {
            // Auto-generate
            e.printStackTrace();
        }
    }
    public void addUserToGroup(String userId, String groupId) { addUserToGroup(new ObjectId(userId), new ObjectId(groupId)); }
    public void addUserToGroup(String groupId) { addUserToGroup(currentUserId, new ObjectId(groupId));}

    /**
     * 
     * @param followerUserId
     * @param followedUserId
     */
    public void addFollow(ObjectId followerUserId, ObjectId followedUserId) {
        try {
            database.followUser(followerUserId, followedUserId);
            manager.setUserUpdate(followerUserId, System.currentTimeMillis());
            manager.setUserUpdate(followerUserId, System.currentTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addFollow(String followedUserID) { addFollow(currentUserId, new ObjectId(followedUserID)); }


    /**
     * 
     * @param userId
     * @param text
     */
    public void addPost(ObjectId userId, String text) {
        try {
            database.createPost(userId, text, 0);
            manager.setUserUpdate(userId, System.currentTimeMillis());
        } catch (Exception e) {
            // Auto-generate
            e.printStackTrace();
        }
    }
    public void addPost(String userId, String text) { addPost(new ObjectId(userId), text); }
    public void addPost(String text) { addPost(currentUserId, text); }

    public int getMetricNumUsers() {
        return manager.getAllUsers().size();
    }

    public int getMetricNumGroups() {
        return manager.getAllGroups().size();
    }

    public int getMetricFeedSize(ObjectId userId) {
        return getFeed(userId).size();
    }
    public int getMetricFeedSize(String userId) { return getMetricFeedSize(new ObjectId(userId)); }
    public int getMetricFeedSize() { return getMetricFeedSize(currentUserId); }

    public double getMetricPositiveFeedProportion(ObjectId userId) {
        
        List<String> feed = getFeed(userId);
        int feedSize = feed.size();
        int posCount = 0;

        for (String post : feed) {
            if (containsWord(post, Arrays.asList("Good", "Great", "Excellent"))) posCount++;
        }

        return (double) posCount / (double) feedSize;
    }
    public double getMetricPositiveFeedProportion(String userId) { return getMetricPositiveFeedProportion(new ObjectId(userId)); }
    public double getMetricPositiveFeedProportion() { return getMetricPositiveFeedProportion(currentUserId); }

    public static boolean containsWord(String str, List<String> list) {
        for (String word : list) {
            if (str.contains(word)) return true;
        }
        return false;
    }

    public String getUsername(ObjectId userId) {
        return manager.getUser(userId).getUsername();
    }
    public String getUsername(String userId) { return getUsername(new ObjectId(userId)); }
    public String getUsername() { return getUsername(this.currentUserId); }

    public void addGrouptoGroup(ObjectId usergroupId1, ObjectId usergroupId2) {
        
        Group group1 = manager.getGroup(usergroupId1);

        for (ObjectId userId : group1.getUsers()) {

            addUserToGroup(userId, usergroupId2);
        }

    }
    public void addGrouptoGroup(String userGroupId1, String userGroupId2) { addGrouptoGroup(new ObjectId(userGroupId1), new ObjectId(userGroupId2)); }

    public String getFollowersListString(ObjectId userId) {
        User user = new User(userId, database);
        List<ObjectId> followerIds = user.getFollowers();
        String listString = "";
        for (ObjectId id : followerIds) {

            User follower = manager.getUser(id);

            listString += follower.getUsername() + "\n";

        }
        return listString;
    }
    public String getFollowersListString(String userId) { return getFollowersListString(new ObjectId(userId)); }
    public String getFollowersListString() { return getFollowersListString(currentUserId); }

    public String getFollowingListString(ObjectId userId) {
        User user = new User(userId, database);
        List<ObjectId> followIds = user.getFollows();
        String listString = "";
        for (ObjectId id : followIds) {

            User follow = manager.getUser(id);

            listString += follow.getUsername() + "\n";

        }
        return listString;
    }
    public String getFollowingListString(String userId) { return getFollowingListString(new ObjectId(userId)); }
    public String getFollowingListString() { return getFollowingListString(currentUserId); }

    public List<String> getFeed(ObjectId userId) {
        User user = new User(userId, database);
        List<ObjectId> feedPostersIds = user.getFollows();
        feedPostersIds.add(userId);
        List<ObjectId> posts = new ArrayList<>();
        //List<ObjectId> reposts = new ArrayList<>();

        for (ObjectId posterId : feedPostersIds) {
            User poster = manager.getUser(posterId);
            posts.addAll(poster.getPosts());
            //reposts.addAll(poster.getReposts());
        }

        List<String> feed = new ArrayList<>();
        for (ObjectId postId : posts) {
            Post post = manager.getPost(postId);
            String username = manager.getUser(post.getUser()).getUsername();
            feed.add(username + ": " + post.getText());
        }

        return feed;
    }
    public List<String> getFeed(String userId) { return getFeed(new ObjectId(userId)); }
    public List<String> getFeed() { return getFeed(currentUserId); }

    public List<String> getConsole() {
        return consoleLines;
    }

    public boolean hasCurrentUser() { return currentUserId != null; }

    public void setUser(ObjectId userId) { 
        User user = manager.getUser(userId);
        this.dislog("Current User Set to \t" + user.getUsername() + " \t" + user.getId().toString());
        this.currentUserId = userId; 
    }
    public void setUser(String userId) { setUser(new ObjectId(userId)); }

    public List<String> getUsersList() {
        List<User> userList = manager.getAllUsers();
        List<String> stringList = new ArrayList<>();
        User mostRecentlyUpdatedUser = manager.getMostRecentlyupdatedUser();

        stringList.add("Most Recently Updated User");
        stringList.add(mostRecentlyUpdatedUser.getUsername() + " \t" + mostRecentlyUpdatedUser.getId());
        stringList.add("Username\t\t\tID\t\tRegistration\t\tUpdate");
        for (User user : userList) {
            stringList.add(user.getUsername() + " \t" + user.getId() + "\t" + user.getRegistration().getTimestamp() + "\t" + user.getUpdateTimestamp());
        }

        return stringList;
    }

    public List<String> getGroupsList() {
        List<Group> groupList = manager.getAllGroups();
        List<String> stringList = new ArrayList<>();

        stringList.add("Groupname\t\t\tID\t\tCreation");
        for (Group group : groupList) {
            stringList.add(group.getName() + " \t" + group.getId() + "\t" + group.getTimestamp());
        }

        return stringList;
    }



}
