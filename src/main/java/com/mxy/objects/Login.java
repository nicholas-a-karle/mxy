package com.mxy.objects;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mxy.admin.Database;

public class Login {
    private ObjectId loginId;
    private Database database;

    public Login(ObjectId loginId, Database database) {
        this.loginId = loginId;
        this.database = database;
    }

    // Getting the user of a login by loginId
    public ObjectId getLoginUser() {
        Document loginDoc = getLoginById(loginId);
        return (ObjectId) loginDoc.get("user");
    }

    // Getting the login timestamp of a login by loginId
    public Integer getLoginTimestamp() {
        Document loginDoc = getLoginById(loginId);
        return loginDoc.getInteger("loginTimestamp");
    }

    // Getting the logout timestamp of a login by loginId
    public Integer getLogoutTimestamp() {
        Document loginDoc = getLoginById(loginId);
        return loginDoc.getInteger("logoutTimestamp");
    }

    // Private method to retrieve login document from the database
    private Document getLoginById(ObjectId loginId) {
        // Assuming implementation of database retrieval
        return database.getLoginById(loginId); // Implement according to your database retrieval logic
    }
}
