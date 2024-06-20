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
     *     numLikes: <Integer>,
     *     likes: List<ObjectId>, // points to user accounts
     *     numReposts: <Integer>,
     *     reposts: List<ObjectId>, // points to the repost object
     *     numQuotes: <Integer>,
     *     qoutes: <ObjectId>, // points to the post object qouted in
     *     numReplies: <Integer>,
     *     numDirectReplies: <Integer>,
     *     replies: List<ObjectId>, // points to posts below it
     * }
     * 
     * Repost:
     * {
     *     _id: <ObjectId>,
     *     user: <ObjectId>,
     *     ogPost: <ObjectId>, // original post
     *     timestamp: <Integer>
     * }
     * 
     * Login:
     * {
     *     _id: <ObjectId>,
     *     user: <ObjectId>,
     *     loginTimestamp: <Integer>,
     *     logoutTimestampe: <Integer>
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
     *     numUsers: <Integer>
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

// All Modification Functions
//#region
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

        ObjectId userId = new ObjectId();

        // Create a new registration document
        Document registrationDoc = new Document("_id", new ObjectId())
                .append("user", userId)
                .append("timestamp", timestamp);

        // Insert the registration document into the registrations collection
        registrationsCollection.insertOne(registrationDoc);

        // Create a new user document
        Document userDoc = new Document("_id", userId)
                .append("username", username)
                .append("hashword", hashword)
                .append("numLogins", 0)
                .append("logins", new ArrayList<>())
                .append("recentLogin", null)
                .append("registration", registrationDoc.get("_id"))
                .append("numPostsReposts", 0)
                .append("numPosts", 0)
                .append("posts", new ArrayList<>())
                .append("numReposts", 0)
                .append("reposts", new ArrayList<>())
                .append("numFollowers", 0)
                .append("followers", new ArrayList<>())
                .append("numFollowed", 0)
                .append("followed", new ArrayList<>())
                .append("numUsergroups", 0)
                .append("usergroups", new ArrayList<>())
                .append("numLikes", 0)
                .append("likes", new ArrayList<>());

        // Insert the user document into the users collection
        usersCollection.insertOne(userDoc);
    }
    /**
     * 
     * @param userId
     * @param loginTimestamp
     * @throws Exception
     */
    public void loginUser(ObjectId userId, int loginTimestamp) throws Exception {
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
                .append("loginTimestamp", loginTimestamp)
                .append("logoutTimestamp", -1); // -1 indicates user is currently logged in

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
     * @param logoutTimestamp
     * @throws Exception
     */
    public void logoutUser(ObjectId userId, int logoutTimestamp) throws Exception {
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
                new Document("$set", new Document("logoutTimestamp", logoutTimestamp))
        );

        // Update user's recentLogin and loggedin status
        usersCollection.updateOne(
                new Document("_id", userId),
                new Document("$set", new Document("recentLogin", null).append("loggedin", false))
        );
    }

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
                .append("numLikes", 0)
                .append("likes", new ArrayList<>())
                .append("numReposts", 0)
                .append("reposts", new ArrayList<>())
                .append("numQuotes", 0)
                .append("quotes", new ArrayList<>())
                .append("numReplies", 0)
                .append("numDirectReplies", 0)
                .append("replies", new ArrayList<>());

        // Insert post document into posts collection
        postsCollection.insertOne(postDoc);

        // Update user's post information
        usersCollection.updateOne(
                new Document("_id", userId),
                new Document("$inc", new Document("numPosts", 1))
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
                .append("numLikes", 0)
                .append("likes", new ArrayList<>())
                .append("numReposts", 0)
                .append("reposts", new ArrayList<>())
                .append("numQuotes", 0)
                .append("quotes", new ArrayList<>())
                .append("numReplies", 0)
                .append("numDirectReplies", 0)
                .append("replies", new ArrayList<>());

        // Insert quote post document into posts collection
        postsCollection.insertOne(quotePostDoc);

        // Update quoted post with the new quote
        postsCollection.updateOne(
                new Document("_id", quotedPostId),
                new Document("$inc", new Document("numQuotes", 1))
                        .append("$push", new Document("quotes", quotePostDoc.getObjectId("_id")))
        );

        // Update user's post information
        usersCollection.updateOne(
                new Document("_id", userId),
                new Document("$inc", new Document("numPosts", 1))
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
                .append("numLikes", 0)
                .append("likes", new ArrayList<>())
                .append("numReposts", 0)
                .append("reposts", new ArrayList<>())
                .append("numQuotes", 0)
                .append("quotes", new ArrayList<>())
                .append("numReplies", 0)
                .append("numDirectReplies", 0)
                .append("replies", new ArrayList<>());

        // Insert reply post document into posts collection
        postsCollection.insertOne(replyPostDoc);

        // Update reply-to post with the new reply
        postsCollection.updateOne(
                new Document("_id", replyToPostId),
                new Document("$inc", new Document("numReplies", 1))
                        .append("$push", new Document("replies", replyPostDoc.getObjectId("_id")))
        );

        // Update user's post information
        usersCollection.updateOne(
                new Document("_id", userId),
                new Document("$inc", new Document("numPosts", 1))
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
                    .append("$inc", new Document("numPosts", -1))
        );

        // Remove the post from the likes of any users who liked it
        List<ObjectId> likes = postDoc.getList("likes", ObjectId.class, new ArrayList<>());
        for (ObjectId likerId : likes) {
            usersCollection.updateOne(
                new Document("_id", likerId),
                new Document("$pull", new Document("likes", postId))
                        .append("$inc", new Document("numLikes", -1))
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
            new Document("$inc", new Document("numPosts", -1))
                    .append("$pull", new Document("posts", postId))
        );
    }

    /**
     * Deletes a post. Depending on the nuclearOption parameter, it either performs a nuclear delete (removing all references to the post)
     * or a soft delete (marking the post as deleted).
     *
     * @param postId Id of the post to be deleted.
     * @param nuclearOption If true, performs a nuclear delete; if false, performs a soft delete.
     * @throws Exception if an error occurs during the delete operation.
     */
    public void deletePost(ObjectId postId, boolean nuclearOption) throws Exception {
        if (nuclearOption) {
            nuclearDeletePost(postId);
        } else {
            softDeletePost(postId);
        }
    }

    /**
     * Deletes a post using the default delete option (soft delete).
     *
     * @param postId Id of the post to be deleted.
     * @throws Exception if an error occurs during the delete operation.
     */
    public void deletePost(ObjectId postId) throws Exception {
        softDeletePost(postId);
    }


    /**
     * Creates a new repost of an original post by a user.
     * @param userId ObjectId of the user creating the repost
     * @param ogPostId ObjectId of the original post being reposted
     * @param timestamp Timestamp of when the repost was created
     * @throws Exception If user does not exist, original post does not exist, or there is an error creating the repost
     */
    public void createRepost(ObjectId userId, ObjectId ogPostId, int timestamp) throws Exception {
        // Check if the user exists
        Document userDoc = usersCollection.find(new Document("_id", userId)).first();
        if (userDoc == null) {
            throw new Exception("User with ID " + userId + " does not exist.");
        }

        // Check if the original post exists
        Document originalPostDoc = postsCollection.find(new Document("_id", ogPostId)).first();
        if (originalPostDoc == null) {
            throw new Exception("Original post with ID " + ogPostId + " does not exist.");
        }

        // Create repost document
        Document repostDoc = new Document()
                .append("_id", new ObjectId())
                .append("user", userId)
                .append("ogPost", ogPostId)
                .append("timestamp", timestamp);

        // Insert repost document into reposts collection
        repostsCollection.insertOne(repostDoc);

        // Update original post with the new repost
        postsCollection.updateOne(
                new Document("_id", ogPostId),
                new Document("$inc", new Document("numReposts", 1))
                        .append("$push", new Document("reposts", repostDoc.getObjectId("_id")))
        );
    }

    /**
     * Deletes a repost by a user.
     *
     * @param repostId ObjectId of the repost to be deleted
     * @throws Exception If repost does not exist, user does not exist, or there is an error deleting the repost
     */
    public void deleteRepost(ObjectId repostId) throws Exception {
        // Find the repost in the database
        Document repostDoc = repostsCollection.find(new Document("_id", repostId)).first();
        if (repostDoc == null) {
            throw new Exception("Repost with ID " + repostId + " not found.");
        }

        // Get the user and original post from the repost document
        ObjectId userId = repostDoc.getObjectId("user");
        ObjectId ogPostId = repostDoc.getObjectId("ogPost");

        // Check if the user exists
        Document userDoc = usersCollection.find(new Document("_id", userId)).first();
        if (userDoc == null) {
            throw new Exception("User with ID " + userId + " does not exist.");
        }

        // Check if the original post exists
        Document originalPostDoc = postsCollection.find(new Document("_id", ogPostId)).first();
        if (originalPostDoc == null) {
            throw new Exception("Original post with ID " + ogPostId + " does not exist.");
        }

        // Delete the repost document from the reposts collection
        repostsCollection.deleteOne(new Document("_id", repostId));

        // Update the original post to remove the repost
        postsCollection.updateOne(
                new Document("_id", ogPostId),
                new Document("$inc", new Document("numReposts", -1))
                        .append("$pull", new Document("reposts", repostId))
        );

        // Update the user's repost list
        usersCollection.updateOne(
                new Document("_id", userId),
                new Document("$pull", new Document("reposts", repostId))
        );
    }


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
        int numLikes = postDoc.getInteger("numLikes", 0);
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
                new Document("$set", new Document("numLikes", numLikes).append("likes", likes)));

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
            new Document("$set", new Document("numLikes", numLikes).append("likes", likes))
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
            .append("$inc", new Document("numFollowers", 1))
        );

        // Update the follower user's followed list
        List<ObjectId> followed = followerDoc.getList("followed", ObjectId.class, new ArrayList<>());
        followed.add(followedId);
        usersCollection.updateOne(
            new Document("_id", followerId),
            new Document("$set", new Document("followed", followed))
            .append("$inc", new Document("numFollowed", 1))
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
            .append("$inc", new Document("numFollowers", -1))
        );

        // Update the follower user's followed list
        List<ObjectId> followed = followerDoc.getList("followed", ObjectId.class, new ArrayList<>());
        followed.remove(followedId);
        usersCollection.updateOne(
            new Document("_id", followerId),
            new Document("$set", new Document("followed", followed))
            .append("$inc", new Document("numFollowed", -1))
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
            .append("$inc", new Document("numUsers", 1))
        );

        // Update the user's usergroups list
        List<ObjectId> userGroups = userDoc.getList("usergroups", ObjectId.class, new ArrayList<>());
        userGroups.add(groupId);
        usersCollection.updateOne(
            new Document("_id", userId),
            new Document("$set", new Document("usergroups", userGroups))
            .append("$inc", new Document("numUsergroups", 1))
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
            .append("$inc", new Document("numUsers", -1))
        );

        // Update the user's usergroups list
        List<ObjectId> userGroups = userDoc.getList("usergroups", ObjectId.class, new ArrayList<>());
        userGroups.remove(groupId);
        usersCollection.updateOne(
            new Document("_id", userId),
            new Document("$set", new Document("usergroups", userGroups))
            .append("$inc", new Document("numUsergroups", -1))
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
                .append("numUsers", 0)
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
                .append("$inc", new Document("numUsergroups", -1))
            );
        }

        // Delete the group document from the groups collection
        groupsCollection.deleteOne(new Document("_id", groupId));
    }
