package com.mxy.objects;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mxy.admin.Database;

public class Repost {
    private ObjectId repostId;
    private Database database;

    public Repost(ObjectId repostId, Database database) {
        this.repostId = repostId;
        this.database = database;
    }

    // Getting the user of a repost by repostId
    public ObjectId getRepostUser() {
        Document repostDoc = getRepostById(repostId);
        return (ObjectId) repostDoc.get("user");
    }

    // Getting the original post of a repost by repostId
    public ObjectId getRepostOgPost() {
        Document repostDoc = getRepostById(repostId);
        return (ObjectId) repostDoc.get("ogPost");
    }

    // Getting the timestamp of a repost by repostId
    public Integer getRepostTimestamp() {
        Document repostDoc = getRepostById(repostId);
        return repostDoc.getInteger("timestamp");
    }

    // Private method to retrieve repost document from the database
    private Document getRepostById(ObjectId repostId) {
        // Assuming implementation of database retrieval
        return database.getRepostById(repostId); // Implement according to your database retrieval logic
    }
}
