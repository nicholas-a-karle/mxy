package com.mxy.objects;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mxy.admin.Database;

public class Registration {
    private ObjectId registrationId;
    private Database database;
    private ObjectId user;
    private Long timestamp;

    public Registration(ObjectId registrationId, Database database) {
        this.registrationId = registrationId;
        this.database = database;
    }

    public int memSize() {
        int mem = 2;

        mem += (user == null) ? 1 : 2;
        mem += (timestamp == null) ? 1 : 2;

        return mem;
    }    

    public ObjectId getId() {
        return registrationId;
    }

    public ObjectId getUser() {
        if (user == null) user = database.getRegistrationUser(registrationId);
        return user;
    }

    public Long getTimestamp() {
        if (timestamp == null) timestamp = database.getRegistrationTimestamp(registrationId);
        return timestamp;
    }

    public void retrieveAll() {
        timestamp = database.getRegistrationTimestamp(registrationId);
        user = database.getRegistrationUser(registrationId);
    }

}