//#endregion

// All Retrieval Functions
//#region
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

    // Assuming you have a method to retrieve a user Document by userId
    public Document getUserById(ObjectId userId) {
        // Example of how you might retrieve a user document from your collection
        return usersCollection.find(new Document("_id", userId)).first();
    }

    // Getters for User Document fields

    public String getUserUsername(ObjectId userId) {
        Document userDoc = getUserById(userId);
        return userDoc.getString("username");
    }

    public String getUserHashword(ObjectId userId) {
        Document userDoc = getUserById(userId);
        return userDoc.getString("hashword");
    }

    public int getUserNumLogins(ObjectId userId) {
        Document userDoc = getUserById(userId);
        return userDoc.getInteger("numLogins");
    }

    public List<ObjectId> getUserLogins(ObjectId userId) {
        Document userDoc = getUserById(userId);
        return (List<ObjectId>) userDoc.get("logins");
    }

    public ObjectId getUserRecentLogin(ObjectId userId) {
        Document userDoc = getUserById(userId);
        return (ObjectId) userDoc.get("recentLogin");
    }

    public ObjectId getUserRegistration(ObjectId userId) {
        Document userDoc = getUserById(userId);
        return (ObjectId) userDoc.get("registration");
    }

    public int getUserNumPostsReposts(ObjectId userId) {
        Document userDoc = getUserById(userId);
        return userDoc.getInteger("numPostsReposts");
    }

    public int getUserNumPosts(ObjectId userId) {
        Document userDoc = getUserById(userId);
        return userDoc.getInteger("numPosts");
    }

    public List<ObjectId> getUserPosts(ObjectId userId) {
        Document userDoc = getUserById(userId);
        return (List<ObjectId>) userDoc.get("posts");
    }

    public int getUserNumReposts(ObjectId userId) {
        Document userDoc = getUserById(userId);
        return userDoc.getInteger("numReposts");
    }

    public List<ObjectId> getUserReposts(ObjectId userId) {
        Document userDoc = getUserById(userId);
        return (List<ObjectId>) userDoc.get("reposts");
    }

    public int getUserNumFollowers(ObjectId userId) {
        Document userDoc = getUserById(userId);
        return userDoc.getInteger("numFollowers");
    }

    public List<ObjectId> getUserFollowers(ObjectId userId) {
        Document userDoc = getUserById(userId);
        return (List<ObjectId>) userDoc.get("followers");
    }

    public int getUserNumFollowed(ObjectId userId) {
        Document userDoc = getUserById(userId);
        return userDoc.getInteger("numFollowed");
    }

    public List<ObjectId> getUserFollows(ObjectId userId) {
        Document userDoc = getUserById(userId);
        return (List<ObjectId>) userDoc.get("followed");
    }

    public int getUserNumUsergroups(ObjectId userId) {
        Document userDoc = getUserById(userId);
        return userDoc.getInteger("numUsergroups");
    }

    public List<ObjectId> getUserUsergroups(ObjectId userId) {
        Document userDoc = getUserById(userId);
        return (List<ObjectId>) userDoc.get("usergroups");
    }

    public int getUserNumLikes(ObjectId userId) {
        Document userDoc = getUserById(userId);
        return userDoc.getInteger("numLikes");
    }

    public List<ObjectId> getUserLikes(ObjectId userId) {
        Document userDoc = getUserById(userId);
        return (List<ObjectId>) userDoc.get("likes");
    }

    // Getting a post by postId
    public Document getPostById(ObjectId postId) {
        return postsCollection.find(new Document("_id", postId)).first();
    }

    // Getting the user of a post by postId
    public ObjectId getPostUser(ObjectId postId) {
        Document postDoc = getPostById(postId);
        return (ObjectId) postDoc.get("user");
    }

    // Getting the text of a post by postId
    public String getPostText(ObjectId postId) {
        Document postDoc = getPostById(postId);
        return postDoc.getString("text");
    }

    // Getting the deleted status of a post by postId
    public Boolean getPostDeleted(ObjectId postId) {
        Document postDoc = getPostById(postId);
        return postDoc.getBoolean("deleted");
    }

    // Getting the replyto of a post by postId
    public ObjectId getPostReplyTo(ObjectId postId) {
        Document postDoc = getPostById(postId);
        return (ObjectId) postDoc.get("replyto");
    }

    // Getting the quoted post by postId
    public ObjectId getPostQuoted(ObjectId postId) {
        Document postDoc = getPostById(postId);
        return (ObjectId) postDoc.get("quoted");
    }

    // Getting the timestamp of a post by postId
    public Integer getPostTimestamp(ObjectId postId) {
        Document postDoc = getPostById(postId);
        return postDoc.getInteger("timestamp");
    }

    // Getting the number of likes of a post by postId
    public Integer getPostNumLikes(ObjectId postId) {
        Document postDoc = getPostById(postId);
        return postDoc.getInteger("numLikes");
    }

    // Getting the likes of a post by postId
    public List<ObjectId> getPostLikes(ObjectId postId) {
        Document postDoc = getPostById(postId);
        return (List<ObjectId>) postDoc.get("likes");
    }

    // Getting the number of reposts of a post by postId
    public Integer getPostNumReposts(ObjectId postId) {
        Document postDoc = getPostById(postId);
        return postDoc.getInteger("numReposts");
    }

    // Getting the reposts of a post by postId
    public List<ObjectId> getPostReposts(ObjectId postId) {
        Document postDoc = getPostById(postId);
        return (List<ObjectId>) postDoc.get("reposts");
    }

    // Getting the number of quotes of a post by postId
    public Integer getPostNumQuotes(ObjectId postId) {
        Document postDoc = getPostById(postId);
        return postDoc.getInteger("numQuotes");
    }

    // Getting the quotes of a post by postId
    public ObjectId getPostQuotes(ObjectId postId) {
        Document postDoc = getPostById(postId);
        return (ObjectId) postDoc.get("quotes");
    }

    // Getting the number of replies of a post by postId
    public Integer getPostNumReplies(ObjectId postId) {
        Document postDoc = getPostById(postId);
        return postDoc.getInteger("numReplies");
    }

    // Getting the number of direct replies of a post by postId
    public Integer getPostNumDirectReplies(ObjectId postId) {
        Document postDoc = getPostById(postId);
        return postDoc.getInteger("numDirectReplies");
    }

    // Getting the replies of a post by postId
    public List<ObjectId> getPostReplies(ObjectId postId) {
        Document postDoc = getPostById(postId);
        return (List<ObjectId>) postDoc.get("replies");
    }


    // Getting a repost by repostId
    public Document getRepostById(ObjectId repostId) {
        return repostsCollection.find(new Document("_id", repostId)).first();
    }

    // Getting the user of a repost by repostId
    public ObjectId getRepostUser(ObjectId repostId) {
        Document repostDoc = getRepostById(repostId);
        return (ObjectId) repostDoc.get("user");
    }

    // Getting the original post of a repost by repostId
    public ObjectId getRepostOgPost(ObjectId repostId) {
        Document repostDoc = getRepostById(repostId);
        return (ObjectId) repostDoc.get("ogPost");
    }

    // Getting the timestamp of a repost by repostId
    public Integer getRepostTimestamp(ObjectId repostId) {
        Document repostDoc = getRepostById(repostId);
        return repostDoc.getInteger("timestamp");
    }


    // Getting a login by loginId
    public Document getLoginById(ObjectId loginId) {
        return loginsCollection.find(new Document("_id", loginId)).first();
    }

    // Getting the user of a login by loginId
    public ObjectId getLoginUser(ObjectId loginId) {
        Document loginDoc = getLoginById(loginId);
        return (ObjectId) loginDoc.get("user");
    }

    // Getting the login timestamp of a login by loginId
    public Integer getLoginLoginTimestamp(ObjectId loginId) {
        Document loginDoc = getLoginById(loginId);
        return loginDoc.getInteger("loginTimestamp");
    }

    // Getting the logout timestamp of a login by loginId
    public Integer getLoginLogoutTimestamp(ObjectId loginId) {
        Document loginDoc = getLoginById(loginId);
        return loginDoc.getInteger("logoutTimestamp");
    }


    // Getting a registration by registrationId
    public Document getRegistrationById(ObjectId registrationId) {
        return registrationsCollection.find(new Document("_id", registrationId)).first();
    }

    // Getting the user of a registration by registrationId
    public ObjectId getRegistrationUser(ObjectId registrationId) {
        Document registrationDoc = getRegistrationById(registrationId);
        return (ObjectId) registrationDoc.get("user");
    }

    // Getting the timestamp of a registration by registrationId
    public Integer getRegistrationTimestamp(ObjectId registrationId) {
        Document registrationDoc = getRegistrationById(registrationId);
        return registrationDoc.getInteger("timestamp");
    }


    // Getting a group by groupId
    public Document getGroupById(ObjectId groupId) {
        return groupsCollection.find(new Document("_id", groupId)).first();
    }

    // Getting the name of a group by groupId
    public String getGroupName(ObjectId groupId) {
        Document groupDoc = getGroupById(groupId);
        return groupDoc.getString("name");
    }

    // Getting the number of users in a group by groupId
    public Integer getGroupNumUsers(ObjectId groupId) {
        Document groupDoc = getGroupById(groupId);
        return groupDoc.getInteger("numUsers");
    }

    // Getting the list of users in a group by groupId
    public List<ObjectId> getGroupUsers(ObjectId groupId) {
        Document groupDoc = getGroupById(groupId);
        return (List<ObjectId>) groupDoc.get("users");
    }
//#endregion

}
