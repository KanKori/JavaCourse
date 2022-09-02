package com.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConfig {
    private static final String URL = "localhost";
    public static final int PORT = 5432;
    public static final String DATABASE = "JavaCourse";
    static MongoClient mongoClient;
    static MongoDatabase database;

    public static MongoDatabase getMongoDatabase() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(URL);
            database = mongoClient.getDatabase(DATABASE);
            database.drop();
        }

        return database;
    }
}
