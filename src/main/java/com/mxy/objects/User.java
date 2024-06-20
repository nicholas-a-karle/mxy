package com.mxy.objects;

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
        // Retrieve username
        if (username == null) {
            username = database.getUserUsername(userId);
        }
    
        // Retrieve hashword
        if (hashword == null) {
            hashword = database.getUserHashword(userId);
        }
    
        // Retrieve number of logins
        if (numLogins == null) {
            numLogins = database.getUserNumLogins(userId);
        }
    
        // Retrieve logins
        if (logins == null) {
            List<ObjectId> idLogins = database.getUserLogins(userId);
            logins = new ArrayList<>();
            for (ObjectId id : idLogins) {
                logins.add(new Login(id, database));
            }
        }
    
        // Retrieve recent login
        if (recentLogin == null) {
            ObjectId recentLoginId = database.getUserRecentLogin(userId);
            if (recentLoginId != null) {
                recentLogin = new Login(recentLoginId, database);
            }
        }
    
        // Retrieve registration
        if (registration == null) {
            ObjectId registrationId = database.getUserRegistration(userId);
            if (registrationId != null) {
                registration = new Registration(registrationId, database);
            }
        }
    
        // Retrieve number of posts and reposts
        if (numPostsReposts == null) {
            numPostsReposts = database.getUserNumPostsReposts(userId);
        }
    
        // Retrieve number of posts
        if (numPosts == null) {
            numPosts = database.getUserNumPosts(userId);
        }
    
        // Retrieve posts
        if (posts == null) {
            List<ObjectId> postIds = database.getUserPosts(userId);
            posts = new ArrayList<>();
            for (ObjectId id : postIds) {
                posts.add(new Post(id, database));
            }
        }
    
        // Retrieve number of reposts
        if (numReposts == null) {
            numReposts = database.getUserNumReposts(userId);
        }
    
        // Retrieve reposts
        if (reposts == null) {
            List<ObjectId> repostIds = database.getUserReposts(userId);
            reposts = new ArrayList<>();
            for (ObjectId id : repostIds) {
                reposts.add(new Repost(id, database));
            }
        }
    
        // Retrieve number of followers
        if (numFollowers == null) {
            numFollowers = database.getUserNumFollowers(userId);
        }
    
        // Retrieve followers
        if (followers == null) {
            List<ObjectId> followerIds = database.getUserFollowers(userId);
            followers = new ArrayList<>();
            for (ObjectId id : followerIds) {
                followers.add(new User(id, database));
            }
        }
    
        // Retrieve number of followed users
        if (numFollowed == null) {
            numFollowed = database.getUserNumFollowed(userId);
        }
    
        // Retrieve followed users
        if (follows == null) {
            List<ObjectId> followIds = database.getUserFollows(userId);
            follows = new ArrayList<>();
            for (ObjectId id : followIds) {
                follows.add(new User(id, database));
            }
        }
    
        // Retrieve number of user groups
        if (numUsergroups == null) {
            numUsergroups = database.getUserNumUsergroups(userId);
        }
    
        // Retrieve user groups
        if (usergroups == null) {
            List<ObjectId> groupIds = database.getUserUsergroups(userId);
            usergroups = new ArrayList<>();
            for (ObjectId id : groupIds) {
                usergroups.add(new Group(id, database));
            }
        }
    
        // Retrieve number of likes
        if (numLikes == null) {
            numLikes = database.getUserNumLikes(userId);
        }
    
        // Retrieve likes
        if (likes == null) {
            List<ObjectId> likeIds = database.getUserLikes(userId);
            likes = new ArrayList<>();
            for (ObjectId id : likeIds) {
                likes.add(new Post(id, database));
            }
        }
    }
    
}
