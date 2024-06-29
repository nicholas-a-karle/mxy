package com.mxy.objects;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mxy.admin.Database;

import java.util.List;

public class Group {
    private ObjectId groupId;
    private Database database;
    private String name;
    private Integer numUsers;
    private List<ObjectId> users;
    private Long timestamp;

    public Group(ObjectId groupId, Database database) {
        this.groupId = groupId;
        this.database = database;
    }

    public int memSize() {
        int mem = 2;

        //mem += (groupId == null) ? 1 : 1 ;
        //mem += (database == null) ? 1 : 1 ;
        mem += (name == null) ? 1 : name.length() ;
        mem += (numUsers == null) ? 1 : 2 ;
        mem += (users == null) ? 1 : users.size() + 1 ;

        return mem;
    }

    public ObjectId getId() { 
        return groupId; 
    }

    // Getting the name of a group by groupId
    public String getName() {
        if (name == null) {
            name = database.getGroupName(groupId);
        }
        return name;
    }

    // Getting the number of users in a group by groupId
    public Integer getNumUsers() {
        if (numUsers == null) {
            numUsers = database.getGroupNumUsers(groupId);
        }
        return numUsers;
    }

    // Getting the list of users in a group by groupId
    public List<ObjectId> getUsers() {
        if (users == null) {
            users = database.getGroupUsers(groupId);
        }
        return users;
    }

    public void retrieveAll() {
        name = database.getGroupName(groupId);
        numUsers = database.getGroupNumUsers(groupId);
        users = database.getGroupUsers(groupId);
    }

    public Long getTimestamp() {
        if (timestamp == null) {
            timestamp = database.getGroupTimestamp(groupId);
        }
        return timestamp;
    }

}
