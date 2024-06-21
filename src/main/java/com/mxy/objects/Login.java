package com.mxy.objects;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mxy.admin.Database;

public class Login {
    private ObjectId loginId;
    private Database database;
    private ObjectId user;
    private Integer loginTimestamp;
    private Integer logoutTimestamp;

    public Login(ObjectId loginId, Database database) {
        this.loginId = loginId;
        this.database = database;
    }

    // Getting the loginId
    public ObjectId getId() {
        return loginId;
    }

    // Getting the user of a login by loginId
    public ObjectId getUser() {
        if (user == null) {
            user = database.getLoginUser(loginId);
        }
        return user;
    }

    // Getting the login timestamp of a login by loginId
    public Integer getLoginTimestamp() {
        if (loginTimestamp == null) {
            loginTimestamp = database.getLoginLoginTimestamp(loginId);
        }
        return loginTimestamp;
    }

    // Getting the logout timestamp of a login by loginId
    public Integer getLogoutTimestamp() {
        if (logoutTimestamp == null) {
            logoutTimestamp = database.getLoginLogoutTimestamp(loginId);
        }
        return logoutTimestamp;
    }

    public int memSize() {
        int mem = 2;
        //primary def should be elsewhere
        //mem += (loginId == null) ? 1 : 1 ;
        //mem += (database == null) ? 1 : 1 ;
        mem += (loginTimestamp == null) ? 1 : 2 ;
        mem += (logoutTimestamp == null) ? 1 : 2 ;
        mem += (user == null) ? 1 : 2 ;

        return mem;
    }
}
