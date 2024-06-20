package com.mxy.objects;

import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;
import com.mxy.Database;

public class Post {
    private ObjectId postId;
    private ObjectId user;
    private String text;
    private Boolean deleted;
    private ObjectId replyTo;
    private ObjectId quoted;
    private Integer timestamp;
    private Integer numLikes;
    private List<ObjectId> likes;
    private Integer numReposts;
    private List<ObjectId> reposts;
    private Integer numQuotes;
    private ObjectId quotes;
    private Integer numReplies;
    private Integer numDirectReplies;
    private List<ObjectId> replies;
    private Database database;

    public Post(ObjectId postId, Database database) {
        this.postId = postId;
        this.database = database;
    }

    // Getters for fields

    public ObjectId getPostId() {
        return postId;
    }

    public ObjectId getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public ObjectId getReplyTo() {
        return replyTo;
    }

    public ObjectId getQuoted() {
        return quoted;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public Integer getNumLikes() {
        return numLikes;
    }

    public List<ObjectId> getLikes() {
        return likes;
    }

    public Integer getNumReposts() {
        return numReposts;
    }

    public List<ObjectId> getReposts() {
        return reposts;
    }

    public Integer getNumQuotes() {
        return numQuotes;
    }

    public ObjectId getQuotes() {
        return quotes;
    }

    public Integer getNumReplies() {
        return numReplies;
    }

    public Integer getNumDirectReplies() {
        return numDirectReplies;
    }

    public List<ObjectId> getReplies() {
        return replies;
    }

    // retrieveAll() method to populate all fields from database

    public void retrieveAll() {
        Document postDoc = getPostById(postId);

        // Populate fields from document
        user = (ObjectId) postDoc.get("user");
        text = postDoc.getString("text");
        deleted = postDoc.getBoolean("deleted");
        replyTo = (ObjectId) postDoc.get("replyto");
        quoted = (ObjectId) postDoc.get("quoted");
        timestamp = postDoc.getInteger("timestamp");
        numLikes = postDoc.getInteger("numLikes");
        likes = (List<ObjectId>) postDoc.get("likes");
        numReposts = postDoc.getInteger("numReposts");
        reposts = (List<ObjectId>) postDoc.get("reposts");
        numQuotes = postDoc.getInteger("numQuotes");
        quotes = (ObjectId) postDoc.get("quotes");
        numReplies = postDoc.getInteger("numReplies");
        numDirectReplies = postDoc.getInteger("numDirectReplies");
        replies = (List<ObjectId>) postDoc.get("replies");
    }

    // Helper method to retrieve post document from database
    private Document getPostById(ObjectId postId) {
        return database.getPostById(postId);
    }
}
