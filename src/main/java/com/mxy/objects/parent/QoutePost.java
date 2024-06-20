package com.mxy.objects.parent;

import org.bson.types.ObjectId;

public abstract class QoutePost {
    protected ObjectId _id;
    protected String username;

    // Constructor
    public QoutePost(ObjectId _id) {
        this._id = _id;
    }

    // Getters
    public ObjectId get_id() {
        return _id;
    }
}
