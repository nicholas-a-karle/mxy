package com.mxy;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.*;
import com.mongodb.client.*;

public class Database {
    /**
     * Redesigned Version from ivote
     * 
     * All are now represented as lists.
     * 
     * this.collection -> focused collection
     * 
     * Planned: Memoization to speed up data grabbing
    */

    private static final String LOCALHOST = "mongodb://localhost:27017";

    // MongoDB Client
    protected MongoClient client;

    // Database
    protected MongoDatabase database;

    // Collections
    protected List<MongoCollection<Document>> collections;
    protected int coi;

    // constructor
    public Database(String uri, String database) {

    }

    
}
