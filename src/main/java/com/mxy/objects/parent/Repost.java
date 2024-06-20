package com.mxy.objects.parent;

import org.bson.types.ObjectId;

public abstract class Repost {
    protected ObjectId _id;
    protected String username;

    // Constructor
    public Repost(ObjectId _id) {
        this._id = _id;
    }

    // Getters
    public ObjectId get_id() {
        return _id;
    }
}
