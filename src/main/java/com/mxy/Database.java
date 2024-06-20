package com.mxy;

// This class is the definition of overengineering for a project

/**
     * This class is meant to control access to the db which stores User Accounts
     * For the database, the 1:many/many:1 group:user/user:group relationship is not defined in database
     * I don't like the requirement so I made it exist only on the frontend, in reality I could just change the button
     * 
     * User:    
     * {
     *     _id: <ObjectId>,
     *     username: <String>,
     *     hashword: <String>,
     *     num_logins: <Integer>,
     *     logins: List<ObjectId>,
     *     recentLogin: ObjectId,
     *     num_posts_reposts: <Integer>,
     *     num_posts: <Integer>,
     *     posts: List<ObjectId>,
     *     num_reposts: <Integer>,
     *     reposts: List<ObjectId>,
     *     num_followers: <Integer>,
     *     followers: List<ObjectId>,
     *     num_followed: <Integer>,
     *     follows: List<ObjectId>,
     *     num_usergroups: <Integer>,
     *     usergroups: List<ObjectId>,
     *     num_likes: <Integer>,
     *     likes: List<ObjectId>
     * }
     * 
     * Post:
     * {
     *     _id: <ObjectId>,
     *     user: <ObjectId>,
     *     text: <String>,
     *     deleted: <Boolean>,
     *     replyto: <ObjectId>, // points to another post or null
     *     qouted: <ObjectId>, // points to another post or null
     *     timestamp: <Integer>,
     *     num_likes: <Integer>,
     *     likes: List<ObjectId>, // points to user accounts
     *     num_reposts: <Integer>,
     *     reposts: List<ObjectId>, // points to the repost object
     *     num_qoutes: <Integer>,
     *     qoutes: <ObjectId>, // points to the post object qouted in
     *     num_replies: <Integer>,
     *     num_direct_replies: <Integer>,
     *     replies: List<ObjectId>, // points to posts below it
     * }
     * 
     * Repost:
     * {
     *     _id: <ObjectId>,
     *     user: <ObjectId>,
     *     ogpost: <ObjectId>, // original post
     *     timestamp: <Integer>
     * }
     * 
     * Login:
     * {
     *     _id: <ObjectId>,
     *     user: <ObjectId>,
     *     logintimestamp: <Integer>,
     *     logouttimestampe: <Integer>
     * }
     * 
     * Registration:
     * {
     *     _id: <ObjectId>,
     *     user: <ObjectId>,
     *     timestamp: <Integer>
     * }
     * 
     * Group:
     * {
     *     _id: <ObjectId>,
     *     name: <String>,
     *     num_users: <Integer>
     *     users: List<ObjectId>
     * }
     * 
     * 
     * Collections:
     * 
     * Users {User}
     * Posts {Post}
     * Logins {Login}
     * Registrations {Registration}
     * Groups {Group}
     * 
     * Relationships:
     *     User    - Login
     *             - Registration
     *             - Post
     *             - Repost
     *             - Group
     * 
     *     Post    - Repost
     *             - Qoute
     *             - User (Likes)
     * 
     *     Repost  - User (Reposter)
     *             - Post (og)
     */

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> usersCollection;
    private MongoCollection<Document> postsCollection;
    private MongoCollection<Document> repostsCollection;
    private MongoCollection<Document> loginsCollection;
    private MongoCollection<Document> registrationsCollection;
    private MongoCollection<Document> groupsCollection;
    
    public Database() {
        // Connect to MongoDB server
        mongoClient = MongoClients.create("mongodb://localhost:27017");

        // Select the database
        mongoDatabase = mongoClient.getDatabase("social_media_db");

        // Initialize collections
        usersCollection = mongoDatabase.getCollection("users");
        postsCollection = mongoDatabase.getCollection("posts");
        repostsCollection = mongoDatabase.getCollection("reposts");
        loginsCollection = mongoDatabase.getCollection("logins");
        registrationsCollection = mongoDatabase.getCollection("registrations");
        groupsCollection = mongoDatabase.getCollection("groups");
    }

    // Getters for the collections
    public MongoCollection<Document> getUsersCollection() {
        return usersCollection;
    }

    public MongoCollection<Document> getPostsCollection() {
        return postsCollection;
    }

    public MongoCollection<Document> getLoginsCollection() {
        return loginsCollection;
    }

    public MongoCollection<Document> getRegistrationsCollection() {
        return registrationsCollection;
    }

    public MongoCollection<Document> getGroupsCollection() {
        return groupsCollection;
    }

    /**
     * Method to register a new user
     *
     * @param username  New User username
     * @param hashword  New User hashed password
     * @param timestamp Moment of creation
     * @throws Exception If username is already taken or any MongoDB operation fails
     */
    public void registerUser(String username, String hashword, int timestamp) throws Exception {
        // Check if the username already exists
        Document existingUser = usersCollection.find(new Document("username", username)).first();
        if (existingUser != null) {
            throw new Exception("Username already taken.");
        }

        // Create a new user document
        ObjectId userId = new ObjectId();
        Document userDoc = new Document("_id", userId)
                .append("username", username)
                .append("hashword", hashword)
                .append("num_logins", 0)
                .append("logins", new ArrayList<>())
                .append("recentLogin", null)
                .append("num_posts_reposts", 0)
                .append("num_posts", 0)
                .append("posts", new ArrayList<>())
                .append("num_reposts", 0)
                .append("reposts", new ArrayList<>())
                .append("num_followers", 0)
                .append("followers", new ArrayList<>())
                .append("num_followed", 0)
                .append("followed", new ArrayList<>())
                .append("num_usergroups", 0)
                .append("usergroups", new ArrayList<>())
                .append("num_likes", 0)
                .append("likes", new ArrayList<>());

        // Insert the user document into the users collection
        usersCollection.insertOne(userDoc);

        // Create a new registration document
        Document registrationDoc = new Document("_id", new ObjectId())
                .append("user", userId)
                .append("timestamp", timestamp);

        // Insert the registration document into the registrations collection
        registrationsCollection.insertOne(registrationDoc);
    }
    /**
     * 
     * @param userId
     * @param logintimestamp
     * @throws Exception
     */
    public void loginUser(ObjectId userId, int logintimestamp) throws Exception {
        // Check if the user exists
        Document userDoc = usersCollection.find(new Document("_id", userId)).first();
        if (userDoc == null) {
            throw new Exception("User with ID " + userId + " does not exist.");
        }

        // Check if the user is already logged in
        Document recentLogin = (Document) userDoc.get("recentLogin");
        if (recentLogin != null) {
            throw new Exception("User with ID " + userId + " is already logged in.");
        }

        // Create login document
        Document loginDoc = new Document()
                .append("user", userId)
                .append("logintimestamp", logintimestamp)
                .append("logouttimestamp", -1); // -1 indicates user is currently logged in

        // Insert login document
        loginsCollection.insertOne(loginDoc);

        // Update user's recent login information
        usersCollection.updateOne(
                new Document("_id", userId),
                new Document("$set", new Document("recentLogin", loginDoc))
        );
    }

    /**
     * 
     * @param userId
     * @param logouttimestamp
     * @throws Exception
     */
    public void logoutUser(ObjectId userId, int logouttimestamp) throws Exception {
        // Check if the user exists
        Document userDoc = usersCollection.find(new Document("_id", userId)).first();
        if (userDoc == null) {
            throw new Exception("User with ID " + userId + " does not exist.");
        }

        // Retrieve recent login information
        Document recentLogin = (Document) userDoc.get("recentLogin");
        if (recentLogin == null) {
            throw new Exception("User with ID " + userId + " is not logged in.");
        }

        // Update the login document in the loginsCollection
        ObjectId loginId = recentLogin.getObjectId("_id");
        loginsCollection.updateOne(
                new Document("_id", loginId),
                new Document("$set", new Document("logouttimestamp", logouttimestamp))
        );

        // Update user's recentLogin and loggedin status
        usersCollection.updateOne(
                new Document("_id", userId),
                new Document("$set", new Document("recentLogin", null).append("loggedin", false))
        );
    }

    // TODO: loginIntegrityCheck

    // TODO: loginIntegrityFix

    /**
     * Creates a new post for a user.
     * @param userId ObjectId of the user creating the post
     * @param text Content of the post
     * @param timestamp Timestamp of when the post was created
     * @throws Exception If user does not exist or there is an error creating the post
     */
    public void createPost(ObjectId userId, String text, int timestamp) throws Exception {
        // Check if the user exists
        Document userDoc = usersCollection.find(new Document("_id", userId)).first();
        if (userDoc == null) {
            throw new Exception("User with ID " + userId + " does not exist.");
        }

        // Create post document
        Document postDoc = new Document()
                .append("_id", new ObjectId())
                .append("user", userId)
                .append("text", text)
                .append("deleted", false)
                .append("timestamp", timestamp)
                .append("num_likes", 0)
                .append("likes", new ArrayList<>())
                .append("num_reposts", 0)
                .append("reposts", new ArrayList<>())
                .append("num_quotes", 0)
                .append("quotes", new ArrayList<>())
                .append("num_replies", 0)
                .append("num_direct_replies", 0)
                .append("replies", new ArrayList<>());

        // Insert post document into posts collection
        postsCollection.insertOne(postDoc);

        // Update user's post information
        usersCollection.updateOne(
                new Document("_id", userId),
                new Document("$inc", new Document("num_posts", 1))
                        .append("$push", new Document("posts", postDoc.getObjectId("_id")))
        );
    }

    /**
     * Creates a new quote post for a user.
     *
     * @param userId ObjectId of the user creating the quote post.
     * @param quotedPostId ObjectId of the post being quoted.
     * @param text Content of the quote post.
     * @param timestamp Timestamp of when the quote post was created.
     * @throws Exception If user does not exist, quoted post does not exist, or there is an error creating the quote post.
     */
    public void createQuotePost(ObjectId userId, ObjectId quotedPostId, String text, int timestamp) throws Exception {
        // Check if the user exists
        Document userDoc = usersCollection.find(new Document("_id", userId)).first();
        if (userDoc == null) {
            throw new Exception("User with ID " + userId + " does not exist.");
        }

        // Check if the quoted post exists
        Document quotedPostDoc = postsCollection.find(new Document("_id", quotedPostId)).first();
        if (quotedPostDoc == null) {
            throw new Exception("Post with ID " + quotedPostId + " does not exist.");
        }

        // Create quote post document
        Document quotePostDoc = new Document()
                .append("_id", new ObjectId())
                .append("user", userId)
                .append("text", text)
                .append("deleted", false)
                .append("quoted", quotedPostId)
                .append("timestamp", timestamp)
                .append("num_likes", 0)
                .append("likes", new ArrayList<>())
                .append("num_reposts", 0)
                .append("reposts", new ArrayList<>())
                .append("num_quotes", 0)
                .append("quotes", new ArrayList<>())
                .append("num_replies", 0)
                .append("num_direct_replies", 0)
                .append("replies", new ArrayList<>());

        // Insert quote post document into posts collection
        postsCollection.insertOne(quotePostDoc);

        // Update quoted post with the new quote
        postsCollection.updateOne(
                new Document("_id", quotedPostId),
                new Document("$inc", new Document("num_quotes", 1))
                        .append("$push", new Document("quotes", quotePostDoc.getObjectId("_id")))
        );

        // Update user's post information
        usersCollection.updateOne(
                new Document("_id", userId),
                new Document("$inc", new Document("num_posts", 1))
                        .append("$push", new Document("posts", quotePostDoc.getObjectId("_id")))
        );
    }


    /**
     * Creates a new reply post for a user.
     *
     * @param userId ObjectId of the user creating the reply.
     * @param replyToPostId ObjectId of the post being replied to.
     * @param text Content of the reply post.
     * @param timestamp Timestamp of when the reply post was created.
     * @throws Exception If user does not exist, reply-to post does not exist, or there is an error creating the reply post.
     */
    public void createReply(ObjectId userId, ObjectId replyToPostId, String text, int timestamp) throws Exception {
        // Check if the user exists
        Document userDoc = usersCollection.find(new Document("_id", userId)).first();
        if (userDoc == null) {
            throw new Exception("User with ID " + userId + " does not exist.");
        }

        // Check if the reply-to post exists
        Document replyToPostDoc = postsCollection.find(new Document("_id", replyToPostId)).first();
        if (replyToPostDoc == null) {
            throw new Exception("Post with ID " + replyToPostId + " does not exist.");
        }

        // Create reply post document
        Document replyPostDoc = new Document()
                .append("_id", new ObjectId())
                .append("user", userId)
                .append("text", text)
                .append("deleted", false)
                .append("replyto", replyToPostId)
                .append("timestamp", timestamp)
                .append("num_likes", 0)
                .append("likes", new ArrayList<>())
                .append("num_reposts", 0)
                .append("reposts", new ArrayList<>())
                .append("num_quotes", 0)
                .append("quotes", new ArrayList<>())
                .append("num_replies", 0)
                .append("num_direct_replies", 0)
                .append("replies", new ArrayList<>());

        // Insert reply post document into posts collection
        postsCollection.insertOne(replyPostDoc);

        // Update reply-to post with the new reply
        postsCollection.updateOne(
                new Document("_id", replyToPostId),
                new Document("$inc", new Document("num_replies", 1))
                        .append("$push", new Document("replies", replyPostDoc.getObjectId("_id")))
        );

        // Update user's post information
        usersCollection.updateOne(
                new Document("_id", userId),
                new Document("$inc", new Document("num_posts", 1))
                        .append("$push", new Document("posts", replyPostDoc.getObjectId("_id")))
        );
    }


    /**
     * Method to delete a post and all its associated replies, quotes, and reposts.
     *
     * @param postId ObjectId of the post to delete
     * @throws Exception If post is not found or if there is an error in the deletion process
     */
    public void nuclearDeletePost(ObjectId postId) throws Exception {
        // Find the post in the database
        Document postDoc = postsCollection.find(new Document("_id", postId)).first();
        if (postDoc == null) {
            throw new Exception("Post with ID " + postId + " not found.");
        }

        // Get the list of associated replies, quotes, and reposts
        List<ObjectId> replies = postDoc.getList("replies", ObjectId.class, new ArrayList<>());
        List<ObjectId> quotes = postDoc.getList("quotes", ObjectId.class, new ArrayList<>());
        List<ObjectId> reposts = postDoc.getList("reposts", ObjectId.class, new ArrayList<>());

        // Delete all replies
        for (ObjectId replyId : replies) {
            nuclearDeletePost(replyId); // Recursive call to delete nested replies
        }

        // Delete all quotes
        for (ObjectId quoteId : quotes) {
            postsCollection.deleteOne(new Document("_id", quoteId));
        }

        // Delete all reposts
        for (ObjectId repostId : reposts) {
            repostsCollection.deleteOne(new Document("_id", repostId));
        }

        // Remove the post from the user's post list and decrement the user's post count
        ObjectId userId = postDoc.getObjectId("user");
        usersCollection.updateOne(
            new Document("_id", userId),
            new Document("$pull", new Document("posts", postId))
                    .append("$inc", new Document("num_posts", -1))
        );

        // Remove the post from the likes of any users who liked it
        List<ObjectId> likes = postDoc.getList("likes", ObjectId.class, new ArrayList<>());
        for (ObjectId likerId : likes) {
            usersCollection.updateOne(
                new Document("_id", likerId),
                new Document("$pull", new Document("likes", postId))
                        .append("$inc", new Document("num_likes", -1))
            );
        }

        // Finally, delete the post itself
        postsCollection.deleteOne(new Document("_id", postId));
    }

    /**
     * Method to soft delete a post by marking it as deleted.
     *
     * @param postId ObjectId of the post to soft delete
     * @throws Exception If post is not found or if there is an error in the deletion process
     */
    public void softDeletePost(ObjectId postId) throws Exception {
        // Find the post in the database
        Document postDoc = postsCollection.find(new Document("_id", postId)).first();
        if (postDoc == null) {
            throw new Exception("Post with ID " + postId + " not found.");
        }

        // Update the post document to set the deleted flag to true
        postsCollection.updateOne(
            new Document("_id", postId),
            new Document("$set", new Document("deleted", true))
        );

        // Optionally, decrement the user's post count and update their post list
        ObjectId userId = postDoc.getObjectId("user");
        usersCollection.updateOne(
            new Document("_id", userId),
            new Document("$inc", new Document("num_posts", -1))
                    .append("$pull", new Document("posts", postId))
        );
    }

    /**
     * Creates a new repost of an original post by a user.
     * @param userId ObjectId of the user creating the repost
     * @param ogpostId ObjectId of the original post being reposted
     * @param timestamp Timestamp of when the repost was created
     * @throws Exception If user does not exist, original post does not exist, or there is an error creating the repost
     */
    public void createRepost(ObjectId userId, ObjectId ogpostId, int timestamp) throws Exception {
        // Check if the user exists
        Document userDoc = usersCollection.find(new Document("_id", userId)).first();
        if (userDoc == null) {
            throw new Exception("User with ID " + userId + " does not exist.");
        }

        // Check if the original post exists
        Document originalPostDoc = postsCollection.find(new Document("_id", ogpostId)).first();
        if (originalPostDoc == null) {
            throw new Exception("Original post with ID " + ogpostId + " does not exist.");
        }

        // Create repost document
        Document repostDoc = new Document()
                .append("_id", new ObjectId())
                .append("user", userId)
                .append("ogpost", ogpostId)
                .append("timestamp", timestamp);

        // Insert repost document into reposts collection
        repostsCollection.insertOne(repostDoc);

        // Update original post with the new repost
        postsCollection.updateOne(
                new Document("_id", ogpostId),
                new Document("$inc", new Document("num_reposts", 1))
                        .append("$push", new Document("reposts", repostDoc.getObjectId("_id")))
        );
    }

    // TODO: deleteRepost

    /**
     * Method to like a post by a user.
     *
     * @param postId ObjectId of the post to like
     * @param userId ObjectId of the user liking the post
     * @throws Exception If post or user is not found, or if user has already liked the post
     */
    public void likePost(ObjectId postId, ObjectId userId) throws Exception {
        // Find the post in the database
        Document postDoc = postsCollection.find(new Document("_id", postId)).first();
        if (postDoc == null) {
            throw new Exception("Post with ID " + postId + " not found.");
        }

        // Update the post document to increment likes and add userId to likes list
        int numLikes = postDoc.getInteger("num_likes", 0);
        List<ObjectId> likes = postDoc.getList("likes", ObjectId.class, new ArrayList<>());

        // Check if the user has already liked the post
        if (likes.contains(userId)) {
            throw new Exception("User with ID " + userId + " has already liked the post with ID " + postId + ".");
        }

        // Update post document
        numLikes++;
        likes.add(userId);

        // Update the post document in the collection
        postsCollection.updateOne(new Document("_id", postId),
                new Document("$set", new Document("num_likes", numLikes).append("likes", likes)));

        // Find the user in the database
        Document userDoc = usersCollection.find(new Document("_id", userId)).first();
        if (userDoc == null) {
            throw new Exception("User with ID " + userId + " not found.");
        }

        // Update the user document to add postId to likes list
        List<ObjectId> userLikes = userDoc.getList("likes", ObjectId.class, new ArrayList<>());

        // Update user document
        userLikes.add(postId);

        // Update the user document in the collection
        usersCollection.updateOne(new Document("_id", userId),
                new Document("$set", new Document("likes", userLikes)));
    }

    /**
     * Method to unlike a post by a user.
     *
     * @param postId ObjectId of the post to unlike
     * @param userId ObjectId of the user unliking the post
     * @throws Exception If post or user is not found, or if user has not liked the post
     */
    public void unlikePost(ObjectId postId, ObjectId userId) throws Exception {
        // Find the post in the database
        Document postDoc = postsCollection.find(new Document("_id", postId)).first();
        if (postDoc == null) {
            throw new Exception("Post with ID " + postId + " not found.");
        }

        // Retrieve the likes list from the post document
        List<ObjectId> likes = postDoc.getList("likes", ObjectId.class, new ArrayList<>());

        // Check if the user has liked the post
        if (!likes.contains(userId)) {
            throw new Exception("User with ID " + userId + " has not liked the post with ID " + postId + ".");
        }

        // Remove the user's ID from the likes list
        likes.remove(userId);

        // Update the post document to decrement likes and update the likes list
        int numLikes = likes.size();

        // Update the post document in the collection
        postsCollection.updateOne(
            new Document("_id", postId),
            new Document("$set", new Document("num_likes", numLikes).append("likes", likes))
        );

        // Find the user in the database
        Document userDoc = usersCollection.find(new Document("_id", userId)).first();
        if (userDoc == null) {
            throw new Exception("User with ID " + userId + " not found.");
        }

        // Retrieve the likes list from the user document
        List<ObjectId> userLikes = userDoc.getList("likes", ObjectId.class, new ArrayList<>());

        // Remove the post's ID from the user's likes list
        userLikes.remove(postId);

        // Update the user document in the collection
        usersCollection.updateOne(
            new Document("_id", userId),
            new Document("$set", new Document("likes", userLikes))
        );
    }


    /**
     * Follows another user.
     *
     * @param followerId ID of the user who is following
     * @param followedId ID of the user being followed
     * @throws Exception If follower or followed user does not exist, or MongoDB operations fail
     */
    public void followUser(ObjectId followerId, ObjectId followedId) throws Exception {
        // Check if the follower user exists
        Document followerDoc = usersCollection.find(new Document("_id", followerId)).first();
        if (followerDoc == null) {
            throw new Exception("Follower with ID " + followerId + " does not exist.");
        }

        // Check if the followed user exists
        Document followedDoc = usersCollection.find(new Document("_id", followedId)).first();
        if (followedDoc == null) {
            throw new Exception("User to be followed with ID " + followedId + " does not exist.");
        }

        // Update the followed user's followers list
        List<ObjectId> followers = followedDoc.getList("followers", ObjectId.class, new ArrayList<>());
        if (followers.contains(followerId)) {
            throw new Exception("User with ID " + followerId + " is already following the user with ID " + followedId + ".");
        }
        followers.add(followerId);
        usersCollection.updateOne(
            new Document("_id", followedId),
            new Document("$set", new Document("followers", followers))
            .append("$inc", new Document("num_followers", 1))
        );

        // Update the follower user's followed list
        List<ObjectId> followed = followerDoc.getList("followed", ObjectId.class, new ArrayList<>());
        followed.add(followedId);
        usersCollection.updateOne(
            new Document("_id", followerId),
            new Document("$set", new Document("followed", followed))
            .append("$inc", new Document("num_followed", 1))
        );
    }


    /**
     * Unfollows another user.
     *
     * @param followerId ID of the user who is unfollowing
     * @param followedId ID of the user being unfollowed
     * @throws Exception If follower or followed user does not exist, or MongoDB operations fail
    */
    public void unfollowUser(ObjectId followerId, ObjectId followedId) throws Exception {
        // Check if the follower user exists
        Document followerDoc = usersCollection.find(new Document("_id", followerId)).first();
        if (followerDoc == null) {
            throw new Exception("Follower with ID " + followerId + " does not exist.");
        }

        // Check if the followed user exists
        Document followedDoc = usersCollection.find(new Document("_id", followedId)).first();
        if (followedDoc == null) {
            throw new Exception("User to be unfollowed with ID " + followedId + " does not exist.");
        }

        // Update the followed user's followers list
        List<ObjectId> followers = followedDoc.getList("followers", ObjectId.class, new ArrayList<>());
        if (!followers.contains(followerId)) {
            throw new Exception("User with ID " + followerId + " is not following the user with ID " + followedId + ".");
        }
        followers.remove(followerId);
        usersCollection.updateOne(
            new Document("_id", followedId),
            new Document("$set", new Document("followers", followers))
            .append("$inc", new Document("num_followers", -1))
        );

        // Update the follower user's followed list
        List<ObjectId> followed = followerDoc.getList("followed", ObjectId.class, new ArrayList<>());
        followed.remove(followedId);
        usersCollection.updateOne(
            new Document("_id", followerId),
            new Document("$set", new Document("followed", followed))
            .append("$inc", new Document("num_followed", -1))
        );
    }


    /**
     * Adds a user to a group.
     *
     * @param userId ID of the user joining the group
     * @param groupId ID of the group being joined
     * @throws Exception If the user or group does not exist, or if the user is already a member of the group
     */
    public void addUserToGroup(ObjectId userId, ObjectId groupId) throws Exception {
        // Check if the user exists
        Document userDoc = usersCollection.find(new Document("_id", userId)).first();
        if (userDoc == null) {
            throw new Exception("User with ID " + userId + " does not exist.");
        }

        // Check if the group exists
        Document groupDoc = groupsCollection.find(new Document("_id", groupId)).first();
        if (groupDoc == null) {
            throw new Exception("Group with ID " + groupId + " does not exist.");
        }

        // Update the group's users list
        List<ObjectId> groupUsers = groupDoc.getList("users", ObjectId.class, new ArrayList<>());
        if (groupUsers.contains(userId)) {
            throw new Exception("User with ID " + userId + " is already a member of the group with ID " + groupId + ".");
        }
        groupUsers.add(userId);
        groupsCollection.updateOne(
            new Document("_id", groupId),
            new Document("$set", new Document("users", groupUsers))
            .append("$inc", new Document("num_users", 1))
        );

        // Update the user's usergroups list
        List<ObjectId> userGroups = userDoc.getList("usergroups", ObjectId.class, new ArrayList<>());
        userGroups.add(groupId);
        usersCollection.updateOne(
            new Document("_id", userId),
            new Document("$set", new Document("usergroups", userGroups))
            .append("$inc", new Document("num_usergroups", 1))
        );
    }

    /**
     * Removes a user from a group.
     *
     * @param userId ID of the user leaving the group
     * @param groupId ID of the group being left
     * @throws Exception If the user or group does not exist, or if the user is not a member of the group
     */
    public void removeUserFromGroup(ObjectId userId, ObjectId groupId) throws Exception {
        // Check if the user exists
        Document userDoc = usersCollection.find(new Document("_id", userId)).first();
        if (userDoc == null) {
            throw new Exception("User with ID " + userId + " does not exist.");
        }

        // Check if the group exists
        Document groupDoc = groupsCollection.find(new Document("_id", groupId)).first();
        if (groupDoc == null) {
            throw new Exception("Group with ID " + groupId + " does not exist.");
        }

        // Update the group's users list
        List<ObjectId> groupUsers = groupDoc.getList("users", ObjectId.class, new ArrayList<>());
        if (!groupUsers.contains(userId)) {
            throw new Exception("User with ID " + userId + " is not a member of the group with ID " + groupId + ".");
        }
        groupUsers.remove(userId);
        groupsCollection.updateOne(
            new Document("_id", groupId),
            new Document("$set", new Document("users", groupUsers))
            .append("$inc", new Document("num_users", -1))
        );

        // Update the user's usergroups list
        List<ObjectId> userGroups = userDoc.getList("usergroups", ObjectId.class, new ArrayList<>());
        userGroups.remove(groupId);
        usersCollection.updateOne(
            new Document("_id", userId),
            new Document("$set", new Document("usergroups", userGroups))
            .append("$inc", new Document("num_usergroups", -1))
        );
    }

    /**
     * Creates a new user group.
     *
     * @param groupName Name of the new group
     * @throws Exception If a group with the same name already exists
     */
    public void createUserGroup(String groupName) throws Exception {
        // Check if a group with the same name already exists
        Document existingGroup = groupsCollection.find(new Document("name", groupName)).first();
        if (existingGroup != null) {
            throw new Exception("Group with name " + groupName + " already exists.");
        }

        // Create a new group document
        ObjectId groupId = new ObjectId();
        Document groupDoc = new Document("_id", groupId)
                .append("name", groupName)
                .append("num_users", 0)
                .append("users", new ArrayList<>());

        // Insert the group document into the groups collection
        groupsCollection.insertOne(groupDoc);
    }


    /**
     * Deletes a user group.
     *
     * @param groupId ID of the group to delete
     * @throws Exception If the group does not exist
     */
    public void deleteUserGroup(ObjectId groupId) throws Exception {
        // Check if the group exists
        Document groupDoc = groupsCollection.find(new Document("_id", groupId)).first();
        if (groupDoc == null) {
            throw new Exception("Group with ID " + groupId + " does not exist.");
        }

        // Remove the group from all its users' usergroups lists
        List<ObjectId> users = groupDoc.getList("users", ObjectId.class, new ArrayList<>());
        for (ObjectId userId : users) {
            usersCollection.updateOne(
                new Document("_id", userId),
                new Document("$pull", new Document("usergroups", groupId))
                .append("$inc", new Document("num_usergroups", -1))
            );
        }

        // Delete the group document from the groups collection
        groupsCollection.deleteOne(new Document("_id", groupId));
    }

    // TODO: All Retrieval Functions

}
