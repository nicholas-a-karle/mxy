package com.mxy.objects.parent;

import org.bson.types.ObjectId;

public abstract class User {
    protected ObjectId _id;
    protected String username;

    // Constructor
    public User(ObjectId _id, String username) {
        this._id = _id;
        this.username = username;
    }

    // Getters
    public ObjectId get_id() {
        return _id;
    }

    public String getUsername() {
        return username;
    }
}
