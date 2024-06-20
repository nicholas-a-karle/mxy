package com.mxy.objects;

import org.bson.Document;
import org.bson.types.ObjectId;
import com.mxy.Database;

public class Registration {
    private ObjectId registrationId;
    private Database database;

    public Registration(ObjectId registrationId, Database database) {
        this.registrationId = registrationId;
        this.database = database;
    }

    // Getting the user of a registration by registrationId
    public ObjectId getRegistrationUser() {
        Document registrationDoc = getRegistrationById(registrationId);
        return (ObjectId) registrationDoc.get("user");
    }

    // Getting the timestamp of a registration by registrationId
    public Integer getRegistrationTimestamp() {
        Document registrationDoc = getRegistrationById(registrationId);
        return registrationDoc.getInteger("timestamp");
    }

    // Private method to retrieve registration document from the database
    private Document getRegistrationById(ObjectId registrationId) {
        // Assuming implementation of database retrieval
        return database.getRegistrationById(registrationId); // Implement according to your database retrieval logic
    }
}
