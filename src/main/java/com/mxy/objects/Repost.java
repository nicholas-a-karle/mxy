package com.mxy.objects;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mxy.admin.Database;

public class Repost {
    private ObjectId repostId;
    private Database database;
    private Integer timestamp;
    private ObjectId user;
    private ObjectId ogPost;

    public Repost(ObjectId repostId, Database database) {
        this.repostId = repostId;
        this.database = database;
    }
    
    public int memSize() {
        int mem = 2;
        
        mem += (timestamp == null) ? 1 : 2;
        mem += (user == null) ? 1 : 2;
        mem += (ogPost == null) ? 1 : 2;

        return mem;
    }

    public ObjectId getId() {
        return repostId;
    }

    // Getting the user of a repost by repostId
    public ObjectId getReposter() {
        if (user == null) user = database.getRepostUser(repostId);
        return user;
    }

    // Getting the original post of a repost by repostId
    public ObjectId getOgPost() {
        if (ogPost == null) ogPost = database.getRepostOgPost(repostId);
        return ogPost;
    }

    // Getting the timestamp of a repost by repostId
    public Integer getTimestamp() {
        if (timestamp == null) timestamp = database.getRepostTimestamp(repostId);
        return timestamp;
    }

    public void retrieveAll() {
        user = database.getRepostUser(repostId);
        ogPost = database.getRepostOgPost(repostId);
        timestamp = database.getRepostTimestamp(repostId);
    }
}
