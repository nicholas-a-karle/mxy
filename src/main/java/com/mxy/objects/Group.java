package com.mxy.objects;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mxy.admin.Database;

import java.util.List;

public class Group {
    private ObjectId groupId;
    private Database database;

    public Group(ObjectId groupId, Database database) {
        this.groupId = groupId;
        this.database = database;
    }

    // Getting the name of a group by groupId
    public String getGroupName() {
        Document groupDoc = getGroupById(groupId);
        return groupDoc.getString("name");
    }

    // Getting the number of users in a group by groupId
    public Integer getGroupNumUsers() {
        Document groupDoc = getGroupById(groupId);
        return groupDoc.getInteger("numUsers");
    }

    // Getting the list of users in a group by groupId
    public List<ObjectId> getGroupUsers() {
        Document groupDoc = getGroupById(groupId);
        return (List<ObjectId>) groupDoc.get("users");
    }

    // Private method to retrieve group document from the database
    private Document getGroupById(ObjectId groupId) {
        // Assuming implementation of database retrieval
        return database.getGroupById(groupId); // Implement according to your database retrieval logic
    }
}
