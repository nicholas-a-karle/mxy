package com.mxy.objects;

/*
    * User:    
     * {
     *     _id: <ObjectId>,
     *     username: <String>,
     *     hashword: <String>,
     *     numLogins: <Integer>,
     *     logins: List<ObjectId>,
     *     recentLogin: ObjectId,
     *     registration: ObjectId,
     *     numPostsReposts: <Integer>,
     *     numPosts: <Integer>,
     *     posts: List<ObjectId>,
     *     numReposts: <Integer>,
     *     reposts: List<ObjectId>,
     *     numFollowers: <Integer>,
     *     followers: List<ObjectId>,
     *     numFollowed: <Integer>,
     *     follows: List<ObjectId>,
     *     numUsergroups: <Integer>,
     *     usergroups: List<ObjectId>,
     *     numLikes: <Integer>,
     *     likes: List<ObjectId>
     * }
 */

import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

import com.mxy.admin.Database;

public class User {
    // Using the classes since Java does references so I don't need to worry about stopping duplication
    private ObjectId userId;
    private String username;
    private Integer numLogins;
    private List<ObjectId> logins;
    private Login recentLogin;
    private Registration registration;
    private Integer numPostsReposts;
    private Integer numPosts;
    private List<ObjectId> posts;
    private Integer numReposts;
    private List<ObjectId> reposts;
    private Integer numFollowers;
    private List<ObjectId> followers;
    private Integer numFollowed;
    private List<ObjectId> follows;
    private Integer numUsergroups;
    private List<ObjectId> usergroups;
    private Integer numLikes;
    private List<ObjectId> likes;
    private Database database;

    public User(ObjectId userId, Database database) {
        this.userId = userId;
        this.database = database;
    }

    public int memSize() {
        int mem = 2;

        mem += (username == null) ? 1 : username.length() + 1 ;
        mem += (numLogins == null) ? 1 : 2 ;
        mem += (logins == null) ? 1 : logins.size()  +1 ;
        mem += (recentLogin == null) ? 1 : 2 ;
        mem += (registration == null) ? 1 : 2 ;
        mem += (numPostsReposts == null) ? 1 : 2 ;
        mem += (numPosts == null) ? 1 : 2 ;
        mem += (posts == null) ? 1 : 1 + posts.size() ;
        mem += (numReposts == null) ? 1 : 2 ;
        mem += (reposts == null) ? 1 : 1 + reposts.size() ;
        mem += (numFollowers == null) ? 1 : 2 ;
        mem += (followers == null) ? 1 : 1 + followers.size() ;
        mem += (numFollowed == null) ? 1 : 2 ;
        mem += (follows == null) ? 1 : 1 + follows.size() ;
        mem += (numUsergroups == null) ? 1 : 2 ;
        mem += (usergroups == null) ? 1 : 1 + usergroups.size() ;
        mem += (numLikes == null) ? 1 : 2 ;
        mem += (likes == null) ? 1 : 1 + likes.size() ;

        return mem;
    }

    public ObjectId getId() {
        return userId;
    }

    public String getUsername() {
        if (username == null) {
            username = database.getUserUsername(userId);
        }
        return username;
    }

    public Integer getNumLogins() {
        if (numLogins == null) {
            numLogins = database.getUserNumLogins(userId);
        }
        return numLogins;
    }

    public List<ObjectId> getLogins() {
        if (logins == null) {
            List<ObjectId> idLogins = database.getUserLogins(userId);
            logins = new ArrayList<>();
            for (ObjectId id : idLogins) {
                logins.add(id);
            }
        }
        return logins;
    }

    public Login getRecentLogin() {
        if (recentLogin == null) {
            ObjectId recentLoginId = database.getUserRecentLogin(userId);
            if (recentLoginId != null) {
                recentLogin = new Login(recentLoginId, database);
            }
        }
        return recentLogin;
    }

    public Registration getRegistration() {
        if (registration == null) {
            ObjectId registrationId = database.getUserRegistration(userId);
            if (registrationId != null) {
                registration = new Registration(registrationId, database);
            }
        }
        return registration;
    }

    public Integer getNumPostsReposts() {
        if (numPostsReposts == null) {
            numPostsReposts = database.getUserNumPostsReposts(userId);
        }
        return numPostsReposts;
    }

    public Integer getNumPosts() {
        if (numPosts == null) {
            numPosts = database.getUserNumPosts(userId);
        }
        return numPosts;
    }

    public List<ObjectId> getPosts() {
        if (posts == null) {
            List<ObjectId> postIds = database.getUserPosts(userId);
            posts = new ArrayList<>();
            for (ObjectId id : postIds) {
                posts.add(id);
            }
        }
        return posts;
    }

