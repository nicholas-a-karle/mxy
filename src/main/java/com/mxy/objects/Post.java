package com.mxy.objects;

import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mxy.admin.Database;

public class Post {
    private ObjectId postId;
    private ObjectId user;
    private String text;
    private Boolean deleted;
    private ObjectId replyTo;
    private ObjectId quoted;
    private Long timestamp;
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

    public int memSize() {
        // TODO Not done
        System.out.println("Unimplemented method 'memSize'");
        return 10;
    }

    // Getters for fields

    public ObjectId getId() {
        return postId;
    }

    public ObjectId getUser() {
        if (user == null) user = database.getPostUser(postId);
        return user;
    }
    
    public String getText() {
        if (text == null) text = database.getPostText(postId);
        return text;
    }
    
    public Boolean isDeleted() {
        if (deleted == null) deleted = database.getPostDeleted(postId);
        return deleted;
    }
    
    public ObjectId getReplyTo() {
        if (replyTo == null) replyTo = database.getPostReplyTo(postId);
        return replyTo;
    }
    
    public ObjectId getQuoted() {
        if (quoted == null) quoted = database.getPostQuoted(postId);
        return quoted;
    }
    
    public Long getTimestamp() {
        if (timestamp == null) timestamp = database.getPostTimestamp(postId);
        return timestamp;
    }
    
    public Integer getNumLikes() {
        if (numLikes == null) numLikes = database.getPostNumLikes(postId);
        return numLikes;
    }
    
    public List<ObjectId> getLikes() {
        if (likes == null) likes = database.getPostLikes(postId);
        return likes;
    }
    
    public Integer getNumReposts() {
        if (numReposts == null) numReposts = database.getPostNumReposts(user);
        return numReposts;
    }
    
    public List<ObjectId> getReposts() {
        if (reposts == null) reposts = database.getPostReposts(postId);
        return reposts;
    }
    
    public Integer getNumQuotes() {
        if (numQuotes == null) numQuotes = database.getPostNumQuotes(postId);
        return numQuotes;
    }
    
    public ObjectId getQuotes() {
        if (quotes == null) quotes = database.getPostQuotes(postId);
        return quotes;
    }
    
    public Integer getNumReplies() {
        if (numReplies == null) numReplies = database.getPostNumReplies(postId);
        return numReplies;
    }
    
    public Integer getNumDirectReplies() {
        if (numDirectReplies == null) numDirectReplies = database.getPostNumDirectReplies(postId);
        return numDirectReplies;
    }
    
    public List<ObjectId> getReplies() {
        if (replies == null) replies = database.getPostReplies(postId);
        return replies;
    }
    

    // retrieveAll() method to populate all fields from database

    public void retrieveAll() {
        user = database.getPostUser(postId);
        text = database.getPostText(postId);
        deleted = database.getPostDeleted(postId);
        replyTo = database.getPostReplyTo(postId);
        quoted = database.getPostQuoted(postId);
        timestamp = database.getPostTimestamp(postId);
        numLikes = database.getPostNumLikes(postId);
        likes = database.getPostLikes(postId);
        numReposts = database.getPostNumReposts(postId);
        reposts = database.getPostReposts(postId);
        numQuotes = database.getPostNumQuotes(postId);
        quotes = database.getPostQuotes(postId);
        numReplies = database.getPostNumReplies(postId);
        numDirectReplies = database.getPostNumDirectReplies(postId);
        replies = database.getPostReplies(postId);
    }

    
    
}
