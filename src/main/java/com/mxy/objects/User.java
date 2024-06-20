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
import com.mxy.Database;

public class User {
    // Using the classes since Java does references so I don't need to worry about stopping duplication
    private ObjectId userId;
    private String username;
    private String hashword;
    private Integer numLogins;
    private List<Login> logins;
    private Login recentLogin;
    private Registration registration;
    private Integer numPostsReposts;
    private Integer numPosts;
    private List<Post> posts;
    private Integer numReposts;
    private List<Repost> reposts;
    private Integer numFollowers;
    private List<User> followers;
    private Integer numFollowed;
    private List<User> follows;
    private Integer numUsergroups;
    private List<Group> usergroups;
    private Integer numLikes;
    private List<Post> likes;
    private Database database;

    public User(ObjectId userId, Database database) {
        this.userId = userId;
        this.database = database;
    }

    public ObjectId getuserId() {
        return userId;
    }

    public String getUsername() {
        if (username == null) {
            username = database.getUserUsername(userId);
        }
        return username;
    }

    public String getHashword() {
        if (hashword == null) {
            hashword = database.getUserHashword(userId);
        }
        return hashword;
    }

    public Integer getNumLogins() {
        if (numLogins == null) {
            numLogins = database.getUserNumLogins(userId);
        }
        return numLogins;
    }

    public List<Login> getLogins() {
        if (logins == null) {
            List<ObjectId> idLogins = database.getUserLogins(userId);
            logins = new ArrayList<>();
            for (ObjectId id : idLogins) {
                logins.add(new Login(id, database));
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

    public List<Post> getPosts() {
        if (posts == null) {
            List<ObjectId> postIds = database.getUserPosts(userId);
            posts = new ArrayList<>();
            for (ObjectId id : postIds) {
                posts.add(new Post(id, database));
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

    public List<Repost> getReposts() {
        if (reposts == null) {
            List<ObjectId> repostIds = database.getUserReposts(userId);
            reposts = new ArrayList<>();
            for (ObjectId id : repostIds) {
                reposts.add(new Repost(id, database));
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

    public List<User> getFollowers() {
        if (followers == null) {
            List<ObjectId> followerIds = database.getUserFollowers(userId);
            followers = new ArrayList<>();
            for (ObjectId id : followerIds) {
                followers.add(new User(id, database));
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

    public List<User> getFollows() {
        if (follows == null) {
            List<ObjectId> followIds = database.getUserFollows(userId);
            follows = new ArrayList<>();
            for (ObjectId id : followIds) {
                follows.add(new User(id, database));
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

    public List<Group> getUsergroups() {
        if (usergroups == null) {
            List<ObjectId> groupIds = database.getUserUsergroups(userId);
            usergroups = new ArrayList<>();
            for (ObjectId id : groupIds) {
                usergroups.add(new Group(id, database));
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

    public List<Post> getLikes() {
        if (likes == null) {
            List<ObjectId> likeIds = database.getUserLikes(userId);
            likes = new ArrayList<>();
            for (ObjectId id : likeIds) {
                likes.add(new Post(id, database));
            }
        }
        return likes;
    }

    public void retrieveAll() {
        username = database.getUserUsername(userId);
        hashword = database.getUserHashword(userId);
        numLogins = database.getUserNumLogins(userId);

        // Retrieve Logins, recentLogin, and registration
        List<ObjectId> idLogins = database.getUserLogins(userId);
        logins = new ArrayList<>();
        for (ObjectId id : idLogins) {
            logins.add(new Login(id, database));
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
            posts.add(new Post(id, database));
        }
    
        numReposts = database.getUserNumReposts(userId);
    
        // Retrieve reposts
        List<ObjectId> repostIds = database.getUserReposts(userId);
        reposts = new ArrayList<>();
        for (ObjectId id : repostIds) {
            reposts.add(new Repost(id, database));
        }
    
        numFollowers = database.getUserNumFollowers(userId);
    
        // Retrieve followers
        List<ObjectId> followerIds = database.getUserFollowers(userId);
        followers = new ArrayList<>();
        for (ObjectId id : followerIds) {
            followers.add(new User(id, database));
        }
    
        numFollowed = database.getUserNumFollowed(userId);
        
        // Retrieve followed users
        List<ObjectId> followIds = database.getUserFollows(userId);
        follows = new ArrayList<>();
        for (ObjectId id : followIds) {
            follows.add(new User(id, database));
        }
    
        numUsergroups = database.getUserNumUsergroups(userId);
    
        // Retrieve user groups
        List<ObjectId> groupIds = database.getUserUsergroups(userId);
        usergroups = new ArrayList<>();
        for (ObjectId id : groupIds) {
            usergroups.add(new Group(id, database));
        }
    
        numLikes = database.getUserNumLikes(userId);
    
        // Retrieve likes
        List<ObjectId> likeIds = database.getUserLikes(userId);
        likes = new ArrayList<>();
        for (ObjectId id : likeIds) {
            likes.add(new Post(id, database));
        }
    }
    
}