    public Integer getNumReposts() {
        if (numReposts == null) {
            numReposts = database.getUserNumReposts(userId);
        }
        return numReposts;
    }

    public List<ObjectId> getReposts() {
        if (reposts == null) {
            List<ObjectId> repostIds = database.getUserReposts(userId);
            reposts = new ArrayList<>();
            for (ObjectId id : repostIds) {
                reposts.add(id);
            }
        }
        return reposts;
    }

    public Integer getNumFollowers() {
        if (numFollowers == null) {
            numFollowers = database.getUserNumFollowers(userId);
        }
        return numFollowers;
    }

    public List<ObjectId> getFollowers() {
        if (followers == null) {
            List<ObjectId> followerIds = database.getUserFollowers(userId);
            followers = new ArrayList<>();
            for (ObjectId id : followerIds) {
                followers.add(id);
            }
        }
        return followers;
    }

    public Integer getNumFollowed() {
        if (numFollowed == null) {
            numFollowed = database.getUserNumFollowed(userId);
        }
        return numFollowed;
    }

    public List<ObjectId> getFollows() {
        if (follows == null) {
            List<ObjectId> followIds = database.getUserFollows(userId);
            follows = new ArrayList<>();
            for (ObjectId id : followIds) {
                follows.add(id);
            }
        }
        return follows;
    }

    public Integer getNumUsergroups() {
        if (numUsergroups == null) {
            numUsergroups = database.getUserNumUsergroups(userId);
        }
        return numUsergroups;
    }

    public List<ObjectId> getUsergroups() {
        if (usergroups == null) {
            List<ObjectId> groupIds = database.getUserUsergroups(userId);
            usergroups = new ArrayList<>();
            for (ObjectId id : groupIds) {
                usergroups.add(id);
            }
        }
        return usergroups;
    }

    public Integer getNumLikes() {
        if (numLikes == null) {
            numLikes = database.getUserNumLikes(userId);
        }
        return numLikes;
    }

    public List<ObjectId> getLikes() {
        if (likes == null) {
            List<ObjectId> likeIds = database.getUserLikes(userId);
            likes = new ArrayList<>();
            for (ObjectId id : likeIds) {
                likes.add(id);
            }
        }
        return likes;
    }

    public void retrieveAll() {
        username = database.getUserUsername(userId);
        numLogins = database.getUserNumLogins(userId);

        // Retrieve Logins, recentLogin, and registration
        List<ObjectId> idLogins = database.getUserLogins(userId);
        logins = new ArrayList<>();
        for (ObjectId id : idLogins) {
            logins.add(id);
        }
        ObjectId recentLoginId = database.getUserRecentLogin(userId);
        if (recentLoginId != null) recentLogin = new Login(recentLoginId, database);
        ObjectId registrationId = database.getUserRegistration(userId);
        if (registrationId != null) registration = new Registration(registrationId, database);

        numPostsReposts = database.getUserNumPostsReposts(userId);
        numPosts = database.getUserNumPosts(userId);

        // Retrieve posts
        List<ObjectId> postIds = database.getUserPosts(userId);
        posts = new ArrayList<>();
        for (ObjectId id : postIds) {
            posts.add(id);
        }
    
        numReposts = database.getUserNumReposts(userId);
    
        // Retrieve reposts
        List<ObjectId> repostIds = database.getUserReposts(userId);
        reposts = new ArrayList<>();
        for (ObjectId id : repostIds) {
            reposts.add(id);
        }
    
        numFollowers = database.getUserNumFollowers(userId);
    
        // Retrieve followers
        List<ObjectId> followerIds = database.getUserFollowers(userId);
        followers = new ArrayList<>();
        for (ObjectId id : followerIds) {
            followers.add(id);
        }
    
        numFollowed = database.getUserNumFollowed(userId);
        
        // Retrieve followed users
        List<ObjectId> followIds = database.getUserFollows(userId);
        follows = new ArrayList<>();
        for (ObjectId id : followIds) {
            follows.add(id);
        }
    
        numUsergroups = database.getUserNumUsergroups(userId);
    
        // Retrieve user groups
        List<ObjectId> groupIds = database.getUserUsergroups(userId);
        usergroups = new ArrayList<>();
        for (ObjectId id : groupIds) {
            usergroups.add(id);
        }
    
        numLikes = database.getUserNumLikes(userId);
    
        // Retrieve likes
        List<ObjectId> likeIds = database.getUserLikes(userId);
        likes = new ArrayList<>();
        for (ObjectId id : likeIds) {
            likes.add(id);
        }
    }
    
}
